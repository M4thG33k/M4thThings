package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class ItemWaterGenerator extends ItemBlock {


    public ItemWaterGenerator(Block block) {
        super(block);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {

        MovingObjectPosition movingObjectPosition = this.getMovingObjectPositionFromPlayer(world,player,true);

        if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int i = movingObjectPosition.blockX;
            int j = movingObjectPosition.blockY;
            int k = movingObjectPosition.blockZ;

            if (world.getBlock(i,j,k).getMaterial()== Material.water && world.getBlockMetadata(i,j,k)==0 && world.getBlock(i,j+1,k)==Blocks.air)
            {
                world.setBlock(i,j+1,k, ModBlocks.blockWaterGenerator);
                return true;
            }
        }

//        if (block== Blocks.water)
//        {
//            LogHelper.info("It's water!");
//        }
//        else
//        {
//            LogHelper.info("Not water...");
//        }
        return false;
    }
}
