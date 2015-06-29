package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class TileMedQT extends TileQuantumTank {

    protected boolean isComplete;
    protected boolean isBuilt;

    public TileMedQT()
    {
        super();
        cap = Configurations.MED_QT_CAP;
        isComplete = false;
        isBuilt = false;
    }

    @Override
    public void updateEntity() {
        advanceTimer();

        if(timer==0)//testing this to see if it will keep the light level synced better
        {
            prepareSync();
        }

        //If the multiblock is not complete, check to see if we can form the multiblock structure. If we can, do it.
        if (!isComplete)
        {
            isComplete = isReadyToConstruct();
        }
        else
        {
            //construct the multiblock, replacing the construction blocks
            if (!isBuilt)
            {
                isBuilt = constructStructure();
                prepareSync();
//                LogHelper.info("Multiblock formed!");
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
        if (world.getBlock(x+2,y,z)==ModBlocks.blockMedQTController || world.getBlock(x,y,z+2)==ModBlocks.blockMedQTController || (y+2<world.getActualHeight() && world.getBlock(x,y+2,z)==ModBlocks.blockMedQTController)) {
            return false;
        }

        if (!(world.getBlock(x,y+1,z)== ModBlocks.blockQTValve))
        {
            return false;
        }
        if (!(world.getBlock(x,y-1,z)== ModBlocks.blockQTValve))
        {
            return false;
        }


        for (int i=-1;i<=1;i++)
        {
            for (int j=-1;j<=1;j++)
            {
                for (int k=-1;k<=1;k++)
                {
                    if (i != 0 || j != 0)
                    {
                        if (!(world.getBlock(x+i,y+k,z+j) == Blocks.glass) )
                        {
                            return false;
                        }
                    }
                }
            }
        }

        //if we get here, then everything is in place to construct the multiblock
        return true;
    }

    public boolean constructStructure() {
        World world = this.worldObj;
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        world.setBlock(x, y + 1, z, ModBlocks.blockQTComponent,1,3);
        ((TileQTComponent)world.getTileEntity(x,y+1,z)).setParentCoordinates(x,y,z);
        world.setBlock(x, y - 1, z, ModBlocks.blockQTComponent,1,3);
        ((TileQTComponent)world.getTileEntity(x,y-1,z)).setParentCoordinates(x,y,z);


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i != 0 || j != 0) {
                        world.setBlock(x + i, y + k, z + j, ModBlocks.blockQTComponent,0,3);
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
        //first clear all blocks
        worldObj.setBlockToAir(xCoord,yCoord+1,zCoord);
        worldObj.removeTileEntity(xCoord,yCoord+1,zCoord);
        worldObj.setBlockToAir(xCoord,yCoord-1,zCoord);
        worldObj.removeTileEntity(xCoord,yCoord-1,zCoord);

        for (int i=-1;i<=1;i++)
        {
            for (int j=-1;j<=1;j++)
            {
                for (int k=-1;k<=1;k++)
                {
                    if (!(i==0 && j==0 && k==0))
                    {
                        worldObj.setBlockToAir(xCoord+i,yCoord+k,zCoord+j);
                        worldObj.removeTileEntity(xCoord+i,yCoord+k,zCoord+j);
                    }
                }
            }
        }

        //replace all blocks
        if(!(x==xCoord && y==yCoord+1 && z==zCoord))
        {
            worldObj.setBlock(xCoord,yCoord+1,zCoord,ModBlocks.blockQTValve);
        }
        if(!(x==xCoord && y==yCoord-1 && z==zCoord))
        {
            worldObj.setBlock(xCoord,yCoord-1,zCoord,ModBlocks.blockQTValve);
        }

        for (int i=-1;i<=1;i++)
        {
            for (int j=-1;j<=1;j++)
            {
                for (int k=-1;k<=1;k++)
                {
                    if (!(i==0 && j==0) && !(xCoord+i==x && yCoord+k==y && zCoord+j==z))
                    {
                        worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,Blocks.glass);
                    }
                }
            }
        }
        isComplete = false;
        isBuilt = false;
        prepareSync();
    }

    public int getLightValue()
    {
        if (getFluidAmount()==0)
        {
            return 0;
        }

        int fluidLight = fluid.getFluid().getLuminosity();
        double percentage = ((double)getFluidAmount())/((double)getCapacity());
        return ((int)(fluidLight*percentage));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord-1,yCoord-1,zCoord-1,xCoord+1,yCoord+1,zCoord+1);
    }


}
