package com.M4thG33k.m4ththings.particles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.MathHelper;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import java.util.Arrays;

/**
 * Created by M4thG33k on 7/4/2015.
 */
public class ParticleFluidOrbArc extends ParticleFluidOrb {

    double radius; //distance between start and end points
    double theta; //rotation angle used about the z axis
    double phi; //rotation angle used about the y axis
    double r1; //all the "r" values are the rotation values used in matrix multiplications
    double r2;
    double r3;
    double r4;

    double[] NAN_avoider = {0,0,-1};

    double[] startVals = new double[3];
    int xDirec;

    public ParticleFluidOrbArc(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, Fluid fluid, int life)
    {
        super(world,startX,startY,startZ,0,0,0,fluid,1,life);
        radius = MathHelper.getDistance(startX,startY,startZ,endX,endY,endZ);
        theta = Math.asin(-(endY-startY)/radius);
        xDirec = (int)Math.signum(endX-startX);
        r1 = Math.cos(theta);
        r2 = Math.sin(theta);
        phi = Math.asin(-(endZ-startZ)/(r1*radius));
//        r3 = xDirec*Math.sqrt((r1*radius)*(r1*radius)-(endZ-startZ)*(endZ-startZ))/(r1*radius);
        r3 = Math.cos(phi);
        r4 = Math.sin(phi);
//        r4 = -(endZ-startZ)/(r1*radius);
        startVals[0] = startX;
        startVals[1] = startY;
        startVals[2] = startZ;
    }

    @Override
    public void moveEntity(double xMotion, double yMotion, double zMotion) {
//        this.posY += 0.05;
        double t = radius * ((double) this.particleAge) / ((double) this.particleMaxAge);
        if (!(Arrays.equals(startVals,NAN_avoider))) {
//            double t = radius * ((double) this.particleAge) / ((double) this.particleMaxAge);

            double[] pos = calculatePosition(t);

            this.posX = startVals[0] + xDirec * pos[0];
            this.posY = startVals[1] + pos[1];
            this.posZ = startVals[2] + pos[2];
        }
        else
        {
            posX = 0;
            posY = f(t);
            posZ = -t;
        }
//
//        LogHelper.info("Particle position: " + StringHelper.makeCoords(this.posX,this.posY,this.posZ));
    }

    private double f(double t) //a simple trig function of one variable. hits the "t" axis when t=0 & t=radius
    {
        return 0.25*Math.sin(MathHelper.pi*t/radius);
    }

    private double g(double t) //a helper function to save computation time
    {
        return r1*t+r2*f(t);
    }

    private double[] calculatePosition(double t)
    {
        double[] pos = new double[3];
        double gOfT = g(t);
        pos[0] = r3*gOfT;
        pos[1] = -r2*t+r1*f(t);
        pos[2] = -r4*gOfT;

        return pos;
    }
}
