package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileMediumQuantumTank;
import com.M4thG33k.m4ththings.tiles.TileTankAir;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class BlockTankAir extends Block implements ITileEntityProvider {

    public BlockTankAir(Material material)
    {
        super(material);
        setBlockName(Reference.MOD_ID + "_" + "blockTankAir");
        setBlockTextureName(Reference.MOD_ID + ":" + "blockCobbleChest");
        setBlockUnbreakable();
        setStepSound(Blocks.glass.stepSound);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTankAir();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
//        return Item.getItemFromBlock(Blocks.glass);
        return null;
    }

    //    @Override
//    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
//        TileEntity tileEntity = world.getTileEntity(x,y,z);
//        if (tileEntity instanceof TileTankAir)
//        {
//            TileEntity tEnt = ((TileTankAir)tileEntity).getController();
//            if (tEnt instanceof TileMediumQuantumTank)
//            {
//                ((TileMediumQuantumTank)tEnt).breakStructure(x,y,z);
//            }
//        }
//        super.breakBlock(world, x,y,z, block, meta);
//    }

//    @Override
//    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
//        TileEntity tileEntity = world.getTileEntity(x,y,z);
//        if (tileEntity instanceof TileTankAir)
//        {
//            TileEntity tEnt = ((TileTankAir)tileEntity).getController();
//            if (tEnt instanceof TileMediumQuantumTank)
//            {
//                ((TileMediumQuantumTank)tEnt).breakStructure(x,y,z);
//            }
//        }
//    }
}
