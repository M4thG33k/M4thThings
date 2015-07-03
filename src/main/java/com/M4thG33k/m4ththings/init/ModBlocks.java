package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.blocks.*;
import com.M4thG33k.m4ththings.items.ItemM4thBlock;
import com.M4thG33k.m4ththings.items.ItemQTControllerMeta;
import com.M4thG33k.m4ththings.items.ItemQuantumTank;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ModBlocks {

    public static final BlockCobbleChest blockCobbleChest = new BlockCobbleChest();
//    public static final BlockBaseChest blockBaseChest = new BlockBaseChest(Material.rock);
//    public static final BlockFrozenDiamond blockFrozenDiamond = new BlockFrozenDiamond(Material.ice);
//    public static final BlockBaseTank blockBaseTank = new BlockBaseTank(Material.rock);
    public static final BlockQuantumTank blockQuantumTank = new BlockQuantumTank(Material.glass);
//    public static final BlockQuantumTankValve blockQuantumTankValve = new BlockQuantumTankValve(Material.iron);
//    public static final BlockMediumQTController blockMediumQTController = new BlockMediumQTController(Material.glass);
//    public static final BlockTankAir blockTankAir = new BlockTankAir(Material.glass);
//    public static final BlockTankTop blockTankTop = new BlockTankTop(Material.glass);
    public static final BlockQTValve blockQTValve = new BlockQTValve(Material.iron);
    public static final BlockMedQTController blockMedQTController = new BlockMedQTController(Material.glass);
    public static final BlockQTComponent blockQTComponent = new BlockQTComponent(Material.glass);
    public static final BlockM4thBlock blockM4thBlock = new BlockM4thBlock(Material.iron);
    public static final BlockEnergyCage blockEnergyCage = new BlockEnergyCage(Material.piston);
    public static final BlockSolarCollector blockSolarCollector = new BlockSolarCollector(Material.iron);
    public static final BlockTextureDummy blockTextureDummy = new BlockTextureDummy(Material.rock);

    public static void init()
    {
//        GameRegistry.registerBlock(blockCobbleChest,"blockCobbleChest");
//        GameRegistry.registerBlock(blockBaseChest,"blockBaseChest");
//        GameRegistry.registerBlock(blockFrozenDiamond,"blockFrozenDiamond");
//        GameRegistry.registerBlock(blockBaseTank,ItemBaseTank.class,"blockBaseTank");
        GameRegistry.registerBlock(blockQuantumTank, ItemQuantumTank.class,"blockQuantumTank");
//        GameRegistry.registerBlock(blockQuantumTankValve,"blockQuantumTankValve");
//        GameRegistry.registerBlock(blockMediumQTController,"blockMediumQTController");
//        GameRegistry.registerBlock(blockTankAir,"blockTankAir");
//        GameRegistry.registerBlock(blockTankTop,"blockTankTop");
        GameRegistry.registerBlock(blockQTValve,"blockQTValve");
        GameRegistry.registerBlock(blockMedQTController,ItemQTControllerMeta.class,"blockMedQTController");
        GameRegistry.registerBlock(blockQTComponent,"blockQTComponent");
        GameRegistry.registerBlock(blockM4thBlock, ItemM4thBlock.class,"blockM4thBlock");
//        GameRegistry.registerBlock(blockEnergyCage, "blockEnergyCage");
//        GameRegistry.registerBlock(blockSolarCollector,"blockSolarCollector");
        GameRegistry.registerBlock(blockTextureDummy,"blockTextureDummy");
    }
}
