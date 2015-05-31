package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
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
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Filled"))
        {
            stack.getTagCompound().setBoolean("Filled",!stack.getTagCompound().getBoolean("Filled"));
            return true;
        }
        if (stack.hasTagCompound())
        {
            stack.getTagCompound().setBoolean("Filled",true);
            return true;
        }
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setBoolean("Filled", true);
        stack.setTagCompound(tagCompound);
        return true;

    }


}
