package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileMedQT;
import com.M4thG33k.m4ththings.tiles.TileQTComponent;
import com.M4thG33k.m4ththings.tiles.TileQTComponentValve;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class BlockQTComponent extends Block implements ITileEntityProvider {

    IIcon[] icons = new IIcon[2];

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
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (meta)
        {
            case 1:
                return icons[1];
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

            TileEntity parent = world.getTileEntity(parentCoords[0],parentCoords[1],parentCoords[2]);
            if(parent!=null && parent instanceof TileMedQT)
            {
                ((TileMedQT)parent).breakStructure(x,y,z);
            }

        }
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        switch (meta)
        {
            case 1:
                return Item.getItemFromBlock(ModBlocks.blockQTValve);
            default:
                return Item.getItemFromBlock(Blocks.glass);
        }
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
                return parent.getLightValue();
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
}
