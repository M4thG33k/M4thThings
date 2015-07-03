package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.M4thThings;
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
 * Created by M4thG33k on 6/22/2015.
 */
public class TileQuantumTank extends TileEntity implements IFluidTank, IFluidHandler {

    protected int timer;
    protected FluidStack fluid;
    protected int cap = Configurations.QT_CAP;
    protected int verticalDistance;
    protected int orientation;
    protected int tankSize; //this variable controls different visual elements. 0=small, 1=medium, 2=large

    public TileQuantumTank()
    {
        timer = 0;
        fluid = null;
        verticalDistance = 1;
        tankSize = 0;
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

        if (getFluidAmount()>getCapacity())
        {
            fluid.amount = cap;
            prepareSync();
        }

        attemptDrain(1);

    }

    public void setFluid(FluidStack fluidStack)
    {
        if (fluidStack!=null && fluidStack.amount!=0)
        {
            fluid = fluidStack;
            if (fluid.amount>cap)
            {
                fluid.amount = cap;
            }
        }
    }

    public void setEmpty()
    {
        fluid = null;
    }

    //attempts to drain from this tank into a tank 'y' blocks below
    public void attemptDrain(int y)
    {
        if (fluid!=null && getFluidAmount()>0)
        {
            TileEntity tile = worldObj.getTileEntity(xCoord,yCoord-y,zCoord);
            if (tile!=null && tile instanceof TileQuantumTank && ((TileQuantumTank)tile).getOrientation()==0) {
                TileQuantumTank tank = (TileQuantumTank) tile;

                if (tank.getFluidAmount() < tank.getCapacity()) {
                    int transferred = tank.fill(fluid, true);
                    if (transferred == fluid.amount) {
                        fluid = null;
                    } else {
                        fluid.amount -= transferred;
                        prepareSync();
                    }
                }
            }
//            else if (tile!=null && tile instanceof IFluidHandler)
//            {
//                IFluidHandler tank = (IFluidHandler)tile;
//
//                if (tank.fill(ForgeDirection.UP,fluid,false) > 0)
//                {
////                    LogHelper.info("Able to transfer " + tank.fill(ForgeDirection.UP,fluid,false) + "mB from (" + xCoord + ", " + yCoord + ", " + zCoord + ")");
//                    int transferred = tank.fill(ForgeDirection.UP,fluid,true);
////                    LogHelper.info("Actual filled: " + transferred);
//                    FluidStack drained = this.drain(ForgeDirection.DOWN,transferred,true);
////                    LogHelper.info("Actual drained: " + drained.amount);
////                    drain(ForgeDirection.DOWN,tank.fill(ForgeDirection.UP,fluid,true),true);
////                    drain(tank.fill(ForgeDirection.UP,fluid,true),true);
//                }
//            }
        }
    }

    public int getTimer()
    {
        return timer;
    }

    public void prepareSync()
    {
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        markDirty();
    }

    //IFluidTank
    @Override
    public FluidStack getFluid()
    {
        return fluid;
    }

    @Override
    public int getFluidAmount()
    {
        return fluid==null ? 0 : fluid.amount;
    }

    @Override
    public int getCapacity()
    {
        return cap;
    }

    @Override
    public FluidTankInfo getInfo()
    {
        return new FluidTankInfo(fluid,cap);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
//        LogHelper.info("Filling at " + myCoords());
        if (resource==null || resource.amount==0)
        {
            return 0;
        }

        if (fluid == null)
        {
            if (doFill)
            {
                fluid = new FluidStack(resource.getFluid(), Math.min(cap,resource.amount));
                prepareSync();
            }
            return Math.min(cap,resource.amount);
        }

        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }

