package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.tiles.TileSolarGenerator;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by M4thG33k on 7/6/2015.
 */
public class BlockBaseRFGenerator extends Block implements ITileEntityProvider {

    public BlockBaseRFGenerator(Material material)
    {
        super(material);
        setHardness(1.5f);
        setResistance(5.0f);
        setBlockName(StringHelper.nameHelper() + "blockBaseRFGenerator");
        setBlockTextureName(StringHelper.iconHelper() + "blockBaseRFGenerator");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (meta)
        {
            default:
                return new TileSolarGenerator();
        }
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        for (int i=0;i<1;i++)
        {
            list.add(new ItemStack(item,1,i));
        }
    }
}
