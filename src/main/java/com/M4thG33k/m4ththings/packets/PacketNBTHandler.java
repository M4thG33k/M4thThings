package com.M4thG33k.m4ththings.packets;

import com.M4thG33k.m4ththings.interfaces.IM4thNBTSync;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by M4thG33k on 7/6/2015.
 */
public class PacketNBTHandler implements IMessageHandler<PacketNBT,IMessage> {

    @Override
    public IMessage onMessage(PacketNBT message, MessageContext ctx)
    {
        if (message==null || ctx==null)
        {
            return null;
        }
        TileEntity tileEntity =  (Minecraft.getMinecraft().theWorld.getTileEntity(message.location[0],message.location[1],message.location[2]));

        if (tileEntity!=null && tileEntity instanceof IM4thNBTSync)
        {
            ((IM4thNBTSync)tileEntity).receiveNBTPacket(message.tagCompound);
        }

        return null;
    }
}
