package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 7/9/2015.
 */
public class TileFluidTransferPipe extends TileEntity implements IFluidHandler {


    /* START FluidConnection class */

    class FluidConnection implements IFluidHandler
    {
        private int FLOWRATE = 1000; //how many mb/t we're allowed to transfer through the pipe
        private FluidTank buffer = new FluidTank(FLOWRATE); //the buffer used for throughput
        private ForgeDirection direction; //the direction this connection in relation to the center of the block

        public FluidConnection(ForgeDirection direc)
        {
            direction = direc;
        }

        /* START IFluidHandler */

        @Override
        public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
            if (from!=direction)
            {
                return 0;
            }

            return buffer.fill(resource,doFill);
        }

        @Override
        public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
            if (from!=direction || resource==null || resource.amount==0 || !(resource.isFluidEqual(buffer.getFluid())))
            {
                return null;
            }

            return buffer.drain(resource.amount,doDrain);
        }

        @Override
        public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
            if (from!=direction || maxDrain<=0)
            {
                return null;
            }

            return buffer.drain(maxDrain,doDrain);
        }

        @Override
        public boolean canFill(ForgeDirection from, Fluid fluid) {
            if (from!=direction || fluid==null || (buffer.getFluid()!=null && buffer.getFluid().getFluid()!=fluid))
            {
                return false;
            }

            return true;
        }

        @Override
        public boolean canDrain(ForgeDirection from, Fluid fluid) {
            if (from!=direction || fluid==null || buffer.getFluidAmount()==0 || (buffer.getFluid()!=null && buffer.getFluid().getFluid()!=fluid))
            {
                return false;
            }

            return true;
        }

        @Override
        public FluidTankInfo[] getTankInfo(ForgeDirection from) {
            return new FluidTankInfo[]{buffer.getInfo()};
        }
        /* END IFluidHandler */

        public void writeToNBT(NBTTagCompound tagCompound)
        {
            buffer.writeToNBT(tagCompound);
        }

        public void readFromNBT(NBTTagCompound tagCompound)
        {
            buffer.readFromNBT(tagCompound);
        }

        public FluidStack getFluid()
        {
            return buffer.getFluid();
        }

        public int getFluidAmount()
        {
            return buffer.getFluidAmount();
        }

        public int getAmountNeeded()
        {
            return buffer.getCapacity()-buffer.getFluidAmount();
        }

        public void setFluid(FluidStack resource)
        {
            if (resource==null || resource.amount==0)
            {
                buffer.setFluid(null);
            }
            else
            {
                buffer.setFluid(resource);
            }
        }
    }

    /* END FluidConnection class */




    protected ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
    protected int[] opposites = ForgeDirection.OPPOSITES;
    protected boolean[] flowAllowed = new boolean[]{true,true,true,true,true,true};
    protected int PIPEBUFFER = 6000; //amount of fluid allowed to be in any pipe segment at a time (I believe this should be 6x the FLOWRATE
    protected FluidTank myPipeBuffer = new FluidTank(PIPEBUFFER);
    protected FluidConnection[] inputConnections;
    protected FluidConnection[] outputConnections;
    protected int scanIndex = 0; //this value is used in the moveFluidToOutputs(...) method to rotate which sides get the extra fluid
    protected int inputScanIndex = 0;//this value is used in the moveFluidIn(...) method to rotate through which side we import from first



    public TileFluidTransferPipe()
    {
        inputConnections = new FluidConnection[6];
        outputConnections = new FluidConnection[6];
        for (int i=0;i<6;i++)
        {
            inputConnections[i] = new FluidConnection(directions[i]);
            outputConnections[i] = new FluidConnection(directions[i]);
        }
    }


    @Override
    public void updateEntity() {
        super.updateEntity();

        moveFluidOut(); //first attempt to clear the output buffers

        if (needsBalancing())
        {
            redistributeOutputs(); //if the buffer is empty, but we have fluid in the outputs, suck it back in for redistribution first (this should keep the pipes from retaining any fluid)
        }

        moveFluidToOutputs(); //attempts to distribute the internal buffer of fluid evenly to all output buffers
        moveFluidIn(); //attempts to move fluid from the input buffers into the main buffer
    }


    //attempts to clear the output connections of their fluid (pushes fluid into adjacent IFluidHandlers)
    public void moveFluidOut()
    {
        TileEntity tileEntity;
        for (int i=0;i<6;i++)
        {
            if (flowAllowed[i] && outputConnections[i].getFluidAmount()>0) //we don't want to move fluid unless the flow is allowed and we have fluid
            {
                tileEntity = MiscHelper.getTileInDirection(worldObj,xCoord,yCoord,zCoord,i);
                if (tileEntity!=null && tileEntity instanceof IFluidHandler)
                {
                    int filledAmount = ((IFluidHandler)tileEntity).fill(directions[opposites[i]], outputConnections[i].getFluid(), true);
//                    LogHelper.info("Moved " + filledAmount + " fluid in the direction of " + i);
                    outputConnections[i].drain(directions[i],filledAmount,true);
                }
            }
        }
    }

    //attempts to push fluid from the central buffer into the external buffers (which connect to nearby TE's)
    public void moveFluidToOutputs()
    {
        if (myPipeBuffer.getFluid()==null || myPipeBuffer.getFluidAmount()==0)
        {
            return; //break if we have no fluid to push
        }
        int amount = myPipeBuffer.getFluidAmount();
        int amountNeeded = 0;
        int[] needed = new int[6];
        int numOutputs = 0;
        for (int i=0;i<6;i++)
        {
            int I = (i+scanIndex)%6;
            needed[I] = 0;
            if (flowAllowed[I])
            {
                needed[i] = outputConnections[I].getAmountNeeded();
                amountNeeded += needed[i];
                numOutputs++;
            }
        }



        if (numOutputs==0 || amountNeeded==0) //we break here if we can't output the fluid anywhere or if we have no room for the fluid
        {
            return;
        }

        if (amount>=amountNeeded) //in this case, we have enough fluid in the internal buffer to fill each output connection
        {
            for (int i=0;i<6;i++)
            {
                if (flowAllowed[i])
                {
                    myPipeBuffer.drain(outputConnections[i].fill(directions[i],myPipeBuffer.getFluid(),true),true); //fill each available output connection
                }
            }
        }
        else //otherwise, we don't have enough to completely fill each connection, so we try to balance as much as possible
        {
            Fluid fluid = myPipeBuffer.getFluid().getFluid();

            int deficit = amountNeeded-amount;
            int evenDeficit = deficit/numOutputs;
            int leftover = deficit%numOutputs;

            for (int i=0;i<6;i++)
            {
                int I = (i+scanIndex)%6;
                if (flowAllowed[I])
                {
                    myPipeBuffer.drain(outputConnections[I].fill(directions[I],new FluidStack(fluid,needed[I]-evenDeficit-(leftover>0?1:0)),true),true);
                    leftover = Math.max(leftover-1,0);
                }
            }

        }
        scanIndex = (scanIndex+1)%6;
    }

    //attempts to push fluid from the input buffers into the internal buffer
    public void moveFluidIn()
    {
        for (int i=0;i<6;i++)
        {
            if (myPipeBuffer.getCapacity()-myPipeBuffer.getFluidAmount()==0)
            {
                inputScanIndex = (inputScanIndex+1)%6;
                return; //break if we can't add any more fluid
            }
            int I = (i+inputScanIndex)%6; //shifts the index so that we can start somewhere other than "DOWN"
            if (inputConnections[I].getFluidAmount()>0)
            {
                myPipeBuffer.fill(inputConnections[I].drain(directions[I],inputConnections[i].getFluid(),true),true);
            }
        }

        inputScanIndex = (inputScanIndex+1)%6; //increment this value so that we don't always start looking at the same place


//        int allowedInput = myPipeBuffer.getCapacity()-myPipeBuffer.getFluidAmount();
//        if (allowedInput==0)
//        {
//            return; //break if there's no room for fluid in the internal buffer
//        }
//
//        int numInputs = 0;
//        int amountAvailable = 0;
//        int[] avail = new int[6];
//        for (int i=0;i<6;i++)
//        {
//            avail[i] = 0;
//            if (flowAllowed[i])
//            {
//                numInputs++;
//                avail[i] = inputConnections[i].getFluidAmount();
//                amountAvailable += avail[i];
//            }
//        }
//
//        if (numInputs==0 || amountAvailable==0)
//        {
//            return; //break if fluid isn't allowed in or if there is not fluid trying to enter
//        }
//
//        if (amountAvailable<=allowedInput) //in this case, we have room for all of the fluid available
//        {
//            for (int i=0;i<6;i++)
//            {
//                if (flowAllowed[i])
//                {
//                    myPipeBuffer.fill(inputConnections[i].drain(directions[i],inputConnections[i].getFluid(),true),true);
//                }
//            }
//        }
//        else //in this case, we have more fluid trying to enter than we have room for, so we must attempt to pull evenly from each direction
//        {
//            int extra = amountAvailable - allowedInput;
//            int evenExtra = extra / numInputs;
//            int leftover = extra % numInputs;
//
//            for (int i = 0; i < 6; i++)
//            {
//                if (flowAllowed[i])
//                {
//                    myPipeBuffer.fill(inputConnections[i].drain(directions[i],avail[i]-evenExtra-(leftover>0?1:0),true),true);
//                    leftover--;
//                }
//            }
//        }


    }

    //returns true if the internal buffer is empty but we have fluid in the output connections
    public boolean needsBalancing()
    {
        if (myPipeBuffer.getFluidAmount()>0)
        {
            return false;
        }
        for (int i=0;i<6;i++)
        {
            if (inputConnections[i].getFluidAmount()>0)
            {
                return false;
            }
        }

        for (int i=0;i<6;i++)
        {
            if (outputConnections[i].getFluidAmount()>0)
            {
                return true;
            }
        }

        return false;
    }

    //takes all of the fluid in the output connections and redistributes it among all available (valid) connections as evenly as possible
    //if there is extra fluid, it gets stored in the pipe's buffer. THIS METHOD MUST ONLY BE CALLED AFTER NEEDSBALANCING() RETURNS TRUE
    public void redistributeOutputs()
    {
        LogHelper.info("Break to see 'Before' state");
        //since the main buffer is empty (and should be able to hold the total of all export buffers,
        //we just move them all back into the main buffer. Other methods will deal with the redistribution
        for (int i=0;i<6;i++)
        {
            if (outputConnections[i].getFluidAmount()>0)
            {
                int toMove = outputConnections[i].getFluidAmount();
                myPipeBuffer.fill(outputConnections[i].drain(directions[i],toMove,true),true);
            }
        }

        LogHelper.info("Break to see 'After' state");

//        int amount = 0;
//        Fluid fluid = null;
//
//        //tallies up the amount of fluid in the output buffers and nullifies it all
//        for (int i=0;i<6;i++)
//        {
//            if (fluid==null && outputConnections[i].getFluid()!=null && outputConnections[i].getFluid().getFluid()!=null)
//            {
//                fluid = outputConnections[i].getFluid().getFluid();
//            }
//            amount += outputConnections[i].getFluidAmount();
//            outputConnections[i].setFluid(null);
//        }
//
//        //puts the fluid in the pipe's buffer instead
//        myPipeBuffer.setFluid(new FluidStack(fluid,amount));
    }


    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        myPipeBuffer.writeToNBT(tagCompound);

        NBTTagList inputs = new NBTTagList();
        NBTTagList outputs = new NBTTagList();
        int[] allowances = new int[6];
        for (int i=0;i<6;i++)
        {
            NBTTagCompound tagIn = new NBTTagCompound();
            NBTTagCompound tagOut = new NBTTagCompound();

            inputConnections[i].writeToNBT(tagIn);
            outputConnections[i].writeToNBT(tagOut);
            allowances[i] = (flowAllowed[i] ? 1 : 0);

            inputs.appendTag(tagIn);
            outputs.appendTag(tagOut);
        }

        tagCompound.setTag("InputConnections",inputs);
        tagCompound.setTag("OutputConnections",outputs);
        tagCompound.setIntArray("FlowAllowed",allowances);
        tagCompound.setInteger("ScanIndex",scanIndex);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        myPipeBuffer.readFromNBT(tagCompound);

        if (tagCompound.hasKey("FlowAllowed"))
        {
            int[] allowances = tagCompound.getIntArray("FlowAllowed");

            for (int i=0;i<6;i++)
            {
                flowAllowed[i] = (allowances[i]==1);
            }
        }

        if (tagCompound.hasKey("InputConnections"))
        {
            NBTTagList inputs = tagCompound.getTagList("InputConnections",10);

            for (int i=0;i<6;i++)
            {
                inputConnections[i].readFromNBT(inputs.getCompoundTagAt(i));
            }
        }

        if (tagCompound.hasKey("OutputConnections"))
        {
            NBTTagList outputs = tagCompound.getTagList("OutputConnections",10);

            for (int i=0;i<6;i++)
            {
                outputConnections[i].readFromNBT(outputs.getCompoundTagAt(i));
            }
        }

        if (tagCompound.hasKey("ScanIndex"))
        {
            scanIndex = tagCompound.getInteger("ScanIndex");
        }
    }

    public boolean canAcceptFluid(FluidStack resource)
    {
        if (resource==null || resource.amount==0)
        {
            return false;
        }
        if (myPipeBuffer.getFluidAmount()>0 && !(myPipeBuffer.getFluid().isFluidEqual(resource)))
        {
            return false;
        }
        for (int i=0;i<6;i++)
        {
            if (inputConnections[i].getFluidAmount()>0 && !(inputConnections[i].getFluid().isFluidEqual(resource)))
            {
                return false;
            }
            if (outputConnections[i].getFluidAmount()>0 && !(outputConnections[i].getFluid().isFluidEqual(resource)))
            {
                return false;
            }
        }

        return true;
    }

    /* START IFluidHandler */

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (from==ForgeDirection.UNKNOWN|| !flowAllowed[from.ordinal()] || !canAcceptFluid(resource) )
        {
            return 0;
        }

        return inputConnections[from.ordinal()].fill(from,resource,doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (from==ForgeDirection.UNKNOWN || !flowAllowed[from.ordinal()])
        {
            return null;
        }

        return inputConnections[from.ordinal()].drain(from, resource, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from==ForgeDirection.UNKNOWN || !flowAllowed[from.ordinal()])
        {
            return null;
        }

        return inputConnections[from.ordinal()].drain(from, maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (from==ForgeDirection.UNKNOWN || !flowAllowed[from.ordinal()])
        {
            return false;
        }

        return inputConnections[from.ordinal()].canFill(from,fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (from==ForgeDirection.UNKNOWN || !flowAllowed[from.ordinal()])
        {
            return false;
        }

        return inputConnections[from.ordinal()].canDrain(from, fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{myPipeBuffer.getInfo()};
    }

    /* END IFluidHandler */
}
