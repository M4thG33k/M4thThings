package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.tiles.TileBaseTank;
import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.SphereVertexLayout;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 6/17/2015.
 */
public class BaseTankRenderer extends TileEntitySpecialRenderer {

    private IModelCustom modelCustom;
    private IModelCustom modelSphere;
    private IModelCustom tankPylons;
    private ResourceLocation modelTexture;
    private ResourceLocation modelTexture2;
    private ResourceLocation texture;

    public BaseTankRenderer()
    {
        modelCustom = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/fluidTank1_noSphere.obj"));
        modelSphere = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/fluidTankSphere.obj"));
        tankPylons = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/fluidTank3.obj"));
        modelTexture = new ResourceLocation("m4ththings","models/fluidTank1.png");
        modelTexture2 = new ResourceLocation("m4ththings","models/xpjuiceflowing.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        //render the frame
        TileBaseTank tank = (TileBaseTank)tileEntity;
//        GL11.glPushMatrix();
//        GL11.glTranslated(x,y,z);
//        bindTexture(modelTexture);
//        modelCustom.renderAll();
//        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5,y+0.5,z+0.5);
        //double sFactor = 1/Math.sqrt(2.0);
        //GL11.glScaled(sFactor,sFactor,sFactor);
        //GL11.glRotated(45,0.0,1.0,0.0);
        texture = new ResourceLocation("m4ththings","models/defaultTexture.png");
        bindTexture(texture);
        tankPylons.renderAll();
        GL11.glPopMatrix();


        //render the sphere inside
        double percentage = ((double)tank.getFluidAmount())/((double)tank.getCapacity());
        if (percentage != 0)
        {
//            GL11.glPushMatrix();
//            GL11.glTranslated(x + 0.5, y + 0.5 + 0.05 * percentage * Math.sin(((double) tank.getTimer() * 3.14159 / 180)), z + 0.5);
//            GL11.glRotated((double) tank.getTimer(), 0.0, 1.0, 0.0);
//            GL11.glScaled(1.25*percentage,1.25*percentage,1.25*percentage);
//
////            IIcon icon = tank.getFluid().getFluid().getIcon();
////            texture = new ResourceLocation(icon.toString());
////            LogHelper.info("Attempting to use location: " + tank.getFluid().getUnlocalizedName());
//
//
//            //TextureMap.locationBlocksTexture
//            //bindTexture(texture);
//
//            bindTexture(modelTexture2);
//            modelSphere.renderAll();
//            GL11.glPopMatrix();


//            //attempting a crystal shape
//            GL11.glPushMatrix();
//            GL11.glTranslated(x+0.5,y+0.5,z+0.5);
//            GL11.glRotated((double) tank.getTimer(), 0.0, 1.0, 0.0);
//            GL11.glScaled(0.8*percentage,0.8*percentage,0.8*percentage);
//            Tessellator t = Tessellator.instance;
//
//            IIcon icon = tank.getFluid().getFluid().getIcon();
//            texture = TextureMap.locationBlocksTexture;
//            bindTexture(texture);
//            t.startDrawingQuads();
//
//            //bottom sections
//            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(0.0,0.0,-0.5,icon.getMinU(),icon.getMinV());
//            t.addVertexWithUV(0.25,0.0,-0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//
//            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//            t.addVertexWithUV(0.25,0.0,0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(0.0,0.0,0.5,icon.getMaxU(),icon.getMaxV());
//
//            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(0.0,0.0,0.5,icon.getMaxU(),icon.getMaxV());
//            t.addVertexWithUV(-0.25,0.0,0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(-0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//
//            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(-0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//            t.addVertexWithUV(-0.25,0.0,-0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(0.0,0.0,-0.5,icon.getMaxU(),icon.getMaxV());
//
//            //top sections
//            t.addVertexWithUV(0.0,0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//            t.addVertexWithUV(0.25,0.0,-0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(0.0,0.0,-0.5,icon.getMinU(),icon.getMinV());
//
//            t.addVertexWithUV(0.0,0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(0.0,0.0,0.5,icon.getMaxU(),icon.getMaxV());
//            t.addVertexWithUV(0.25,0.0,0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//
//            t.addVertexWithUV(0.0,0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(-0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//            t.addVertexWithUV(-0.25,0.0,0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(0.0,0.0,0.5,icon.getMaxU(),icon.getMaxV());
//
//            t.addVertexWithUV(0.0,0.5,0.0,icon.getMinU(),icon.getMaxV());
//            t.addVertexWithUV(0.0,0.0,-0.5,icon.getMaxU(),icon.getMaxV());
//            t.addVertexWithUV(-0.25,0.0,-0.25,icon.getMaxU(),icon.getMinV());
//            t.addVertexWithUV(-0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
//
//
//
////            t.addVertexWithUV(0.0,0.0,-0.5,icon.getMinU(),icon.getMaxV());
////            t.addVertexWithUV(0.5,0.0,0.0,icon.getMaxU(),icon.getMaxV());
////            t.addVertexWithUV(0.0,0.0,0.5,icon.getMaxU(),icon.getMinV());
////            t.addVertexWithUV(-0.5,0.0,0,icon.getMinU(),icon.getMinV());
//    //        t.addVertexWithUV(0.5,0.5,0.0,0,0);
//    //        t.addVertexWithUV(1.0,0.5,0.5,0,1);
//    //        t.addVertexWithUV(0.5,0.5,1.0,1,1);
//    //        t.addVertexWithUV(0.0,0.5,0.5,1,0);
//            t.draw();

//            GL11.glPopMatrix();

            //attempting a sphere...another way...
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y + 0.5 + 0.05 * percentage * Math.sin(((double) tank.getTimer() * 3.14159 / 180)), z + 0.5);
            GL11.glRotated((double) tank.getTimer(), 0.0, 1.0, 0.0);
            GL11.glScaled(0.75*percentage,0.75*percentage,0.75*percentage);
            Tessellator t = Tessellator.instance;

            IIcon icon = tank.getFluid().getFluid().getIcon();
            texture = TextureMap.locationBlocksTexture;
            bindTexture(texture);

            //double[] vertices = SphereVertexLayout.vertices;

            t.startDrawing(GL11.GL_TRIANGLES);
            t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());

            t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
            t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
            t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());

            t.draw();



            GL11.glPopMatrix();

        }

//        double percentage = ((double)tank.getFluidAmount())/((double)tank.getCapacity());
//        double scaleFactor = 0.1 + percentage*0.9;
//        double translateFactor = 0.5 - 0.5*scaleFactor;
//        GL11.glPushMatrix();
//        GL11.glTranslated(x+translateFactor,y+translateFactor,z+translateFactor);
//        GL11.glScaled(scaleFactor,scaleFactor,scaleFactor);
//        bindTexture(modelTexture);
//
//
//
//        modelCustom.renderAll();
//        GL11.glPopMatrix();


    }


}
