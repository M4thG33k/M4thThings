package com.M4thG33k.m4ththings.managers;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.init.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by M4thG33k on 5/30/2015.
 */
public class VanillaCraftingManager {

    public void init() {


//        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockCobbleChest),"ccc","c c","ccc",'c',new ItemStack(GameRegistry.findBlock("ExtraUtilities","cobblestone_compressed"),1,0));
        GameRegistry.addRecipe(new ItemStack(ModItems.itemModIngot,1,0)," g ","gig"," g ",'g',new ItemStack(Items.gold_nugget,1),'i',new ItemStack(Items.iron_ingot,1));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuantumTank,1),"ggg","gig","ggg",'g',new ItemStack(Blocks.glass,1),'i',new ItemStack(ModItems.itemModIngot,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQTValve,2,0),"ii","ii",'i',new ItemStack(ModItems.itemModIngot,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockM4thBlock,1,0),"iii","iii","iii",'i',new ItemStack(ModItems.itemModIngot,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockM4thBlock,1,0)," g ","gig"," g ",'g',new ItemStack(Items.gold_ingot,1),'i',new ItemStack(Blocks.iron_block,1));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemModIngot,9,0),new ItemStack(ModBlocks.blockM4thBlock,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMedQTController,1,0)," i ","iti"," i ",'i',new ItemStack(ModItems.itemModIngot,1,0),'t',new ItemStack(ModBlocks.blockQuantumTank,1));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMedQTController,1,1)," m ","mvm"," m ",'m',new ItemStack(ModBlocks.blockMedQTController,1,0),'v',new ItemStack(ModBlocks.blockQTValve,1));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,1),new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(Blocks.stone,1)); //import valve
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(ModBlocks.blockQTValve,1,1)); //import->normal valve
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,2),new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(Items.redstone,1),new ItemStack(Items.redstone,1)); //export valve
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(ModBlocks.blockQTValve,1,2)); //import->normal valve

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQuantumTank,1),new ItemStack(ModBlocks.blockQuantumTank,1)); //allows re-crafting tanks to remove their inventory

        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockWaterGenerator,1,0)," L ","LbL"," L ",'L',new ItemStack(Items.dye,1,4),'b',new ItemStack(Items.bucket)); //aquam accio
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockWaterGenerator,1,1)," g ","WeW"," g ",'g',new ItemStack(ModItems.itemModIngot,1,0),'W',new ItemStack(Item.getItemFromBlock(ModBlocks.blockWaterGenerator),1,0),'e',new ItemStack(Items.ender_pearl,1)); //magnum aquam accio

        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSolarCollector,1),"ggg","sGs","sss",'g',new ItemStack(Item.getItemFromBlock(Blocks.glass),1),'G',new ItemStack(ModItems.itemModIngot,1,0),'s',new ItemStack(Item.getItemFromBlock(Blocks.stone),1)); // solar collector
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockBaseRFGenerator,1,0), "srs","rGr","srs",'r',new ItemStack(Items.redstone,1),'G',new ItemStack(ModItems.itemModIngot,1,0),'s',new ItemStack(Item.getItemFromBlock(Blocks.stone),1)); // solar generator
    }

}
