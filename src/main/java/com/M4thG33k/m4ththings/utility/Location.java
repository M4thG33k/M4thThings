package com.M4thG33k.m4ththings.utility;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class Location {

    private int[] location = new int[3];

    public Location(){}

    public Location(int x, int y, int z)
    {
        location[0] = x;
        location[1] = y;
        location[2] = z;
    }

    public int[] getLocation() {
        return location;
    }

    public int getX()
    {
        return location[0];
    }

    public int getY()
    {
        return location[1];
    }

    public int getZ()
    {
        return location[2];
    }

    public String getCoordinates()
    {
        return StringHelper.makeCoords(location[0],location[1],location[2]);
    }

    public void readFromNBT(NBTTagCompound tagCompound)
    {
        if (tagCompound.hasKey("M4thLocation"))
        {
            location = tagCompound.getIntArray("M4thLocation");
        }
    }

    public void writeToNBT(NBTTagCompound tagCompound)
    {
        if (location!=null)
        {
            tagCompound.setIntArray("M4thLocation",location);
        }
    }
}
