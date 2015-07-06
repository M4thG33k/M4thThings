package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModFluids;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import com.M4thG33k.m4ththings.tiles.TileSolarCollector;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class BlockSolarCollector extends Block implements ITileEntityProvider {

    public BlockSolarCollector(Material material)
    {
        super(material);
        setBlockName(Reference.MOD_ID + "_" + "blockSolarCollector");
        setHardness(2.0f);
        setResistance(3.0f);
        setStepSound(Blocks.iron_block.stepSound);
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setBlockTextureName(Reference.MOD_ID + ":" + "solarCollectionIcon");
    }


    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSolarCollector();
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x,y,z,x+1,y+0.8,z+1);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x,y,z,x+1,y+0.8,z+1);
    }
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0,0,0,1,0.8f,1);
    }

    //    @Override
//    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
//        return AxisAlignedBB.getBoundingBox(x+0.5,y,z+0.5,x+0.5,y,z+0.5);
//    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }

        ItemStack held = player.getHeldItem();
        FluidStack fluidHeld = FluidContainerRegistry.getFluidForFilledItem(held);
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (fluidHeld!=null && fluidHeld.getFluid()== FluidRegistry.getFluid("water") && tileEntity instanceof TileSolarCollector)
        {
            TileSolarCollector tank = (TileSolarCollector)tileEntity;
            if (tank.canReceiveWaterBucket())
            {
                tank.addBucketOfWater();
                if (FluidContainerRegistry.isBucket(held) && !(((EntityPlayerMP)player).theItemInWorldManager.isCreative()))
                {
                    int heldLocation = player.inventory.currentItem;
                    player.inventory.decrStackSize(heldLocation,1);
                    world.spawnEntityInWorld(new EntityItem(world,player.posX,player.posY,player.posZ,new ItemStack(Items.bucket, 1)));
//                    player.inventory.setInventorySlotContents(heldLocation, new ItemStack(Items.bucket, 1));
                }
            }
            return true;
        }
        if (held != null && held.getItem() == Items.bucket && tileEntity instanceof TileSolarCollector)
        {
            TileSolarCollector tank = (TileSolarCollector)tileEntity;
            if (tank.canGiveSolarWater()) {
                ItemStack filledBucket = FluidContainerRegistry.fillFluidContainer(new FluidStack(ModFluids.fluidSolarWater,1000), new ItemStack(Items.bucket, 1));
                tank.removeBucketOfSolarWater();
                if(!(((EntityPlayerMP)player).theItemInWorldManager.isCreative()))
                {
                    player.inventory.decrStackSize(player.inventory.currentItem,1);
                    //player.inventory.setInventorySlotContents(player.inventory.getFirstEmptyStack(), filledBucket);
                    world.spawnEntityInWorld(new EntityItem(world,player.posX,player.posY,player.posZ,filledBucket));
                }
                return true;
            }
        }



        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tileEntity =  world.getTileEntity(x, y, z);
        if (tileEntity!=null && tileEntity instanceof TileSolarCollector)
        {
            return ((TileSolarCollector)tileEntity).getLightValue();
        }
        return 0;
    }
}