        if (fluid.amount!=cap)
        {
            int toTransfer = Math.min(cap - fluid.amount, resource.amount);
            if (doFill) {
                fluid.amount += toTransfer;
                prepareSync();
            }
            return toTransfer;
        }
        else{
            TileEntity tEnt = worldObj.getTileEntity(xCoord,yCoord+verticalDistance,zCoord);
            if (tEnt!=null && tEnt instanceof TileQuantumTank)
            {
                return ((TileQuantumTank)tEnt).fill(ForgeDirection.DOWN,resource,doFill);
            }
            else{
                return 0;
            }
        }

    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
//        LogHelper.info("Draining at " + myCoords());
        if (fluid == null || fluid.amount==0)
        {
            TileEntity tEnt = worldObj.getTileEntity(xCoord,yCoord-verticalDistance,zCoord);
            if (tEnt!=null && tEnt instanceof TileQuantumTank)
            {
                return ((TileQuantumTank)tEnt).drain(maxDrain,doDrain);
            }
            return null;
        }

        FluidStack toReturn = new FluidStack(fluid.getFluid(), Math.min(fluid.amount,maxDrain));

        if (doDrain)
        {
            if (toReturn.amount==fluid.amount)
            {
                fluid = null;
            }
            else
            {
                fluid.amount -= toReturn.amount;
            }
            prepareSync();
        }
        return toReturn;
    }

    //IFluidHandler
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (isValveDirection(from))
        {
//            LogHelper.info("Hello! Client?: " + worldObj.isRemote);
            int toReturn = this.fill(resource,doFill);

            if (Configurations.ENABLE_TANK_PARTICLES && doFill && toReturn>0) {
//                LogHelper.info("Sending QT packet to client (hopefully).");
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord, MiscHelper.getDirectionInteger(from),1,FluidRegistry.getFluidName(resource),toReturn,tankSize),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,16));
//                fillParticles(0,1);
            }
            return toReturn;
        }

        return 0;
    }

    @Override
    public  FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (isValveDirection(from))
        {
            FluidStack toReturn = this.drain(resource.amount,doDrain);

            if (Configurations.ENABLE_TANK_PARTICLES && doDrain && toReturn.amount>0)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord,MiscHelper.getDirectionInteger(from),0,FluidRegistry.getFluidName(resource),toReturn.amount,tankSize),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,16));
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
            FluidStack toReturn = this.drain(maxDrain,doDrain);

            if (Configurations.ENABLE_TANK_PARTICLES && doDrain && toReturn.amount>0)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord,MiscHelper.getDirectionInteger(from),0,FluidRegistry.getFluidName(toReturn),toReturn.amount,tankSize),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,16));
            }

            return toReturn;
        }

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return isValveDirection(from);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return isValveDirection(from);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        FluidTankInfo[] toReturn = new FluidTankInfo[1];
        toReturn[0] = getInfo();
        return toReturn;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        if (tagCompound.hasKey("x"))
        {
            super.readFromNBT(tagCompound);
        }

        if (tagCompound.hasKey("fluid") && tagCompound.hasKey("amount"))
        {
            fluid = new FluidStack(FluidRegistry.getFluid(tagCompound.getString("fluid")),tagCompound.getInteger("amount"));
        }
        else
        {
            fluid = null;
        }

        if (tagCompound.hasKey("timer"))
        {
            timer = tagCompound.getInteger("timer");
        }
        else
        {
            timer = 0;
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

        if (fluid!=null && fluid.amount!=0)
        {
            tagCompound.setString("fluid",fluid.getFluid().getName());
            tagCompound.setInteger("amount",fluid.amount);
        }

        tagCompound.setInteger("orientation",orientation);

        tagCompound.setInteger("timer",timer);
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
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
    }

    public String myCoords()
    {
        return "(" + xCoord + ", " + yCoord + ", " + zCoord + ")";
    }

    public boolean isStructureBuilt()
    {
        return true;
    }

    public double getPercentFilled()
    {
        return ((double)getFluidAmount())/((double)getCapacity());
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
        double verticalOffset = getPercentFilled()*Math.sin(((double)timer)*MathHelper.pi/(180.0));
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

}


















