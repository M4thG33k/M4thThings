package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.tiles.TileCobbleChest;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ModTiles {

    public static void init()
    {
        GameRegistry.registerTileEntity(TileCobbleChest.class,"tileCobbleChest");
    }
}
