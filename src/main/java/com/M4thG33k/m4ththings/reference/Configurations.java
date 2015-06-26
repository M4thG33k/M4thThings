package com.M4thG33k.m4ththings.reference;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by M4thG33k on 5/29/2015.
 */
public class Configurations {


    public static int MAX_COBBLE_ALLOWED;
    public static int MAX_DROPPED_COBBLE;
    public static int MAX_BASE_TANK_CAP;

    public static int QT_CAP;
    public static int MED_QT_CAP;
    public static int LARGE_QT_CAP;
    public static boolean RENDER_PLATES;
    public static boolean RENDER_TORI;
    public static boolean ENABLE_ROTATION;


    public static void preInit(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        //get config information for the cobble chest
        MAX_COBBLE_ALLOWED = config.get(Configuration.CATEGORY_GENERAL,"MAX_COBBLE_ALLOWED",256).getInt() - 128;
        MAX_DROPPED_COBBLE = config.get(Configuration.CATEGORY_GENERAL,"MAX_DROPPED_COBBLE",256).getInt();
        MAX_BASE_TANK_CAP = config.get(Configuration.CATEGORY_GENERAL,"MAX_BASE_TANK_CAP",8000).getInt();


        if (MAX_DROPPED_COBBLE==-1 || MAX_DROPPED_COBBLE>MAX_COBBLE_ALLOWED+128)
        {
            MAX_DROPPED_COBBLE = MAX_COBBLE_ALLOWED+128;
        }

        if (MAX_BASE_TANK_CAP<0)
        {
            MAX_BASE_TANK_CAP = 0;
        }


        //tank configs
        //config.getCategory("tanks");
        QT_CAP = config.get("tanks", "quantumTankCap", 8000, "The maximum number of millibuckets the small (1x1) Quantum Tank can hold.",0, 32000).getInt();
        MED_QT_CAP = config.getInt("mediumQuantumTankCap","tanks",80000,0,320000,"The maximum number of millibuckets the medium (3x3) Quantum Tank can hold.");
        LARGE_QT_CAP = config.getInt("largeQuantumTankCap","tanks",8000000,0,32000000,"The maximum number of millibuckets the large (9x9) Quantum Tank can hold.");

        RENDER_PLATES = config.get("tanks","renderPlates",true,"Setting this to false will turn off the rendering of the floating plates in the medium and large tanks").getBoolean();
        RENDER_TORI = config.get("tanks","renderTori",true,"Setting this to false will turn off the rendering of the tori (donuts) in the large tanks").getBoolean();
        ENABLE_ROTATION = config.get("tanks", "enableRotation", true, "Setting this to false will disable the rotation of the sphere in all tanks and building guide blocks").getBoolean();




        config.save();
    }


}


