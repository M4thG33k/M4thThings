package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileMediumQuantumTank;
import com.M4thG33k.m4ththings.tiles.TileTankAir;
import com.M4thG33k.m4ththings.tiles.TileTankTop;
import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class BlockTankTop extends BlockTankAir {

    public BlockTankTop(Material material)
    {
        super(material);
        setBlockName(Reference.MOD_ID + "_" + "blockTankTop");
        setHardness(2.0f);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTankTop();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity instanceof TileTankTop)
        {
            TileEntity tEnt = ((TileTankTop)tileEntity).getController();
            if(tEnt==null)
            {
                LogHelper.info("ERROR! No controller found for this tank top!");
            }
            if (tEnt instanceof TileMediumQuantumTank)
            {
//                ((TileMediumQuantumTank)tEnt).breakStructure(x,y,z);
                int[] location = ((TileTankTop)tileEntity).getControllerLocation();
                world.getBlock(location[0],location[1],location[2]).breakBlock(world,location[0],location[1],location[2],ModBlocks.blockMediumQTController,0);
            }
        }
//        super.breakBlock(world, x,y,z, block, meta);
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


}
