package com.M4thG33k.m4ththings.proxy;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.renderers.LargeQTRenderer;
import com.M4thG33k.m4ththings.renderers.MediumQuantumTankRenderer;
import com.M4thG33k.m4ththings.renderers.QuantumTankItemRenderer;
import com.M4thG33k.m4ththings.renderers.QuantumTankRenderer;
import com.M4thG33k.m4ththings.tiles.TileLargeQT;
import com.M4thG33k.m4ththings.tiles.TileMedQT;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ClientProxy extends CommonProxy{

    public static int renderPass;
    public static int  frozenDiamondRenderType;

    @Override
    public void registerRenderers()
    {
        super.registerRenderers();
//        ClientRegistry.bindTileEntitySpecialRenderer(TileCobbleChest.class, new CobbleChestRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileBaseTank.class, new BaseTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumTank.class, new QuantumTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMedQT.class, new MediumQuantumTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLargeQT.class, new LargeQTRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockQuantumTank),new QuantumTankItemRenderer(new QuantumTankRenderer(),new TileQuantumTank()));
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockBaseTank),new BaseTankItemRenderer(new BaseTankRenderer(),new TileBaseTank()));
    }

    public static void setCustomRenderers()
    {
//        frozenDiamondRenderType = RenderingRegistry.getNextAvailableRenderId();
//        RenderingRegistry.registerBlockHandler(new FrozenDiamondRenderer());
//        RenderingRegistry.registerEntityRenderingHandler(Boomer.class,new RenderCreeper());




    }

}
