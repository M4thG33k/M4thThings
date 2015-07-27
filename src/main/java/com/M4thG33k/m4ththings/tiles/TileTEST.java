package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * Created by M4thG33k on 7/7/2015.
 */
public class TileTEST extends TileEntity {

    public TileTEST()
    {
    }

    @Override
    public void updateEntity() {
        MovingObjectPosition movingObjectPosition = worldObj.rayTraceBlocks(Vec3.createVectorHelper(xCoord + 1.0, yCoord + 0.5, zCoord + 0.5), Vec3.createVectorHelper(xCoord + 5.5, yCoord + 0.5, zCoord + 0.5));

        if (movingObjectPosition==null)
        {
            LogHelper.info("Getting nothing");
        }
        else
        {
            Block blockHit = worldObj.getBlock(movingObjectPosition.blockX,movingObjectPosition.blockY,movingObjectPosition.blockZ);

            LogHelper.info(blockHit.getLocalizedName());
        }

        int x = 5;
    }
}
