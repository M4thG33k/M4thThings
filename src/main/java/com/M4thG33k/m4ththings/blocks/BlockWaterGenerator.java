package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.tiles.TileWaterGenerator;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class BlockWaterGenerator extends Block implements ITileEntityProvider {

    public BlockWaterGenerator(Material material)
    {
        super(material);
        setHardness(1.0f);
        setResistance(5.0f);
        setBlockName(StringHelper.nameHelper() + "blockWaterGenerator");
        setBlockTextureName(StringHelper.iconHelper() + "defaultTexture");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.air.stepSound);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileWaterGenerator();
    }
}
