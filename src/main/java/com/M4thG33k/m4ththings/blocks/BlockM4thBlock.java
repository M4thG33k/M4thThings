package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

/**
 * Created by M4thG33k on 6/26/2015.
 */
public class BlockM4thBlock extends Block {

    IIcon[] icons = new IIcon[1];

    public BlockM4thBlock(Material material)
    {
        super(material);
        setHardness(5.0f);
        setResistance(10.f);
        setBlockName(Reference.MOD_ID + "_" + "blockM4thBlock");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.iron_block.stepSound);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Reference.MOD_ID + ":" + "goldPlatedIronBlock");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (meta)
        {
            default:
                return icons[0];
        }
    }
}
