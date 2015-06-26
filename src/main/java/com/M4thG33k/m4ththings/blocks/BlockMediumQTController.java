package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileMediumQuantumTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class BlockMediumQTController extends Block implements ITileEntityProvider {

    public BlockMediumQTController(Material material)
    {
        super(material);
        setHardness(2.0f);
        setResistance(5.0f);
        setBlockName(Reference.MOD_ID + "_" + "blockMediumQTController");
        setBlockTextureName(Reference.MOD_ID + ":" + "blockCobbleChest");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.glass.stepSound);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileMediumQuantumTank();
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity instanceof TileMediumQuantumTank)
        {
//            ((TileMediumQuantumTank)tileEntity).breakStructure(x,y,z);
            ((TileMediumQuantumTank)tileEntity).dropStructure();
        }
        world.setBlockToAir(x,y,z);
//        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        drops.add(new ItemStack(ModBlocks.blockQuantumTankValve,2));
        drops.add(new ItemStack(Blocks.glass,24));

        return drops;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return super.getIcon(world, x, y, z, side);
    }
}
