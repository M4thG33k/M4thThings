package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by M4thG33k on 6/22/2015.
 */
public class TileMediumQuantumTank extends TileQuantumTank{

    public TileMediumQuantumTank()
    {
        super();
        cap = 16000;
        constructStructure();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource==null || resource.amount==0)
        {
            return 0;
        }

        if (fluid == null)
        {
            if (doFill)
            {
                fluid = new FluidStack(resource.getFluid(), Math.min(cap,resource.amount));
                prepareSync();
            }
            return Math.min(cap,resource.amount);
        }

        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }

        if (fluid.amount!=cap)
        {
            int toTransfer = Math.min(cap - fluid.amount, resource.amount);
            if (doFill) {
                fluid.amount += toTransfer;
                prepareSync();
            }
            return toTransfer;
        }
        else{
            TileEntity tEnt = worldObj.getTileEntity(xCoord,yCoord+verticalDistance,zCoord);
            if (tEnt!=null && tEnt instanceof TileQuantumTank)
            {
                return ((TileQuantumTank)tEnt).fill(ForgeDirection.DOWN,resource,doFill);
            }
            tEnt = worldObj.getTileEntity(xCoord,yCoord+3,zCoord);
            if (tEnt!=null && tEnt instanceof  TileQuantumTank)
            {
                return ((TileQuantumTank)tEnt).fill(ForgeDirection.DOWN,resource,doFill);
            }
            else
            {
                return 0;
            }
        }
    }

    //removes all blocks used in the multiblock (sets them to air)
    public void dropStructure()
    {
        //top valve
        worldObj.setBlockToAir(xCoord,yCoord+2,zCoord);

        //glass cylinder
        for (int i=-1;i<2;i++)
        {
            for (int j=-1;j<2;j++)
            {
                for (int k=0;k<3;k++)
                {
                    if (!(i==0 && j==0))
                    {
                        worldObj.setBlockToAir(xCoord+i,yCoord+k,zCoord+j);
                    }
                }
            }
        }

        //don't set the bottom block to air. instead, the block class will drop all the raw materials upon destruction
    }


    //breaks the multiblock, replacing all the blocks that were not broken (breaking the tank looses all information
    public void breakStructure(int x, int y, int z)
    {
        LogHelper.info("Breaking the structure, not replacing (" + x + "," + y + "," + z + ")");
        //replace the top tank valve
        if (!(x==xCoord && y==yCoord+2 && z==zCoord))
        {
            worldObj.setBlock(xCoord,yCoord+2,zCoord,ModBlocks.blockQuantumTankValve);
        }

        //replace the glass cylinder
        for (int k = 0; k < 3; k++) //for each y-value
        {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (!(i == 0 && j == 0) && !(xCoord+i==x && yCoord+k==y && zCoord+j==z)) {
                        worldObj.setBlock(xCoord + i, yCoord + k, zCoord + j, Blocks.glass);
                    }
                }
            }
        }


        //replace the bottom tank valve
        if (!(x==xCoord && y==yCoord && z==zCoord))
        {
            worldObj.setBlock(xCoord,yCoord,zCoord,ModBlocks.blockQuantumTankValve);
        }
    }

    public void constructStructure()
    {
        int X;
        int Y;
        int Z;

        for (int i=-1;i<2;i++)
        {
            for (int j=-1;j<2;j++)
            {
                for (int k=-1;k<2;k++)
                {
                    X = xCoord+i;
                    Y = yCoord+k;
                    Z = zCoord+j;
                    if (!(i==0 && j==0 && k==0))
                    {
                        if (i==0 && j==0)
                        {
                            LogHelper.info("Changing: " + X + " " + Y + " " + Z);
                            worldObj.setBlock(xCoord + i, yCoord + k, zCoord + j, ModBlocks.blockTankTop);
                        }
                        else
                        {
                            LogHelper.info("Changing: " + X + " " + Y + " " + Z);
                            worldObj.setBlock(xCoord+i,yCoord+k,zCoord+j,ModBlocks.blockTankAir);
                        }
                    }
                }
            }
        }
    }

}
