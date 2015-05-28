package com.M4thG33k.m4ththings.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 4/26/2015.
 */
public class ChatHelper {

    public static void sayMessage(World world, EntityPlayer player, String text)
    {
        if(!world.isRemote)
        {
            player.addChatMessage(new ChatComponentText(text));
        }
    }
}
