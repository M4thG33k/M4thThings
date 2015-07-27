package com.M4thG33k.m4ththings.utility;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by M4thG33k on 7/2/2015.
 */
public class MiscHelper {

    public static IIcon icon64 = new IIcon() {
        @Override
        public int getIconWidth() {
            return 64;
        }

        @Override
        public int getIconHeight() {
            return 64;
        }

        @Override
        public float getMinU() {
            return 0;
        }

        @Override
        public float getMaxU() {
            return 1.0f;
        }

        @Override
        public float getInterpolatedU(double input) {
            return (float)(input/64.0);
        }

        @Override
        public float getMinV() {
            return 0;
        }

        @Override
        public float getMaxV() {
            return 1.0f;
        }

        @Override
        public float getInterpolatedV(double input) {
            return (float)(input/64.0);
        }

        @Override
        public String getIconName() {
            return "icon64";
        }
    };

    public static IIcon icon16 = new IIcon() {
        @Override
        public int getIconWidth() {
            return 16;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }

        @Override
        public float getMinU() {
            return 0;
        }

        @Override
        public float getMaxU() {
            return 1.0f;
        }

        @Override
        public float getInterpolatedU(double input) {
            return (float)(input/16.0);
        }

        @Override
        public float getMinV() {
            return 0;
        }

        @Override
        public float getMaxV() {
            return 1.0f;
        }

        @Override
        public float getInterpolatedV(double input) {
            return (float)(input/16.0);
        }

        @Override
        public String getIconName() {
            return "icon16";
        }
    };

    public static int getDirectionInteger(ForgeDirection direction)
    {
        if (direction==ForgeDirection.UP)
        {
            return 0;
        }
        else if (direction==ForgeDirection.DOWN)
        {
            return 1;
        }
        else if (direction==ForgeDirection.NORTH)
        {
            return 2;
        }
        else if (direction==ForgeDirection.SOUTH)
        {
            return 3;
        }
        else if (direction==ForgeDirection.WEST)
        {
            return 4;
        }
        else
        {
            return 5;
        }
    }

    public static ForgeDirection getDirectionDirection(int direction)
    {
        switch (direction)
        {
            case 1:
                return ForgeDirection.DOWN;
            case 2:
                return ForgeDirection.NORTH;
            case 3:
                return ForgeDirection.SOUTH;
            case 4:
                return ForgeDirection.WEST;
            case 5:
                return ForgeDirection.EAST;
            default:
                return ForgeDirection.UP;
        }
    }

    public static ForgeDirection getOppositeDirectionDirection(ForgeDirection direction)
    {
        if (direction==ForgeDirection.UP)
        {
            return ForgeDirection.DOWN;
        }
        else if (direction==ForgeDirection.DOWN)
        {
            return ForgeDirection.UP;
        }
        else if (direction==ForgeDirection.NORTH)
        {
            return ForgeDirection.SOUTH;
        }
        else if (direction==ForgeDirection.SOUTH)
        {
            return ForgeDirection.NORTH;
        }
        else if (direction==ForgeDirection.EAST)
        {
            return ForgeDirection.WEST;
        }
        else
        {
            return ForgeDirection.EAST;
        }
    }

    public static int getOppositeDirectionInt(int direction)
    {
        switch (direction)
        {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 4;
            default: //default to DOWN
                return 1;
        }
    }

    public static ForgeDirection getOppositeDirectionDirection(int direction)
    {
        return getOppositeDirectionDirection(getDirectionDirection(direction));
    }

    public static int getOppositeDirectionInt(ForgeDirection direction)
    {
        return getOppositeDirectionInt(getDirectionInteger(direction));
    }

    public static Location[] getLocationsExcludingCenter(int x, int y, int z, int minI, int maxI, int minJ, int maxJ, int minK, int maxK)
    {
        int totalLocs = (maxI-minI+1)*(maxJ-minJ+1)*(maxK-minK+1)-1;
        Location[] locations = new Location[totalLocs];
        int index = 0;

        for (int i=minI;i<=maxI;i++)
        {
            for (int j=minJ;j<=maxK;j++)
            {
                for (int k=minK;k<=maxK;k++)
                {
                    if (i!=0 || j!=0 || k!=0)
                    {
                        locations[index++] = new Location(x+i,y+k,z+j);
                    }
                }
            }
        }

        return locations;
    }

