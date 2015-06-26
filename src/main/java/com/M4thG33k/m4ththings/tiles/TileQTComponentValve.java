package com.M4thG33k.m4ththings.tiles;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class TileQTComponentValve extends TileQTComponent implements IFluidHandler, IFluidTank {

    public TileQTComponentValve()
    {
        super();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return getParentTank().fill(from,resource,doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return getParentTank().drain(from,resource,doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return getParentTank().drain(from, maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return getParentTank().canFill(from, fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return getParentTank().canDrain(from, fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return getParentTank().getTankInfo(from);
    }

    @Override
    public FluidStack getFluid() {
        return getParentTank().getFluid();
    }

    @Override
    public int getFluidAmount() {
        return getParentTank().getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return getParentTank().getCapacity();
    }

    @Override
    public FluidTankInfo getInfo() {
        return getParentTank().getInfo();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return getParentTank().fill(resource, doFill);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return getParentTank().drain(maxDrain, doDrain);
    }
}
