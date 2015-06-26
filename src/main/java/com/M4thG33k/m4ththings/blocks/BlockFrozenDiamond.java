package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.proxy.ClientProxy;
import com.M4thG33k.m4ththings.reference.Reference;
import com.sun.deploy.util.SessionState;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by M4thG33k on 6/2/2015.
 */
public class BlockFrozenDiamond extends Block {

    public BlockFrozenDiamond(Material material)
    {
        super(material);
        this.setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        this.slipperiness = 0.98F;
        setBlockTextureName(Reference.MOD_ID + ":" + "blockCobbleChest");
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return ClientProxy.frozenDiamondRenderType;
    }

    @Override
    public boolean canRenderInPass(int pass)
    {
        ClientProxy.renderPass = pass;
        return true;
    }

    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }
}