    //returns a location array for all adjacent blocks. the blacklist is a bitwise check to see if we want to include a specific direction (U-D-N-S-W-E)
    public static Location[] getAdjacentLocations(int x, int y, int z, int blacklist)
    {
        int numLocations = 6-Integer.bitCount(blacklist);
//        LogHelper.info("numLocations = " + numLocations);
        Location[] locations = new Location[numLocations];
        int index = 0;

        if ((blacklist&1)==0)
        {
            locations[index++] = new Location(x,y+1,z);
        }
        if ((blacklist&2)==0)
        {
            locations[index++] = new Location(x,y-1,z);
        }
        if ((blacklist&4)==0)
        {
            locations[index++] = new Location(x,y,z-1);
        }
        if ((blacklist&8)==0)
        {
            locations[index++] = new Location(x,y,z+1);
        }
        if ((blacklist&16)==0)
        {
            locations[index++] = new Location(x-1,y,z);
        }
        if ((blacklist&32)==0)
        {
            locations[index++] = new Location(x+1,y,z);
        }

        return locations;
    }


    public static int[] getAdjacentDirections(int blacklist)
    {
        int numDirections = 6-Integer.bitCount(blacklist);
        int[] directions = new int[numDirections];
        int index = 0;
        int val = 1;
        for (int i=0;i<6;i++)
        {
            if ((val&blacklist)==0)
            {
                directions[index++] = i;
            }
            val *= 2;
        }

        return directions;
    }

    public static int getDirectionBlacklistValue(ForgeDirection direction)
    {
        return (int)(Math.pow(2.0,getDirectionInteger(direction)));
    }

    public static void writeLocationArrayToNBT(String tagName,Location[] locations,NBTTagCompound tagCompound)
    {
        if (locations!=null)
        {
            NBTTagList tagList = new NBTTagList();

            for (int i=0;i<locations.length;i++)
            {
                NBTTagCompound tag = new NBTTagCompound();
                locations[i].writeToNBT(tag);

                tagList.appendTag(tag);
            }

            tagCompound.setTag(tagName,tagList);
        }
    }

    public static Location[] readLocationArrayFromNBT(String tagName,NBTTagCompound tagCompound)
    {
        if (tagCompound.hasKey(tagName))
        {
            NBTTagList tagList = tagCompound.getTagList(tagName,10);

            Location[] locations = new Location[tagList.tagCount()];
            for (int i=0;i<tagList.tagCount();i++)
            {
                Location loc = new Location();
                loc.readFromNBT(tagList.getCompoundTagAt(i));
                locations[i] = loc;
            }

            return locations;
        }
        return null;
    }

    public static TileEntity getTileInDirection(World world, int x, int y, int z, int direcOrd)
    {
        switch (direcOrd)
        {
            case 1: //up
                return world.getTileEntity(x,y+1,z);
            case 2: //north
                return world.getTileEntity(x,y,z-1);
            case 3: //south
                return world.getTileEntity(x,y,z+1);
            case 4: //west
                return world.getTileEntity(x-1,y,z);
            case 5: //east
                return world.getTileEntity(x+1,y,z);
            default : //down
                return world.getTileEntity(x,y-1,z);
        }
    }

    public static TileEntity getTileInDirection(World world, int x, int y, int z, ForgeDirection direction)
    {
        return world.getTileEntity(x+direction.offsetX,y+direction.offsetY,z+direction.offsetZ);
    }

    public static int[] convertToIntegerArray(boolean[] array)
    {
        int[] toReturn = new int[array.length];

        for (int i=0;i<array.length;i++)
        {
            toReturn[i] = (array[i]?1:0);
        }

        return toReturn;
    }

    public static boolean[] convertToBooleanArray(int[] array)
    {
        boolean[] toReturn = new boolean[array.length];

        for (int i=0;i<array.length;i++)
        {
            toReturn[i] = array[i]==1;
        }

        return toReturn;
    }

}
