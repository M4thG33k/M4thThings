package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.init.ModFluids;
import com.M4thG33k.m4ththings.tiles.TileSolarCollector;
import com.M4thG33k.m4ththings.tiles.TileSolarGenerator;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Created by M4thG33k on 7/6/2015.
 */
public class BlockBaseRFGenerator extends Block implements ITileEntityProvider {

    public BlockBaseRFGenerator(Material material)
    {
        super(material);
        setHardness(1.5f);
        setResistance(5.0f);
        setBlockName(StringHelper.nameHelper() + "blockBaseRFGenerator");
        setBlockTextureName(StringHelper.iconHelper() + "blockBaseRFGenerator");
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
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (meta)
        {
            default:
                return new TileSolarGenerator();
        }
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        for (int i=0;i<1;i++)
        {
            list.add(new ItemStack(item,1,i));
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }

        int meta = world.getBlockMetadata(x,y,z);

        switch (meta) {

            default:
                ItemStack held = player.getHeldItem();
                FluidStack fluidHeld = FluidContainerRegistry.getFluidForFilledItem(held);
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if (fluidHeld != null && fluidHeld.getFluid() == ModFluids.fluidSolarWater && tileEntity instanceof TileSolarGenerator) {
                    TileSolarGenerator tank = (TileSolarGenerator) tileEntity;
                    if (tank.canReceiveBucket()) {
                        tank.addBucket();
                        if (FluidContainerRegistry.isBucket(held) && !(((EntityPlayerMP) player).theItemInWorldManager.isCreative())) {
                            int heldLocation = player.inventory.currentItem;
                            player.inventory.decrStackSize(heldLocation, 1);
                            world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Items.bucket, 1)));
//                        player.inventory.setInventorySlotContents(heldLocation, new ItemStack(Items.bucket, 1));
                        }
                    }
                    return true;
                }

        }


        return false;
    }
}
