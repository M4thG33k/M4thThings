package com.M4thG33k.m4ththings.packets;

import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.StringHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class PacketFillingHandler implements IMessageHandler<PacketFilling,IMessage> {

    @Override
    public IMessage onMessage(PacketFilling message, MessageContext ctx)
    {
        LogHelper.info("Received message from the server! " + message.getDirection() + ":" + message.getIsFilling());
        LogHelper.info("I should be spawning a particle at: " + message.getDimensionID()+ "::" + StringHelper.makeCoords(message.getX() + 1.0, message.getY() + 1.0, message.getZ() + 1.0));
        (DimensionManager.getWorld(message.getDimensionID())).spawnParticle("happyVillager", message.getX() + 1.0, message.getY() + 2.0, message.getZ() + 1.0, 0.0, 0.0, 0.0);
        return null;
    }
}
