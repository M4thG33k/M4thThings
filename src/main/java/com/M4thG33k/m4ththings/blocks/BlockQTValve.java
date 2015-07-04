package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class BlockQTValve extends Block {

    IIcon[] icons = new IIcon[3];

    public BlockQTValve(Material material)
    {
        super(material);
        setHardness(2.0f);
        setResistance(5.0f);
        setBlockName(Reference.MOD_ID + "_" + "blockQTValve");
//        setBlockTextureName(Reference.MOD_ID + ":" + "blockQTValve");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.iron_block.stepSound);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Reference.MOD_ID + ":" + "blockQTValve");
        icons[1] = reg.registerIcon(Reference.MOD_ID + ":" + "blockQTValveImport");
        icons[2] = reg.registerIcon(Reference.MOD_ID + ":" + "blockQTValveExport");
    }

    @Override
    public IIcon getIcon(int side,int meta)
    {
        switch(meta)
        {
            case 1:
                return icons[1];
            case 2:
                return icons[2];
            default:
                return icons[0];
        }
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i=0;i<icons.length;i++)
        {
            list.add(new ItemStack(item,1,i));
        }
    }
}
