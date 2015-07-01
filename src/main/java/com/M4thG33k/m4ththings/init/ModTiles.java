package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.tiles.*;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ModTiles {

    public static void init()
    {
//        GameRegistry.registerTileEntity(TileCobbleChest.class,"tileCobbleChest");
//        GameRegistry.registerTileEntity(TileBaseChest.class,"tileBaseChest");
//        GameRegistry.registerTileEntity(TileBaseTank.class,"tileBaseTank");
        GameRegistry.registerTileEntity(TileQuantumTank.class,"tileQuantumTank");
//        GameRegistry.registerTileEntity(TileMediumQuantumTank.class,"tileMediumQuantumTank");
//        GameRegistry.registerTileEntity(TileQTShapeSeeker.class,"tileQTShapeSeeker");
//        GameRegistry.registerTileEntity(TileTankAir.class,"tileTankAir");
//        GameRegistry.registerTileEntity(TileTankTop.class,"tileTankTop");
        GameRegistry.registerTileEntity(TileQTComponent.class,"tileQTComponent");
        GameRegistry.registerTileEntity(TileMedQT.class,"tileMedQT");
        GameRegistry.registerTileEntity(TileQTComponentValve.class,"tileQTComponentValve");
        GameRegistry.registerTileEntity(TileLargeQT.class,"tileLargeQT");
        GameRegistry.registerTileEntity(TileEnergyCage.class,"tileEnergyCage");
        GameRegistry.registerTileEntity(TileSolarCollector.class,"tileSolarCollector");
    }
}
