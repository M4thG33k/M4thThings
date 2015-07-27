package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.blocks.*;
import com.M4thG33k.m4ththings.blocks.dyeSeeds.BlockDyeCrop;
import com.M4thG33k.m4ththings.items.*;
import com.M4thG33k.m4ththings.items.tankComponents.ItemQTControllerMeta;
import com.M4thG33k.m4ththings.items.tankComponents.ItemQTValveMeta;
import com.M4thG33k.m4ththings.items.tankComponents.ItemQuantumTank;
import com.M4thG33k.m4ththings.reference.Configurations;
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
    public static final BlockWaterGenerator blockWaterGenerator = new BlockWaterGenerator(Material.ground);
    public static final BlockBaseRFGenerator blockBaseRFGenerator = new BlockBaseRFGenerator(Material.iron);
    public static final BlockTEST blockTEST = new BlockTEST(Material.ground);

    //0.1.1
    public static final BlockFluidTransferPipe blockFluidTransferPipe = new BlockFluidTransferPipe(Material.rock);
    public static final BlockDyeCrop blockDyeCrop = new BlockDyeCrop();

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
        GameRegistry.registerBlock(blockQTValve, ItemQTValveMeta.class,"blockQTValve");
        GameRegistry.registerBlock(blockMedQTController,ItemQTControllerMeta.class,"blockMedQTController");
        GameRegistry.registerBlock(blockQTComponent,"blockQTComponent");
        GameRegistry.registerBlock(blockM4thBlock, ItemM4thBlock.class,"blockM4thBlock");
//        GameRegistry.registerBlock(blockEnergyCage, "blockEnergyCage");
        GameRegistry.registerBlock(blockSolarCollector,"blockSolarCollector");
        GameRegistry.registerBlock(blockTextureDummy,"blockTextureDummy");
        if (Configurations.ENABLE_WATER_GEN) {
            GameRegistry.registerBlock(blockWaterGenerator, ItemWaterGenerator.class, "blockWaterGenerator");
        }
        GameRegistry.registerBlock(blockBaseRFGenerator, ItemBlockBaseRFGenerator.class, "blockBaseRFGenerator");
        //GameRegistry.registerBlock(blockTEST, "blockTEST");

        //0.1.1
        //GameRegistry.registerBlock(blockFluidTransferPipe, "blockFluidTransferPipe");
        GameRegistry.registerBlock(blockDyeCrop,"dyeCrop");
    }
}
