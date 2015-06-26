package com.M4thG33k.m4ththings.reference;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Optional;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by M4thG33k on 6/26/2015.
 */

@Optional.Interface(iface = "codechicken.nei.api.API",modid = "NotEnoughItems")
public class NEIM4thThingsConfig implements IConfigureNEI{


    @Override
    public String getName() {
        return M4thThings.class.getAnnotation(Mod.class).name();
    }

    @Override
    public String getVersion() {
        return M4thThings.class.getAnnotation(Mod.class).version();
    }

    @Optional.Method(modid="NotEnoughItems")
    @Override
    public void loadConfig()
    {
        LogHelper.info("Adding NEI integration");
        API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.blockQTComponent)));
    }

}
