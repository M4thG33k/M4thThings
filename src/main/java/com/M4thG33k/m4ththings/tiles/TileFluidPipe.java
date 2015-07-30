package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.interfaces.IM4thNBTSync;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.Location;
import com.M4thG33k.m4ththings.utility.MathHelper;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.List;

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
    protected int inputScan;

    protected int[] priorities = new int[6];

    public TileFluidPipe()
    {
        for (int i=0;i<6;i++)
        {
            inputs[i] = new FluidTank(BUFFER_SIZE);
            outputs[i] = new FluidTank(THROUGHPUT);
            allowedInputs[i] = 1;
            allowedOutputs[i] = 1;
            activeOutputs[i] = 1;
            priorities[i] = 0;
        }

        mainBuffer = new FluidTank(BUFFER_SIZE);

        outputScan = 0;
        inputScan = 0;
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
        alterPriorities();
        attemptFluidDistributionToPriorityOutputBuffers(2);
        attemptFluidDistributionToPriorityOutputBuffers(1);
        attemptFluidDistributionToOutputBuffers();
        pullFluidFromInputBuffers();
    }


    public boolean doesPipeHaveValidConnection(ArrayList<int[]> pastLocations,FluidStack testStack)
    {
        int[] checks = new int[6];
        for (int i=0;i<6;i++)
        {
            int[] loc = new int[]{xCoord+directions[i].offsetX,yCoord+directions[i].offsetY,zCoord+directions[i].offsetZ};

            if (pastLocations.contains(loc))
            {
                checks[i] = 0;
            }
            else
            {
                TileEntity tileEntity = worldObj.getTileEntity(loc[0],loc[1],loc[2]);
                if (!(tileEntity instanceof IFluidHandler))
                {
                    checks[i] = 0;
                }
                else
                {
                    if (!(tileEntity instanceof TileFluidPipe))
                    {
                        if (((IFluidHandler)tileEntity).fill(directions[opposites[i]],testStack,false)>0)
                        {
                            checks[i] = 1;
                        }
                        else
                        {
                            checks[i] = 0;
                        }
                    }
                    else
                    {
                        pastLocations.add(loc);
                        if (((TileFluidPipe)tileEntity).doesPipeHaveValidConnection(pastLocations,testStack))
                        {
                            checks[i] = 1;
                        }
                        else
                        {
                            checks[i] = 0;
                        }
                    }
                }
            }
        }

        return MathHelper.getMinIgnoreZero(checks)>0;
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
                            if (((((TileFluidPipe)neighbor).mainBuffer.getFluidAmount() < this.mainBuffer.getFluidAmount()) || (((TileFluidPipe)neighbor).mainBuffer.getFluidAmount()==0)))// && (this.outputs[i].getFluidAmount()>(((TileFluidPipe)neighbor).outputs[opposites[i]]).getFluidAmount()))
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

                int amountTransferred = (neighbor.fill(directions[opposites[i]],outputs[i].getFluid(),true));

                outputs[i].drain(amountTransferred,true);

                if (amountTransferred==0) //if we failed to push any fluid (meaning that the target is full or for some other reason, we attempt to put the fluid back in the pipe
                {
                    outputs[i].drain(inputs[i].fill(outputs[i].getFluid(),true),true);
                    priorities[i] = 0;
                }
                else
                {
                    priorities[i] = 1;
                }
            }
            else //if not active, attempt to drain its contents into this segment's input buffer on the same side. (This is to try to keep fluid from getting "stuck" in output buffers.)
            {
                outputs[i].drain(inputs[i].fill(outputs[i].getFluid(),true),true);
                priorities[i] = 0;
            }
        }
    }

    public void alterPriorities()
    {
        if (mainBuffer.getFluidAmount()==0)
        {
            return;
        }

        for (int i=0;i<6;i++)
        {
            if (activeOutputs[i]==1)
            {
                int[] thisLoc = new int[]{xCoord,yCoord,zCoord};
                int[] loc = new int[]{xCoord+directions[i].offsetX,yCoord+directions[i].offsetY,zCoord+directions[i].offsetZ};
                ArrayList<int[]> pastLocations = new ArrayList<int[]>();
                pastLocations.add(thisLoc);
                pastLocations.add(loc);

                if (this.doesPipeHaveValidConnection(pastLocations,mainBuffer.getFluid()))
                {
                    priorities[i]++;
                }
            }
        }
    }


    public void attemptFluidDistributionToPriorityOutputBuffers(int priorityLevel)
    {
        int fluidAvailable = mainBuffer.getFluidAmount();

        if (fluidAvailable==0)
        {
            return; //break if we have no fluid to distribute
        }

        int fluidNeeded = 0;
        int[] neededInOutputs = new int[6];
        int numPriorities = 0;
        //first calculate how much fluid we need to fill each of the priority buffers
        for (int i=0;i<6;i++)
        {
            if (activeOutputs[i]==1 && priorities[i]==priorityLevel)
            {
                numPriorities++;
                neededInOutputs[i] = outputs[i].getCapacity()-outputs[i].getFluidAmount();
                fluidNeeded += neededInOutputs[i];
            }
            else
            {
                neededInOutputs[i] = 0;
            }
        }

        if (numPriorities==0)
        {
            return; //break if we have no priority buffers
        }
        if (fluidAvailable>=fluidNeeded) //in this case, we have enough fluid in the main buffer to completely fill each active buffer
        {
            for (int i=0;i<6;i++)
            {
                if (neededInOutputs[i]>0)
                {
                    mainBuffer.drain(outputs[i].fill(new FluidStack(mainBuffer.getFluid(),mainBuffer.getFluidAmount()),true),true); //fill each active output buffer completely
                }
            }
        }
        else //otherwise, we don't have enough fluid, so we must split it up as evenly as possible
        {
            int min = MathHelper.getMinIgnoreZero(neededInOutputs);
            int evenSplit = fluidAvailable/numPriorities; //denom>0, otherwise we will have already returned
            int excess = fluidAvailable%numPriorities; //the remainder after dividing the fluid evenly

            while (evenSplit+1>min && mainBuffer.getFluidAmount()>0)
            {
                for (int i=0;i<6;i++)
                {
                    //add as much fluid as possible to each output without overfilling anything
                    if (neededInOutputs[i]>0)
                    {
                        mainBuffer.drain(outputs[i].fill(new FluidStack(mainBuffer.getFluid(),min),true),true);
                        fluidAvailable-=min;
                        neededInOutputs[i]-=min;
                        if (neededInOutputs[i]==0)
                        {
                            numPriorities--;
                        }
                    }
                }
                if (numPriorities==0)
                {
                    return; //break if we ever run out of invalid outputs
                }
                min = MathHelper.getMinIgnoreZero(neededInOutputs);
                evenSplit = fluidAvailable/numPriorities;
                excess = fluidAvailable%numPriorities;
            }
            //now that we're here, we can go ahead and just fill each active output buffer with the evenSplit amount (rotating between which active one gets the excess)
            for (int i=0;i<6;i++)
            {
                if (mainBuffer.getFluidAmount()==0)
                {
                    return;//break if we ever run out of fluid to push
                }

                int I = (i+outputScan)%6;

                if (neededInOutputs[I]>0)
                {
                    mainBuffer.drain(outputs[I].fill(new FluidStack(mainBuffer.getFluid(),evenSplit + (excess-->0?1:0)),true),true);
                }
            }
        }
    }
    public void attemptFluidDistributionToOutputBuffers()
    {

        int fluidAvailable = mainBuffer.getFluidAmount();

        if (fluidAvailable==0)
        {
            return; //break if we have no fluid to distribute
        }
        int fluidNeeded = 0;
        int[] neededInOutputs = new int[6];
        int numActiveOutputs = 0;

        //first calculate how much fluid we need to fill each of the (active) output buffers
        for (int i=0;i<6;i++)
        {
            if (activeOutputs[i]==1)
            {
                numActiveOutputs++;
                neededInOutputs[i] = outputs[i].getCapacity()-outputs[i].getFluidAmount();
                fluidNeeded += neededInOutputs[i];
            }
            else
            {
                neededInOutputs[i] = 0;
            }
        }

        if (numActiveOutputs==0)
        {
            return; //break if we have no active outputs
        }

        if (fluidAvailable>=fluidNeeded) //in this case, we have enough fluid in the main buffer to completely fill each active buffer
        {
            for (int i=0;i<6;i++)
            {
                if (neededInOutputs[i]>0)
                {
                    mainBuffer.drain(outputs[i].fill(new FluidStack(mainBuffer.getFluid(),mainBuffer.getFluidAmount()),true),true); //fill each active output buffer completely
                }
            }
        }
        else //otherwise, we don't have enough fluid, so we must split it up as evenly as possible
        {
            int min = MathHelper.getMinIgnoreZero(neededInOutputs);
            int evenSplit = fluidAvailable/numActiveOutputs; //denom>0, otherwise we will have already returned
            int excess = fluidAvailable%numActiveOutputs; //the remainder after dividing the fluid evenly

            while (evenSplit+1>min && mainBuffer.getFluidAmount()>0)
            {
                for (int i=0;i<6;i++)
                {
                    //add as much fluid as possible to each output without overfilling anything
                    if (neededInOutputs[i]>0)
                    {
                        mainBuffer.drain(outputs[i].fill(new FluidStack(mainBuffer.getFluid(),min),true),true);
                        fluidAvailable-=min;
                        neededInOutputs[i]-=min;
                        if (neededInOutputs[i]==0)
                        {
                            numActiveOutputs--;
                        }
                    }
                }
                if (numActiveOutputs==0)
                {
                    return; //break if we ever run out of invalid outputs
                }
                min = MathHelper.getMinIgnoreZero(neededInOutputs);
                evenSplit = fluidAvailable/numActiveOutputs;
                excess = fluidAvailable%numActiveOutputs;
            }
            //now that we're here, we can go ahead and just fill each active output buffer with the evenSplit amount (rotating between which active one gets the excess)
            for (int i=0;i<6;i++)
            {
                if (mainBuffer.getFluidAmount()==0)
                {
                    outputScan = (outputScan+1)%6;
                    return;//break if we ever run out of fluid to push
                }

                int I = (i+outputScan)%6;

                if (neededInOutputs[I]>0)
                {
                    mainBuffer.drain(outputs[I].fill(new FluidStack(mainBuffer.getFluid(),evenSplit + (excess-->0?1:0)),true),true);
                }
            }
            outputScan = (outputScan+1)%6;
        }

    }

    public void pullFluidFromInputBuffers()
    {
        int spaceAvailable = mainBuffer.getCapacity()-mainBuffer.getFluidAmount();

        if (spaceAvailable==0)
        {
            return; //break if the main buffer is already full
        }

        int[] fluidAvail = new int[6];
        int totalFluid = 0;
        int numInputs = 0;
        for (int i=0;i<6;i++)
        {
            fluidAvail[i] = inputs[i].getFluidAmount();
            totalFluid += fluidAvail[i];
            if (fluidAvail[i]>0)
            {
                numInputs++;
            }
        }

        if (numInputs==0)
        {
            return; //break if we have no fluid attempting to enter
        }

        if (spaceAvailable >= totalFluid) //in this case, we have enough room for all the fluid in all input buffers
        {
            for (int i=0;i<6;i++)
            {
                inputs[i].drain(mainBuffer.fill(inputs[i].getFluid(),true),true);
            }
        }
        else //otherwise, we have too much fluid trying to enter, so we attempt to extract evenly from all directions with fluid
        {
            int evenSplit = spaceAvailable/numInputs;
            int excess = spaceAvailable%numInputs;
            int min = MathHelper.getMinIgnoreZero(fluidAvail);

            while (evenSplit+1>min)
            {
                for (int i=0;i<6;i++)
                {
                    if (fluidAvail[i]>0)
                    {
                        inputs[i].drain(mainBuffer.fill(new FluidStack(inputs[i].getFluid(),min),true),true);
                        fluidAvail[i]-=min;
                        spaceAvailable-=min;
                        if (fluidAvail[i]==0)
                        {
                            numInputs--;
                        }
                    }
                }

                if (spaceAvailable==0 || numInputs==0)
                {
                    return; //break if we end up filling the main buffer or we have no more valid inputs
                }

                evenSplit = spaceAvailable/numInputs;
                excess = spaceAvailable%numInputs;
                min = MathHelper.getMinIgnoreZero(fluidAvail);
            }

            //now that we're here, we should be able to evenly remove fluid from all the remaining available inputs
            for (int i=0;i<6;i++)
            {
                int I = (i+inputScan)%6;

                if (fluidAvail[I]>0)
                {
                    inputs[I].drain(mainBuffer.fill(new FluidStack(inputs[I].getFluid(),evenSplit+(excess-->0?1:0)),true),true);
                }
            }
        }

        inputScan = (inputScan+1)%6;
    }

    //region Data read/write
    /* START Data read/write */


    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("TankData"))
        {
            NBTTagList tankList = tagCompound.getTagList("TankData",10);
            for (int i=0;i<6;i++)
            {
                inputs[i].readFromNBT(tankList.getCompoundTagAt(i));
                outputs[i].readFromNBT(tankList.getCompoundTagAt(i+6));
            }
            mainBuffer.readFromNBT(tankList.getCompoundTagAt(12));
        }

        if (tagCompound.hasKey("ConnectionData"))
        {
            int[] connectionData = tagCompound.getIntArray("ConnectionData");
            for (int i=0;i<6;i++)
            {
                allowedInputs[i] = connectionData[i];
                allowedOutputs[i] = connectionData[i+6];
                activeOutputs[i] = connectionData[i+12];
            }
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        NBTTagList tankList = new NBTTagList();
        for (int i=0;i<6;i++)
        {
            NBTTagCompound tag = new NBTTagCompound();
            inputs[i].writeToNBT(tag);
            tankList.appendTag(tag);
        }
        for (int i=0;i<6;i++)
        {
            NBTTagCompound tag = new NBTTagCompound();
            outputs[i].writeToNBT(tag);
            tankList.appendTag(tag);
        }
        NBTTagCompound tag = new NBTTagCompound();
        mainBuffer.writeToNBT(tag);
        tankList.appendTag(tag);

        tagCompound.setTag("TankData",tankList);

        int[] intArrays = MiscHelper.combineIntArrays(allowedInputs,MiscHelper.combineIntArrays(allowedOutputs,activeOutputs));
        tagCompound.setIntArray("ConnectionData",intArrays);

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
