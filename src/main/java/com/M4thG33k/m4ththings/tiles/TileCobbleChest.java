package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class TileCobbleChest extends TileEntity implements IInventory {

    private ItemStack[] inv;
    private int cobble;

    public TileCobbleChest()
    {
        inv = null;
        cobble = 0;
    }


    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public String getInventoryName() {
        return Reference.MOD_ID + "_" + "tileCobbleChest";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return (p_94041_2_.getItem() == Item.getItemFromBlock(Blocks.cobblestone));
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        if (cobble>0)
        {
            ItemStack stack = new ItemStack(Blocks.cobblestone,Math.min(64,cobble));
            return stack;
        }
        return null;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return worldObj.getTileEntity(xCoord,yCoord,zCoord) == this && p_70300_1_.getDistanceSq(xCoord+0.5,yCoord+0.5,zCoord+0.5)<64;
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
        cobble = tagCompound.getInteger("Cobble");
        LogHelper.info("Reading from NBT: " + tagCompound.getInteger("Cobble"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("Cobble",cobble);
        LogHelper.info("Writing to NBT: " + tagCompound.getInteger("Cobble"));
    }

    //takes the cobble and adds as much into the chest as possible. It returns whatever is left over if the chest is full
    public ItemStack addCobble(ItemStack itemStack)
    {
        if (cobble == Reference.MAX_COBBLE_ALLOWED)
        {
            return itemStack;
        }

        if (cobble + itemStack.stackSize > Reference.MAX_COBBLE_ALLOWED)
        {
            ItemStack toReturn = new ItemStack(itemStack.getItem(),itemStack.stackSize-(Reference.MAX_COBBLE_ALLOWED-cobble));
            cobble = Reference.MAX_COBBLE_ALLOWED;
            return toReturn;
        }

        cobble += itemStack.stackSize;
        return null;
    }

    public int getCobble()
    {
        return cobble;
    }

}
