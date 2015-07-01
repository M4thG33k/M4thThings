package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.init.ModFluids;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class TileSolarCollector extends TileEntity implements IFluidHandler{

    protected FluidTank waterTank = new FluidTank(8000);
    protected FluidTank solarTank = new FluidTank(8000);

    public TileSolarCollector()
    {
        super();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if(worldObj.canBlockSeeTheSky(xCoord,yCoord,zCoord) && (waterTank.getFluidAmount()>0) && (solarTank.getFluidAmount() < solarTank.getCapacity()))
        {
            waterTank.drain(1,true);
            solarTank.fill(new FluidStack(ModFluids.fluidSolarWater,1),true);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);


        if (tagCompound.hasKey("TankData"))
        {
            NBTTagList tanks = tagCompound.getTagList("TankData",10);

            for (int i=0;i<tanks.tagCount();i++)
            {
                NBTTagCompound tankTag = (NBTTagCompound)tanks.getCompoundTagAt(i);
                if (tankTag.hasKey("water"))
                {
                    waterTank.readFromNBT(tankTag);
                }
                else if (tankTag.hasKey("solar"))
                {
                    solarTank.readFromNBT(tankTag);
                }
            }
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        NBTTagList tanks = new NBTTagList();
        NBTTagCompound waterTag = new NBTTagCompound();
        NBTTagCompound solarTag = new NBTTagCompound();

        waterTank.writeToNBT(waterTag);
        waterTag.setString("water","");
        solarTank.writeToNBT(solarTag);
        solarTag.setString("solar","");

        tanks.appendTag(waterTag);
        tanks.appendTag(solarTag);

        tagCompound.setTag("TankData",tanks);
    }

    /* IFluidHandler */

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (from==ForgeDirection.UP || !(resource.isFluidEqual(FluidRegistry.getFluidStack("water",1000))))
        {
            return 0;
        }
        else
        {
            prepareSync();
            int toReturn = waterTank.fill(resource,doFill);
            if (toReturn>0)
            {
                worldObj.spawnParticle("happyVillager",xCoord+1.5,yCoord+1.5+0.0,zCoord+1.5,0.0,0.0,0.0);
            }
            return toReturn;
        }
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource==null || from==ForgeDirection.UP || !(resource.isFluidEqual(new FluidStack(ModFluids.fluidSolarWater,1000))))
        {
            return null;
        }

        prepareSync();
        return solarTank.drain(resource.amount,doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from==ForgeDirection.UP)
        {
            return null;
        }

        prepareSync();
        return solarTank.drain(maxDrain,doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return ((from!=ForgeDirection.UP) && (fluid == FluidRegistry.WATER));
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return ((from!=ForgeDirection.UP) && (fluid==ModFluids.fluidSolarWater));
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {waterTank.getInfo(), solarTank.getInfo()};
    }

    public int getWater()
    {
        return waterTank.getFluidAmount();
    }

    public double getWaterPercentage()
    {
        return ((double)getWater())/((double)waterTank.getCapacity());
    }

    public double getSolarPercentage()
    {
        return ((double)solarTank.getFluidAmount())/((double)solarTank.getCapacity());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();

        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,1,tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
//        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    public void prepareSync()
    {
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        markDirty();
    }
}
