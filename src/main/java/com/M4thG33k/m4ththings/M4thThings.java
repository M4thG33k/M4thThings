package com.M4thG33k.m4ththings;

import com.M4thG33k.m4ththings.Explosions.ExplosionHandler;
import com.M4thG33k.m4ththings.init.*;
import com.M4thG33k.m4ththings.managers.GuiHandler;
import com.M4thG33k.m4ththings.managers.VanillaCraftingManager;
import com.M4thG33k.m4ththings.packets.ModPackets;
import com.M4thG33k.m4ththings.proxy.CommonProxy;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * Created by M4thG33k on 5/28/2015.
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class M4thThings {

    public static VanillaCraftingManager vanillaCraftingManager = new VanillaCraftingManager();
    public static ExplosionHandler explosionHandler;

    @Mod.Instance(Reference.MOD_ID)
    public static M4thThings m4ththings;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        FMLInterModComms.sendMessage("Waila","register","com.M4thG33k.m4ththings.reference.WailaInteraction.load");
        Configurations.preInit(event);
        ModPackets.init();
        ModBlocks.init();
        ModFluids.init();
        ModTiles.init();
        ModItems.init();
        ModMobs.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        vanillaCraftingManager.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(m4ththings,new GuiHandler());
        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
