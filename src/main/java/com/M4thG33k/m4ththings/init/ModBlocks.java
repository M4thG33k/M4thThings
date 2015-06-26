package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.blocks.*;
import com.M4thG33k.m4ththings.items.ItemBaseTank;
import com.M4thG33k.m4ththings.items.ItemQTControllerMeta;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ModBlocks {

    public static final BlockCobbleChest blockCobbleChest = new BlockCobbleChest();
    public static final BlockBaseChest blockBaseChest = new BlockBaseChest(Material.rock);
    public static final BlockFrozenDiamond blockFrozenDiamond = new BlockFrozenDiamond(Material.ice);
    public static final BlockBaseTank blockBaseTank = new BlockBaseTank(Material.rock);
    public static final BlockQuantumTank blockQuantumTank = new BlockQuantumTank(Material.glass);
    public static final BlockQuantumTankValve blockQuantumTankValve = new BlockQuantumTankValve(Material.iron);
    public static final BlockMediumQTController blockMediumQTController = new BlockMediumQTController(Material.glass);
    public static final BlockTankAir blockTankAir = new BlockTankAir(Material.glass);
    public static final BlockTankTop blockTankTop = new BlockTankTop(Material.glass);
    public static final BlockQTValve blockQTValve = new BlockQTValve(Material.iron);
    public static final BlockMedQTController blockMedQTController = new BlockMedQTController(Material.glass);
    public static final BlockQTComponent blockQTComponent = new BlockQTComponent(Material.glass);

    public static void init()
    {
//        GameRegistry.registerBlock(blockCobbleChest,"blockCobbleChest");
//        GameRegistry.registerBlock(blockBaseChest,"blockBaseChest");
//        GameRegistry.registerBlock(blockFrozenDiamond,"blockFrozenDiamond");
//        GameRegistry.registerBlock(blockBaseTank,ItemBaseTank.class,"blockBaseTank");
        GameRegistry.registerBlock(blockQuantumTank,"blockQuantumTank");
//        GameRegistry.registerBlock(blockQuantumTankValve,"blockQuantumTankValve");
//        GameRegistry.registerBlock(blockMediumQTController,"blockMediumQTController");
//        GameRegistry.registerBlock(blockTankAir,"blockTankAir");
//        GameRegistry.registerBlock(blockTankTop,"blockTankTop");
        GameRegistry.registerBlock(blockQTValve,"blockQTValve");
        GameRegistry.registerBlock(blockMedQTController,ItemQTControllerMeta.class,"blockMedQTController");
        GameRegistry.registerBlock(blockQTComponent,"blockQTComponent");
    }
}
