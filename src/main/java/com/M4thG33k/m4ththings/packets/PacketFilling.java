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
    private String fluidName;
    private int amount;
    private int size;

    public PacketFilling(){}

    public PacketFilling(int X, int Y, int Z, int direc, int filling,String fluidName,int amount,int size)
    {
        x = X;
        y = Y;
        z = Z;
        this.direction = direc;
        this.isFilling = filling;
        this.fluidName = fluidName;
        this.amount = amount;
        this.size = size;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = ByteBufUtils.readVarInt(buf, 5);
        y = ByteBufUtils.readVarInt(buf,5);
        z = ByteBufUtils.readVarInt(buf,5);
        direction = ByteBufUtils.readVarInt(buf,1);
        isFilling = ByteBufUtils.readVarInt(buf,1);
        fluidName = ByteBufUtils.readUTF8String(buf);
        amount = ByteBufUtils.readVarInt(buf,5);
        size = ByteBufUtils.readVarInt(buf,1);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarInt(buf,x,5);
        ByteBufUtils.writeVarInt(buf,y,5);
        ByteBufUtils.writeVarInt(buf,z,5);
        ByteBufUtils.writeVarInt(buf,direction,1);
        ByteBufUtils.writeVarInt(buf,isFilling,1);
        ByteBufUtils.writeUTF8String(buf,fluidName);
        ByteBufUtils.writeVarInt(buf,amount,5);
        ByteBufUtils.writeVarInt(buf,size,1);
    }

    public int getDirection()
    {
        return direction;
    }

    public int getIsFilling()
    {
        return isFilling;
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

    public String getFluidName()
    {
        return fluidName;
    }

    public int getAmount()
    {
        return amount;
    }

    public int getSize() {
        return size;
    }
}
