package com.M4thG33k.m4ththings.items;

import com.M4thG33k.m4ththings.reference.Configurations;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("fluid"))
        {
            FluidStack fluid = new FluidStack(FluidRegistry.getFluid(stack.getTagCompound().getString("fluid")),1);

            list.add(fluid.getLocalizedName());
            list.add(stack.getTagCompound().getInteger("amount") + "/" + Configurations.QT_CAP);
        }
    }

}
