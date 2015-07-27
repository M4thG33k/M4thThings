package com.M4thG33k.m4ththings.blocks;

import cofh.api.block.IDismantleable;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileTEST;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by M4thG33k on 7/7/2015.
 */
public class BlockTEST extends Block implements ITileEntityProvider, IDismantleable {

    public BlockTEST(Material material)
    {
        super(material);
        setBlockName(StringHelper.nameHelper() + "blockTEST");
        setBlockTextureName(StringHelper.iconHelper() + "defaultTexture");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileTEST();
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer entityPlayer, World world, int i, int i1, int i2, boolean b) {
        ArrayList<ItemStack> toReturn = this.getDrops(world,i,i1,i2,world.getBlockMetadata(i,i1,i2),0);
        world.setBlockToAir(i,i1,i2);
        for (int j=0;j<toReturn.size();j++)
        {
            EntityItem entityItem = new EntityItem(world,i,i1,i2,toReturn.get(j));
            world.spawnEntityInWorld(entityItem);
        }
        return toReturn;
    }

    @Override
    public boolean canDismantle(EntityPlayer entityPlayer, World world, int i, int i1, int i2) {
        return true;
    }
}
