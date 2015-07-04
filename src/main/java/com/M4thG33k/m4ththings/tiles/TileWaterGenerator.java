package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.reference.Configurations;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class TileWaterGenerator extends TileEntity implements IFluidHandler {

    FluidTank tank = new FluidTank(2000);
    int timer;
    private int WATER_GEN = Configurations.WATER_GEN_PER_TICK;

    public TileWaterGenerator()
    {
        timer = 0;
    }

    @Override
    public void updateEntity() {
        advanceTimer();
        //first, try to get fluid from below
        if (tank.getFluidAmount()<tank.getCapacity() && worldObj.getBlock(xCoord,yCoord-1,zCoord)==Blocks.water)
        {
            int numSource = 0;
            Block block = worldObj.getBlock(xCoord-1,yCoord-1,zCoord);
            if (block== Blocks.water)
            {
                numSource += 1;
            }
            block = worldObj.getBlock(xCoord+1,yCoord-1,zCoord);
            if (block== Blocks.water)
            {
                numSource += 1;
            }
            block = worldObj.getBlock(xCoord,yCoord-1,zCoord-1);
            if (block== Blocks.water)
            {
                numSource += 1;
            }
            block = worldObj.getBlock(xCoord,yCoord-1,zCoord+1);
            if (block== Blocks.water)
            {
                numSource += 1;
            }

            if (numSource>=2) //this means we have an infinite water source beneath us
            {
                this.fill(ForgeDirection.DOWN,new FluidStack(FluidRegistry.getFluid("water"),WATER_GEN),true);
//                tank.fill(new FluidStack(FluidRegistry.getFluid("water"),10),true);
//                prepareSync();
            }
        }

        //now scan a 5x2x5 area above the water level for IFluidHandlers and try to pass it a bucket of water
        for (int i=-2;i<=2;i++)
        {
            for (int j=-2;j<=2;j++)
            {
                for (int k=0;k<2;k++)
                {
                    if (tank.getFluidAmount()<1000)
                    {
                        break;
                    }
                    if (i!=0 || j!=0 || k!=0) //ignores itself
                    {
                        attemptToFill(xCoord+i,yCoord+k,zCoord+j);
                    }
                }
                if (tank.getFluidAmount()<1000)
                {
                    break;
                }
            }
            if (tank.getFluidAmount()<1000)
            {
                break;
            }
        }

    }

    public void advanceTimer()
    {
        timer += 1;
        if (timer>=720)
        {
            timer = 0;
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (from!=ForgeDirection.DOWN)
        {
            return 0;
        }
        int toReturn = tank.fill(resource, doFill);
        prepareSync();
        return toReturn;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (from!=ForgeDirection.UP || resource == null || !resource.isFluidEqual(tank.getFluid()))
        {
            return null;
        }
        FluidStack toReturn = tank.drain(resource.amount, doDrain);
        prepareSync();
        return toReturn;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from!=ForgeDirection.UP)
        {
            return null;
        }
        FluidStack toReturn = tank.drain(maxDrain, doDrain);
        prepareSync();
        return toReturn;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        tank.readFromNBT(tagCompound);

        timer = tagCompound.getInteger("timer");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tank.writeToNBT(tagCompound);

        tagCompound.setInteger("timer",timer);
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
        prepareSync();
    }

    public void prepareSync()
    {
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        markDirty();
    }

    //attempt to fill an IFluidHandler with one bucket of water
    public void attemptToFill(int x, int y, int z)
    {
        Fluid water = FluidRegistry.getFluid("water");
        FluidStack transferAttempt = new FluidStack(water,1000);
        TileEntity tileEntity = worldObj.getTileEntity(x,y,z);
        int transferred;

        if (tileEntity!=null && tileEntity instanceof IFluidHandler && !(tileEntity instanceof TileMedQT) && !(tileEntity instanceof TileQTComponent))
        {
            IFluidHandler target = (IFluidHandler)tileEntity;

            if (target.canFill(ForgeDirection.UP,water))
            {
                transferred = target.fill(ForgeDirection.UP,transferAttempt,true);
                if (transferred>0)
                {
                    this.drain(ForgeDirection.UP,transferred,true);
                    return;
                }
            }
            if (target.canFill(ForgeDirection.DOWN,water))
            {
                transferred = target.fill(ForgeDirection.DOWN,transferAttempt,true);
                if (transferred>0)
                {
                    this.drain(ForgeDirection.UP,transferred,true);
                    return;
                }
            }
            if (target.canFill(ForgeDirection.NORTH,water))
            {
                transferred = target.fill(ForgeDirection.NORTH,transferAttempt,true);
                if (transferred>0)
                {
                    this.drain(ForgeDirection.UP,transferred,true);
                    return;
                }
            }
            if (target.canFill(ForgeDirection.SOUTH,water))
            {
                transferred = target.fill(ForgeDirection.SOUTH,transferAttempt,true);
                if (transferred>0)
                {
                    this.drain(ForgeDirection.UP,transferred,true);
                    return;
                }
            }
            if (target.canFill(ForgeDirection.EAST,water))
            {
                transferred = target.fill(ForgeDirection.EAST,transferAttempt,true);
                if (transferred>0)
                {
                    this.drain(ForgeDirection.UP,transferred,true);
                    return;
                }
            }
            if (target.canFill(ForgeDirection.WEST,water))
            {
                transferred = target.fill(ForgeDirection.WEST,transferAttempt,true);
                if (transferred>0)
                {
                    this.drain(ForgeDirection.UP,transferred,true);
                    return;
                }
            }
        }
    }

    public int getTimer()
    {
        return timer;
    }
}
