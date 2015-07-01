package com.M4thG33k.m4ththings.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class PacketFilling implements IMessage {

    private int direction; //0-5: U,D,N,S,W,E
    private int isFilling; //1 if filling, anything else if draining
    private int x;
    private int y;
    private int z;
    private int dimensionID;

    public PacketFilling(){}

    public PacketFilling(int X, int Y, int Z, int ID, int direc, int filling)
    {
        x = X;
        y = Y;
        z = Z;
        dimensionID = ID;
        this.direction = direc;
        this.isFilling = filling;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = ByteBufUtils.readVarInt(buf,5);
        y = ByteBufUtils.readVarInt(buf,5);
        z = ByteBufUtils.readVarInt(buf,5);
        dimensionID = ByteBufUtils.readVarInt(buf,5);
        direction = ByteBufUtils.readVarInt(buf,1);
        isFilling = ByteBufUtils.readVarInt(buf,1);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarInt(buf,x,5);
        ByteBufUtils.writeVarInt(buf,y,5);
        ByteBufUtils.writeVarInt(buf,z,5);
        ByteBufUtils.writeVarInt(buf,dimensionID,5);
        ByteBufUtils.writeVarInt(buf,direction,1);
        ByteBufUtils.writeVarInt(buf,isFilling,1);
    }

    public int getDirection()
    {
        return direction;
    }

    public int getIsFilling()
    {
        return isFilling;
    }

    public int getDimensionID()
    {
        return dimensionID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
