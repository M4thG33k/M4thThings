package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class TileTankTop extends TileTankAir implements IFluidHandler,IFluidTank{

    public TileTankTop()
    {
        super();
        setControllerLocation(this.xCoord,this.yCoord-2,this.zCoord);
    }

    @Override
    public void updateEntity() {
        if (controllerLocation==null)
        {
            setControllerLocation(this.xCoord,this.yCoord-2,this.zCoord);
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 30 (as usual)");
            return 0;
        }
        return getTank().fill(from,resource,doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 41");
            return null;
        }
        return getTank().drain(from,resource,doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! line 52");
            return null;
        }
        return getTank().drain(from,maxDrain,doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 63");
            return false;
        }
        return getTank().canFill(from,fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 74");
            return false;
        }
        return getTank().canDrain(from,fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 85");
            return null;
        }
        return getTank().getTankInfo(from);
    }

    @Override
    public FluidStack getFluid() {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 96");
            return null;
        }
        return getTank().getFluid();
    }

    @Override
    public int getFluidAmount() {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 107");
            return 0;
        }
        return getTank().getFluidAmount();
    }

    @Override
    public int getCapacity() {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 118");
            return 0;
        }
        return getTank().getCapacity();
    }

    @Override
    public FluidTankInfo getInfo() {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 129");
            return null;
        }
        return getTank().getInfo();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 140");
            return 0;
        }
        return getTank().fill(resource,doFill);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        TileQuantumTank tileQuantumTank = getTank();
        if (tileQuantumTank==null)
        {
            LogHelper.info("ERROR EXTREME!!!! Line 151");
            return null;
        }
        return getTank().drain(maxDrain,doDrain);
    }
}
