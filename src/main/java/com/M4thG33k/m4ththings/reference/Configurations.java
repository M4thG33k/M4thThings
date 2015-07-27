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
    public static boolean ENABLE_TANK_PARTICLES;
    public static int MAX_VALVE_PUSH;

    //mob configs
    public static boolean SPAWN_CREEPSTERS;
    public static boolean ENABLE_BOOM;
    public static boolean ENABLE_PACMAN;
    public static boolean ENABLE_HATDANCE;
    public static int ALLOWED_CREEPSTER_SOUNDS = 0;

    //water generation configs
    public static int WATER_GEN_PER_TICK;
    public static boolean ENABLE_WATER_GEN;
    public static boolean ENABLE_WATER_GEN_PARTICLES;

    //pipe configs
    public static int PIPE_THROUGHPUT;
//    public static int PIPE_BUFFER;

    //dye seeds configs
    public static boolean ENABLE_DYE_SEEDS;
    public static int QUASI_DYE_DROPPED;
    public static int[] DYE_FROM_CRAFTING = new int[16];
    public static int SEEDS_FROM_CRAFTING;


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
        QT_CAP = config.getInt("quantumTankCap","tanks",  256000,0, 512000, "The maximum number of millibuckets the small (1x1) Quantum Tank can hold.");
        MED_QT_CAP = config.getInt("mediumQuantumTankCap","tanks",7680000,0,15360000,"The maximum number of millibuckets the medium (3x3) Quantum Tank can hold.");
        LARGE_QT_CAP = config.getInt("largeQuantumTankCap","tanks",230400000,0,2000000000,"The maximum number of millibuckets the large (9x9) Quantum Tank can hold.");

        RENDER_PLATES = config.get("tanks","renderPlates",true,"Setting this to false will turn off the rendering of the floating plates in the medium and large tanks").getBoolean();
//        RENDER_TORI = config.get("tanks","renderTori",true,"Setting this to false will turn off the rendering of the tori (donuts) in the large tanks").getBoolean();
        ENABLE_ROTATION = config.get("tanks", "enableRotation", true, "Setting this to false will disable the rotation of the sphere in all tanks and building guide blocks").getBoolean();
        ENABLE_TANK_PARTICLES = config.get("tanks","enableParticles",true,"Setting this to false will disable the fill/drain particle effects of the tanks...and they look really cool.").getBoolean();
        MAX_VALVE_PUSH = config.getInt("maximumValvePush","tanks",2000,0,2000000000,"The maximum number of millibuckets each export valve should attempt to push into adjacent pipes/tanks per tick");

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

        //water generation configs
        WATER_GEN_PER_TICK = config.getInt("waterPerTick","Aquam Accio",  25, 1, 2000, "The amount in millibuckets the Aquam Accio will produce per tick.");
        ENABLE_WATER_GEN = config.get("Aquam Accio","enableWaterGen",true,"Setting this to false will remove the Aquam Accio (passive water generation) from the game.").getBoolean();
        ENABLE_WATER_GEN_PARTICLES = config.get("Aquam Accio","enableParticles",true,"Settings this to false will remove all particle effects from the Aquam Accio. (This may make it difficult to see how they are behaving.)").getBoolean();

        //pipe configs
        PIPE_THROUGHPUT = config.getInt("pipeThroughput", "pipes", 1000, 1, 32000, "The amount in millibuckets that the pipe is allowed to output per tick. (Note: this value also directly affects the amount of fluid each pipe segment can store internally at one time.)");
//        PIPE_BUFFER = config.get("pipes","pipeBuffer",8000,"The maximum amount of fluid that any segment of pipe is allowed to hold at one time. (Must be at least 6x'pipeThroughput'.",6,500000).getInt();

        //dye seeds configs
        ENABLE_DYE_SEEDS = config.get("enableDyeSeeds","Dye Seeds",true,"Setting this to false will disable the recipes for dye seed base and dye seeds. (Plants and seeds already existing will not be removed.").getBoolean();
        QUASI_DYE_DROPPED = config.getInt("quasiDyeDropped","Dye Seeds",  1, 0, 6, "The number of Quasi Dye that are dropped from each mature Dye Seed plant.");
        String[] dyes = new String[]{"InkSac","RoseRed","CactusGreen","CocoaBeans","LapisLazuli","PurpleDye","CyanDye","LightGrayDye","GrayDye","PinkDye","LimeDye","DandelionYellow","LightBlueDye","MagentaDye","OrangeDye","BoneMeal"};
        for (int i=0;i<16;i++)
        {
            DYE_FROM_CRAFTING[i] = config.getInt("crafting"+dyes[i],"Dye Seeds Crafting",(i==15?0:(i==4?3:6)),0,16,"The number of " + dyes[i] + " you get from crafting with Quasi Dye.");
//            DYE_FROM_CRAFTING[i] = config.get("Dye Seeds Crafting","crafting"+dyes[i],(i==15?0:(i==4?3:6)),"The number of " + dyes[i] + " you get from crafting with Quasi Dye.",0,16).getInt();
        }
        SEEDS_FROM_CRAFTING = config.getInt("seedsFromCrafting","Dye Seeds", 3, 1, 6, "The number of Dye Seeds you get from one crafting recipe.");


        config.save();
    }


}


