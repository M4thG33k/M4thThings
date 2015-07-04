package com.M4thG33k.m4ththings.utility;

import com.M4thG33k.m4ththings.reference.Reference;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class StringHelper {

    public static String makeCoords(int x, int y, int z)
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public static String makeCoords(double x, double y, double z)
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public static String makeCoords(float x, float y, float z)
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public static String nameHelper()
    {
        return Reference.MOD_ID + "_";
    }

    public static String iconHelper()
    {
        return Reference.MOD_ID + ":";
    }
}
