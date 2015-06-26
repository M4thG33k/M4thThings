package com.M4thG33k.m4ththings.containers;

import com.M4thG33k.m4ththings.tiles.TileBaseChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by M4thG33k on 6/2/2015.
 */
public class ContainerBaseChest extends Container {

    protected TileBaseChest tileEntity;

    public ContainerBaseChest (InventoryPlayer inventoryPlayer, TileBaseChest tile)
    {
        tileEntity = tile;

        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                addSlotToContainer(new Slot(tileEntity,j+i*3,62+j*18,17+i*18));
            }
        }

        bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return tileEntity.isUseableByPlayer(player);
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<9;j++)
            {
                addSlotToContainer(new Slot(inventoryPlayer,j+i*9+9,8+j*18,84+i*18));
            }
        }

        for (int i=0;i<9;i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer,i,8+i*18,142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        if (slotObject!=null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            if (slot < tileEntity.getSizeInventory())
            {
                if(!this.mergeItemStack(stackInSlot, tileEntity.getSizeInventory(), 36+tileEntity.getSizeInventory(),false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stackInSlot,0,tileEntity.getSizeInventory(),false))
            {
                return null;
            }

            if (stackInSlot.stackSize==0)
            {
                slotObject.putStack(null);
            }
            else
            {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize)
            {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
}
