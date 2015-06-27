package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Created by M4thG33k on 6/26/2015.
 */
public class ItemModIngot extends Item {

    IIcon[] icons = new IIcon[1];

    public ItemModIngot()
    {
        setUnlocalizedName(Reference.MOD_ID + "_" + "itemModIngot");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setMaxStackSize(64);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Reference.MOD_ID + ":" + "goldPlatedIron");
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        switch (meta)
        {
            default:
                return icons[0];
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName() + "_" + itemStack.getItemDamage();
    }
}
