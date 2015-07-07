package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.interfaces.IM4thNBTSync;
import com.M4thG33k.m4ththings.packets.ModPackets;
import com.M4thG33k.m4ththings.packets.PacketNBT;
import com.M4thG33k.m4ththings.particles.ParticleFluidOrb;
import com.M4thG33k.m4ththings.particles.ParticleFluidOrbArc;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class TileWaterGenerator extends TileEntity implements IFluidHandler, IM4thNBTSync {

    FluidTank tank = new FluidTank(2000);
    int timer;
    protected int WATER_GEN = Configurations.WATER_GEN_PER_TICK;
    protected boolean PARTICLES_ALLOWED = Configurations.ENABLE_WATER_GEN_PARTICLES;
    protected Fluid waterFluid;
    protected Location[] locations;
    protected int locIndex = 0;
    protected int numLocations;
    protected boolean isAdvanced;



    public TileWaterGenerator()
    {
        timer = 0;
        waterFluid  = FluidRegistry.getFluid("water");
        isAdvanced = true;
    }

    public void grabLocations()
    {
        if (isAdvanced) {
            locations = MiscHelper.getLocationsExcludingCenter(xCoord, yCoord, zCoord, -2, 2, -2, 2, 0, 2);
            numLocations = locations.length;
        }
        else
        {
            locations = new Location[5];
            numLocations = 0;
            locations[numLocations++] = new Location(xCoord,yCoord+1,zCoord);
            locations[numLocations++] = new Location(xCoord,yCoord,zCoord-1);
            locations[numLocations++] = new Location(xCoord,yCoord,zCoord+1);
            locations[numLocations++] = new Location(xCoord-1,yCoord,zCoord);
            locations[numLocations++] = new Location(xCoord+1,yCoord,zCoord);
        }

//        LogHelper.info("NumLocations = " + numLocations);
    }

    @Override
    public void updateEntity() {
        if (locations==null)
        {
            grabLocations();
        }

//        LogHelper.info("is indirectly powered: " + worldObj.isBlockIndirectlyGettingPowered(xCoord,yCoord,zCoord));
//        LogHelper.info("power input: " + worldObj.getBlockPowerInput(xCoord,yCoord,zCoord));
//        LogHelper.info("strongest indirect powered: " + worldObj.getStrongestIndirectPower(xCoord,yCoord,zCoord));


        if (worldObj.isBlockIndirectlyGettingPowered(xCoord,yCoord,zCoord) || worldObj.getBlockPowerInput(xCoord,yCoord,zCoord)>0 || worldObj.getStrongestIndirectPower(xCoord,yCoord,zCoord)>0) //if the block is getting a redstone signal, it doesn't fill itself or any other tanks (also freezes the tile's timer)
        {
            return;
        }

        advanceTimer();
//        LogHelper.info("Redstone Level: " + worldObj.getBlockPowerInput(xCoord,yCoord,zCoord));
        //first, try to get fluid from below
        if (tank.getFluidAmount()<tank.getCapacity() && worldObj.getBlock(xCoord,yCoord-1,zCoord)==Blocks.water)
        {
            int numSource = 0;
            Block block = worldObj.getBlock(xCoord-1,yCoord-1,zCoord);
            int meta = worldObj.getBlockMetadata(xCoord-1,yCoord-1,zCoord);
            if (block== Blocks.water && meta == 0)
            {
                numSource += 1;
            }
            block = worldObj.getBlock(xCoord+1,yCoord-1,zCoord);
            meta = worldObj.getBlockMetadata(xCoord+1,yCoord-1,zCoord);
            if (block== Blocks.water && meta == 0)
            {
                numSource += 1;
            }
            block = worldObj.getBlock(xCoord,yCoord-1,zCoord-1);
            meta = worldObj.getBlockMetadata(xCoord,yCoord-1,zCoord-1);
            if (block== Blocks.water && meta == 0)
            {
                numSource += 1;
            }
            block = worldObj.getBlock(xCoord,yCoord-1,zCoord+1);
            meta = worldObj.getBlockMetadata(xCoord,yCoord-1,zCoord+1);
            if (block== Blocks.water && meta == 0)
            {
                numSource += 1;
            }

            if (numSource>=2) //this means we have an infinite water source beneath us
            {
                this.fill(ForgeDirection.DOWN,new FluidStack(FluidRegistry.getFluid("water"),WATER_GEN),true);
                if (worldObj.isRemote)
                {
                    fillParticles();
                }
//                tank.fill(new FluidStack(FluidRegistry.getFluid("water"),10),true);
//                prepareSync();
            }
        }

        //now scan a 5x3x5 area above the water level for IFluidHandlers and try to pass it a bucket of water
        if (tank.getFluidAmount()<1000)
        {
//            LogHelper.info("Exiting because I don't have enough water");
            return;
        }
        if (attemptToFill(locations[locIndex].getX(),locations[locIndex].getY(),locations[locIndex].getZ()))
        {
            locIndex++;
            if (locIndex>=numLocations)
            {
                locIndex = 0;
            }
            return;
        }
        int i= locIndex+1;
        if (i>=numLocations)
        {
            i = 0;
        }

        while (i!=locIndex)
        {
            if (attemptToFill(locations[i].getX(),locations[i].getY(),locations[i].getZ()))
            {
                locIndex = i+1;
                if (locIndex>=numLocations)
                {
                    locIndex = 0;
                }
                return;
            }

            i++;
            if (i>=numLocations)
            {
                i=0;
            }
        }
//        for (int i=-2;i<=2;i++)
//        {
//            for (int j=-2;j<=2;j++)
//            {
//                for (int k=0;k<3;k++)
//                {
//                    if (tank.getFluidAmount()<1000)
//                    {
//                        break;
//                    }
//                    if (i!=0 || j!=0 || k!=0) //ignores itself
//                    {
//                        attemptToFill(xCoord+i,yCoord+k,zCoord+j);
//                    }
//                }
//                if (tank.getFluidAmount()<1000)
//                {
//                    break;
//                }
//            }
//            if (tank.getFluidAmount()<1000)
//            {
//                break;
//            }
//        }

    }

    public void advanceTimer()
    {
        timer += 1;
        if (timer>=720)
        {
            timer = 0;
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (from!=ForgeDirection.DOWN)
        {
            return 0;
        }
        int toReturn = tank.fill(resource, doFill);
        prepareSync();
        return toReturn;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (from!=ForgeDirection.UP || resource == null || !resource.isFluidEqual(tank.getFluid()))
        {
            return null;
        }
        FluidStack toReturn = tank.drain(resource.amount, doDrain);
        prepareSync();
        return toReturn;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from!=ForgeDirection.UP)
        {
            return null;
        }
        FluidStack toReturn = tank.drain(maxDrain, doDrain);
        prepareSync();
        return toReturn;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        tank.readFromNBT(tagCompound);

        timer = tagCompound.getInteger("timer");

        if (tagCompound.hasKey("isAdvanced"))
        {
            isAdvanced = tagCompound.getBoolean("isAdvanced");
        }
        else
        {
            isAdvanced = true;
        }

        if (tagCompound.hasKey("FillLocations"))
        {
            NBTTagList tagList = tagCompound.getTagList("FillLocations",10);

            numLocations = tagList.tagCount();
            locations = new Location[numLocations];
            for (int i=0;i<numLocations;i++)
            {
                Location loc = new Location();
                loc.readFromNBT(tagList.getCompoundTagAt(i));
                locations[i] = loc;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tank.writeToNBT(tagCompound);

        tagCompound.setInteger("timer",timer);

        if (locations!=null)
        {

            NBTTagList tagList = new NBTTagList();

            for (int i=0;i<numLocations;i++)
            {
                NBTTagCompound tag = new NBTTagCompound();
                locations[i].writeToNBT(tag);

                tagList.appendTag(tag);
            }

            tagCompound.setTag("FillLocations",tagList);
        }

        tagCompound.setBoolean("isAdvanced",isAdvanced);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();

        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,1,tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
        prepareSync();
    }

    public void prepareSync()
    {
        if (!worldObj.isRemote)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            this.writeToNBT(tagCompound);
            ModPackets.INSTANCE.sendToAllAround(new PacketNBT(xCoord,yCoord,zCoord,tagCompound),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
        }
//        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
//        markDirty();
    }

    //attempt to fill an IFluidHandler with one bucket of water. returns true if successful (Will not fill if the target cannot accept at least 500mb)
    public boolean attemptToFill(int x, int y, int z)
    {
//        LogHelper.info("Attempting to fill: " + StringHelper.makeCoords(x,y,z));
        Fluid water = FluidRegistry.getFluid("water");
        FluidStack transferAttempt = new FluidStack(water,1000);
        TileEntity tileEntity = worldObj.getTileEntity(x,y,z);
        int transferred;
        ForgeDirection direction;

        if (tileEntity!=null && tileEntity instanceof IFluidHandler && !(tileEntity instanceof TileMedQT) && !(tileEntity instanceof TileQTComponent))
        {
            IFluidHandler target = (IFluidHandler)tileEntity;

            for (int i=0;i<6;i++)
            {
                direction = MiscHelper.getDirectionDirection(i);
                if (target.canFill(direction,water))
                {
                    transferred = target.fill(direction,transferAttempt,false);
                    if (transferred>=500)
                    {
                        target.fill(direction,new FluidStack(water,transferred),true);
                        this.drain(ForgeDirection.UP,transferred,true);

                        if (worldObj.isRemote)
                        {
                            transferParticles(x,y,z,transferred);
                        }
                        return true;
                    }
                }
            }

//            if (target.canFill(ForgeDirection.UP,water))
//            {
//                transferred = target.fill(ForgeDirection.UP,transferAttempt,true);
//                if (transferred>0)
//                {
//                    this.drain(ForgeDirection.UP,transferred,true);
//
//                    if (worldObj.isRemote)
//                    {
//                        transferParticles(x, y, z);
//                    }
//                    return true;
//                }
//            }
//            if (target.canFill(ForgeDirection.DOWN,water))
//            {
//                transferred = target.fill(ForgeDirection.DOWN,transferAttempt,true);
//                if (transferred>0)
//                {
//                    this.drain(ForgeDirection.UP,transferred,true);
//                    return true;
//                }
//            }
//            if (target.canFill(ForgeDirection.NORTH,water))
//            {
//                transferred = target.fill(ForgeDirection.NORTH,transferAttempt,true);
//                if (transferred>0)
//                {
//                    this.drain(ForgeDirection.UP,transferred,true);
//                    return true;
//                }
//            }
//            if (target.canFill(ForgeDirection.SOUTH,water))
//            {
//                transferred = target.fill(ForgeDirection.SOUTH,transferAttempt,true);
//                if (transferred>0)
//                {
//                    this.drain(ForgeDirection.UP,transferred,true);
//                    return true;
//                }
//            }
//            if (target.canFill(ForgeDirection.EAST,water))
//            {
//                transferred = target.fill(ForgeDirection.EAST,transferAttempt,true);
//                if (transferred>0)
//                {
//                    this.drain(ForgeDirection.UP,transferred,true);
//                    return true;
//                }
//            }
//            if (target.canFill(ForgeDirection.WEST,water))
//            {
//                transferred = target.fill(ForgeDirection.WEST,transferAttempt,true);
//                if (transferred>0)
//                {
//                    this.drain(ForgeDirection.UP,transferred,true);
//                    return true;
//                }
//            }
        }
        return false;
    }

    public int getTimer()
    {
        return timer;
    }

    public void fillParticles()
    {
        if (!PARTICLES_ALLOWED)
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

    public double getFillPercentage()
    {
        return ((double)tank.getFluidAmount())/((double)tank.getCapacity());
    }

    public void transferParticles(double x,double y, double z,int transferred)
    {
        if (!PARTICLES_ALLOWED)
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
            fluidOrb = new ParticleFluidOrbArc(worldObj,this.xCoord+xRand,this.yCoord+0.51,this.zCoord+zRand,x+0.5,y+0.5,z+0.5,waterFluid,baseLife+lifeRandomizer);
            renderer.addEffect(fluidOrb);
        }
    }

    public int getFluidAmount()
    {
        return tank.getFluidAmount();
    }

    public void fillUp()
    {
        tank.setFluid(new FluidStack(waterFluid,2000));
    }

    public boolean getIsAdvanced()
    {
        return isAdvanced;
    }

    public void setAdvanced(boolean value)
    {
        isAdvanced = value;
        locations = null;
    }

    @Override
    public void receiveNBTPacket(NBTTagCompound tagCompound) {
        this.readFromNBT(tagCompound);
    }
}
