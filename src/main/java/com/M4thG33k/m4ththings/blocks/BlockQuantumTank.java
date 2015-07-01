package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class BlockQuantumTank extends Block implements ITileEntityProvider {


    public BlockQuantumTank(Material material)
    {
        super(material);
        setHardness(2.0f);
        setResistance(5.0f);
        setStepSound(Blocks.glass.stepSound);
        setBlockName(Reference.MOD_ID + "_" + "blockQuantumTank");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuantumTank();
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
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity instanceof TileQuantumTank)
        {
            TileQuantumTank tank = (TileQuantumTank)tileEntity;
            if (tank.getFluid()==null || tank.getFluidAmount()==0)
            {
                return 0;
            }
            else {
                int fluidLight = tank.getFluid().getFluid().getLuminosity();
                double percentage = ((double) tank.getFluidAmount()) / ((double) tank.getCapacity());
                return (int) (percentage * fluidLight);
            }
        }

        return 0;
    }

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
        if (fluidHeld!=null && tileEntity instanceof TileQuantumTank)
        {
            TileQuantumTank tank = (TileQuantumTank)tileEntity;
            if (tank.fill(fluidHeld,false)==fluidHeld.amount)
            {
                tank.fill(fluidHeld,true);
                if (FluidContainerRegistry.isBucket(held))
                {
                    int heldLocation = player.inventory.currentItem;
                    player.inventory.decrStackSize(heldLocation,1);
                    world.spawnEntityInWorld(new EntityItem(world,player.posX,player.posY,player.posZ,new ItemStack(Items.bucket, 1)));
//                    player.inventory.setInventorySlotContents(heldLocation, new ItemStack(Items.bucket, 1));
                }
            }
            return true;
        }
        if (held != null && held.getItem() == Items.bucket && tileEntity instanceof TileQuantumTank)
        {
            TileQuantumTank tank = (TileQuantumTank)tileEntity;
            if (tank.drain(1000,false)!= null && tank.drain(1000,false).amount==1000) {
                ItemStack filledBucket = FluidContainerRegistry.fillFluidContainer(tank.getFluid(), new ItemStack(Items.bucket, 1));
                tank.drain(1000,true);
                player.inventory.decrStackSize(player.inventory.currentItem,1);
                //player.inventory.setInventorySlotContents(player.inventory.getFirstEmptyStack(), filledBucket);
                world.spawnEntityInWorld(new EntityItem(world,player.posX,player.posY,player.posZ,filledBucket));
                return true;
            }
        }



        return false;
    }

//    @Override
//    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
//        TileEntity tileEntity = world.getTileEntity(x,y,z);
//
//        if (tileEntity!=null && tileEntity instanceof TileQuantumTank)
//        {
//            TileQuantumTank tank = (TileQuantumTank)tileEntity;
//            world.removeTileEntity(x,y,z);
//        }
//        super.breakBlock(world, x, y, z, block, meta);
//    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.glass.getIcon(0,0);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity!=null && tileEntity instanceof TileQuantumTank)
        {
            //do stuff and return
            TileQuantumTank tank = (TileQuantumTank)tileEntity;

            NBTTagCompound tagCompound = new NBTTagCompound();
            tank.writeToNBT(tagCompound);

            ItemStack toReturn = new ItemStack(world.getBlock(x,y,z),1,metadata);

            if (tagCompound!=null && tagCompound.hasKey("fluid"))
            {
                LogHelper.info("Setting the item's tag compound!");
                if (!toReturn.hasTagCompound())
                {
                    toReturn.setTagCompound(new NBTTagCompound());
                }
                toReturn.getTagCompound().setString("fluid",tagCompound.getString("fluid"));
                toReturn.getTagCompound().setInteger("amount",tagCompound.getInteger("amount"));
            }

            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            stacks.add(toReturn);
            return stacks;
        }
        else
        {
            return super.getDrops(world,x,y,z,metadata,fortune);
        }
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
    {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity==null)
        {
            LogHelper.info("The tile hasn't been created yet. :-/");
        }

        if (tileEntity!=null && tileEntity instanceof TileQuantumTank && stack.hasTagCompound())
        {
            tileEntity.readFromNBT(stack.getTagCompound());
        }
    }
}
