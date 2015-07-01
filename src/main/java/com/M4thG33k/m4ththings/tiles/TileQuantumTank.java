package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.packets.ModPackets;
import com.M4thG33k.m4ththings.packets.PacketFilling;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class TileQuantumTank extends TileEntity implements IFluidTank, IFluidHandler {

    protected int timer;
    protected FluidStack fluid;
    protected int cap = Configurations.QT_CAP;
    protected int verticalDistance;
    protected int orientation;

    public TileQuantumTank()
    {
        timer = 0;
        fluid = null;
        verticalDistance = 1;
    }

    public void advanceTimer()
    {
        timer += 1;
        if (timer>=360)
        {
            timer = 0;
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        advanceTimer();


        attemptDrain(1);

    }

    public void setFluid(FluidStack fluidStack)
    {
        if (fluidStack!=null && fluidStack.amount!=0)
        {
            fluid = fluidStack;
            if (fluid.amount>cap)
            {
                fluid.amount = cap;
            }
        }
    }

    public void setEmpty()
    {
        fluid = null;
    }

    //attempts to drain from this tank into a tank 'y' blocks below
    public void attemptDrain(int y)
    {
        if (fluid!=null && getFluidAmount()>0)
        {
            TileEntity tile = worldObj.getTileEntity(xCoord,yCoord-y,zCoord);
            if (tile!=null && tile instanceof TileQuantumTank && ((TileQuantumTank)tile).getOrientation()==0) {
                TileQuantumTank tank = (TileQuantumTank) tile;

                if (tank.getFluidAmount() < tank.getCapacity()) {
                    int transferred = tank.fill(fluid, true);
                    if (transferred == fluid.amount) {
                        fluid = null;
                    } else {
                        fluid.amount -= transferred;
                        prepareSync();
                    }
                }
            }
//            else if (tile!=null && tile instanceof IFluidHandler)
//            {
//                IFluidHandler tank = (IFluidHandler)tile;
//
//                if (tank.fill(ForgeDirection.UP,fluid,false) > 0)
//                {
////                    LogHelper.info("Able to transfer " + tank.fill(ForgeDirection.UP,fluid,false) + "mB from (" + xCoord + ", " + yCoord + ", " + zCoord + ")");
//                    int transferred = tank.fill(ForgeDirection.UP,fluid,true);
////                    LogHelper.info("Actual filled: " + transferred);
//                    FluidStack drained = this.drain(ForgeDirection.DOWN,transferred,true);
////                    LogHelper.info("Actual drained: " + drained.amount);
////                    drain(ForgeDirection.DOWN,tank.fill(ForgeDirection.UP,fluid,true),true);
////                    drain(tank.fill(ForgeDirection.UP,fluid,true),true);
//                }
//            }
        }
    }

    public int getTimer()
    {
        return timer;
    }

    public void prepareSync()
    {
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        markDirty();
    }

    //IFluidTank
    @Override
    public FluidStack getFluid()
    {
        return fluid;
    }

    @Override
    public int getFluidAmount()
    {
        return fluid==null ? 0 : fluid.amount;
    }

    @Override
    public int getCapacity()
    {
        return cap;
    }

    @Override
    public FluidTankInfo getInfo()
    {
        return new FluidTankInfo(fluid,cap);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
//        LogHelper.info("Filling at " + myCoords());
        if (resource==null || resource.amount==0)
        {
            return 0;
        }

        if (fluid == null)
        {
            if (doFill)
            {
                fluid = new FluidStack(resource.getFluid(), Math.min(cap,resource.amount));
                prepareSync();
            }
            return Math.min(cap,resource.amount);
        }

        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }

        if (fluid.amount!=cap)
        {
            int toTransfer = Math.min(cap - fluid.amount, resource.amount);
            if (doFill) {
                fluid.amount += toTransfer;
                prepareSync();
            }
            return toTransfer;
        }
        else{
            TileEntity tEnt = worldObj.getTileEntity(xCoord,yCoord+verticalDistance,zCoord);
            if (tEnt!=null && tEnt instanceof TileQuantumTank)
            {
                return ((TileQuantumTank)tEnt).fill(ForgeDirection.DOWN,resource,doFill);
            }
            else{
                return 0;
            }
        }

    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
//        LogHelper.info("Draining at " + myCoords());
        if (fluid == null || fluid.amount==0)
        {
            TileEntity tEnt = worldObj.getTileEntity(xCoord,yCoord-verticalDistance,zCoord);
            if (tEnt!=null && tEnt instanceof TileQuantumTank)
            {
                return ((TileQuantumTank)tEnt).drain(maxDrain,doDrain);
            }
            return null;
        }

        FluidStack toReturn = new FluidStack(fluid.getFluid(), Math.min(fluid.amount,maxDrain));

        if (doDrain)
        {
            if (toReturn.amount==fluid.amount)
            {
                fluid = null;
            }
            else
            {
                fluid.amount -= toReturn.amount;
            }
            prepareSync();
        }
        return toReturn;
    }

    //IFluidHandler
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (isValveDirection(from))
        {
//            LogHelper.info("Hello! Client?: " + worldObj.isRemote);
            int toReturn = this.fill(resource,doFill);

            if (doFill && toReturn>0) {
                LogHelper.info("Sending QT packet to client (hopefully).");
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord,worldObj.provider.dimensionId,0,1),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,16));
                fillParticles(0,1);
            }
            return toReturn;
        }

        return 0;
    }

    @Override
    public  FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (isValveDirection(from))
        {

            return this.drain(resource.amount,doDrain);
        }

        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        if (isValveDirection(from))
        {
            return this.drain(maxDrain, doDrain);
        }

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return isValveDirection(from);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return isValveDirection(from);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        FluidTankInfo[] toReturn = new FluidTankInfo[1];
        toReturn[0] = getInfo();
        return toReturn;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        if (tagCompound.hasKey("x"))
        {
            super.readFromNBT(tagCompound);
        }

        if (tagCompound.hasKey("fluid") && tagCompound.hasKey("amount"))
        {
            fluid = new FluidStack(FluidRegistry.getFluid(tagCompound.getString("fluid")),tagCompound.getInteger("amount"));
        }
        else
        {
            fluid = null;
        }

        if (tagCompound.hasKey("timer"))
        {
            timer = tagCompound.getInteger("timer");
        }
        else
        {
            timer = 0;
        }

        if (tagCompound.hasKey("orientation"))
        {
            orientation = tagCompound.getInteger("orientation");
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);

        if (fluid!=null && fluid.amount!=0)
        {
            tagCompound.setString("fluid",fluid.getFluid().getName());
            tagCompound.setInteger("amount",fluid.amount);
        }

        tagCompound.setInteger("orientation",orientation);

        tagCompound.setInteger("timer",timer);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();

        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,1,tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    public String myCoords()
    {
        return "(" + xCoord + ", " + yCoord + ", " + zCoord + ")";
    }

    public boolean isStructureBuilt()
    {
        return true;
    }

    public double getPercentFilled()
    {
        return ((double)getFluidAmount())/((double)getCapacity());
    }

    public double getRoundedPercentFilled()
    {
        int percent = (int)(getPercentFilled()*10000);
        return ((double)percent)/100.0;
    }

    public int getOrientation()
    {
        return orientation;
    }

    public boolean isValveDirection(ForgeDirection from)
    {
        return ((orientation==0&&(from==ForgeDirection.DOWN || from==ForgeDirection.UP)) || (orientation==1&&(from==ForgeDirection.NORTH||from==ForgeDirection.SOUTH)) || (orientation==2&&(from==ForgeDirection.EAST||from==ForgeDirection.WEST)));
    }

    public void setOrientation(int orient)
    {
        this.orientation = orient;
    }

    public void fillParticles(int direction, int isFilling)
    {
        LogHelper.info("Spawning QT particles! " + direction + ":" + isFilling);
        worldObj.spawnParticle("happyVillager",xCoord+1.5,yCoord+1.5+0.0,zCoord+1.5,0.0,0.0,0.0);
    }

}


















