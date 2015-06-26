package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 6/25/2015.
 */
public class TileLargeQT extends TileMedQT{

    public TileLargeQT()
    {
        super();
        cap = Configurations.LARGE_QT_CAP;
    }

    @Override
    public boolean isReadyToConstruct()
    {
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
                    if (y+k<0 || y+k>world.getActualHeight()) //checks to see if the block being checked falls within the world's height limit
                    {
                        return false;
                    }

                    if(!(i==0 && j==0 && k==0)) //if i==j==k==0, then we are at the controller's location and we don't need to check that block
                    {

                        if (Math.abs(i)<=1 && Math.abs(j)<=1 && Math.abs(k)==4) //these locations refer to the top/bottom valve blocks
                        {
                            if (world.getBlock(x+i,y+k,z+j)!=ModBlocks.blockQTValve)
                            {
                                return false;
                            }
                        }
                        else if (i==0 && j==0 && Math.abs(k)==3) //these locations refer to the 2 interior valve blocks
                        {
                            if (world.getBlock(x+i,y+k,z+j)!=ModBlocks.blockQTValve)
                            {
                                return false;
                            }
                        }
                        else //everything else should be glass
                        {
                            if (world.getBlock(x+i,y+k,z+j)!= Blocks.glass)
                            {
                                return false;
                            }
                        }

                    }
                }
            }
        }

        //if we get to this point, everything should be in the correct location, so we are ready to construct the structure
        return true;
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
                    //this should only be called if all the coordinates are within the world's dimension, so we won't check them again
                    if (world.getBlock(x+i,y+k,z+j)==ModBlocks.blockQTValve)
                    {
                        world.setBlock(x+i,y+k,z+j,ModBlocks.blockQTComponent,1,3); //replace valves with the valve components
                        ((TileQTComponent)world.getTileEntity(x+i,y+k,z+j)).setParentCoordinates(x,y,z);
                    }
                    else if (world.getBlock(x+i,y+k,z+j)==Blocks.glass)
                    {
                        world.setBlock(x+i,y+k,z+j,ModBlocks.blockQTComponent,0,3); //replace glass with the air components
                        ((TileQTComponent)world.getTileEntity(x+i,y+k,z+j)).setParentCoordinates(x,y,z);
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

        //clear all non-controller blocks
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                for (int k = -4; k <= 4; k++) {
                    if (!(i==0 && j==0 && k==0))//avoid replacing the center
                    {
                        world.setBlockToAir(X+i,Y+k,Z+j);
                        world.removeTileEntity(X+i,Y+k,Z+j);

                        //testing something here
                        if (!(X+i==x && Y+k==y && Z+j==z))
                        {
                            if (Math.abs(i)<=1 && Math.abs(j)<=1 && Math.abs(k)==4) //these locations refer to the top/bottom valve blocks
                            {
                                world.setBlock(X+i,Y+k,Z+j,ModBlocks.blockQTValve);
                            }
                            else if (i==0 && j==0 && Math.abs(k)==3) //these locations refer to the 2 interior valve blocks
                            {
                                world.setBlock(X+i,Y+k,Z+j,ModBlocks.blockQTValve);
                            }
                            else //everything else should be glass
                            {
                                world.setBlock(X+i,Y+k,Z+j,Blocks.glass);
                            }
                        }
                    }
                }
            }
        }

//        //replace all blocks except for the one at the input location
//        for (int i=-4;i<=4;i++)
//        {
//            for (int j=-4;j<=4;j++)
//            {
//                for (int k=-4;k<=4;k++)
//                {
//                    if(!(i==0 && j==0 && k==0) && !(X+i==x && Y+k==y && Z+j==z)) //make sure we aren't at the center or the input block's location
//                    {
//
//                        if (Math.abs(i)<=1 && Math.abs(j)<=1 && Math.abs(k)==4) //these locations refer to the top/bottom valve blocks
//                        {
//                            world.setBlock(X+i,Y+k,Z+j,ModBlocks.blockQTValve);
//                        }
//                        else if (i==0 && j==0 && Math.abs(k)==3) //these locations refer to the 2 interior valve blocks
//                        {
//                            world.setBlock(X+i,Y+k,Z+j,ModBlocks.blockQTValve);
//                        }
//                        else //everything else should be glass
//                        {
//                            world.setBlock(X+i,Y+k,Z+j,Blocks.glass);
//                        }
//
//                    }
//                }
//            }
//        }

        isComplete = false;
        isBuilt = false;
        prepareSync();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord-4,yCoord-4,zCoord-4,xCoord+4,yCoord+4,zCoord+4);
    }
}
