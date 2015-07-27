package com.M4thG33k.m4ththings.items.dyeSeeds;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

/**
 * Created by M4thG33k on 7/14/2015.
 */
public class ItemDyeSeeds extends ItemSeeds {

    public ItemDyeSeeds()
    {
        super(ModBlocks.blockDyeCrop, Blocks.farmland);
        setUnlocalizedName(StringHelper.nameHelper() + "itemDyeSeeds");
        setTextureName(StringHelper.iconHelper() + "itemDyeSeeds");
    }
}
