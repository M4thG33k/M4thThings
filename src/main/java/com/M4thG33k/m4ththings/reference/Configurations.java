package com.M4thG33k.m4ththings.reference;

import com.M4thG33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by M4thG33k on 5/29/2015.
 */
public class Configurations {


    public static int MAX_COBBLE_ALLOWED;
    public static int MAX_DROPPED_COBBLE;

    //tank configs
    public static int QT_CAP;
    public static int MED_QT_CAP;
    public static int LARGE_QT_CAP;
    public static boolean RENDER_PLATES;
    public static boolean RENDER_TORI;
    public static boolean ENABLE_ROTATION;

    //mob configs
    public static boolean SPAWN_CREEPSTERS;
    public static boolean ENABLE_BOOM;
    public static boolean ENABLE_PACMAN;
    public static boolean ENABLE_HATDANCE;
    public static int ALLOWED_CREEPSTER_SOUNDS = 0;


    public static void preInit(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        //get config information for the cobble chest
//        MAX_COBBLE_ALLOWED = config.get(Configuration.CATEGORY_GENERAL,"MAX_COBBLE_ALLOWED",256).getInt() - 128;
//        MAX_DROPPED_COBBLE = config.get(Configuration.CATEGORY_GENERAL,"MAX_DROPPED_COBBLE",256).getInt();
//        MAX_BASE_TANK_CAP = config.get(Configuration.CATEGORY_GENERAL,"MAX_BASE_TANK_CAP",8000).getInt();
//
//
//        if (MAX_DROPPED_COBBLE==-1 || MAX_DROPPED_COBBLE>MAX_COBBLE_ALLOWED+128)
//        {
//            MAX_DROPPED_COBBLE = MAX_COBBLE_ALLOWED+128;
//        }
//
//        if (MAX_BASE_TANK_CAP<0)
//        {
//            MAX_BASE_TANK_CAP = 0;
//        }


        //tank configs
        //config.getCategory("tanks");
        QT_CAP = config.get("tanks", "quantumTankCap", 8000, "The maximum number of millibuckets the small (1x1) Quantum Tank can hold.",0, 32000).getInt();
        MED_QT_CAP = config.getInt("mediumQuantumTankCap","tanks",800000,0,320000,"The maximum number of millibuckets the medium (3x3) Quantum Tank can hold.");
        LARGE_QT_CAP = config.getInt("largeQuantumTankCap","tanks",80000000,0,2000000000,"The maximum number of millibuckets the large (9x9) Quantum Tank can hold.");

        RENDER_PLATES = config.get("tanks","renderPlates",true,"Setting this to false will turn off the rendering of the floating plates in the medium and large tanks").getBoolean();
        RENDER_TORI = config.get("tanks","renderTori",true,"Setting this to false will turn off the rendering of the tori (donuts) in the large tanks").getBoolean();
        ENABLE_ROTATION = config.get("tanks", "enableRotation", true, "Setting this to false will disable the rotation of the sphere in all tanks and building guide blocks").getBoolean();


        //mob configs
        SPAWN_CREEPSTERS = config.get("mobs", "enableCreepsters",true,"Setting this to false will disable Creepsters from spawning in the world. (Note: if any currently exist in the world, they will not be removed.)").getBoolean();
        ENABLE_BOOM = config.get("mobs","enableBoomSound",true,"Setting this to false will disable the Creepsters' Boom sound effect").getBoolean();
        ENABLE_PACMAN = config.get("mobs","enablePacmanSound",true,"Setting this to false will disable the Creepsters' Pacman sound effect").getBoolean();
        ENABLE_HATDANCE = config.get("mobs","enableHatDanceSound",true,"Setting this to false will disable the Creepster's Hat Dance sound effect").getBoolean();

        if (ENABLE_BOOM)
        {
            ALLOWED_CREEPSTER_SOUNDS += 1;
        }
        if (ENABLE_PACMAN)
        {
            ALLOWED_CREEPSTER_SOUNDS += 2;
        }
        if (ENABLE_HATDANCE)
        {
            ALLOWED_CREEPSTER_SOUNDS += 4;
        }

        LogHelper.info("ALLOWED_CREEPSTER_SOUNDS = " + ALLOWED_CREEPSTER_SOUNDS);

        config.save();
    }


}


