package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.tiles.TileCobbleChest;
import com.M4thG33k.m4ththings.utility.ChatHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class BlockCobbleChest extends BlockContainer {

    public BlockCobbleChest(Material material)
    {
        super(material);
        this.setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    public BlockCobbleChest()
    {
        super(Material.rock);
        this.setHardness(2.0f);
        this.setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    public TileEntity createNewTileEntity(World world,int meta)
    {
        return new TileCobbleChest();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        if (world.isRemote)
        {
            return true;
        }
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity == null || !(tileEntity instanceof TileCobbleChest))
        {
            return false;
        }
        TileCobbleChest tEnt = (TileCobbleChest)tileEntity;
        ItemStack held = player.getHeldItem();
        if (player.isSneaking() && held==null)
        {
            ChatHelper.sayMessage(world,player,"This chest currently holds " + tEnt.getCobble() + " cobblestone.");
            return true;
        }
        if (!player.isSneaking() && held==null)
        {
            for (int i=0;i<36;i++)
            {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if (slot!=null && slot.getItem()==Item.getItemFromBlock(Blocks.cobblestone))
                {
                    player.inventory.setInventorySlotContents(i,tEnt.addCobble(slot));
                }
            }
            return true;
        }
        return false;
    }


}
