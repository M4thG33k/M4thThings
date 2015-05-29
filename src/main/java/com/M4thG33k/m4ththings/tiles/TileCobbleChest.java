package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class TileCobbleChest extends TileEntity implements IInventory {

    private ItemStack[] inv;
    private int cobble;
    private boolean isReinforced;

    public TileCobbleChest()
    {
        inv = new ItemStack[2];
        cobble = 0;
        isReinforced = false;
    }


    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        ItemStack stack = getStackInSlot(slot);

        if (stack!=null)
        {
            if (stack.stackSize<=amt)
            {
                setInventorySlotContents(slot,null);
            }
            else
            {
                stack = stack.splitStack(amt);
                if (stack.stackSize == 0)
                {
                    setInventorySlotContents(slot,null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        if (p_70299_1_==1 && p_70299_2_.getItem()==Item.getItemFromBlock(Blocks.cobblestone))
        {
            inv[1] = p_70299_2_;
        }
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
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        if (p_94041_1_ != 1){
            return false;
        }
        return (p_94041_2_.getItem() == Item.getItemFromBlock(Blocks.cobblestone));
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot != 0 && slot != 1)
        {
            return null;
        }
        return inv[slot];
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
        if (tagCompound.hasKey("Output"))
        {
            inv[0] = new ItemStack(Blocks.cobblestone,tagCompound.getInteger("Output"));
        }
        else
        {
            inv[0] = null;
        }

        if (tagCompound.hasKey("Input"))
        {
            inv[1] = new ItemStack(Blocks.cobblestone,tagCompound.getInteger("Input"));
        }
        else
        {
            inv[1] = null;
        }

        if (tagCompound.hasKey("Reinforced"))
        {
            isReinforced = tagCompound.getBoolean("Reinforced");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("Cobble",cobble);
        if (inv[0]!= null)
        {
            tagCompound.setInteger("Output",inv[0].stackSize);
        }
        if (inv[1] != null)
        {
            tagCompound.setInteger("Input",inv[1].stackSize);
        }
        tagCompound.setBoolean("Reinforced",isReinforced);
    }

    //takes the cobble and adds as much into the chest as possible. It returns whatever is left over if the chest is full
    public ItemStack addCobble(ItemStack itemStack)
    {
        //return null if the input is null
        if (itemStack==null || itemStack.stackSize == 0)
        {
            return null;
        }

        int amount = itemStack.stackSize;
        //first try to fill up the output slot
        if (inv[0]==null)
        {
            inv[0] = itemStack;
            return null;
        }
        else
        {
            if (inv[0].stackSize<64)
            {
                int newSize = Math.min(inv[0].stackSize+amount,64);
                amount -= (newSize-inv[0].stackSize);
                inv[0].stackSize = newSize;
            }
        }

        //next try to add to the internal buffer
        if (amount>0 && cobble< Configurations.MAX_COBBLE_ALLOWED)
        {
            int newSize = Math.min(Configurations.MAX_COBBLE_ALLOWED,cobble+amount);
            amount -= (newSize-cobble);
            cobble = newSize;
        }

        //Lastly, try to add it to the input slot
        if (amount>0)
        {
            if (inv[1]==null)
            {
                inv[1] = new ItemStack(itemStack.getItem(),amount);
                return null;
            }
            if (inv[1].stackSize<64)
            {
                int newSize = Math.min(64,inv[1].stackSize+amount);
                amount -= (newSize-inv[1].stackSize);
                inv[1].stackSize = newSize;
            }
        }

        //return leftovers, if they exist
        if (amount==0)
        {
            return null;
        }

        return new ItemStack(itemStack.getItem(),amount);
    }

    public int getCobble(int slot)
    {
        if (slot == 0)
        {
            int toReturn = cobble;
            if (inv[0] != null) {
                toReturn += inv[0].stackSize;
            }
            if (inv[1] != null) {
                toReturn += inv[1].stackSize;
            }
            return toReturn;
        }
        else if (slot==1)
        {
            return cobble;
        }
        else if (slot==2)
        {
            if (inv[0]!=null)
            {
                return inv[0].stackSize;
            }
            return 0;
        }
        else
        {
            if (inv[1]!=null)
            {
                return inv[1].stackSize;
            }
            return 0;
        }
    }

    @Override
    public void updateEntity() {
        int tempVal;
        //move cobble from storage into the 'output' slot
        if (inv[0]==null && cobble>0)
        {
            tempVal = Math.min(64,cobble);
            inv[0] = new ItemStack(Item.getItemFromBlock(Blocks.cobblestone),tempVal);
            cobble -= tempVal;
        }
        if (inv[0]!=null && inv[0].stackSize<64 && cobble>0)
        {
            tempVal = Math.min(64,inv[0].stackSize+cobble);
            cobble -= (tempVal-inv[0].stackSize);
            inv[0].stackSize = tempVal;
        }

        //move cobble from the 'input' slot into storage
        if (cobble<Configurations.MAX_COBBLE_ALLOWED && inv[1]!=null && inv[1].stackSize!=0)
        {
            if ((cobble + inv[1].stackSize) > Configurations.MAX_COBBLE_ALLOWED)
            {
                tempVal = Configurations.MAX_COBBLE_ALLOWED - cobble;
                inv[1].splitStack(tempVal);
                cobble = Configurations.MAX_COBBLE_ALLOWED;
            }
            else
            {
                tempVal = inv[1].stackSize;
                cobble += tempVal;
                inv[1].splitStack(tempVal);
            }
        }
    }

    //return an itemstack of cobble with the given amount (capped at 64 and limited by how much is in the internal buffer)
    public ItemStack popCobbleFromInternalBuffer(int amount)
    {
        int amountToGive = Math.min(64,cobble);
        amountToGive = Math.min(amountToGive,amount);
        cobble -= amountToGive;

        if (amountToGive==0)
        {
            return null;
        }

        return new ItemStack(Blocks.cobblestone,amountToGive);
    }

    //attempts to make the block reinforced. If it is successful, it returns true. If it was already reinforced, it returns false
    public boolean makeReinforced()
    {
        if (!isReinforced)
        {
            isReinforced = true;
            FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendChatMsg(new ChatComponentText("Block at " + this.xCoord + " " + this.yCoord + " " + this.zCoord + " is reinforced"));
            return true;
        }
        return false;
    }

    public boolean getReinforced()
    {
        return isReinforced;
    }
}
