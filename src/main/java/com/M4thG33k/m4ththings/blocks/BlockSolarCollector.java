package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileSolarCollector;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class BlockSolarCollector extends Block implements ITileEntityProvider {

    public BlockSolarCollector(Material material)
    {
        super(material);
        setBlockName(Reference.MOD_ID + "_" + "blockSolarCollector");
        setHardness(2.0f);
        setResistance(3.0f);
        setStepSound(Blocks.iron_block.stepSound);
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSolarCollector();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
