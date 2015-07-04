package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.renderers.*;
import com.M4thG33k.m4ththings.tiles.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class ModRenderers {

    public static void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumTank.class, new QuantumTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMedQT.class, new MediumQuantumTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLargeQT.class, new LargeQTRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockQuantumTank), new QuantumTankItemRenderer(new QuantumTankRenderer(), new TileQuantumTank()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileSolarCollector.class, new SolarCollectorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileWaterGenerator.class, new WaterGeneratorRenderer());
    }
}
