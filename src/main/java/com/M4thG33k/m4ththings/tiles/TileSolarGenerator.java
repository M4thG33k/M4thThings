package com.M4thG33k.m4ththings.tiles;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import com.M4thG33k.m4ththings.init.ModFluids;
import com.M4thG33k.m4ththings.utility.Location;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 7/6/2015.
 */
public class TileSolarGenerator extends TileEntity implements IFluidHandler,IEnergyProvider {

    protected EnergyStorage battery;
    protected FluidTank tank;

    protected int generationTimer;
    protected Location[] locations;
    protected int[] directions;

    public TileSolarGenerator()
    {
        battery = new EnergyStorage(30000,0,120);
        tank = new FluidTank(4000);
        generationTimer = 0;
    }

    /* IEnergyProvider */

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        int transferred = battery.extractEnergy(maxExtract,false);
        if (transferred>0)
        {
            battery.modifyEnergyStored(-transferred);
            prepareSync();
        }
        return transferred;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return battery.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return battery.getMaxEnergyStored();
    }

    /* End IEnergyProvider */

    /* IEnergyConnection */

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    /* End IEnergyConnection */


    /*IFluidHandler*/

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource.getFluid()!= ModFluids.fluidSolarWater)
        {
            return 0;
        }

        int transferred = tank.fill(resource,doFill);
        if (transferred>0 && doFill)
        {
            prepareSync();
        }
        return transferred;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (fluid==ModFluids.fluidSolarWater);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] infos = new FluidTankInfo[1];
        infos[0] = tank.getInfo();
        return  infos;
    }

    /*End IFluidHandler*/

    public void prepareSync()
    {
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        battery.readFromNBT(tagCompound);
        tank.readFromNBT(tagCompound);

        if (tagCompound.hasKey("GenerationTimer"))
        {
            generationTimer = tagCompound.getInteger("GenerationTimer");
        }
        else
        {
            generationTimer = 0;
        }

        locations = MiscHelper.readLocationArrayFromNBT("EnergyLocations",tagCompound);

        if (tagCompound.hasKey("EnergyDirections"))
        {
            directions = tagCompound.getIntArray("EnergyDirections");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        battery.writeToNBT(tagCompound);
        tank.writeToNBT(tagCompound);

        tagCompound.setInteger("GenerationTimer",generationTimer);

        MiscHelper.writeLocationArrayToNBT("EnergyLocations",locations,tagCompound);

        if (directions!=null)
        {
            tagCompound.setIntArray("EnergyDirections",directions);
        }
    }

    public int getFluidAmount()
    {
        return tank.getFluidAmount();
    }

    public int getStoredEnergy()
    {
        return battery.getEnergyStored();
    }

    public int getMaxEnergy()
    {
        return battery.getMaxEnergyStored();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord,yCoord,zCoord,1,tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
        prepareSync();
    }

    public void attemptRFCreation()
    {
        if (generationTimer<0)
        {
            generationTimer = 0;
        }

        if (generationTimer==0)
        {
            return;
        }

        battery.modifyEnergyStored(30);
        generationTimer--;
    }

    public boolean canIMakeRF()
    {
        return ((generationTimer==0) && battery.getEnergyStored()+300<=battery.getMaxEnergyStored() && tank.getFluidAmount()>=100);
    }

    @Override
    public void updateEntity() {
        if (locations==null || directions==null)
        {
            grabLocations();
        }

        if (canIMakeRF())
        {
            generationTimer = 10;
            tank.drain(100,true);
        }

        attemptRFCreation();
        attemptEnergyPush();
        prepareSync();
    }

    public void grabLocations()
    {
        locations = MiscHelper.getAdjacentLocations(xCoord,yCoord,zCoord,0);
        directions = MiscHelper.getAdjacentDirections(0);
    }

    public void attemptEnergyPush()
    {
        TileEntity tileEntity;
        int transferred;
        for (int i=0;i<locations.length;i++)
        {
            if (battery.getEnergyStored()==0)
            {
                return;
            }

            tileEntity = worldObj.getTileEntity(locations[i].getX(),locations[i].getY(),locations[i].getZ());

            if (tileEntity!=null && tileEntity instanceof IEnergyReceiver)
            {
                transferred = ((IEnergyReceiver)tileEntity).receiveEnergy(MiscHelper.getOppositeDirectionDirection(directions[i]),Math.min(battery.getMaxExtract(),battery.getEnergyStored()),true);

                if (transferred>0)
                {
                    battery.modifyEnergyStored(-transferred);
                    prepareSync();
                }
            }

        }
    }
}
