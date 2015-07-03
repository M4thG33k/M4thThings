package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileLargeQT;
import com.M4thG33k.m4ththings.tiles.TileMedQT;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class BlockMedQTController extends Block implements ITileEntityProvider {

    private IIcon[] icons = new IIcon[4];

    public BlockMedQTController(Material material)
    {
        super(material);
        setHardness(2.0f);
        setResistance(5.0f);
        setBlockName(Reference.MOD_ID + "_" + "blockMedQTController");
        setBlockTextureName(Reference.MOD_ID + ":" + "defaultTexture");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setStepSound(Blocks.glass.stepSound);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch(meta)
        {
            case 1:
                return new TileLargeQT();
            default:
                return new TileMedQT();
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return false;
    }


    public boolean isBuilt(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity instanceof TileLargeQT)
        {
            return ((TileLargeQT)tileEntity).isStructureBuilt();
//            if (((TileLargeQT)tileEntity).isStructureBuilt())
//            {
//                return true;
//            }
//            return false;
        }



        if (tileEntity instanceof TileMedQT)
        {
            return ((TileMedQT)tileEntity).isStructureBuilt();
//            if (((TileMedQT) tileEntity).isStructureBuilt())
//            {
//                return true;
//            }
//            return false;
        }
        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        if (isBuilt(world,x,y,z))
        {
            return ((TileMedQT)world.getTileEntity(x,y,z)).getLightValue();
        }
        else
        {
            return 0;
        }
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
            if (tank.isStructureBuilt() && tank.fill(fluidHeld,false)==fluidHeld.amount) //changed here
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
        if (held!=null && held.getItem() == Items.bucket && tileEntity instanceof TileQuantumTank)
        {
            TileQuantumTank tank = (TileQuantumTank)tileEntity;
            if (tank.isStructureBuilt() && tank.drain(1000,false)!= null && tank.drain(1000,false).amount==1000) { //changed here
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

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        for (int i=0;i<2;i++)
        {
            list.add(new ItemStack(item,1,i));
        }
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
    public void registerBlockIcons(IIconRegister reg) {
        this.icons[0] = reg.registerIcon(Reference.MOD_ID + ":" + "medQT");
        this.icons[1] = reg.registerIcon(Reference.MOD_ID + ":" + "largeQT");
        this.icons[2] = reg.registerIcon(Reference.MOD_ID + ":" + "medQT_error");
        this.icons[3] = reg.registerIcon(Reference.MOD_ID + ":" + "largeQT_error");
    }


}
