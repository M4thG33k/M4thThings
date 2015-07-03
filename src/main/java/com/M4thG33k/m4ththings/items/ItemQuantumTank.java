package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Created by M4thG33k on 6/28/2015.
 */
public class ItemQuantumTank extends ItemBlock {

    public ItemQuantumTank(Block block)
    {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("FluidName"))
        {
            FluidStack fluid = new FluidStack(FluidRegistry.getFluid(stack.getTagCompound().getString("FluidName")),1);

            list.add(fluid.getLocalizedName());
            list.add(stack.getTagCompound().getInteger("Amount") + "/" + Configurations.QT_CAP);
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        boolean supers = super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);

        if (supers==false)
        {
            return false;
        }

        TileEntity tileEntity;

        int orientation;
        switch (side)
        {
            case 1:
                orientation = 0;
                tileEntity = world.getTileEntity(x,y+1,z);
                break;
            case 2:
                orientation=1;
                tileEntity = world.getTileEntity(x,y,z-1);
                break;
            case 3:
                orientation = 1;
                tileEntity = world.getTileEntity(x,y,z+1);
                break;
            case 4:
                orientation=2;
                tileEntity = world.getTileEntity(x-1,y,z);
                break;
            case 5:
                orientation = 2;
                tileEntity = world.getTileEntity(x+1,y,z);
                break;
            default:
                orientation = 0;
                tileEntity = world.getTileEntity(x,y-1,z);
                break;
        }

        if (tileEntity!=null && tileEntity instanceof TileQuantumTank)
        {
            ((TileQuantumTank)tileEntity).setOrientation(orientation);
        }

        return true;
    }
}
