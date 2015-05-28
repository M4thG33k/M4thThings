package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.blocks.BlockCobbleChest;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ModBlocks {

    public static final BlockCobbleChest blockCobbleChest = new BlockCobbleChest();

    public static void init()
    {
        GameRegistry.registerBlock(blockCobbleChest,"blockCobbleChest");
    }
}
