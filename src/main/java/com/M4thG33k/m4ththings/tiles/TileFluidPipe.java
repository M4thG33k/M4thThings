package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.interfaces.IM4thNBTSync;
import com.M4thG33k.m4ththings.reference.Configurations;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 7/28/2015.
 */
public class TileFluidPipe extends TileEntity implements IFluidHandler, IM4thNBTSync {

    protected int THROUGHPUT = Configurations.PIPE_THROUGHPUT;
    protected int BUFFER_SIZE = 6*THROUGHPUT;

    protected ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
    protected int[] opposites = ForgeDirection.OPPOSITES;

    protected FluidTank[] inputs = new FluidTank[6];
    protected FluidTank[] outputs = new FluidTank[6];
    protected FluidTank mainBuffer;

    protected int[] allowedInputs = new int[6];
    protected int[] allowedOutputs = new int[6];
    protected int[] activeOutputs = new int[6];

    protected int outputScan;


    public TileFluidPipe()
    {
        for (int i=0;i<6;i++)
        {
            inputs[i] = new FluidTank(BUFFER_SIZE);
            outputs[i] = new FluidTank(THROUGHPUT);
            allowedInputs[i] = 1;
            allowedOutputs[i] = 1;
            activeOutputs[i] = 1;
        }

        mainBuffer = new FluidTank(BUFFER_SIZE);

        outputScan = 0;
    }

    public boolean isEmpty()
    {
        if (mainBuffer.getFluidAmount()>0)
        {
            return false;
        }
        for (int i=0;i<6;i++)
        {
            if (inputs[i].getFluidAmount()>0 || outputs[i].getFluidAmount()>0)
            {
                return false;
            }
        }

        return true;
    }

    public Fluid getFluid()
    {
        if (this.isEmpty())
        {
            return null;
        }

        if (mainBuffer.getFluidAmount()>0)
        {
            return mainBuffer.getFluid().getFluid();
        }

        for (int i=0;i<6;i++)
        {
            if (inputs[i].getFluidAmount()>0)
            {
                return inputs[i].getFluid().getFluid();
            }

            if (outputs[i].getFluidAmount()>0)
            {
                return outputs[i].getFluid().getFluid();
            }
        }

        //we should never get to this point; if the pipe has no fluid, we return null originally, otherwise we will find some in the rest of the body
        return null;
    }

    @Override
    public void updateEntity() {

        calculateActiveOutputs();
        attemptFluidPushToAdjacentTanks();
    }

    public void calculateActiveOutputs()
    {
        for (int i=0;i<6;i++)
        {
            if (allowedOutputs[i]==0)
            {
                activeOutputs[i] = 0;
            }
            else
            {
                TileEntity neighbor = worldObj.getTileEntity(xCoord+directions[i].offsetX,yCoord+directions[i].offsetY,zCoord+directions[i].offsetZ);

                if (neighbor == null || !(neighbor instanceof IFluidHandler))
                {
                    activeOutputs[i] = 0;
                }
                else
                {
                    if (outputs[i].getFluidAmount()>0 && ((IFluidHandler)neighbor).canFill(directions[opposites[i]],outputs[i].getFluid().getFluid()))
                    {
                        if (neighbor instanceof TileFluidPipe)
                        {
                            if (((TileFluidPipe)neighbor).mainBuffer.getFluidAmount() < this.mainBuffer.getFluidAmount())
                            {
                                activeOutputs[i] = 1;
                            }
                            else
                            {
                                activeOutputs[i] = 0;
                            }
                        }
                        else
                        {
                            activeOutputs[i] = 1;
                        }
                    }
                    else
                    {
                        if (outputs[i].getFluidAmount()==0)
                        {
                            if (neighbor instanceof TileFluidPipe)
                            {
                                if (((TileFluidPipe)neighbor).mainBuffer.getFluidAmount() < this.mainBuffer.getFluidAmount())
                                {
                                    activeOutputs[i] = 1;
                                }
                                else
                                {
                                    activeOutputs[i] = 0;
                                }
                            }
                            else
                            {
                                activeOutputs[i] = 1;
                            }
                        }
                        else
                        {
                            activeOutputs[i] = 0;
                        }
                    }
                }
            }
        }
    }


    public void attemptFluidPushToAdjacentTanks()
    {
        for (int i=0;i<6;i++)
        {
            if (activeOutputs[i]==1) //if active, attempt to push into adjacent tank/pipe/etc...
            {
                IFluidHandler neighbor = (IFluidHandler)(worldObj.getTileEntity(xCoord+directions[i].offsetX,yCoord+directions[i].offsetY,zCoord+directions[i].offsetZ));

                outputs[i].drain(neighbor.fill(directions[opposites[i]],outputs[i].getFluid(),true),true);
            }
            else //if not active, attempt to drain its contents into this segment's input buffer on the same side. (This is to try to keep fluid from getting "stuck" in output buffers.)
            {
                outputs[i].drain(inputs[i].fill(outputs[i].getFluid(),true),true);
            }
        }
    }

    public void attemptFluidDistributionToOutputBuffers()
    {

        int fluidAvailable = mainBuffer.getFluidAmount();
        int fluidSpace = 0;
        int numActiveOutputs = 0;

    }

    //todo NBT CODE!!!!
    //region Data read/write
    /* START Data read/write */


    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
    }

    /* END data read/write*/
    //endregion

    //region IFluidHandler
    /* Start IFluidHandler*/
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource==null || (allowedInputs[from.ordinal()]==0) || (!(this.isEmpty()) && this.getFluid()!=resource.getFluid()))
        {
            return 0; //either the incoming fluid is non-existent, we aren't allowing input from that side, or the fluids don't match
        }

        return inputs[from.ordinal()].fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource==null || resource.amount==0 || (allowedOutputs[from.ordinal()]==0))
        {
            return null;
        }

        return outputs[from.ordinal()].drain(resource.amount,doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (maxDrain==0 || (allowedOutputs[from.ordinal()]==0))
        {
            return null;
        }

        return outputs[from.ordinal()].drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (fluid==null || allowedInputs[from.ordinal()]==0 || (!(this.isEmpty()) && this.getFluid()!=fluid))
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (fluid==null || allowedOutputs[from.ordinal()]==0 || (!(this.isEmpty()) && this.getFluid()!=fluid))
        {
            return false;
        }

        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] toReturn = new FluidTankInfo[13];
        for (int i=0;i<6;i++)
        {
            toReturn[i] = inputs[i].getInfo();
            toReturn[i+6] = outputs[i].getInfo();
        }

        toReturn[12] = mainBuffer.getInfo();

        return toReturn;
    }
    /*End IFluidHandler*/
    //endregion


    //region IM4thNBTSync
    /*Start IM4thNBTSync*/

    @Override
    public void receiveNBTPacket(NBTTagCompound tagCompound) {
        this.readFromNBT(tagCompound);
    }

    /*End IM4thNBTSync*/
    //endregion
}
