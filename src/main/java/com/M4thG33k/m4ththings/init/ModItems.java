package com.M4thG33k.m4ththings.init;

import com.M4thG33k.m4ththings.items.ItemModIngot;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ModItems {

//    public static final ItemCobbleChestGrabber cobbleChestGrabber = new ItemCobbleChestGrabber();
    public static final ItemModIngot itemModIngot = new ItemModIngot();

    public static void init()
    {
//        GameRegistry.registerItem(cobbleChestGrabber,cobbleChestGrabber.getUnlocalizedName());
        GameRegistry.registerItem(itemModIngot, "itemModIngot");
    }

}
