package com.M4thG33k.m4ththings.utility;

import java.util.Random;

/**
 * Created by M4thG33k on 7/2/2015.
 */
public class MathHelper {

    public static Random rand = new Random();
    public static double pi = 3.14159265358979;

    public static int randomAngle()
    {
        return rand.nextInt(360);
    }

    public static double randomRad()
    {
        return ((double)randomAngle())*(pi/180.0);
    }

    public static double randomDoubleBetween(double a,double b)
    {
        return (Math.random()*(b-a)+a);
    }

    public static int randomIntInclusiveBetween(int a, int b)
    {
        return rand.nextInt((b-a)+1)+a;
    }

    public static double getDistance(double x, double y, double z, double r, double s, double t)
    {
        return Math.sqrt((x-r)*(x-r)+(y-s)*(y-s)+(z-t)*(z-t));
    }
}
