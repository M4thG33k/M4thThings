package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class TileMedQT extends TileQuantumTank{

    protected boolean isComplete;
    protected boolean isBuilt;
    protected boolean controllerRangeError;

    public TileMedQT()
    {
        super();
        cap = Configurations.MED_QT_CAP;
        tank = new FluidTank(cap);
        isComplete = false;
        isBuilt = false;
        tankSize = 1;
    }

    @Override
    public void updateEntity()
    {
        advanceTimer();

        if (tank.getFluidAmount()>cap)
        {
            tank.setFluid(new FluidStack(tank.getFluid().getFluid(),cap));
            prepareSync();
        }

        if (!isComplete)
        {
            isComplete = isReadyToConstruct();
        }
        else
        {
            if (!isBuilt)
            {
                isBuilt = constructStructure();
                prepareSync();
            }
        }
    }

    public boolean isReadyToConstruct()
    {
        World world = this.worldObj;
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        //check to make sure we don't have another controller "too close", if there is one that's too close, we return false
//        if (world.getBlock(x+2,y,z)==ModBlocks.blockMedQTController || world.getBlock(x,y,z+2)==ModBlocks.blockMedQTController || (y+2<world.getActualHeight() && world.getBlock(x,y+2,z)==ModBlocks.blockMedQTController)) {
//            return false;
//        }

        controllerRangeError = areControllersNearby(2,0);
        if (controllerRangeError)
        {
            return false;
        }

        //now we check if the blocks exist in the world to make the structure
        if (world.getBlock(x,y+1,z)== ModBlocks.blockQTValve) //if this is true, we must be attempting to create a vertical tank
        {
            if (!(world.getBlock(x,y-1,z)==ModBlocks.blockQTValve))
            {
                return false; //missing the bottom valve
            }

            for (int i=-1;i<2;i++)
            {
                for (int j=-1;j<2;j++)
                {
                    for (int k=-1;k<2;k++)
                    {
                        if (i!=0||j!=0)
                        {
                            if (!(world.getBlock(x+i,y+k,z+j)== Blocks.glass))
                            {
                                return false; //missing a piece of glass
                            }
                        }
                    }
                }
            }

            //if we get here, then we are able to construct a vertical tank
            setOrientation(0);
            return true;
        }
        else{ //check the horizontal tank structures
            if (world.getBlock(x,y,z+1)==ModBlocks.blockQTValve) //we must be attempting a North/South tank
            {
//                LogHelper.info("Attempting N-S tank");
                if(!(world.getBlock(x,y,z-1)==ModBlocks.blockQTValve))
                {
//                    LogHelper.info("Missing second valve.");
                    return false; //missing the other valve
                }

                for (int i=-1;i<2;i++)
                {
                    for (int j=-1;j<2;j++)
                    {
                        for (int k=-1;k<2;k++)
                        {
                            if (i!=0||k!=0)
                            {
                                if (!(world.getBlock(x+i,y+k,z+j)==Blocks.glass))
                                {
//                                    LogHelper.info("Missing glass at " + StringHelper.makeCoords(x+i,y+k,z+j));
                                    return false; //missing a piece of glass
                                }
                            }
                        }
                    }
                }
                //if we get here, the North/South tank is complete
                setOrientation(1);
                return true;
            }

            else{
//                LogHelper.info("Attempting E-W tank");
                if (!(world.getBlock(x-1,y,z)==ModBlocks.blockQTValve))
                {
//                    LogHelper.info("Missing first valve");
                    return false;//missing a valve
                }
                if (!(world.getBlock(x+1,y,z)==ModBlocks.blockQTValve))
                {
//                    LogHelper.info("Missing second valve");
                    return false; //missing the other valve
                }

                for (int i=-1;i<2;i++)
                {
                    for (int j=-1;j<2;j++)
                    {
                        for (int k=-1;k<2;k++)
                        {
                            if (k!=0||j!=0)
                            {
                                if (!(world.getBlock(x+i,y+k,z+j)==Blocks.glass))
                                {
//                                    LogHelper.info("Missing glass at " + StringHelper.makeCoords(x+i,y+k,z+j));
                                    return false; //missing a piece of glass
                                }
                            }
                        }
                    }
                }

                //if we get to this point, we must create the East/West tank
                setOrientation(2);
                return true;
            }
        }

//        if (!(world.getBlock(x,y+1,z)== ModBlocks.blockQTValve))
//        {
//            return false;
//        }
//        if (!(world.getBlock(x,y-1,z)== ModBlocks.blockQTValve))
//        {
//            return false;
//        }
//
//
//        for (int i=-1;i<=1;i++)
//        {
//            for (int j=-1;j<=1;j++)
//            {
//                for (int k=-1;k<=1;k++)
//                {
//                    if (i != 0 || j != 0)
//                    {
//                        if (!(world.getBlock(x+i,y+k,z+j) == Blocks.glass) )
//                        {
//                            return false;
//                        }
//                    }
//                }
//            }
//        }

        //if we get here, then everything is in place to construct the multiblock
//        return true;
    }

    public boolean constructStructure() {
        World world = this.worldObj;
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

//        world.setBlock(x, y + 1, z, ModBlocks.blockQTComponent,1,3);
//        ((TileQTComponent)world.getTileEntity(x,y+1,z)).setParentCoordinates(x,y,z);
//        world.setBlock(x, y - 1, z, ModBlocks.blockQTComponent,1,3);
//        ((TileQTComponent)world.getTileEntity(x,y-1,z)).setParentCoordinates(x,y,z);


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i != 0 || j != 0 || k!=0) {
                        if (world.getBlock(x+i,y+k,z+j)==ModBlocks.blockQTValve)
                        {
                            world.setBlock(x+i,y+k,z+j,ModBlocks.blockQTComponent,1,3);
                        }
                        else
                        {
                            world.setBlock(x + i, y + k, z + j, ModBlocks.blockQTComponent,0,3);
                        }
                        ((TileQTComponent)world.getTileEntity(x+i,y+k,z+j)).setParentCoordinates(x,y,z);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isStructureBuilt()
    {
        return isBuilt;
    }

    public boolean isStructureComplete()
    {
        return isComplete;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if(tagCompound.hasKey("isComplete"))
        {
            isComplete = tagCompound.getBoolean("isComplete");
        }

        if (tagCompound.hasKey("isBuilt"))
        {
            isBuilt = tagCompound.getBoolean("isBuilt");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setBoolean("isComplete",isComplete);
        tagCompound.setBoolean("isBuilt",isBuilt);
    }

    //breaks the structure, replacing all blocks except for the one whose coordinates are input here
    public void breakStructure(int x, int y, int z)
    {

        int block;

        for (int i=-1;i<2;i++)
        {
            for (int j=-1;j<2;j++)
            {
                for (int k=-1;k<2;k++)
                {
                    if(i!=0 || j!=0 || k!=0)
                    {
                        block = worldObj.getBlockMetadata(xCoord+i,yCoord+k,zCoord+j);
//                     LogHelper.info("Block: " + block);
                        worldObj.removeTileEntity(xCoord+i,yCoord+k,zCoord+j);
                        worldObj.setBlockToAir(xCoord+i,yCoord+k,zCoord+j);
                        if (xCoord+i!=x || yCoord+k!=y || zCoord+j!=z)
                        {
                            if (block==0)
                            {
                                worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,Blocks.glass);
                            }
                            else
                            {
                                worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,ModBlocks.blockQTValve);
                            }
                        }
                    }
                }
            }
        }


//        //first clear all blocks
//        worldObj.setBlockToAir(xCoord,yCoord+1,zCoord);
//        worldObj.removeTileEntity(xCoord,yCoord+1,zCoord);
//        worldObj.setBlockToAir(xCoord,yCoord-1,zCoord);
//        worldObj.removeTileEntity(xCoord,yCoord-1,zCoord);
//
//        for (int i=-1;i<=1;i++)
//        {
//            for (int j=-1;j<=1;j++)
//            {
//                for (int k=-1;k<=1;k++)
//                {
//                    if (!(i==0 && j==0 && k==0))
//                    {
//                        worldObj.setBlockToAir(xCoord+i,yCoord+k,zCoord+j);
//                        worldObj.removeTileEntity(xCoord+i,yCoord+k,zCoord+j);
//                    }
//                }
//            }
//        }
//
//        //replace all blocks
//        if(!(x==xCoord && y==yCoord+1 && z==zCoord))
//        {
//            worldObj.setBlock(xCoord,yCoord+1,zCoord,ModBlocks.blockQTValve);
//        }
//        if(!(x==xCoord && y==yCoord-1 && z==zCoord))
//        {
//            worldObj.setBlock(xCoord,yCoord-1,zCoord,ModBlocks.blockQTValve);
//        }
//
//        for (int i=-1;i<=1;i++)
//        {
//            for (int j=-1;j<=1;j++)
//            {
//                for (int k=-1;k<=1;k++)
//                {
//                    if (!(i==0 && j==0) && !(xCoord+i==x && yCoord+k==y && zCoord+j==z))
//                    {
//                        worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,Blocks.glass);
//                    }
//                }
//            }
//        }
        isComplete = false;
        isBuilt = false;
        prepareSync();
    }

    public int getLightValue()
    {
        for (int i=-1;i<=1;i++)
        {
            for (int j=-1;j<=1;j++)
            {
                for (int k=-1;k<=1;k++)
                {
                    this.worldObj.markBlockForUpdate(xCoord+i,yCoord+k,zCoord+j);
                }
            }
        }
        if (tank.getFluidAmount()==0)
        {
            return 0;
        }

        int fluidLight = tank.getFluid().getFluid().getLuminosity();
        double percentage = ((double)tank.getFluidAmount())/((double)tank.getCapacity());
        return ((int)(fluidLight*percentage));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord-2,yCoord-2,zCoord-2,xCoord+2,yCoord+2,zCoord+2);
    }

    //checks to see if we have any other medium/large controllers that are "too close" to us
    public boolean areControllersNearby(int radius,int innerRadius) //input the search radius
    {
        for (int i=-radius;i<=radius;i++)
        {
            for (int j=-radius;j<=radius;j++)
            {
                for (int k=-radius;k<=radius;k++)
                {
                    if((Math.abs(i)>innerRadius || Math.abs(j)>innerRadius || Math.abs(k)>innerRadius) && worldObj.getBlock(xCoord+i,yCoord+k,zCoord+j)==ModBlocks.blockMedQTController)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean getControllerRangeError()
    {
        return controllerRangeError;
    }
}
