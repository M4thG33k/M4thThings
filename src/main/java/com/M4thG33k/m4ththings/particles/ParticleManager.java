package com.M4thG33k.m4ththings.particles;

import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import com.M4thG33k.m4ththings.utility.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by M4thG33k on 7/28/2015.
 */
public class ParticleManager {

    public static Fluid waterFluid = FluidRegistry.getFluid("water");

    public static void tankFillParticles(World worldObj, int xCoord, int yCoord, int zCoord, int direction, int isFilling, Fluid fluid, int amount, int tankSize)
    {
        if (!worldObj.isRemote)
        {
            return; //attempt to avoid spawning particles on the server
        }

        EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;
        TileQuantumTank tileQuantumTank = (TileQuantumTank)(worldObj.getTileEntity(xCoord,yCoord,zCoord));


        double centerSpeed = 0.04; //this corresponds to the speed to/from the center (most of the velocity should come from this)
        double centerDistance; //the distance from the center to the valve
        double radius = 0.375*tileQuantumTank.getPercentFilled();
        double rad;
        double d1; //d1 and d2 are the planar directions
        double d2;
        EntityFX fluidOrb;
        double radiusMultiplier;
        double verticalOffset = tileQuantumTank.getPercentFilled()*Math.sin(((double)tileQuantumTank.getTimer())* MathHelper.pi/(180.0));
        double randomHelper;
        double baseLifeLength = 10.0;

        switch (tankSize)
        {
            case 1: //3x3
                centerSpeed = 0.14;
                centerDistance = 1.4;
                radius = 0.09375*tileQuantumTank.getPercentFilled();
                radiusMultiplier = 10.0;
                verticalOffset *= 0.125;
                break;
            case 2: //9x9
                centerSpeed = 0.44;
                centerDistance = 4.4;
                radius = 0.3*tileQuantumTank.getPercentFilled();
                radiusMultiplier = 10.0;
                verticalOffset *= 0.45;
                break;
            default: //1x1
                centerSpeed = 0.04;
                centerDistance = 0.4;
                radius = 0.0375*tileQuantumTank.getPercentFilled();
                radiusMultiplier = 10.0;
                verticalOffset *= 0.05;
                break;
        }

//        LogHelper.info("Radius: " + radius);
//        String name = "happyVillager";

//        LogHelper.info("Spawning QT particles! " + direction + ":" + isFilling);
        switch (direction)
        {
            case 1: //from DOWN
                if (isFilling==1) //do filling particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5, yCoord + 0.5 - centerDistance, zCoord + 0.5, d1 * radius, centerSpeed*randomHelper, d2 * radius, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                else //do draining particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 + radiusMultiplier*d1*radius, yCoord + 0.5, zCoord + 0.5 + radiusMultiplier*d2*radius , -d1 * radius, -centerSpeed*randomHelper, -d2 * radius, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
            case 2: //from NORTH
                if (isFilling==1) //do filling particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5 - centerDistance, d1 * radius, d2 * radius+verticalOffset/10.0, centerSpeed*randomHelper,  fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                else //do draining particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 + radiusMultiplier*d1*radius, yCoord + 0.5+ radiusMultiplier*d2*radius + verticalOffset, zCoord + 0.5  , -d1 * radius, -d2 * radius - verticalOffset/10.0, -centerSpeed*randomHelper, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
            case 3: //from SOUTH
                if (isFilling==1) //do filling particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5 + centerDistance, d1 * radius, d2 * radius+verticalOffset/10.0, -centerSpeed*randomHelper,  fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                else //do draining particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 + radiusMultiplier*d1*radius, yCoord + 0.5+ radiusMultiplier*d2*radius+verticalOffset, zCoord + 0.5  , -d1 * radius, -d2 * radius-verticalOffset/10.0, centerSpeed*randomHelper, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
            case 4: //from WEST
                if (isFilling==1) //do filling particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 - centerDistance, yCoord + 0.5, zCoord + 0.5, centerSpeed*randomHelper, d1 * radius+verticalOffset/10.0, d2 * radius,  fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                else //do draining particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 , yCoord + 0.5+ radiusMultiplier*d2*radius+verticalOffset, zCoord + 0.5 + radiusMultiplier*d1*radius , -centerSpeed*randomHelper, -d2 * radius-verticalOffset/10.0, -d1 * radius, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
            case 5: //from EAST
                if (isFilling==1) //do filling particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 + centerDistance, yCoord + 0.5, zCoord + 0.5, -centerSpeed*randomHelper, d1 * radius+verticalOffset/10.0, d2 * radius,  fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                else //do draining particles
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 , yCoord + 0.5+ radiusMultiplier*d2*radius+verticalOffset, zCoord + 0.5 + radiusMultiplier*d1*radius , centerSpeed*randomHelper, -d2 * radius-verticalOffset/10.0, -d1 * radius, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
            default: //from UP
                if (isFilling==1) //do filling particles
                {
//                    LogHelper.info("I should be rendering filling particles upward");
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5, yCoord + 0.5 + centerDistance, zCoord + 0.5, d1 * radius, -centerSpeed*randomHelper, d2 * radius, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
//                    worldObj.spawnParticle(name,xCoord+0.5,yCoord+1,zCoord+0.5,0.0,-1.0,0.0);
                }
                else //do draining particles
                {
//                    LogHelper.info("I should be rendering draining particles upward");
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0-Math.random()*0.1;
                        d1 = Math.sin(rad);
                        d2 = Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(worldObj, xCoord + 0.5 + radiusMultiplier*d1*radius, yCoord + 0.5, zCoord + 0.5 + radiusMultiplier*d2*radius , -d1 * radius, centerSpeed*randomHelper, -d2 * radius, fluid,tankSize,(int)(baseLifeLength/randomHelper));
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
        }
    }

    public static void AAFillParticles(World worldObj, int xCoord, int yCoord, int zCoord)
    {
        boolean PARTICLES_ALLOWED = Configurations.ENABLE_WATER_GEN_PARTICLES;
        int WATER_GEN = Configurations.WATER_GEN_PER_TICK;

        if (!PARTICLES_ALLOWED || !(worldObj.isRemote))
        {
            return;
        }
        EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;

        double theta;
        double radius;
        double baseSpeed = 0.01;
        double random;
        double baseLife = 25;
        EntityFX fluidOrb;
        double d1;
        double d2;

        for (int i=0;i<=((int)((double)WATER_GEN/5.0));i++)
        {
            theta = MathHelper.randomRad();
            radius = MathHelper.randomDoubleBetween(0.05,0.45);
            random = MathHelper.randomDoubleBetween(0.75,1.0);
            d1 = radius*Math.sin(theta);
            d2 = radius*Math.cos(theta);

            fluidOrb = new ParticleFluidOrb(worldObj,xCoord+0.5+d1,yCoord,zCoord+0.5+d2,-d1*random/baseLife,baseSpeed*random,-d2*random/baseLife,waterFluid,0,((int)(baseLife/random)));
            renderer.addEffect(fluidOrb);
        }
    }

    public static void AATransferParticles(World worldObj,int xCoord, int yCoord, int zCoord, int x, int y, int z, int transferred)
    {

        boolean PARTICLES_ALLOWED = Configurations.ENABLE_WATER_GEN_PARTICLES;


        if (!PARTICLES_ALLOWED || !(worldObj.isRemote))
        {
            return;
        }

//        LogHelper.info("Inside transferParticles");
        EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;

        EntityFX fluidOrb;// = new ParticleFluidOrbArc(worldObj,this.xCoord+0.5,this.yCoord+0.51,this.zCoord+0.5,x+0.5,y+0.5,z+0.5,waterFluid,10);
//        renderer.addEffect(fluidOrb);

        int baseLife = 10;
        int lifeRandomizer;
        double xRand;
        double zRand;
        int numParticles = Math.max(1, (int) ((double) transferred / 250));

        for (int i=0;i<numParticles;i++)
        {
            lifeRandomizer = MathHelper.randomIntInclusiveBetween(-1,1);
            xRand = MathHelper.randomDoubleBetween(0.45,0.55);
            zRand = MathHelper.randomDoubleBetween(0.45,0.55);
            fluidOrb = new ParticleFluidOrbArc(worldObj,xCoord+xRand,yCoord+0.51,zCoord+zRand,x+0.5,y+0.5,z+0.5,waterFluid,baseLife+lifeRandomizer);
            renderer.addEffect(fluidOrb);
        }
    }
}
