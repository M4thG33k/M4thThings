package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.proxy.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

/**
 * Created by M4thG33k on 6/2/2015.
 */
public class FrozenDiamondRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block,int metadata, int modlID, RenderBlocks renderer)
    {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        //which render are we doing?
        if (ClientProxy.renderPass == 0)
        {
            //we are on the solid block render pass, render the solid diamond
            drawDiamond(Blocks.diamond_block,x,y,z);
        }
        else
        {
            renderer.renderStandardBlock(Blocks.ice,x,y,z);
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelID)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return ClientProxy.frozenDiamondRenderType;
    }

    public void drawDiamond(Block block,int x, int y, int z)
    {
        double parX = (double)x;
        double parY = (double)y+0.25F;
        double parZ = (double)z;

        float factor = 0.5F;

        Tessellator tessellator = Tessellator.instance;

        ResourceLocation resourceLocation = new ResourceLocation("minecraft:/blocks/diamond_block.png");
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);

        tessellator.startDrawingQuads();

        tessellator.addVertexWithUV(0,0,0,0,0);
        tessellator.addVertexWithUV(0,0.5,0,0,1);
        tessellator.addVertexWithUV(Math.sqrt(0.5),0.5,Math.sqrt(0.5),1,1);
        tessellator.addVertexWithUV(Math.sqrt(0.5),0,Math.sqrt(0.5),1,0);

        tessellator.draw();
    }

}
