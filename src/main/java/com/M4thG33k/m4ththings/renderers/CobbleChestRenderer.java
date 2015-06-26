package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.blocks.BlockCobbleChest;
import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.tiles.TileCobbleChest;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 6/17/2015.
 */
public class CobbleChestRenderer extends TileEntitySpecialRenderer {

    private IModelCustom modelCustom[];


    public CobbleChestRenderer()
    {
        modelCustom = new IModelCustom[2];
        modelCustom[0] = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/test1.obj"));
        modelCustom[1] = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/test2.obj"));
    }


    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        tileEntity.getWorldObj().scheduleBlockUpdate(tileEntity.xCoord,tileEntity.yCoord,tileEntity.zCoord, tileEntity.getBlockType(),10);
        Tessellator tessellator = Tessellator.instance;
        //Using glPushMatrix before doing our rendering, and then using glPopMatrix at the end means any "transformation"
        //that we do does not screw up  rendering in an unrelated part of the game.
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z); //translates the rendering to the right location +one block up
        //GL11.glScalef(0.5f,0.5f,0.5f);

        TileCobbleChest tileCobbleChest = (TileCobbleChest)tileEntity;

        if (tileCobbleChest.getCobble(0)>0)
        {
            modelCustom[0].renderAll();
        }
        else
        {
            modelCustom[1].renderAll();
        }

        GL11.glPopMatrix();

    }

}
