package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.tiles.TileWaterGenerator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 7/4/2015.
 */
public class WaterGeneratorItemRenderer implements IItemRenderer{

    private TileEntitySpecialRenderer renderer;
    private TileWaterGenerator tileEntity;

    public WaterGeneratorItemRenderer(TileEntitySpecialRenderer tesr, TileEntity ent)
    {
        this.renderer = tesr;
        this.tileEntity = (TileWaterGenerator)ent;
        tileEntity.fillUp();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return (type==ItemRenderType.ENTITY || type==ItemRenderType.INVENTORY || type==ItemRenderType.EQUIPPED || type==ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        tileEntity.setAdvanced(item.getItemDamage()==1);

        if (type==ItemRenderType.ENTITY)
        {
            GL11.glTranslated(-0.5, -0.25, -0.5);
            GL11.glPushMatrix();

            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }

        if (type==ItemRenderType.INVENTORY)
        {
            GL11.glTranslated(0.0,0.5,0.0);
            GL11.glScaled(1.5,1.5,1.5);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }

        if (type==ItemRenderType.EQUIPPED)
        {
            GL11.glTranslated(0.0,0.0,-0.5);
            GL11.glScaled(2.0,2.0,2.0);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }

        if (type==ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glTranslated(0.0,0.0,-0.5);
            GL11.glScaled(3.0,3.0,3.0);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }
    }
}
