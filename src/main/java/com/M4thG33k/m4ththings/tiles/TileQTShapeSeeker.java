package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class TileQTShapeSeeker extends TileEntity {

    private int radius;

    public TileQTShapeSeeker(int rad)
    {
        this.radius = rad;
        if (radius<1)
        {
            radius = 1;
        }
    }

    @Override
    public void updateEntity()
    {
        if (radius == 1) {
            //check for the other valve
            if (worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.blockQuantumTankValve) {
                //check if the center is empty
                if(worldObj.getBlock(xCoord,yCoord-1,zCoord)!=Blocks.air)
                {
                    return;
                }

                //check for the glass cylinder
                for (int k = 0; k >= -2; k--) //for each y-value
                {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (!(i == 0 && j == 0)) {
                                if (worldObj.getBlock(xCoord+i,yCoord+k,zCoord+j)!= Blocks.glass)
                                {
                                    return;
                                }
                            }
                        }
                    }
                }

                //if we get to this point, the multiblock is complete.

                //coords of the controller block
                int contX = xCoord;
                int contY = yCoord-1;
                int contZ = zCoord;
                //make the controller block (center of the 3x3 area)
                worldObj.setBlock(contX,contY,contZ,ModBlocks.blockMediumQTController);
                //the code for deconstructing the current blocks and adding the multiblock structure
                //are now placed in TileMediumQTController.
//
//
//                //replace the glass cylinder
//                for (int k = 0; k >= -2; k--) //for each y-value
//                {
//                    for (int i = -1; i < 2; i++) {
//                        for (int j = -1; j < 2; j++) {
//                            if (!(i == 0 && j == 0)) {
//                                worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,ModBlocks.blockTankAir);
//                                TileTankAir tileEntity = (TileTankAir)worldObj.getTileEntity(xCoord+i, yCoord+k, zCoord+j);
//                                tileEntity.setControllerLocation(contX,contY,contZ);
//                            }
//                        }
//                    }
//                }
//
//                //replace the top block
//                worldObj.setBlock(contX,contY+2,contZ,ModBlocks.blockTankTop);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("radius"))
        {
            radius = tagCompound.getInteger("radius");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("radius",radius);
    }
}
