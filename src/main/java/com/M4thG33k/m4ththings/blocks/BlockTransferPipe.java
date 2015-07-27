package com.M4thG33k.m4ththings.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

/**
 * Created by M4thG33k on 7/9/2015.
 */
public abstract class BlockTransferPipe extends Block implements ITileEntityProvider {

    public BlockTransferPipe(Material material)
    {
        super(material);
    }



}
