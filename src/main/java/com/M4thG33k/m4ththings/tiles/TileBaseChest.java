package com.M4thG33k.m4ththings.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by M4thG33k on 6/2/2015.
 */
public class TileBaseChest extends TileEntity implements ISidedInventory {

    private ItemStack[] inv;

    public TileBaseChest()
    {
        inv = new ItemStack[9];
    }

    @Override
    public int getSizeInventory()
    {
        return inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot){
        return inv[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inv[slot] = stack;
        if (stack!=null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack stack = inv[slot];
        if (stack!=null)
        {
            if (stack.stackSize<=amount)
            {
                setInventorySlotContents(slot,null);
            }
            else
            {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0)
                {
                    setInventorySlotContents(slot,null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null)
        {
            setInventorySlotContents(slot,null);
        }
        return stack;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return (worldObj.getTileEntity(xCoord,yCoord,zCoord)==this && player.getDistanceSq(xCoord+0.5,yCoord+0.5,zCoord+0.5) < 64);
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);

        NBTTagList tagList = tagCompound.getTagList("Inventory",10);

        for (int i=0;i<tagList.tagCount();i++)
        {
            NBTTagCompound tag = (NBTTagCompound)tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >=0 && slot <inv.length)
            {
                inv[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);

        NBTTagList itemList = new NBTTagList();
        for (int i=0;i<inv.length;i++){
            ItemStack stack = inv[i];
            if (stack!=null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot",(byte)i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        tagCompound.setTag("Inventory",itemList);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return false;
    }

    @Override
    public String getInventoryName() {
        return "M4thThings.BaseChest";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return false;
    }
}
