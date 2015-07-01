package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.sql.Ref;
import java.util.List;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class BlockTextureDummy extends Block {

    public BlockTextureDummy(Material material)
    {
        super(material);
        setBlockName(Reference.MOD_ID + "_" + "blockTextureDummy");
        setBlockTextureName(Reference.MOD_ID + ":" + "glassTest");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {

    }
}
