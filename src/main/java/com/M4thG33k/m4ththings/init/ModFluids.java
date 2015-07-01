package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.fluids.FluidSolarWater;
import com.M4thG33k.m4ththings.items.ItemM4thBucket;
import com.M4thG33k.m4ththings.managers.BucketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by M4thG33k on 6/28/2015.
 */
public class ModFluids {

    public static Fluid fluidSolarWater;
    public static BlockFluidClassic fluidSolarWaterBlock;
    public static Item itemBucket;

    public static void init()
    {
        fluidSolarWater =  new Fluid("solarwater").setLuminosity(15).setViscosity(800);
        FluidRegistry.registerFluid(fluidSolarWater);
        fluidSolarWaterBlock= new FluidSolarWater(fluidSolarWater, Material.water);
//        fluidSolarWater.setStillIcon(fluidSolarWaterBlock.getIcon(0,0)).setFlowingIcon(fluidSolarWaterBlock.getIcon(2,2));
        GameRegistry.registerBlock(fluidSolarWaterBlock,"fluidSolarWater");
        itemBucket = new ItemM4thBucket(fluidSolarWaterBlock);
        GameRegistry.registerItem(itemBucket,"itemM4thBucket");
        FluidContainerRegistry.registerFluidContainer(fluidSolarWater,new ItemStack(itemBucket),new ItemStack(Items.bucket));

        BucketHandler.INSTANCE.buckets.put(fluidSolarWaterBlock,itemBucket);
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
    }
}
