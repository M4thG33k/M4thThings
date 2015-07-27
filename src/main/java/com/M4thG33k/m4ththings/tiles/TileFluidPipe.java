package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.MathHelper;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 7/10/2015.
 */
public class TileFluidPipe extends TileEntity implements IFluidHandler {

    protected ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
    protected int[] opposites = ForgeDirection.OPPOSITES;
    protected FluidTank[] buffers = new FluidTank[13]; //the first 6 are export buffers, the second 6 are imports, and the 13th is the main pipe buffer
    protected boolean[] allowedConnection = new boolean[]{true,true,true,true,true,true};
    protected int scanOffset = 0; //used to rotate through which direction gets extra fluid in case of non-divisible amounts
    protected int inputScanOffset = 0; //use to rotate through which directions give up extra fluid in case of non-divisible amounts
    private int MAX_THROUGHPUT = Configurations.PIPE_THROUGHPUT;
    private int BUFFER = Configurations.PIPE_THROUGHPUT*6;


    public TileFluidPipe()
    {
        for (int i=0;i<6;i++)
        {
            buffers[i] = new FluidTank(MAX_THROUGHPUT);
        }

        for (int i=6;i<13;i++) {
            buffers[i] = new FluidTank(BUFFER);
        }
    }

    //region Data read-write
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("ScanOffset"))
        {
            scanOffset = tagCompound.getInteger("ScanOffset");
        }

        if (tagCompound.hasKey("InputScanOffset"))
        {
            inputScanOffset = tagCompound.getInteger("InputScanOffset");
        }

        if (tagCompound.hasKey("AllowedConnections"))
        {
            allowedConnection = MiscHelper.convertToBooleanArray(tagCompound.getIntArray("AllowedConnections"));
        }

        if (tagCompound.hasKey("Buffers"))
        {
            NBTTagList tagList = tagCompound.getTagList("Buffers",10);
            for (int i=0;i<tagList.tagCount();i++)
            {
                buffers[i].readFromNBT(tagList.getCompoundTagAt(i));
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("ScanOffset",scanOffset);
        tagCompound.setInteger("InputScanOffset",inputScanOffset);

        tagCompound.setIntArray("AllowedConnections", MiscHelper.convertToIntegerArray(allowedConnection));

        NBTTagList tagList = new NBTTagList();
        for (int i=0;i<13;i++)
        {
            NBTTagCompound tag = new NBTTagCompound();
            buffers[i].writeToNBT(tag);
            tagList.appendTag(tag);
        }
        tagCompound.setTag("Buffers",tagList);
    }
    //endregion

    //region IFluidHandler
    /* BEGIN IFluidHandler */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (allowedConnection[from.ordinal()])
        {
            return buffers[from.ordinal()+6].fill(resource,doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (allowedConnection[from.ordinal()])
        {
            return buffers[from.ordinal()+6].drain(resource.amount,doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (allowedConnection[from.ordinal()])
        {
            return buffers[from.ordinal()+6].drain(maxDrain,doDrain);
        }

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (!(allowedConnection[from.ordinal()]) || fluid==null)
        {
            return false;
        }
        if (buffers[from.ordinal()+6].getCapacity()==buffers[from.ordinal()+6].getFluidAmount())
        {
            return false;
        }
        for (int i=0;i<13;i++)
        {
            if (buffers[i]!=null && buffers[i].getFluid()!=null && buffers[i].getFluid().getFluid()!=fluid)
            {
                return false;
            }

        }
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (!(allowedConnection[from.ordinal()]) || fluid==null)
        {
            return false;
        }

        if (buffers[from.ordinal()].getFluidAmount()==0)
        {
            return false;
        }

        if (buffers[from.ordinal()].getFluid()!=null && buffers[from.ordinal()].getFluid().getFluid()!=fluid)
        {
            return false;
        }
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] infos = new FluidTankInfo[13];
        for (int i=0;i<13;i++)
        {
            infos[i] = buffers[i].getInfo();
        }

        return infos;
    }

    /* END IFluidHandler */
    //endregion


    @Override
    public void updateEntity() {

//        if (!(worldObj.isRemote)) //todo put this back in after you have packet handling worked out
//        {
//            return; //break if we aren't on the server
//        }
        attemptToRedistributeExportFluids();
        moveFluidToAdjacentStructures();
        moveFromInternalToExternalBuffers();
        moveFromInputToInternal(); //move fluid from the input buffers to the internal buffer
    }

    //attempts to move fluid from the output buffers to the internal buffer
    public void moveFromInputToInternal()
    {
        for (int i=0;i<6;i++)
        {
            if (buffers[12].getCapacity()==buffers[12].getFluidAmount())
            {
                return; //we break if the internal buffer is full
            }

            int I = ((i+inputScanOffset)%6)+6; //shift to account for our rotation
            if (buffers[I].getFluidAmount()==0)
            {
                continue; //start the next iteration of the for loop if the current buffer is empty
            }
            buffers[12].fill(buffers[I].drain(BUFFER,true),true); //move as much fluid as possible from the given input buffer to the internal buffer
        }

        inputScanOffset = (inputScanOffset+1)%6;
    }

    //attempt to move fluid from the internal buffer as evenly as possible to the external buffers
    public void moveFromInternalToExternalBuffers()
    {
        if (buffers[12].getFluidAmount()==0)
        {
            return; //break if we have no fluid in the internal buffer
        }

        int amountAvailable = buffers[12].getFluidAmount();
        int numConnections = 0;
        int[] neededFluid = new int[6];
        int fluidNeeded = 0;

        //figure out how many connections we have and how much fluid each connection can receive
        for (int i=0;i<6;i++)
        {
            if (allowedConnection[i])
            {
                numConnections++;
                neededFluid[i] = MAX_THROUGHPUT-buffers[i].getFluidAmount();
                fluidNeeded += neededFluid[i];
            }
            else
            {
                neededFluid[i] = 0;
            }
        }

        if (numConnections==0 || fluidNeeded==0)
        {
            return; //break if we have no allowed outputs or none of them need fluid
        }

        if (amountAvailable>=fluidNeeded) //in this case, we have enough fluid to fill each output
        {
            for (int i=0;i<6;i++)
            {
                if (allowedConnection[i])
                {
                    buffers[12].drain(buffers[i].fill(buffers[12].getFluid(),true),true); //drain fluid from the internal buffer to the export buffer
                }
            }
        }
        else //in this case, we don't have enough fluid to fill each output, so we must attempt to fill each one as evenly as possible
        {
//            int I = scanOffset;

            //this goes through and reduces the amount of fluid we can push to each export
//            while (amountAvailable>0)
//            {
//
////                LogHelper.info("I'm loopy, I'm loopy...");
//                if (allowedConnection[I] && neededFluid[I]>0)
//                {
//                    neededFluid[I]--;
//                    amountAvailable--;
//                }
//                I = (I+1)%6;
//            }

            int evenAmount = amountAvailable/numConnections;
            int excess = amountAvailable%numConnections;
            int minNeeded = MathHelper.getMinIgnoreZero(neededFluid);
            Fluid fluid = buffers[12].getFluid().getFluid();

            while (minNeeded < evenAmount+1) //we can't push the evenAmount into each buffer because it may overflow at least one buffer.
            {
                int numConnectionsNew = 0;
                for (int i=0;i<6;i++)
                {
                    int I = (i+scanOffset)%6;
                    if (neededFluid[I]>0)
                    {
                        buffers[12].drain(buffers[I].fill(new FluidStack(fluid,minNeeded),true),true);
                        neededFluid[I] -= minNeeded;
                        if (neededFluid[I]>0)
                        {
                            numConnectionsNew++;
                        }
                    }
                }
                //we've drained out the minNeeded from the main buffer and pushed it into the export buffers

                fluidNeeded = MathHelper.sum(neededFluid);
                amountAvailable = buffers[12].getFluidAmount();
                if (fluidNeeded==0 || amountAvailable==0)
                {
                    scanOffset = (scanOffset+1)%6;
                    return;
                }

                evenAmount = amountAvailable/numConnectionsNew;
                excess = amountAvailable%numConnectionsNew;
                minNeeded = MathHelper.getMinIgnoreZero(neededFluid);

            }

            //if we get here, that means every (legal) buffer is able to hold the evenAmount+1 amount of fluid, so we iterate through and place that fluid
            for (int i=0;i<6;i++)
            {
                int I = (i+scanOffset)%6;

                if (neededFluid[I]>0)
                {
                    buffers[12].drain(buffers[I].fill(new FluidStack(fluid,evenAmount + (excess-->0?1:0)),true),true);
                }
            }

//
//
//            //now that the values have been adjusted, we can push the values correctly
//            for (int i=0;i<6;i++)
//            {
//                if (neededFluid[i]>0)
//                {
//                    buffers[12].drain(buffers[i].fill(new FluidStack(fluid,neededFluid[i]),true),true); //fill each allowable buffer with fluid
//                }
//            }
        }

        scanOffset = (scanOffset+1)%6;
    }

    //attempts to move fluid to adjacent TileEntities which implement IFluidHandler
    public void moveFluidToAdjacentStructures()
    {
        TileEntity tileEntity;

        for (int i=0;i<6;i++)
        {
            if (allowedConnection[i] && buffers[i].getFluidAmount()>0)
            {
                tileEntity = MiscHelper.getTileInDirection(worldObj,xCoord,yCoord,zCoord,directions[i]);

                if (tileEntity instanceof IFluidHandler)
                {
                    buffers[i].drain(((IFluidHandler)tileEntity).fill(directions[opposites[i]],buffers[i].getFluid(),true),true);
                }
            }
        }
    }


    //checks to see if the only fluid in the pipe is in the export buffers (and if at least one export is empty
    public boolean doesFluidOnlyExistInExports()
    {
        for (int i=6;i<13;i++)
        {
            if (buffers[i].getFluidAmount()>0)
            {
                return false;
            }
        }

        int numEmpty = 0;
        for (int i=0;i<6;i++)
        {
            if (buffers[i].getFluidAmount()==0)
            {
                numEmpty++;
            }
        }

        return !((numEmpty==0) || (numEmpty==6));
    }


    //if the only fluid that exists in the pipe is in the export buffers, then we need to attempt to re-distribute the values
    public void attemptToRedistributeExportFluids()
    {
        if (doesFluidOnlyExistInExports())
        {
//            LogHelper.info("Attempting to redistribute!");
            for (int i=0;i<6;i++)
            {
                if (buffers[i].getFluidAmount()>0)
                {
                    int transferred = buffers[12].fill(buffers[i].getFluid(),true);
                    LogHelper.info("Amount transferred: " + transferred);
                    buffers[i].drain(transferred, true);
                    LogHelper.info("Amount in output buffer: " + buffers[i].getFluidAmount());
                }
            }

//            LogHelper.info("Internal buffer size: " + buffers[12].getFluidAmount());

            moveFromInternalToExternalBuffers();
        }


    }



}
