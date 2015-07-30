package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.tiles.TileFluidPipe;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 7/9/2015.
 */
public class BlockFluidTransferPipe extends BlockTransferPipe {

    public BlockFluidTransferPipe(Material material)
    {
        super(material);
        setBlockName(StringHelper.nameHelper() + "blockFluidTransferPipe");
        setBlockTextureName(StringHelper.iconHelper() + "defaultTexture");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileFluidPipe();
//        return new TileFluidPipe();
//        return new TileFluidTransferPipe();

    }
}
