package com.M4thG33k.m4ththings.reference;

import com.M4thG33k.m4ththings.tiles.*;
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
        registrar.registerBodyProvider(INSTANCE,TileQuantumTank.class);
        registrar.registerNBTProvider(INSTANCE, TileQTComponent.class);
        registrar.registerBodyProvider(INSTANCE, TileSolarCollector.class);
        registrar.registerBodyProvider(INSTANCE, TileWaterGenerator.class);
        registrar.registerBodyProvider(INSTANCE, TileSolarGenerator.class);


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

        //if it is part of a quantum tank
        if (te instanceof TileQTComponent && config.getConfig("option.m4ththings.showTankStorage"))
        {
            TileEntity quantumTank = ((TileQTComponent)te).getParentTile();
            int capacity;

            if (((TileMedQT)quantumTank).isStructureBuilt()) {
                if (quantumTank instanceof TileLargeQT) {
                    capacity = ((TileLargeQT) quantumTank).getCapacity();
                } else {
                    capacity = ((TileMedQT) quantumTank).getCapacity();
                }

                if (((TileMedQT) quantumTank).getFluidAmount() == 0) {
                    currenttip.add("EMPTY");
                } else {
                    currenttip.add(((TileMedQT) quantumTank).getFluid().getLocalizedName() + ": " + ((TileMedQT) quantumTank).getFluidAmount() + " / " + capacity);
                    currenttip.add(((TileMedQT) quantumTank).getRoundedPercentFilled() + "%");
                }
            }
//            currenttip.add((((TileMedQT)quantumTank).getFluid() != null )?((TileMedQT) quantumTank).getFluid().getLocalizedName()+": "  + ((TileMedQT)quantumTank).getFluidAmount() + " / " + capacity : "EMPTY");
        }

        //do small QT tips
        if (te instanceof TileQuantumTank && !(te instanceof TileMedQT) && config.getConfig("option.m4ththings.showTankStorage"))
        {
            TileQuantumTank quantumTank = ((TileQuantumTank)te);

            if (quantumTank.getFluidAmount()==0)
            {
                currenttip.add("EMPTY");
            }
            else
            {
                currenttip.add(quantumTank.getFluid().getLocalizedName() + ": " + quantumTank.getFluidAmount() + "/" + quantumTank.getCapacity());
                currenttip.add(quantumTank.getRoundedPercentFilled() + "%");
            }


        }

        //if it is a solar collector
        if (te instanceof TileSolarCollector && config.getConfig("option.m4ththings.showTankStorage"))
        {
            TileSolarCollector tileSolarCollector = ((TileSolarCollector)te);

            if (tileSolarCollector.getWater()==0)
            {
                currenttip.add("Water: EMPTY");
            }
            else
            {
                currenttip.add("Water: " + tileSolarCollector.getWater());
            }

            if (tileSolarCollector.getSolar()==0)
            {
                currenttip.add("Solar Water: EMPTY");
            }
            else
            {
                currenttip.add("Solar Water: " + tileSolarCollector.getSolar());
            }
        }

        //if it is a water generator
        if (te instanceof TileWaterGenerator && config.getConfig("options.m4ththings.showTankStorage"))
        {
            if (((TileWaterGenerator)te).getFluidAmount()==0)
            {
                currenttip.add("Water: EMPTY");
            }
            else
            {
                currenttip.add("Water: " + ((TileWaterGenerator)te).getFluidAmount());
            }
        }

        //if it is a solar generator
        if (te instanceof TileSolarGenerator && config.getConfig("options.m4ththings.showTankStorage"))
        {
            if (((TileSolarGenerator)te).getFluidAmount()==0)
            {
                currenttip.add("Solar Water: EMPTY");
            }
            else
            {
                currenttip.add("Solar Water: " + ((TileSolarGenerator)te).getFluidAmount());
            }
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
