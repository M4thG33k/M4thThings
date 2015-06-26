package com.M4thG33k.m4ththings.reference;

import com.M4thG33k.m4ththings.tiles.TileLargeQT;
import com.M4thG33k.m4ththings.tiles.TileMedQT;
import com.M4thG33k.m4ththings.tiles.TileQTComponent;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by M4thG33k on 6/25/2015.
 */
public class WailaInteraction implements IWailaDataProvider {

    public static final WailaInteraction INSTANCE = new WailaInteraction();

    public static void load(IWailaRegistrar registrar)
    {
        registrar.registerBodyProvider(INSTANCE,TileQTComponent.class);
        registrar.registerNBTProvider(INSTANCE, TileQTComponent.class);


        registrar.addConfig("M4thThings","option.m4ththings.showTankStorage");
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return list;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {


        TileEntity te = accessor.getTileEntity();

        if (te instanceof TileQTComponent && config.getConfig("option.m4ththings.showTankStorage"))
        {
            TileEntity quantumTank = ((TileQTComponent)te).getParentTile();
            int capacity;

            if (quantumTank instanceof TileLargeQT)
            {
                capacity = ((TileLargeQT)quantumTank).getCapacity();
            }
            else
            {
                capacity = ((TileMedQT)quantumTank).getCapacity();
            }
            currenttip.add((((TileMedQT)quantumTank).getFluid() != null )?((TileMedQT) quantumTank).getFluid().getLocalizedName()+": "  + ((TileMedQT)quantumTank).getFluidAmount() + " / " + capacity : "EMPTY");
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return list;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity, NBTTagCompound nbtTagCompound, World world, int i, int i1, int i2) {
        if (tileEntity!=null)
        {
            tileEntity.writeToNBT(nbtTagCompound);
        }

        return nbtTagCompound;
    }
}
