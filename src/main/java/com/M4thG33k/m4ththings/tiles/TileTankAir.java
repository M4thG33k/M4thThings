package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class TileTankAir extends TileEntity {

    protected int[] controllerLocation;

    public TileTankAir()
    {
    }

    public void setControllerLocation(int x,int y, int z)
    {
        if (controllerLocation == null)
        {
            controllerLocation = new int[3];
        }

        controllerLocation[0] = x;
        controllerLocation[1] = y;
        controllerLocation[2] = z;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("controllerX"))
        {
            setControllerLocation(tagCompound.getInteger("controllerX"),tagCompound.getInteger("controllerY"),tagCompound.getInteger("controllerZ"));
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        if (controllerLocation!=null)
        {
            tagCompound.setInteger("controllerX",controllerLocation[0]);
            tagCompound.setInteger("controllerY",controllerLocation[1]);
            tagCompound.setInteger("controllerZ",controllerLocation[2]);
        }
    }

    public TileEntity getController()
    {
        if (controllerLocation!=null)
        {
            TileEntity tileEntity = worldObj.getTileEntity(controllerLocation[0],controllerLocation[1],controllerLocation[2]);
            if (tileEntity == null)
            {
                LogHelper.info("We have a problem, Houston...the TE is null. :-(");
            }
            return tileEntity;
        }

        LogHelper.info("Controller location is empty...something isn't right.");
        return null;
    }

    public int[] getControllerLocation()
    {
        return controllerLocation;
    }

    public TileMediumQuantumTank getTank()
    {
        if (getController() instanceof  TileMediumQuantumTank)
        {
            LogHelper.info("Returning instance of TileMediumQuantumTank");
            return (TileMediumQuantumTank)getController();
        }

//        LogHelper.info("Apparently we have in instance of: " + getController().getClass().getName());
        return null;
    }
}
