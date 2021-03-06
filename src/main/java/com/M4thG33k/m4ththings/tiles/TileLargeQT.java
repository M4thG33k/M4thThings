package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.Location;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created by M4thG33k on 6/25/2015.
 */
public class TileLargeQT extends TileMedQT{

    public TileLargeQT()
    {
        super();
        cap = Configurations.LARGE_QT_CAP;
        tank = new FluidTank(cap);
        tankSize = 2;
        importValves = new Location[18];
        exportValves = new Location[18];
    }

    @Override
    public boolean isReadyToConstruct()
    {
        World world = this.worldObj;
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        //check to make sure we don't have another controller "too close", if there is one that's too close, we return false
        controllerRangeError = areControllersNearby(8,3,5);
        if (controllerRangeError)
        {
            return false;
        }

        //now we check to see if the correct blocks are in place to make the structure
        if (world.getBlock(x,y+3,z)==ModBlocks.blockQTValve) //if this is true, we must be attempting a vertical tank
        {
            for (int i=-4;i<=4;i++)
            {
                for (int j=-4;j<=4;j++)
                {
                    for (int k=-4;k<=4;k++)
                    {
                        if (Math.abs(k)==4) //we're on the top/bottom
                        {
                            if (Math.abs(i)<=1 && Math.abs(j)<=1)//these should be valves
                            {
                                if (world.getBlock(x+i,y+k,z+j)!=ModBlocks.blockQTValve)
                                {
                                    return false;
                                }
                            }
                            else //these should be glass
                            {
                                if (world.getBlock(x+i,y+k,z+j)!=Blocks.glass)
                                {
                                    return false;
                                }
                            }
                        }
                        else if (Math.abs(k)==3) //other layers with valves
                        {
                            if (i==0 && j==0) //should be a valve
                            {
                                if (world.getBlock(x+i,y+k,z+j)!=ModBlocks.blockQTValve)
                                {
                                    return false;
                                }
                            }
                            else
                            {
                                if (world.getBlock(x+i,y+k,z+j)!=Blocks.glass)
                                {
                                    return false;
                                }
                            }
                        }
                        else if (i!=0 || j!=0 || k!=0) //everywhere else but the controller should be glass
                        {
                            if (world.getBlock(x+i,y+k,z+j)!=Blocks.glass)
                            {
                                return false;
                            }
                        }
                    }
                }
            }
            //if we get here, we must are able to form the vertical tank
            setOrientation(0);
            return true;
        }
        else //check horizontal tank structures
        {
            if (world.getBlock(x,y,z+4)==ModBlocks.blockQTValve) //we must be attempting a N-S tank
            {
                for (int i=-4;i<=4;i++) {
                    for (int j = -4; j <= 4; j++) {
                        for (int k = -4; k <= 4; k++) {
                            if (Math.abs(j) == 4) //we're on the top/bottom
                            {
                                if (Math.abs(i) <= 1 && Math.abs(k) <= 1)//these should be valves
                                {
                                    if (world.getBlock(x + i, y + k, z + j) != ModBlocks.blockQTValve) {
                                        return false;
                                    }
                                } else //these should be glass
                                {
                                    if (world.getBlock(x + i, y + k, z + j) != Blocks.glass) {
                                        return false;
                                    }
                                }
                            } else if (Math.abs(j) == 3) //other layers with valves
                            {
                                if (i == 0 && k == 0) //should be a valve
                                {
                                    if (world.getBlock(x + i, y + k, z + j) != ModBlocks.blockQTValve) {
                                        return false;
                                    }
                                } else {
                                    if (world.getBlock(x + i, y + k, z + j) != Blocks.glass) {
                                        return false;
                                    }
                                }
                            } else if (i != 0 || j != 0 || k != 0) //everywhere else but the controller should be glass
                            {
                                if (world.getBlock(x + i, y + k, z + j) != Blocks.glass) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                //if we get this far, we must be able to construct the N-S tank
                setOrientation(1);
                return true;
            }
            else //we must be attempting a E-W tank
            {
                for (int i=-4;i<=4;i++) {
                    for (int j = -4; j <= 4; j++) {
                        for (int k = -4; k <= 4; k++) {
                            if (Math.abs(i) == 4) //we're on the top/bottom
                            {
                                if (Math.abs(j) <= 1 && Math.abs(k) <= 1)//these should be valves
                                {
                                    if (world.getBlock(x + i, y + k, z + j) != ModBlocks.blockQTValve) {
                                        return false;
                                    }
                                } else //these should be glass
                                {
                                    if (world.getBlock(x + i, y + k, z + j) != Blocks.glass) {
                                        return false;
                                    }
                                }
                            } else if (Math.abs(i) == 3) //other layers with valves
                            {
                                if (k == 0 && j == 0) //should be a valve
                                {
                                    if (world.getBlock(x + i, y + k, z + j) != ModBlocks.blockQTValve) {
                                        return false;
                                    }
                                } else {
                                    if (world.getBlock(x + i, y + k, z + j) != Blocks.glass) {
                                        return false;
                                    }
                                }
                            } else if (i != 0 || j != 0 || k != 0) //everywhere else but the controller should be glass
                            {
                                if (world.getBlock(x + i, y + k, z + j) != Blocks.glass) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                //if we get this far, we must construct the E-W tank
                setOrientation(2);
                return true;
            }
        }


//        for (int i=-4;i<=4;i++)
//        {
//            for (int j=-4;j<=4;j++)
//            {
//                for (int k=-4;k<=4;k++)
//                {
//                    if (y+k<0 || y+k>world.getActualHeight()) //checks to see if the block being checked falls within the world's height limit
//                    {
//                        return false;
//                    }
//
//                    if(!(i==0 && j==0 && k==0)) //if i==j==k==0, then we are at the controller's location and we don't need to check that block
//                    {
//
//                        if (Math.abs(i)<=1 && Math.abs(j)<=1 && Math.abs(k)==4) //these locations refer to the top/bottom valve blocks
//                        {
//                            if (world.getBlock(x+i,y+k,z+j)!=ModBlocks.blockQTValve)
//                            {
//                                return false;
//                            }
//                        }
//                        else if (i==0 && j==0 && Math.abs(k)==3) //these locations refer to the 2 interior valve blocks
//                        {
//                            if (world.getBlock(x+i,y+k,z+j)!=ModBlocks.blockQTValve)
//                            {
//                                return false;
//                            }
//                        }
//                        else //everything else should be glass
//                        {
//                            if (world.getBlock(x+i,y+k,z+j)!= Blocks.glass)
//                            {
//                                return false;
//                            }
//                        }
//
//                    }
//                }
//            }
//        }

        //if we get to this point, everything should be in the correct location, so we are ready to construct the structure
//        return true;
    }

    @Override
    public boolean constructStructure() {
        World world = this.worldObj;
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        for (int i=-4;i<=4;i++)
        {
            for (int j=-4;j<=4;j++)
            {
                for (int k=-4;k<=4;k++)
                {
                    if (i!=0 || j!=0 || k!=0)
                    {

                        //this should only be called if all the coordinates are within the world's dimension, so we won't check them again
                        if (world.getBlock(x + i, y + k, z + j) == ModBlocks.blockQTValve) {
                            int meta = world.getBlockMetadata(x+i,y+k,z+j);
                            switch (meta)
                            {
                                case 1:
                                    world.setBlock(x+i,y+k,z+j,ModBlocks.blockQTComponent,2,3);
                                    if (Math.abs(i)==4 || Math.abs(j)==4 || Math.abs(k)==4) //we're along the outer wall (we don't want to include the interior valve)
                                    {
                                        importValves[importValveIndex++] = new Location(i,k,j);
                                    }
                                    break;
                                case 2:
                                    world.setBlock(x+i,y+k,z+j,ModBlocks.blockQTComponent,3,3);
                                    if (Math.abs(i)==4 || Math.abs(j)==4 || Math.abs(k)==4) //we're along the outer wall (we don't want to include the interior valve)
                                    {
                                        exportValves[exportValveIndex++] = new Location(i,k,j);
                                    }
                                    break;
                                default:
                                    world.setBlock(x+i,y+k,z+j,ModBlocks.blockQTComponent,1,3);
                                    break;
                            }
//                            world.setBlock(x + i, y + k, z + j, ModBlocks.blockQTComponent, 1, 3); //replace valves with the valve components
                        } else if (world.getBlock(x + i, y + k, z + j) == Blocks.glass) {
                            world.setBlock(x + i, y + k, z + j, ModBlocks.blockQTComponent, 0, 3); //replace glass with the air components
                            ((TileQTComponent) world.getTileEntity(x + i, y + k, z + j)).setParentCoordinates(x, y, z);
                        }
                        ((TileQTComponent) world.getTileEntity(x + i, y + k, z + j)).setParentCoordinates(x, y, z); //set the component's parent
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void breakStructure(int x, int y, int z) {
        World world = this.worldObj;
        int X = this.xCoord;
        int Y = this.yCoord;
        int Z = this.zCoord;

        int meta; //temp storage for block metadata during replacement

        //clear all non-controller blocks
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                for (int k = -4; k <= 4; k++) {
                    if (i!=0 || j!=0 || k!=0)//avoid replacing the center
                    {
                        meta = world.getBlockMetadata(X+i,Y+k,Z+j);

                        world.removeTileEntity(X+i,Y+k,Z+j);
                        world.setBlockToAir(X+i,Y+k,Z+j);

                        if (X+i!=x || Y+k!=y || Z+j!=z)
                        {
                            switch(meta)
                            {
                                case 1:
                                    worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,ModBlocks.blockQTValve,0,3);
                                    break;
                                case 2:
                                    worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,ModBlocks.blockQTValve,1,3);
                                    break;
                                case 3:
                                    worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,ModBlocks.blockQTValve,2,3);
                                    break;
                                default:
                                    worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,Blocks.glass);
                                    break;
                            }
//                            if (block == 0)
//                            {
//                                worldObj.setBlock(X+i,Y+k,Z+j,Blocks.glass);
//                            }
//                            else
//                            {
//                                worldObj.setBlock(X+i,Y+k,Z+j,ModBlocks.blockQTValve);
//                            }

//                            if (Math.abs(i)<=1 && Math.abs(j)<=1 && Math.abs(k)==4) //these locations refer to the top/bottom valve blocks
//                            {
//                                world.setBlock(X+i,Y+k,Z+j,ModBlocks.blockQTValve);
//                            }
//                            else if (i==0 && j==0 && Math.abs(k)==3) //these locations refer to the 2 interior valve blocks
//                            {
//                                world.setBlock(X+i,Y+k,Z+j,ModBlocks.blockQTValve);
//                            }
//                            else //everything else should be glass
//                            {
//                                world.setBlock(X+i,Y+k,Z+j,Blocks.glass);
//                            }
                        }
                    }
                }
            }
        }

        isComplete = false;
        isBuilt = false;
        importValveIndex = 0;
        importValves = new Location[18];
        exportValveIndex = 0;
        exportValves = new Location[18];
        prepareSync();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord-4,yCoord-4,zCoord-4,xCoord+4,yCoord+4,zCoord+4);
    }

    //checks to see if we have any other medium/large controllers that are "too close" to us
    public boolean areControllersNearby(int radius,int innerRadius,int medContRadius) //input the search radius
    {
        for (int i=-radius;i<=radius;i++)
        {
            for (int j=-radius;j<=radius;j++)
            {
                for (int k=-radius;k<=radius;k++)
                {
                    if((Math.abs(i)>innerRadius || Math.abs(j)>innerRadius || Math.abs(k)>innerRadius) && worldObj.getBlock(xCoord+i,yCoord+k,zCoord+j)==ModBlocks.blockMedQTController)
                    {
                        if ((Math.abs(i)>medContRadius || Math.abs(j)>medContRadius || Math.abs(k)>medContRadius))
                        {
                            if (worldObj.getBlockMetadata(xCoord+i,yCoord+k,zCoord+j)==1)
                            {
                                return true;
                            }
                        }
                        else {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
