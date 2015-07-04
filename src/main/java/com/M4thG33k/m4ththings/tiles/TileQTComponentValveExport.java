package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.reference.Configurations;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Arrays;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class TileQTComponentValveExport extends TileQTComponentValve {

    protected int THROUGHPUT_CAP = Configurations.MAX_VALVE_PUSH;

    public TileQTComponentValveExport()
    {
        super();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    //this valve will try to push fluid into any adjacent IFluidHandler (as long as it's not a part of the same multi-block structure)
    //the amount of fluid that gets pushed per valve per tick is configurable
    @Override
    public void updateEntity() {
        TileMedQT myParent = getParentTank();
        if (myParent.getFluidAmount()==0) //if the tank is empty, we can't really do much
        {
            return;
        }

        FluidStack toTransfer = new FluidStack(myParent.getFluid(),Math.min(THROUGHPUT_CAP,myParent.getFluidAmount()));
        World world = this.worldObj;
        int x = xCoord;
        int y = yCoord;
        int z = zCoord;


        int orientation = myParent.getOrientation();
        int[] parentCoords = getParentCoordinates();

        TileEntity tileEntity;
        int transferred;

        switch(orientation)
        {
            case 1: //NS tank
                if (z>parentCoords[2]) //south-facing side
                {
                    tileEntity = world.getTileEntity(x,y,z+1);
                    if (tileEntity!=null && tileEntity instanceof IFluidHandler)
                    {
                        if (tileEntity instanceof TileQTComponent && Arrays.equals(parentCoords,((TileQTComponent)tileEntity).getParentCoordinates()))
                        {
                            return;
                        }

                        if (((IFluidHandler)tileEntity).canFill(ForgeDirection.NORTH,myParent.getFluid().getFluid())) //if we can push fluid into the adjacent TE
                        {
                            transferred = ((IFluidHandler)tileEntity).fill(ForgeDirection.NORTH,toTransfer,true);
                            this.drain(ForgeDirection.SOUTH,transferred,true);
                            return;
                        }

                    }
                }
                else //north-facing side
                {
                    tileEntity = world.getTileEntity(x,y,z-1);
                    if (tileEntity!=null && tileEntity instanceof IFluidHandler)
                    {
                        if (tileEntity instanceof TileQTComponent && this.hasSameParent((TileQTComponent)tileEntity))
                        {
                            return;
                        }

                        if (((IFluidHandler)tileEntity).canFill(ForgeDirection.SOUTH,myParent.getFluid().getFluid())) //if we can push fluid into the adjacent TE
                        {
                            transferred = ((IFluidHandler)tileEntity).fill(ForgeDirection.SOUTH,toTransfer,true);
                            this.drain(ForgeDirection.NORTH,transferred,true);
                            return;
                        }

                    }
                }
                break;
            case 2: //EW tank
                if (x>parentCoords[0]) //east-facing side
                {
                    tileEntity = world.getTileEntity(x+1,y,z);
                    if (tileEntity!=null && tileEntity instanceof IFluidHandler)
                    {
                        if (tileEntity instanceof TileQTComponent && Arrays.equals(parentCoords,((TileQTComponent)tileEntity).getParentCoordinates()))
                        {
                            return;
                        }

                        if (((IFluidHandler)tileEntity).canFill(ForgeDirection.WEST,myParent.getFluid().getFluid())) //if we can push fluid into the adjacent TE
                        {
                            transferred = ((IFluidHandler)tileEntity).fill(ForgeDirection.WEST,toTransfer,true);
                            this.drain(ForgeDirection.EAST,transferred,true);
                            return;
                        }

                    }
                }
                else //west-facing side
                {
                    tileEntity = world.getTileEntity(x-1,y,z);
                    if (tileEntity!=null && tileEntity instanceof IFluidHandler)
                    {
                        if (tileEntity instanceof TileQTComponent && this.hasSameParent((TileQTComponent)tileEntity))
                        {
                            return;
                        }

                        if (((IFluidHandler)tileEntity).canFill(ForgeDirection.EAST,myParent.getFluid().getFluid())) //if we can push fluid into the adjacent TE
                        {
                            transferred = ((IFluidHandler)tileEntity).fill(ForgeDirection.EAST,toTransfer,true);
                            this.drain(ForgeDirection.WEST,transferred,true);
                            return;
                        }

                    }
                }
                break;
            default: //same as 0: up/down
                if (y>parentCoords[1]) //above the controller
                {
                    tileEntity = world.getTileEntity(x,y+1,z);
                    if (tileEntity!=null && tileEntity instanceof IFluidHandler)
                    {
                        if (tileEntity instanceof TileQTComponent && Arrays.equals(parentCoords,((TileQTComponent)tileEntity).getParentCoordinates()))
                        {
                            return;
                        }

                        if (((IFluidHandler)tileEntity).canFill(ForgeDirection.DOWN,myParent.getFluid().getFluid())) //if we can push fluid into the adjacent TE
                        {
                            transferred = ((IFluidHandler)tileEntity).fill(ForgeDirection.DOWN,toTransfer,true);
                            this.drain(ForgeDirection.UP,transferred,true);
                            return;
                        }

                    }
                }
                else //below the controller
                {
                    tileEntity = world.getTileEntity(x,y-1,z);
                    if (tileEntity!=null && tileEntity instanceof IFluidHandler)
                    {
                        if (tileEntity instanceof TileQTComponent && this.hasSameParent((TileQTComponent)tileEntity))
                        {
                            return;
                        }

                        if (((IFluidHandler)tileEntity).canFill(ForgeDirection.UP,myParent.getFluid().getFluid())) //if we can push fluid into the adjacent TE
                        {
                            transferred = ((IFluidHandler)tileEntity).fill(ForgeDirection.UP,toTransfer,true);
                            this.drain(ForgeDirection.DOWN,transferred,true);
                            return;
                        }

                    }
                }
        }

    }
}
