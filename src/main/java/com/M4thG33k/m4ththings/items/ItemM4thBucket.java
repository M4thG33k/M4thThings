package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.IIcon;

/**
 * Created by M4thG33k on 6/28/2015.
 */
public class ItemM4thBucket extends ItemBucket {

    IIcon icon;

    public ItemM4thBucket(Block block)
    {
        super(block);
        setUnlocalizedName(Reference.MOD_ID + "_" + "itemM4thBucket");
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icon = reg.registerIcon(Reference.MOD_ID + ":" + "bucketSolarWater");
    }

    @Override
    public IIcon getIconFromDamage(int p_77617_1_) {
        return icon;
    }
}
