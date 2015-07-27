package com.M4thG33k.m4ththings.items.tankComponents;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class ItemQTValveMeta extends ItemBlockWithMetadata {

    public ItemQTValveMeta(Block block)
    {
        super(block,block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "_" + stack.getItemDamage();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        switch(stack.getItemDamage())
        {
            case 1:
            case 2:
                list.add(StatCollector.translateToLocal("valveFlavorText"));
                break;
            default:
                break;
        }
    }
}
