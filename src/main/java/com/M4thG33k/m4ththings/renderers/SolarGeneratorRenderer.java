package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.init.ModFluids;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileSolarGenerator;
import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.MiscHelper;
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
 * Created by M4thG33k on 7/7/2015.
 */
public class SolarGeneratorRenderer extends TileEntitySpecialRenderer {

    private IModelCustom blockModel;
    private ResourceLocation blockTexture;
    private IIcon icon16 = MiscHelper.icon16;
    private IIcon icon64 = MiscHelper.icon64;
    private ResourceLocation energyTexture;
    private ResourceLocation fluidTexture;
    private ResourceLocation sunEmblem;

    private double h1 = 0.22727;
    private double h2 = 0.40909;
    private double v1 = 0.40909;
    private double h3 = 0.495;

    public SolarGeneratorRenderer()
    {
        super();

        blockModel = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MOD_ID,"models/solarGenerator.obj"));
        blockTexture = new ResourceLocation(Reference.MOD_ID,"models/solarGenerator.png");
        energyTexture = new ResourceLocation(Reference.MOD_ID,"textures/overlays/energySquare.png");
        fluidTexture = new ResourceLocation(Reference.MOD_ID,"textures/overlays/waterSquare.png");
        sunEmblem = new ResourceLocation(Reference.MOD_ID,"textures/overlays/SunEmblem.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

        TileSolarGenerator tile = (TileSolarGenerator)tileEntity;

        double fluidPercentage = tile.getFluidPercentage();
        double energyPercentage = tile.getEnergyPercentage();

        //render the main model
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5,y+0.5,z+0.5);
//        GL11.glScaled(0.5,0.5,0.5);
        bindTexture(blockTexture);
        blockModel.renderAll();
        GL11.glPopMatrix();

        //render the overlays
        renderBoxes(x+0.5,y+0.5,z+0.5,fluidPercentage,energyPercentage);

        //render the sun emblem overlay
        renderSun(x+0.5,y+1.005,z+0.5,0.35,-tile.getTimer()/2.0);


//        //render the inner fluid sphere if it has fluid
//        if (percentage>0)
//        {
//            renderSphere(x+0.5,y+0.5,z+0.5,0,0.2*percentage,fluidMap, ModFluids.fluidSolarWater.getIcon(),false);
//        }
//
//        //render the glass tank
//        renderSphere(x+0.5,y+0.5,z+0.5,0,0.25,glassTexture,icon16,true);


    }

    //renders all the filling overlays
    public void renderBoxes(double x, double y, double z,double fluidPercentage,double energyPercentage)
    {
        boolean flipped = false;
        for (int i=0;i<4;i++)
        {
            renderBox(x,y,z,!flipped,90.0*i,energyTexture,icon16,energyPercentage);
            renderBox(x,y,z,flipped,90.0*i,fluidTexture,icon16,fluidPercentage);
            flipped = !flipped;
        }

    }

    public void renderSun(double x, double y, double z, double scale, double rotation)
    {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glTranslated(x,y,z);
        GL11.glScaled(scale,scale,scale);
        GL11.glRotated(rotation,0.0,1.0,0.0);
        Tessellator t = Tessellator.instance;
        bindTexture(sunEmblem);

        t.startDrawingQuads();
        t.addVertexWithUV(1,0,-1,icon16.getMaxU(),icon64.getMaxV());
        t.addVertexWithUV(-1,0,-1,icon16.getMinU(),icon64.getMaxV());
        t.addVertexWithUV(-1,0,1,icon16.getMinU(),icon64.getMinV());
        t.addVertexWithUV(1,0,1,icon16.getMaxU(),icon64.getMinV());

        t.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    //renders one specific overlay for the energy/solar water panels
    public void renderBox(double x, double y, double z, boolean right, double rotation, ResourceLocation textureMap, IIcon icon,double percentage)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glRotated(rotation,0.0,1.0,0.0);
//        GL11.glScaled(0.5,0.5,0.5);
        Tessellator t = Tessellator.instance;
        bindTexture(textureMap);

        t.startDrawingQuads();

        if (right) {
            //outward-facing box
            t.addVertexWithUV(-h1, -v1, -0.5, icon.getMaxU(), icon.getMaxV());
            t.addVertexWithUV(-h2, -v1, -0.5, icon.getMinU(), icon.getMaxV());
            t.addVertexWithUV(-h2, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMinU(), icon.getMinV());
            t.addVertexWithUV(-h1, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMinU(), icon.getMinV());

            //top box
            t.addVertexWithUV(-h1, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMaxU(), icon.getMaxV());
            t.addVertexWithUV(-h2, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMinU(), icon.getMaxV());
            t.addVertexWithUV(-h2, v1 * percentage + (1 - percentage) * (-v1), -h3, icon.getMinU(), icon.getMinV());
            t.addVertexWithUV(-h1, v1 * percentage + (1 - percentage) * (-v1), -h3, icon.getMinU(), icon.getMinV());
        }
        else
        {
            //outward-facing box
            t.addVertexWithUV(h2, -v1, -0.5, icon.getMaxU(), icon.getMaxV());
            t.addVertexWithUV(h1, -v1, -0.5, icon.getMinU(), icon.getMaxV());
            t.addVertexWithUV(h1, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMinU(), icon.getMinV());
            t.addVertexWithUV(h2, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMinU(), icon.getMinV());

            //top box
            t.addVertexWithUV(h2, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMaxU(), icon.getMaxV());
            t.addVertexWithUV(h1, v1 * percentage + (1 - percentage) * (-v1), -0.5, icon.getMinU(), icon.getMaxV());
            t.addVertexWithUV(h1, v1 * percentage + (1 - percentage) * (-v1), -h3, icon.getMinU(), icon.getMinV());
            t.addVertexWithUV(h2, v1 * percentage + (1 - percentage) * (-v1), -h3, icon.getMinU(), icon.getMinV());
        }


        t.draw();
        GL11.glPopMatrix();

    }

    //renders the floating sphere. Note that x,y,z must already be adjusted before being used as inputs.
    //the rotation is always about the y-axis and the scale affects all 3 directions.
    protected void renderSphere(double x, double y, double z, double rotation, double scale, ResourceLocation textureMap, IIcon icon, boolean renderAlpha)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glRotated(rotation, 0.0, 1.0, 0.0);
        GL11.glScaled(scale,scale,scale);
        if (renderAlpha)
        {
            GL11.glEnable(GL11.GL_BLEND);
        }
        Tessellator t = Tessellator.instance;

        //bind the fluid's icon for the texture
        bindTexture(textureMap);

        if (icon==null)
        {
            LogHelper.info("The fluid's icon is null!");
        }


        t.startDrawing(GL11.GL_TRIANGLES);
        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());

        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());

        t.draw();

        if(renderAlpha)
        {
            GL11.glDisable(GL11.GL_BLEND);
        }

        GL11.glPopMatrix();
    }
}
