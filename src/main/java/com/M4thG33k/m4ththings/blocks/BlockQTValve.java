package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class BlockQTValve extends Block {

    public BlockQTValve(Material material)
    {
        super(material);
        setHardness(2.0f);
        setResistance(5.0f);
        setBlockName(Reference.MOD_ID + "_" + "blockQTValve");
        setBlockTextureName(Reference.MOD_ID + ":" + "blockQTValve");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.iron_block.stepSound);
    }
}
