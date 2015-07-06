package com.M4thG33k.m4ththings.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by M4thG33k on 7/6/2015.
 */
public class PacketNBT implements IMessage {

    public int[] location;
    public NBTTagCompound tagCompound;

    public PacketNBT()
    {

    }

    public PacketNBT(int x, int y, int z, NBTTagCompound tagCompound)
    {
        this.location = new int[]{x,y,z};
        this.tagCompound = tagCompound;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        location = new int[3];
        for (int i=0;i<3;i++)
        {
            location[i] = ByteBufUtils.readVarInt(buf,5);
        }
        tagCompound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        for (int i=0;i<3;i++)
        {
            ByteBufUtils.writeVarInt(buf,location[i],5);
        }

        ByteBufUtils.writeTag(buf,tagCompound);
    }
}
