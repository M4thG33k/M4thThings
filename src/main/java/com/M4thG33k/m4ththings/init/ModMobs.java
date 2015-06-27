package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.mobs.EntityBoomer;
import com.M4thG33k.m4ththings.reference.Configurations;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Created by M4thG33k on 6/9/2015.
 */
public class ModMobs {

    public static void init()
    {
        EntityRegistry.registerGlobalEntityID(EntityBoomer.class, "BoomerHAHA", EntityRegistry.findGlobalUniqueEntityId(), 0x003300, 0x000000);

        if (Configurations.SPAWN_CREEPSTERS) {
            for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
                if (BiomeGenBase.getBiomeGenArray()[i] != null) {
                    EntityRegistry.addSpawn(EntityBoomer.class, 100, 4, 4, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray()[i]);
                }
            }
        }
    }


}
