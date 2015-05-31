package com.M4thG33k.m4ththings.managers;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 * Created by M4thG33k on 5/30/2015.
 */
public class VanillaCraftingManager {

    public void init() {


        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockCobbleChest),"ccc","c c","ccc",'c',new ItemStack(GameRegistry.findBlock("ExtraUtilities","cobblestone_compressed"),1,0));


    }

}
