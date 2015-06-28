package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.tiles.TileEnergyCage;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 6/27/2015.
 */
public class BlockEnergyCage extends Block implements ITileEntityProvider {

    public BlockEnergyCage(Material material)
    {
        super(material);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEnergyCage();
    }
}
