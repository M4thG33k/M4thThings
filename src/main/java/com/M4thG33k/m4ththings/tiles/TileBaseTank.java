package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 6/17/2015.
 */
public class TileBaseTank extends TileEntity implements IFluidTank, IFluidHandler{

    private int timer;
    private boolean good;
    private FluidStack fluid;
    private int cap = 64000;

    public TileBaseTank()
    {
        timer = 0;
        good = true;
        fluid = null;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        timer += 1;
        if (timer>=360)
        {
            timer = 0;
            good = !good;
        }

        //spawning particle test
        if (worldObj.isRemote==false && timer==10)
        {
            worldObj.spawnParticle("happyVillager",xCoord+0.5,yCoord+1.5,zCoord+0.5,0.0,1.0,0.0);
        }

    }

    public boolean getGood()
    {
        return good;
    }

    public int getTimer()
    {
        return timer;
    }


    @Override
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public int getFluidAmount() {
        if (fluid==null)
        {
            return 0;
        }
        return fluid.amount;
    }

    @Override
    public int getCapacity() {
        return cap;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(fluid,cap);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        int transferred;
        if (fluid==null ||(fluid.amount < cap && fluid.isFluidEqual(resource)))
        {
            int both = getFluidAmount()+resource.amount;
            if (both > cap)
            {
                transferred = cap-getFluidAmount();
            }
            else
            {
                transferred = resource.amount;
            }
        }
        else
        {
            transferred = 0;
        }

        if (doFill) {
            if (fluid == null) {
                fluid = new FluidStack(resource, 0);
            }
            fluid.amount += transferred;
            if (transferred>0)
            {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                markDirty();
            }
        }
        return transferred;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluid==null || fluid.amount==0)
        {
            return null;
        }

        FluidStack toReturn;
        if (fluid.amount <= maxDrain)
        {
            toReturn = fluid.copy();
            if (doDrain)
            {
                fluid = null;
            }
        }
        else
        {
            toReturn = new FluidStack(fluid,maxDrain);
            if (doDrain)
            {
                fluid.amount -= maxDrain;
            }
        }
        if (doDrain)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
        }
        return toReturn;
    }


    //IFluidHandler


    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int toReturn;
        if (from == ForgeDirection.DOWN || from==ForgeDirection.UP)
        {
            toReturn = fill(resource,doFill);
            if (toReturn!=0 && worldObj.isRemote==true)
            {
                LogHelper.info("toReturn = " + toReturn + ", spawning particle.");
                worldObj.spawnParticle("happyVillager",this.xCoord+0.5,this.yCoord-1.0,this.zCoord+0.5,0.0,-3.0,0.0);
                worldObj.spawnParticle("happyVillager",xCoord+0.5,yCoord+3.5,zCoord+0.5,0.0,1.0,0.0);
            }
            return toReturn;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (from == ForgeDirection.DOWN || from==ForgeDirection.UP)
        {
            return drain(resource.amount,doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from == ForgeDirection.DOWN || from==ForgeDirection.UP)
        {
            return drain(maxDrain,doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (from == ForgeDirection.DOWN || from==ForgeDirection.UP) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (from == ForgeDirection.DOWN || from==ForgeDirection.UP)
        {
            return true;
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] toReturn  = new FluidTankInfo[1];
        toReturn[0] = getInfo();
        return toReturn;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
//        LogHelper.info("Reading NBT data of the BaseTank");
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("fluid") && tagCompound.hasKey("amount"))
        {
            fluid = new FluidStack(FluidRegistry.getFluidID(tagCompound.getString("fluid")),tagCompound.getInteger("amount"));
//            LogHelper.info("Read fluid: " + fluid.getFluid().getName() + " with amount: " + fluid.amount);
        }
        else
        {
            fluid = null;
//            LogHelper.info("Tank was empty upon reading");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        if (fluid != null && fluid.amount!=0)
        {
            tagCompound.setString("fluid",fluid.getFluid().getName());
            tagCompound.setInteger("amount",fluid.amount);
        }
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
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }
}
