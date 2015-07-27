package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by M4thG33k on 7/27/2015.
 */
public class ItemFluidGravitationCore extends Item{

    IIcon[] icons = new IIcon[2];

    public ItemFluidGravitationCore()
    {
        setUnlocalizedName(StringHelper.nameHelper() + "itemFluidGravitationCore");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setMaxStackSize(16);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(StringHelper.iconHelper() + "FluidGravitationalCore");
        icons[1] = reg.registerIcon(StringHelper.iconHelper() + "DenseFluidGravitationalCore");
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        switch (meta)
        {
            case 1:
                return icons[1];
            default:
                return icons[0];
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName() + "_" + itemStack.getItemDamage();
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (int i=0;i<2;i++)
        {
            list.add(new ItemStack(item,1,i));
        }
    }
}
