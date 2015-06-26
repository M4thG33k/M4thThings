package com.M4thG33k.m4ththings.managers;

import com.M4thG33k.m4ththings.containers.ContainerBaseChest;
import com.M4thG33k.m4ththings.guis.GuiBaseChest;
import com.M4thG33k.m4ththings.init.ModGuis;
import com.M4thG33k.m4ththings.tiles.TileBaseChest;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 6/2/2015.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == ModGuis.GUIs.BASE_CHEST.ordinal()) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileBaseChest) {
                return new ContainerBaseChest(player.inventory, (TileBaseChest) tileEntity);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == ModGuis.GUIs.BASE_CHEST.ordinal()) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileBaseChest) {
                return new GuiBaseChest(player.inventory, (TileBaseChest) tileEntity);
            }
        }
        return null;
    }
}
