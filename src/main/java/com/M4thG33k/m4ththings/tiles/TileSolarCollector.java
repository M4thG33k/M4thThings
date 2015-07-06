package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.init.ModFluids;
import com.M4thG33k.m4ththings.interfaces.IM4thNBTSync;
import com.M4thG33k.m4ththings.packets.ModPackets;
import com.M4thG33k.m4ththings.packets.PacketNBT;
import com.M4thG33k.m4ththings.utility.Location;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
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
public class TileSolarCollector extends TileEntity implements IFluidHandler, IM4thNBTSync{

    protected FluidTank waterTank = new FluidTank(8000);
    protected FluidTank solarTank = new FluidTank(8000);
    protected Location[] locations;
    protected int numLocations;
    protected int[] directions;

    public TileSolarCollector()
    {
        super();
    }

    @Override
    public void updateEntity() {

        if (locations==null || directions==null)
        {
            grabLocations();
        }



        //attempt to transform water into Solar Water
        if(worldObj.isDaytime() && worldObj.canBlockSeeTheSky(xCoord,yCoord,zCoord) && (waterTank.getFluidAmount()>=5) && (solarTank.getFluidAmount()+5 <= solarTank.getCapacity()))
        {
            waterTank.drain(5,true);
            solarTank.fill(new FluidStack(ModFluids.fluidSolarWater,5),true);
        }

        //attempt to push solar water into adjacent tanks
        attemptPush();
    }

    public void grabLocations()
    {
        locations = new Location[5];
        directions = new int[5];
        numLocations = 0;
        directions[numLocations] = 1;
        locations[numLocations++] = new Location(xCoord,yCoord-1,zCoord);
        directions[numLocations] = 2;
        locations[numLocations++] = new Location(xCoord,yCoord,zCoord-1);
        directions[numLocations] = 3;
        locations[numLocations++] = new Location(xCoord,yCoord,zCoord+1);
        directions[numLocations] = 4;
        locations[numLocations++] = new Location(xCoord-1,yCoord,zCoord);
        directions[numLocations] = 5;
        locations[numLocations++] = new Location(xCoord+1,yCoord,zCoord);
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

    public int getSolar()
    {
        return solarTank.getFluidAmount();
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
        if (!worldObj.isRemote)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            this.writeToNBT(tagCompound);
            ModPackets.INSTANCE.sendToAllAround(new PacketNBT(xCoord,yCoord,zCoord,tagCompound),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
        }
//        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
//        markDirty();
    }

    //attempts to push any/all of its Solar Water into an adjacent TE
    public void attemptPush()
    {
        int amount = solarTank.getFluidAmount();

        if (amount==0)
        {
            return;
        }

        TileEntity tileEntity;
        FluidStack toTransfer = new FluidStack(ModFluids.fluidSolarWater,amount);
        int transferred;
        ForgeDirection opp;

        for (int i=0;i<numLocations;i++)
        {
            tileEntity = worldObj.getTileEntity(locations[i].getX(),locations[i].getY(),locations[i].getZ());
            opp = MiscHelper.getOppositeDirectionDirection(directions[i]);
            if (tileEntity!=null && (tileEntity instanceof IFluidHandler) && (((IFluidHandler)tileEntity).canFill(opp,toTransfer.getFluid())))
            {
                transferred = ((IFluidHandler)tileEntity).fill(opp,toTransfer,true);
                if (transferred>0)
                {
                    solarTank.drain(transferred, true);
                    return;
                }
            }
        }


    }

    public boolean canGiveSolarWater()
    {
        return (solarTank.getFluidAmount()>=1000);
    }

    public void removeBucketOfSolarWater()
    {
        if (solarTank.getFluidAmount()>=1000)
        {
            solarTank.drain(1000,true);
            prepareSync();
        }
    }

    public boolean canReceiveWaterBucket()
    {
        return ((waterTank.getCapacity()-waterTank.getFluidAmount())>=1000);
    }

    public void addBucketOfWater()
    {
        waterTank.fill(new FluidStack(FluidRegistry.getFluid("water"),1000),true);
        prepareSync();
    }

    public int getLightValue()
    {
        int toReturn = (int)(6*getSolarPercentage());
        prepareSync();
        return toReturn;
    }

    @Override
    public void receiveNBTPacket(NBTTagCompound tagCompound) {
        this.readFromNBT(tagCompound);
    }
}
