package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileMediumQuantumTank;
import com.M4thG33k.m4ththings.tiles.TileQTShapeSeeker;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class BlockQuantumTankValve extends Block implements ITileEntityProvider{

    public BlockQuantumTankValve(Material material)
    {
        super(material);
        setHardness(2.0f);
        setResistance(5.0f);
        setBlockName(Reference.MOD_ID + "_" + "blockQuantumTankValve");
        setBlockTextureName(Reference.MOD_ID + ":" + "defaultTexture");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQTShapeSeeker(1);
    }

}
