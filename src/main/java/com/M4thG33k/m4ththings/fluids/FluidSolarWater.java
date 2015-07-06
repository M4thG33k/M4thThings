package com.M4thG33k.m4ththings.fluids;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.init.ModFluids;
import com.M4thG33k.m4ththings.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by M4thG33k on 6/28/2015.
 */
public class FluidSolarWater extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;

    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public FluidSolarWater(Fluid fluid, Material material)
    {
        super(fluid,material);
        setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        setBlockName(Reference.MOD_ID + "_" + "fluidSolarWater");
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return (side==0||side==1)? stillIcon : flowingIcon;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return 6;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg)
    {
        stillIcon = reg.registerIcon(Reference.MOD_ID + ":" + "solarWater_still");//"solarWaterStill");
        flowingIcon = reg.registerIcon(Reference.MOD_ID + ":" + "solarWater_flow");//"solarWaterFlowing");

        ModFluids.fluidSolarWater.setStillIcon(this.stillIcon);
        ModFluids.fluidSolarWater.setFlowingIcon(this.flowingIcon);
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlock(x,y,z).getMaterial().isLiquid())
        {
            return false;
        }
        return super.canDisplace(world,x,y,z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        if (world.getBlock(x,y,z).getMaterial().isLiquid())
        {
            return false;
        }
        return super.displaceIfPossible(world,x,y,z);
    }
}
