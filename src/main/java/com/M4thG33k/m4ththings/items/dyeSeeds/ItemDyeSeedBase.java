package com.M4thG33k.m4ththings.items.dyeSeeds;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.item.Item;

/**
 * Created by M4thG33k on 7/14/2015.
 */
public class ItemDyeSeedBase extends Item{

    public ItemDyeSeedBase()
    {
        setUnlocalizedName(StringHelper.nameHelper() + "itemDyeSeedBase");
        setTextureName(StringHelper.iconHelper() + "itemDyeSeedBase");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }
}
