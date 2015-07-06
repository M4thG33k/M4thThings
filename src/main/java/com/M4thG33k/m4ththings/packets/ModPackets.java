package com.M4thG33k.m4ththings.packets;

import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class ModPackets {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init()
    {
        INSTANCE.registerMessage(PacketFillingHandler.class,PacketFilling.class,0, Side.CLIENT);
        INSTANCE.registerMessage(PacketNBTHandler.class,PacketNBT.class,1,Side.CLIENT);
    }
}
