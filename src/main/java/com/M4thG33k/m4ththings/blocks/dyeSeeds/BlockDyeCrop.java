package com.M4thG33k.m4ththings.blocks.dyeSeeds;

import com.M4thG33k.m4ththings.init.ModItems;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by M4thG33k on 7/14/2015.
 */
public class BlockDyeCrop extends BlockModCrops {

    protected int droppedDye = Configurations.QUASI_DYE_DROPPED;

    public BlockDyeCrop()
    {
        setBlockName(StringHelper.nameHelper() + "dyeCropsBlock");
        setBlockTextureName(StringHelper.iconHelper() + "dyeCropsBlock");
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[maxGrowthStage+1];

        for (int i=0;i<=maxGrowthStage;i++)
        {
            icons[i] = reg.registerIcon(StringHelper.iconHelper() + "dyeCropsBlock_stage_" + i);
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList toReturn = new ArrayList<ItemStack>();

        toReturn.add(new ItemStack(ModItems.itemDyeSeeds));

        if (metadata==7)
        {
            toReturn.add(new ItemStack(ModItems.itemQuasiDye, droppedDye));
            if (world.rand.nextFloat() < 0.01F)
            {
                toReturn.add(new ItemStack(ModItems.itemDyeSeeds,1));
            }
        }

        return toReturn;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.getBlockMetadata(x,y,z) == 7)
        {
            return;
        }

        if (world.getBlockLightValue(x,y+1,z)<9)
        {
            return;
        }

        if (rand.nextInt(8)!=0)
        {
            return;
        }

        world.setBlockMetadataWithNotify(x,y,z,world.getBlockMetadata(x,y,z)+1,2);
    }

}
