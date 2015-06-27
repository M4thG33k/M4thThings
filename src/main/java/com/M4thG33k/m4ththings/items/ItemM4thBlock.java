package com.M4thG33k.m4ththings.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

/**
 * Created by M4thG33k on 6/27/2015.
 */
public class ItemM4thBlock extends ItemBlockWithMetadata {

    public ItemM4thBlock(Block block)
    {
        super(block,block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName()+"_"+stack.getItemDamage();
    }
}
