package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModGuis;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileBaseChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by M4thG33k on 6/2/2015.
 */
public class BlockBaseChest extends BlockContainer {

    public BlockBaseChest(Material material)
    {
        super(material);
        setHardness(2.0F);
        setResistance(5.0F);
        setBlockName(Reference.MOD_ID + "_" + "blockBaseChest");
        setBlockTextureName(Reference.MOD_ID + ":" + "blockBaseChest");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitx, float hity, float hitz)
    {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity==null || player.isSneaking())
        {
            return false;
        }

        player.openGui(M4thThings.m4ththings, ModGuis.GUIs.BASE_CHEST.ordinal(), world, x, y, z);
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6)
    {
        dropItems(world,x,y,z);
        super.breakBlock(world,x,y,z,par5,par6);
    }

    private void dropItems(World world, int x, int y, int z)
    {
        Random rand = new Random();

        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (!(tileEntity instanceof IInventory))
        {
            return;
        }

        IInventory inventory = (IInventory)tileEntity;

        for (int i=0;i<inventory.getSizeInventory();i++)
        {
            ItemStack item = inventory.getStackInSlot(i);

            if (item!=null && item.stackSize>0)
            {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x+rx,y+ry,z+rz,new ItemStack(item.getItem(),item.stackSize,item.getItemDamage()));

                if (item.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian()*factor;
                entityItem.motionY = rand.nextGaussian()*factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian()*factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world,int metadata)
    {

        return new TileBaseChest();
    }




}
