package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileWaterGenerator;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class WaterGeneratorRenderer extends TileEntitySpecialRenderer {

    private IModelCustom pylonModel;
    private ResourceLocation pylonTexture;

    public WaterGeneratorRenderer()
    {
        super();
        pylonModel = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MOD_ID,"models/pylon.obj"));
        pylonTexture = new ResourceLocation(Reference.MOD_ID,"models/defaultTexture.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        double timerHalf = (double)(((TileWaterGenerator)tileEntity).getTimer())/2.0;
        double theta = timerHalf*3.14159265358979/180.0;

        renderAllPylons(x,y,z,timerHalf,theta);
    }

    public void renderPylon(double x,double y,double z,double angle)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glRotated(angle,0.0,1.0,0.0);
        GL11.glScaled(0.1,0.1,0.1);
        bindTexture(pylonTexture);
        pylonModel.renderAll();
        GL11.glPopMatrix();
    }

    public void renderAllPylons(double x, double y, double z, double timer, double angle)
    {
//        double radiusScale = 0.4;
        double pi = 3.14159265358979;
        renderPylon(x+0.5+0.4*Math.sin(-angle*2),y+0.2+0.1*Math.sin(angle+pi/6),z+0.5+0.4*Math.cos(-angle*2),timer);
        renderPylon(x+0.5+.2*Math.sin(angle+4.0*pi/3.0),y+0.25+0.1*Math.sin(2*(angle+13.0*pi/12.0)),z+0.5+.2*Math.cos(angle+4.0*pi/3.0),timer);
        renderPylon(x+0.5+.3*Math.sin(-angle-2.0*pi/3.0),y+0.125+0.1*Math.sin(3*(angle+5.0*pi/3.0)),z+0.5+.3*Math.cos(-angle-2.0*pi/3.0),timer);
    }
}
