package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.packets.ModPackets;
import com.M4thG33k.m4ththings.packets.PacketFilling;
import com.M4thG33k.m4ththings.particles.ParticleFluidOrb;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.MathHelper;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class TileQuantumTank extends TileFluidHandler {
    protected int timer;
    protected int cap;
    protected int orientation;
    protected int tankSize; //this variable controls different visual elements. 0=small, 1=medium, 2=large

    public TileQuantumTank()
    {
        super();
        cap = Configurations.QT_CAP;
        tankSize = 0;
        timer = 0;
        tank = new FluidTank(cap);
    }

    public void advanceTimer()
    {
        timer += 1;
        if (timer>=360)
        {
            timer = 0;
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        advanceTimer();

        if (tank.getFluidAmount()>cap)
        {
            tank.setFluid(new FluidStack(tank.getFluid().getFluid(),cap));
            prepareSync();
        }

        attemptDrain();
    }

    //attempts to drain fluid into another TileFluidHandler directly beneath it at a rate of 500mb/t
    public void attemptDrain()
    {
        if (tank.getFluidAmount()==0)
        {
            return;
        }
        TileEntity tileEntity = worldObj.getTileEntity(xCoord,yCoord-1,zCoord);

        if (tileEntity!=null && tileEntity instanceof TileQuantumTank)
        {
            int transferred = ((IFluidHandler)tileEntity).fill(ForgeDirection.UP,new FluidStack(tank.getFluid().getFluid(),Math.min(500,tank.getFluidAmount())),true);
            this.drain(ForgeDirection.DOWN,transferred,true);
        }
    }



    public void setEmpty()
    {
        tank.setFluid(null);
    }

    public void prepareSync()
    {
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        markDirty();
    }

    /* begin IFluidHandler */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (isValveDirection(from))
        {
            if (from==ForgeDirection.DOWN && tank.getFluidAmount()==cap)
            {
                TileEntity tileEntity = worldObj.getTileEntity(xCoord,yCoord+1,zCoord);

                if (tileEntity != null && tileEntity instanceof TileQuantumTank)
                {
                    int toReturn = ((TileQuantumTank)tileEntity).fill(from,resource,doFill);

                    if (doFill && Configurations.ENABLE_TANK_PARTICLES && toReturn>0)
                    {
                        ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord, MiscHelper.getDirectionInteger(from),1, FluidRegistry.getFluidName(resource),toReturn,tankSize), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
                        ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord, MiscHelper.getDirectionInteger(ForgeDirection.UP),0, FluidRegistry.getFluidName(resource),toReturn,tankSize), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
                    }

                    if (doFill)
                    {
                        prepareSync();
                    }

                    return toReturn;

                }

            }

            //if we get here, the current tank is not full yet or we aren't filling from the bottom
            int toReturn = tank.fill(resource,doFill);

            if (doFill && Configurations.ENABLE_TANK_PARTICLES && toReturn>0)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord, MiscHelper.getDirectionInteger(from),1, FluidRegistry.getFluidName(resource),toReturn,tankSize), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            }

            if (doFill)
            {
                prepareSync();
            }

            return toReturn;
        }

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (isValveDirection(from))
        {
            FluidStack toReturn = tank.drain(resource.amount,doDrain);

            if (doDrain && Configurations.ENABLE_TANK_PARTICLES && toReturn.amount>0)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord, MiscHelper.getDirectionInteger(from),0, FluidRegistry.getFluidName(resource),toReturn.amount,tankSize), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            }

            if (doDrain)
            {
                prepareSync();
            }
            return toReturn;
        }

        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        if (isValveDirection(from))
        {
            FluidStack toReturn = tank.drain(maxDrain,doDrain);

            if (doDrain && Configurations.ENABLE_TANK_PARTICLES && toReturn.amount > 0)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord, MiscHelper.getDirectionInteger(from),0, FluidRegistry.getFluidName(toReturn),toReturn.amount,tankSize), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            }

            if (doDrain)
            {
                prepareSync();
            }

            return toReturn;
        }

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from,Fluid fluid)
    {
        return isValveDirection(from);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return isValveDirection(from);
    }

    /* end IFluidHandler */

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        if (tagCompound.hasKey("x"))
        {
            this.xCoord = tagCompound.getInteger("x");
            this.yCoord = tagCompound.getInteger("y");
            this.zCoord = tagCompound.getInteger("z");
        }

        if (!tagCompound.hasKey("Empty"))
        {
            tank.setFluid(FluidStack.loadFluidStackFromNBT(tagCompound));
        }
        else
        {
            tank.setFluid(null);
        }

        if (tagCompound.hasKey("timer"))
        {
            timer = tagCompound.getInteger("timer");
        }

        if (tagCompound.hasKey("orientation"))
        {
            orientation = tagCompound.getInteger("orientation");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("timer",timer);
        tagCompound.setInteger("orientation",orientation);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();

        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,1,tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
        prepareSync();
    }

    public String myCoords()
    {
        return  "(" + xCoord + ", " + yCoord + ", " + zCoord + ")";
    }

    public boolean isStructureBuilt()
    {
        return true;
    }

    public double getPercentFilled()
    {
        return ((double)tank.getFluidAmount())/((double)cap);
    }

    public double getRoundedPercentFilled()
    {
        int percent = (int)(getPercentFilled()*10000);
        return ((double)percent)/100.0;
    }

    public int getOrientation()
    {
        return orientation;
    }

    public int getTimer()
    {
        return timer;
    }

    public int getTankSize()
    {
        return tankSize;
    }

    public boolean isValveDirection(ForgeDirection from)
    {
        return ((orientation==0&&(from==ForgeDirection.DOWN || from==ForgeDirection.UP)) || (orientation==1&&(from==ForgeDirection.NORTH||from==ForgeDirection.SOUTH)) || (orientation==2&&(from==ForgeDirection.EAST||from==ForgeDirection.WEST)));
    }

    public void setOrientation(int orient)
    {
        this.orientation = orient;
    }

    public void fillParticles(int direction, int isFilling,Fluid fluid,int amount,int tankSize)
    {

        EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;


        double centerSpeed = 0.04; //this corresponds to the speed to/from the center (most of the velocity should come from this)
        double centerDistance; //the distance from the center to the valve
        double radius = 0.375*getPercentFilled();
        double rad;
        double d1; //d1 and d2 are the planar directions
        double d2;
        EntityFX fluidOrb;
        double radiusMultiplier;
        double verticalOffset = getPercentFilled()*Math.sin(((double)timer)* MathHelper.pi/(180.0));
        double randomHelper;
        double baseLifeLength = 10.0;

        switch (tankSize)
        {
            case 1: //3x3
                centerSpeed = 0.14;
                centerDistance = 1.4;
                radius = 0.09375*getPercentFilled();
                radiusMultiplier = 10.0;
                verticalOffset *= 0.125;
                break;
            case 2: //9x9
                centerSpeed = 0.44;
                centerDistance = 4.4;
                radius = 0.3*getPercentFilled();
                radiusMultiplier = 10.0;
                verticalOffset *= 0.45;
                break;
            default: //1x1
                centerSpeed = 0.04;
                centerDistance = 0.4;
                radius = 0.0375*getPercentFilled();
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
//        worldObj.spawnParticle("happyVillager",xCoord+1.5,yCoord+1.5+0.0,zCoord+1.5,0.0,0.0,0.0);
//        EntityFX fluidOrb = new ParticleFluidOrb(worldObj,xCoord+1.0,yCoord+1.0,zCoord+1.0,0.0,-0.1,0.0,fluid);
//        Minecraft.getMinecraft().effectRenderer.addEffect(fluidOrb);
    }

    public int getFluidAmount()
    {
        return tank.getFluidAmount();
    }

    public int getCapacity()
    {
        return cap;
    }

    public FluidStack getFluid()
    {
        return tank.getFluid();
    }

    public void setFluid(FluidStack fluid)
    {
        tank.setFluid(fluid);
    }

    public int fill(FluidStack resource,boolean doFill)
    {
        int toReturn = tank.fill(resource,doFill);
        if (doFill)
        {
            prepareSync();
        }
        return toReturn;
    }

    public FluidStack drain(int maxDrain,boolean doDrain)
    {
        FluidStack toReturn = tank.drain(maxDrain, doDrain);
        if (doDrain)
        {
            prepareSync();
        }
        return toReturn;
    }

    public  FluidTankInfo getInfo()
    {
        return tank.getInfo();
    }

    public void writeFluidDataToNBT(NBTTagCompound tagCompound)
    {
        tank.writeToNBT(tagCompound);
    }
}
