package com.M4thG33k.m4ththings;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.init.ModItems;
import com.M4thG33k.m4ththings.init.ModTiles;
import com.M4thG33k.m4ththings.proxy.CommonProxy;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by M4thG33k on 5/28/2015.
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class M4thThings {

    @Mod.Instance(Reference.MOD_ID)
    public static M4thThings m4ththings;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Configurations.preInit(event);
        ModBlocks.init();
        ModTiles.init();
        ModItems.init();
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
