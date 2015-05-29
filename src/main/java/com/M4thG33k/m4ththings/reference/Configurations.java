package com.M4thG33k.m4ththings.reference;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by M4thG33k on 5/29/2015.
 */
public class Configurations {

    public static int MAX_COBBLE_ALLOWED;
    public static int MAX_DROPPED_COBBLE;


    public static void preInit(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        //get config information for the cobble chest
        MAX_COBBLE_ALLOWED = config.get(Configuration.CATEGORY_GENERAL,"MAX_COBBLE_ALLOWED",256).getInt() - 128;
        MAX_DROPPED_COBBLE = config.get(Configuration.CATEGORY_GENERAL,"MAX_DROPPED_COBBLE",256).getInt();

        if (MAX_DROPPED_COBBLE==-1 || MAX_DROPPED_COBBLE>MAX_COBBLE_ALLOWED+128)
        {
            MAX_DROPPED_COBBLE = MAX_COBBLE_ALLOWED+128;
        }

        config.save();
    }


}


