package com.M4thG33k.m4ththings.utility;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by M4thG33k on 7/1/2015.
 */
public class ParticleUtilities {

    static Map<String, ResourceLocation> boundTextures = new HashMap<String, ResourceLocation>();

    public static void bindTexture(String texture)
    {
        ResourceLocation resourceLocation = null;

        if (boundTextures.containsKey(texture))
        {
            resourceLocation = boundTextures.get(texture);
        }
        else
        {
            resourceLocation = new ResourceLocation("m4ththings",texture);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
    }

    public static ResourceLocation getParticleTexture()
    {
        try
        {
            return (ResourceLocation) ReflectionHelper.getPrivateValue(EffectRenderer.class, null, new String[] {"particleTextures","b","field_110737_b"});}
        catch (Exception e)
        {

        }

        return null;
    }



}
