package com.M4thG33k.m4ththings.utility;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by M4thG33k on 7/2/2015.
 */
public class MiscHelper {

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
}
