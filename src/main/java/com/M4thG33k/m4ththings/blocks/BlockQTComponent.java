package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.*;
import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class BlockQTComponent extends Block implements ITileEntityProvider {

    IIcon[] icons = new IIcon[4];

    public BlockQTComponent(Material material)
    {
        super(material);
        setHardness(2.0f);
        setResistance(5.0f);
        setBlockName(Reference.MOD_ID + "_" + "blockQTComponent");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.glass.stepSound);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = Blocks.glass.getIcon(0,0);
        icons[1] = reg.registerIcon(Reference.MOD_ID + ":" + "blockQTValve");
        icons[2] = reg.registerIcon(Reference.MOD_ID + ":" + "blockQTValveImport");
        icons[3] = reg.registerIcon(Reference.MOD_ID + ":" + "blockQTValveExport");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (meta)
        {
            case 1:
                return icons[1];
            case 2:
                return icons[2];
            case 3:
                return icons[3];
            default:
                return icons[0];
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        switch (meta)
        {
            case (1):
                return new TileQTComponentValve();
            case (2):
                return new TileQTComponentValveImport();
            case (3):
                return new TileQTComponentValveExport();
            default:
                return new TileQTComponent();

        }
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
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity thisTileEntity = world.getTileEntity(x,y,z);
        if (thisTileEntity!=null && thisTileEntity instanceof TileQTComponent)
        {
            int[] parentCoords = ((TileQTComponent)thisTileEntity).getParentCoordinates();

            if (parentCoords!=null) {
                TileEntity parent = world.getTileEntity(parentCoords[0], parentCoords[1], parentCoords[2]);
                if (parent != null && parent instanceof TileMedQT) {
                    ((TileMedQT) parent).breakStructure(x, y, z);
                }
            }
            else{
                LogHelper.info("Something's wrong...my parent's missing. I live at: " + StringHelper.makeCoords(x,y,z));
            }

        }
    }

//    @Override
//    public Item getItemDropped(int meta, Random rand, int fortune) {
//
//        ItemStack temp;
//        switch (meta)
//        {
//            case 1:
//                LogHelper.info("Dropping regular valve");
//                temp = new ItemStack(ModBlocks.blockQTValve,1,0);
//                return temp.getItem();
//            case 2:
//                LogHelper.info("Dropping import valve");
//                temp = new ItemStack(ModBlocks.blockQTValve,1,1);
//                return temp.getItem();
//            default:
//                return Item.getItemFromBlock(Blocks.glass);
//        }
//    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> toDrop = new ArrayList<ItemStack>();

        switch(metadata)
        {
            case 1:
                toDrop.add(new ItemStack(ModBlocks.blockQTValve,1,0));
                break;
            case 2:
                toDrop.add(new ItemStack(ModBlocks.blockQTValve,1,1));
                break;
            case 3:
                toDrop.add(new ItemStack(ModBlocks.blockQTValve,1,2));
            default:
                toDrop.add(new ItemStack(Blocks.glass,1));
                break;
        }

        return toDrop;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if(tileEntity!=null && tileEntity instanceof TileQTComponent)
        {
            TileMedQT parent = ((TileQTComponent)tileEntity).getParentTank();
            if (parent!=null)
            {
                int toReturn = parent.getLightValue();
//                LogHelper.info("Light Level: " + toReturn);
                return toReturn;
            }
        }
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity!=null && tileEntity instanceof TileQTComponent)
        {
            int[] coords = ((TileQTComponent)tileEntity).getParentCoordinates();

            if (coords!=null)
            {
                return world.getBlock(coords[0],coords[1],coords[2]).onBlockActivated(world,coords[0],coords[1],coords[2],player,side,hitX,hitY,hitZ);
            }
        }
        return false;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        TileEntity tileComp = world.getTileEntity(x,y,z);
        if (tileComp!=null && tileComp instanceof TileQTComponent)
        {
            TileMedQT parentTile = ((TileQTComponent) tileComp).getParentTank();
            
            if (parentTile!=null)
            {
                int tankSize = parentTile.getTankSize();
                switch (tankSize)
                {
                    case 1: //medium
                        return AxisAlignedBB.getBoundingBox(parentTile.xCoord-1,parentTile.yCoord-1,parentTile.zCoord-1,parentTile.xCoord+2,parentTile.yCoord+2,parentTile.zCoord+2);
                    case 2: //large
                        return AxisAlignedBB.getBoundingBox(parentTile.xCoord-4,parentTile.yCoord-4,parentTile.zCoord-4,parentTile.xCoord+5,parentTile.yCoord+5,parentTile.zCoord+5);
                    default: //small: should never resort to this
                        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
                }
            }
        }
        
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    //
//    @Override
//    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
//        super.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
//    }
}
