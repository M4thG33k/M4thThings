package com.M4thG33k.m4ththings.managers;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.init.ModItems;
import com.M4thG33k.m4ththings.reference.Configurations;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by M4thG33k on 5/30/2015.
 */
public class VanillaCraftingManager {

    private static int[] dyeCount = Configurations.DYE_FROM_CRAFTING;

    public void init() {


//        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockCobbleChest),"ccc","c c","ccc",'c',new ItemStack(GameRegistry.findBlock("ExtraUtilities","cobblestone_compressed"),1,0));
        GameRegistry.addRecipe(new ItemStack(ModItems.itemModIngot,1,0)," g ","gig"," g ",'g',new ItemStack(Items.gold_nugget,1),'i',new ItemStack(Items.iron_ingot,1));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuantumTank,1),"IVI","G G","IVI",'I',new ItemStack(ModItems.itemModIngot,1,0),'V',new ItemStack(ModBlocks.blockQTValve,1,0),'G',new ItemStack(Blocks.glass,1));
//        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuantumTank,1),"ggg","gig","ggg",'g',new ItemStack(Blocks.glass,1),'i',new ItemStack(ModItems.itemModIngot,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQTValve,2,0),"ii","ii",'i',new ItemStack(ModItems.itemModIngot,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockM4thBlock,1,0),"iii","iii","iii",'i',new ItemStack(ModItems.itemModIngot,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockM4thBlock,1,0)," g ","gig"," g ",'g',new ItemStack(Items.gold_ingot,1),'i',new ItemStack(Blocks.iron_block,1));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemModIngot,9,0),new ItemStack(ModBlocks.blockM4thBlock,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMedQTController,1,0),"GiG","iti","GiG",'i',new ItemStack(ModItems.itemModIngot,1,0),'t',new ItemStack(ModBlocks.blockQuantumTank,1),'G',new ItemStack(ModItems.itemFluidGravitationCore,1,0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMedQTController,1,1),"DmD","mvm","DmD",'m',new ItemStack(ModBlocks.blockMedQTController,1,0),'v',new ItemStack(ModBlocks.blockQTValve,1),'D',new ItemStack(ModItems.itemFluidGravitationCore,1,1));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,1),new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(Blocks.stone,1)); //import valve
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(ModBlocks.blockQTValve,1,1)); //import->normal valve
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,2),new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(Items.redstone,1),new ItemStack(Items.redstone,1)); //export valve
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQTValve,1,0),new ItemStack(ModBlocks.blockQTValve,1,2)); //import->normal valve

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockQuantumTank,1),new ItemStack(ModBlocks.blockQuantumTank,1)); //allows re-crafting tanks to remove their inventory

        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockWaterGenerator,1,0)," L ","LbL"," L ",'L',new ItemStack(Items.dye,1,4),'b',new ItemStack(Items.bucket)); //aquam accio
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockWaterGenerator,1,1)," g ","WeW"," g ",'g',new ItemStack(ModItems.itemModIngot,1,0),'W',new ItemStack(Item.getItemFromBlock(ModBlocks.blockWaterGenerator),1,0),'e',new ItemStack(Items.ender_pearl,1)); //magnum aquam accio

        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSolarCollector,1),"ggg","sGs","sss",'g',new ItemStack(Item.getItemFromBlock(Blocks.glass),1),'G',new ItemStack(ModItems.itemModIngot,1,0),'s',new ItemStack(Item.getItemFromBlock(Blocks.stone),1)); // solar collector
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockBaseRFGenerator,1,0), "srs","rGr","srs",'r',new ItemStack(Items.redstone,1),'G',new ItemStack(ModItems.itemModIngot,1,0),'s',new ItemStack(Item.getItemFromBlock(Blocks.stone),1)); // solar generator

        dyeSeedCrafting();

        GameRegistry.addRecipe(new ItemStack(ModItems.itemFluidGravitationCore,4,0),"rIr","IeI","rIr",'r',new ItemStack(Items.redstone,1),'I',new ItemStack(ModItems.itemModIngot,1,0),'e',new ItemStack(Items.ender_pearl,1));
        GameRegistry.addRecipe(new ItemStack(ModItems.itemFluidGravitationCore,1,1),"gg","gg",'g',new ItemStack(ModItems.itemFluidGravitationCore,1,0));
    }

    public void dyeSeedCrafting()
    {
        if (Configurations.ENABLE_DYE_SEEDS) {
            //dye base
            GameRegistry.addRecipe(new ItemStack(ModItems.itemDyeSeedBase), "RGB", "KCK", "BGR", 'R', new ItemStack(Items.dye, 1, 1), 'G', new ItemStack(Items.dye, 1, 2), 'B', new ItemStack(Items.dye, 1, 4), 'K', new ItemStack(Items.dye, 1, 0), 'C', Items.clay_ball);

            //recipe for the seeds
            GameRegistry.addRecipe(new ItemStack(ModItems.itemDyeSeeds, Configurations.SEEDS_FROM_CRAFTING), "SSS", "SBS", "SSS", 'S', Items.wheat_seeds, 'B', ModItems.itemDyeSeedBase);
        }


        //recipes for the dye (in order of data values)
        if (dyeCount[0]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[0],0),"qqq","   ","   ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[1]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[1],1),"   ","qqq","   ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[2]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[2],2),"   ","   ","qqq",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[3]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[3],3),"q  ","q  ","q  ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[4]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[4],4)," q "," q "," q ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[5]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[5],5),"  q","  q"," q ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[6]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[6],6),"q  "," q ","  q",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[7]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[7],7),"  q"," q "," q ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[8]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[8],8),"q q"," q ","   ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[9]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[9],9),"  q"," q ","  q",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[10]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[10],10),"   "," q ","q q",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[11]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[11],11),"q  ","   ","qq ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[12]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[12],12),"q q","   "," q ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[13]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[13],13)," q ","   ","q q",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[14]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[14],14),"qq ","  q","   ",'q',ModItems.itemQuasiDye);
        }

        if (dyeCount[15]>0)
        {
            GameRegistry.addRecipe(new ItemStack(Items.dye,dyeCount[15],15),"qq ","qq ","   ",'q',ModItems.itemQuasiDye);
        }
    }

}
