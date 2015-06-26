package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileCobbleChest;
import com.M4thG33k.m4ththings.utility.ChatHelper;
import com.M4thG33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 5/30/2015.
 */
public class ItemCobbleChestGrabber extends Item {

    private IIcon[] icons;

    public ItemCobbleChestGrabber()
    {
        setUnlocalizedName(Reference.MOD_ID + "_" + "cobbleChestGrabber");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setMaxStackSize(1);
        setTextureName(Reference.MOD_ID + ":" + "cobbleChestGrabber");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[2];
        icons[0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + "cobbleChestGrabberEmpty");
        icons[1] = iconRegister.registerIcon(Reference.MOD_ID + ":" + "cobbleChestGrabberFull");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (stack.hasTagCompound())
        {
            NBTTagCompound tagCompound = stack.getTagCompound();

            if (tagCompound.hasKey("Filled"))
            {
                if (tagCompound.getBoolean("Filled"))
                {
                    return icons[1];
                }
            }
        }
        return icons[0];
    }

    @Override
    public IIcon getIconIndex(ItemStack p_77650_1_) {
        return this.getIcon(p_77650_1_,0);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz) {
        if (world.isRemote)
        {
            return false;
        }

        //If the grabber has a block
        if (isFilled(stack)) {
            //check if you can place the block on the side of the hit block
            TileEntity tEnt;

            switch (side) {
                case 0:
                    if (!world.isAirBlock(x, y - 1, z)) {
                        return false;
                    }
                    world.setBlock(x, y - 1, z, ModBlocks.blockCobbleChest);
                    tEnt = world.getTileEntity(x,y-1,z);
                    break;
                case 1:
                    if (!world.isAirBlock(x, y + 1, z)) {
                        return false;
                    }
                    world.setBlock(x, y + 1, z, ModBlocks.blockCobbleChest);
                    tEnt = world.getTileEntity(x,y+1,z);
                    break;
                default:
                    return false;
            }
            if (tEnt!=null && (tEnt instanceof  TileCobbleChest))
            {
                if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Cobble"))
                {
                    LogHelper.info("I should be placing " + stack.getTagCompound().getInteger("Cobble") + " cobble in this chest.");
                }
                ((TileCobbleChest)tEnt).readFromNBT(stack.getTagCompound());
            }
            toggleFilled(stack);
            return true;
        }

        else if (world.getBlock(x,y,z) == ModBlocks.blockCobbleChest)
        {
            TileEntity tEnt = world.getTileEntity(x,y,z);
            if (tEnt==null || !(tEnt instanceof TileCobbleChest))
            {
                return false;
            }

            if (!stack.hasTagCompound())
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                stack.setTagCompound(tagCompound);
            }

            ((TileCobbleChest)tEnt).writeToNBT(stack.getTagCompound());
            if(!stack.getTagCompound().hasKey("Cobble"))
            {
                LogHelper.info("An error has occurred! I don't know how much cobble was in the chest.");
            }
            else{
                LogHelper.info("I have " + stack.getTagCompound().getInteger("Cobble") + " cobblestone stored to me.");
            }
            world.setBlockToAir(x,y,z);
            toggleFilled(stack);
            return true;
        }
        else{
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Cobble"))
            {
                ChatHelper.sayMessage(world,player,"My block contains " + stack.getTagCompound().getInteger("Cobble") +" cobble stored in it.");
            }
        }

        return false;

    }

    public boolean isFilled(ItemStack stack)
    {
        if (!stack.hasTagCompound())
        {
            return false;
        }

        if (!stack.getTagCompound().hasKey("Filled"))
        {
            return false;
        }

        return stack.getTagCompound().getBoolean("Filled");
    }

    //Changes the tag "Filled" to true<-->false and returns the updated value
    public boolean toggleFilled(ItemStack stack)
    {
        if (!stack.hasTagCompound())
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }

        NBTTagCompound tagCompound = stack.getTagCompound();
        if (!tagCompound.hasKey("Filled"))
        {
            tagCompound.setBoolean("Filled",true);
        }
        else
        {
            tagCompound.setBoolean("Filled",!tagCompound.getBoolean("Filled"));
        }

        return tagCompound.getBoolean("Filled");
    }

}
