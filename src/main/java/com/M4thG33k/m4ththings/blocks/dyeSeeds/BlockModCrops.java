package com.M4thG33k.m4ththings.blocks.dyeSeeds;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by M4thG33k on 7/14/2015.
 */
public class BlockModCrops extends BlockBush implements IGrowable {

    protected int maxGrowthStage = 7;

    protected IIcon[] icons;

    public BlockModCrops()
    {
        setTickRandomly(true);
        float f = 0.5f;
        setBlockBounds(0.5f-f,0.0f,0.5f-f,0.5f+f,.025f,0.5f+f);
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setHardness(0.0f);
        setStepSound(soundTypeGrass);
        disableStats();
    }

    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return block== Blocks.farmland;
    }

    public void incrementGrowthStage(World world, Random random, int x, int y, int z)
    {
        int growthStage = world.getBlockMetadata(x,y,z) + net.minecraft.util.MathHelper.getRandomIntegerInRange(random,2,5);

        if (growthStage > maxGrowthStage)
        {
            growthStage = maxGrowthStage;
        }

        world.setBlockMetadataWithNotify(x,y,z,growthStage,2);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return null;
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public IIcon getIcon(int side, int growthStage) {
        return icons[growthStage];
    }

    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean p_149851_5_) {
        return world.getBlockMetadata(x,y,z)!=7;
    }

    @Override
    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
        return true;
    }

    @Override
    public void func_149853_b(World world, Random rand, int x, int y, int z) {
        incrementGrowthStage(world,rand,x,y,z);
    }
}
