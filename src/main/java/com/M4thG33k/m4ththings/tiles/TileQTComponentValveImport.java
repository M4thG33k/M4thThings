package com.M4thG33k.m4ththings.tiles;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by M4thG33k on 7/3/2015.
 */

//this type of valve will only allow fluids in. They cannot be extracted by any automatic means. (You can still use buckets)
public class TileQTComponentValveImport extends TileQTComponentValve {

    public TileQTComponentValveImport()
    {
        super();
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }
}
