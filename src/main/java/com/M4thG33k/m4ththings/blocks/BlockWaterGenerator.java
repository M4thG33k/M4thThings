package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.tiles.TileWaterGenerator;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class BlockWaterGenerator extends Block implements ITileEntityProvider {

    public BlockWaterGenerator(Material material)
    {
        super(material);
        setHardness(1.0f);
        setResistance(5.0f);
        setBlockName(StringHelper.nameHelper() + "blockWaterGenerator");
        setBlockTextureName(StringHelper.iconHelper() + "defaultTexture");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.air.stepSound);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileWaterGenerator();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x,y,z,x+1,y+0.25,z+1);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x,y,z,x+1,y+0.25,z+1);
    }
//
//    @Override
//    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_) {
//        return null;
//    }


    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0,0,0,1,0.25f,1);
    }
}
