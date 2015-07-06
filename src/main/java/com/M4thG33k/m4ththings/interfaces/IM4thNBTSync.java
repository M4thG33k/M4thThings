package com.M4thG33k.m4ththings.interfaces;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by M4thG33k on 7/6/2015.
 */
public interface IM4thNBTSync {

    void receiveNBTPacket(NBTTagCompound tagCompound);
}
