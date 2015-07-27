package com.M4thG33k.m4ththings.tiles;

import cofh.api.block.IDismantleable;
import com.M4thG33k.m4ththings.utility.Location;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by M4thG33k on 7/8/2015.
 */
public class TileBasePipe extends TileEntity implements IFluidHandler {

    protected int maxTransfer = 1000;

    public TileBasePipe()
    {

    }

    /* IFluidHandler */

    //upon filling, this will try to push fluid into adjacent IFluidHandlers whose direction differs from the input
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int total = 0;

        Location[] locations = MiscHelper.getAdjacentLocations(xCoord, yCoord, zCoord, MiscHelper.getDirectionBlacklistValue(from));
        int[] directions = MiscHelper.getAdjacentDirections(MiscHelper.getDirectionBlacklistValue(from));

        return 0;
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
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }

    /* End IFluidHandler */
}
