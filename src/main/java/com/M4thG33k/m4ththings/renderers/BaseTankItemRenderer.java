package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 6/17/2015.
 */
public class BaseTankItemRenderer implements IItemRenderer {

//    private IModelCustom modelCustom;
//    private ResourceLocation modelTexture;
//
//    public BaseTankItemRenderer()
//    {
//        modelCustom = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/fluidTank3.obj"));
//        modelTexture = new ResourceLocation("m4ththings","models/defaultTexture.png");
//    }
//
//    @Override
//    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
//        return type != ItemRenderType.FIRST_PERSON_MAP && type != ItemRenderType.INVENTORY;
//    }
//
//    @Override
//    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
//        return type==ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper==ItemRendererHelper.ENTITY_ROTATION);
//    }
//
//    @Override
//    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//        GL11.glPushMatrix();
//        Minecraft.getMinecraft().renderEngine.bindTexture(this.modelTexture);
//
////        if(data[1] instanceof EntityPlayer)
////        {
////            if (type == ItemRenderType.EQUIPPED)
////            {
////                //todo add translate/rotate
////            }
////            else
////            {
////                //todo add translate/rotate
////            }
////        }
////        else
////        {
////            if (type==ItemRenderType.ENTITY)
////            {
////                //todo add translate/rotate
////            }
////        }
////        modelCustom.renderAll();
//
////        GL11.glScalef(0.75f,0.75f,0.75f);
//        GL11.glTranslatef(1f,0.2f,0.0f);
////        GL11.glRotatef(-60.0f,1.0f,0.0f,0.0f);
//        modelCustom.renderAll();
//        GL11.glPopMatrix();
//        //LogHelper.info("Attempting to render the TANK!");
//    }


    //take 2
    private TileEntitySpecialRenderer renderer;
    private TileEntity entity;

    public BaseTankItemRenderer(TileEntitySpecialRenderer renderer, TileEntity entity)
    {
        this.entity = entity;
        this.renderer = renderer;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        if (type == ItemRenderType.ENTITY)
        {
            //breakpoint here
            GL11.glTranslatef(-0.5f,0.0f,-0.5f);
            GL11.glPushMatrix();
            //GL11.glScalef(1.0f,1.0f,1.0f);
            //GL11.glTranslatef(0.0f,0.0f,0.0f);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(entity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }
        if (type == ItemRenderType.INVENTORY)
        {
            //breakpoint here
            GL11.glTranslatef(-0.5f,0.0f,-0.5f);
            GL11.glPushMatrix();
            //GL11.glScalef(1.0f,1.0f,1.0f);
            //GL11.glTranslatef(0.0f,0.0f,0.0f);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(entity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }
    }
}
