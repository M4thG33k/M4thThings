package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz) {
        //If the grabber has a block
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Filled"))
        {
            //check if you can place the block on the side of the hit block
            switch (side)
            {
                case 0:
                    if (!world.isAirBlock(x,y-1,z))
                    {
                        return false;
                    }
                    world.setBlock(x,y-1,z, ModBlocks.blockCobbleChest);
                    break;
                case 1:
                    if (!world.isAirBlock(x,y+1,z))
                    {
                        return false;
                    }
                    world.setBlock(x,y+1,z,ModBlocks.blockCobbleChest);
                    break;
                default:
                    return false;
            }
            stack.getTagCompound().setBoolean("Filled", !stack.getTagCompound().getBoolean("Filled"));
            return true;
        }
        if (stack.hasTagCompound())
        {
            stack.getTagCompound().setBoolean("Filled",true);
            return false;
        }
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setBoolean("Filled", true);
        stack.setTagCompound(tagCompound);
        return false;

    }


}
