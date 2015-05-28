package com.M4thG33k.m4ththings.creativetabs;

import com.M4thG33k.m4ththings.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class CreativeTabM4thThings {
    public static final CreativeTabs M4THTHINGS_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return Items.skull;
        }
    };
}
