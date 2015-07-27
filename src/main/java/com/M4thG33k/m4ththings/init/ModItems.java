package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.items.ItemFluidGravitationCore;
import com.M4thG33k.m4ththings.items.ItemModIngot;
import com.M4thG33k.m4ththings.items.dyeSeeds.ItemDyeSeedBase;
import com.M4thG33k.m4ththings.items.dyeSeeds.ItemDyeSeeds;
import com.M4thG33k.m4ththings.items.dyeSeeds.ItemQuasiDye;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ModItems {

//    public static final ItemCobbleChestGrabber cobbleChestGrabber = new ItemCobbleChestGrabber();
    public static final ItemModIngot itemModIngot = new ItemModIngot();

    //0.1.1
    public static final ItemDyeSeeds  itemDyeSeeds = new ItemDyeSeeds();
    public static final ItemDyeSeedBase itemDyeSeedBase = new ItemDyeSeedBase();
    public static final ItemQuasiDye itemQuasiDye = new ItemQuasiDye();
    public static final ItemFluidGravitationCore itemFluidGravitationCore = new ItemFluidGravitationCore();

    public static void init()
    {
//        GameRegistry.registerItem(cobbleChestGrabber,cobbleChestGrabber.getUnlocalizedName());
        GameRegistry.registerItem(itemModIngot, "itemModIngot");

        //0.1.1
        GameRegistry.registerItem(itemDyeSeeds,"itemDyeSeeds");
        GameRegistry.registerItem(itemDyeSeedBase,"itemDyeSeedBase");
        GameRegistry.registerItem(itemQuasiDye,"itemQuasiDye");
        GameRegistry.registerItem(itemFluidGravitationCore,"itemFluidGravitationCore");
    }

}
