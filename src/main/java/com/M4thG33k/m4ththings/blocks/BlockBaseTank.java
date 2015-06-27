package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileBaseTank;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by M4thG33k on 6/17/2015.
 */
public class BlockBaseTank extends Block implements ITileEntityProvider{


    public BlockBaseTank(Material material)
    {
        super(material);
        setHardness(2.0F);
        setResistance(5.0F);
        setBlockName(Reference.MOD_ID + "_" + "blockBaseTank");
        setBlockTextureName(Reference.MOD_ID + ":" + "blank");
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBaseTank();
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity instanceof TileBaseTank)
        {
            TileBaseTank tank = (TileBaseTank) tileEntity;
            if (tank.getFluid()==null || tank.getFluidAmount()==0)
            {
                return 0;
            }
            else
            {
                int fluidLight = tank.getFluid().getFluid().getLuminosity();
                if (fluidLight==0)
                {
                    return 0;
                }
                double percentage = ((double)tank.getFluidAmount())/((double)tank.getCapacity());
                return (int)(fluidLight*percentage);
            }
        }

        return 0;
    }

}
