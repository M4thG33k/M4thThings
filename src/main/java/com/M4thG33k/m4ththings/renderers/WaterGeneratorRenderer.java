package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileWaterGenerator;
import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.MiscHelper;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 7/3/2015.
 */
public class WaterGeneratorRenderer extends TileEntitySpecialRenderer {

    private IModelCustom pylonModel;
    private ResourceLocation pylonTexture;
    private ResourceLocation glassTexture = new ResourceLocation(Reference.MOD_ID,"textures/blocks/glassTest.png");
    private IIcon icon16 = MiscHelper.icon16;
    private ResourceLocation textureMap = TextureMap.locationBlocksTexture;
    private IIcon iconWater = FluidRegistry.getFluid("water").getIcon();
    private IModelCustom torus;
    private ResourceLocation torusTexture;

    public WaterGeneratorRenderer()
    {
        super();
        pylonModel = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MOD_ID,"models/pylon.obj"));
        pylonTexture = glassTexture;//new ResourceLocation(Reference.MOD_ID,"models/defaultTexture.png");
        torus = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MOD_ID,"models/torus.obj"));
        torusTexture = new ResourceLocation(Reference.MOD_ID,"models/lightBlueTransparent.png");

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        double timerHalf = (double)(((TileWaterGenerator)tileEntity).getTimer())/2.0;
        double theta = timerHalf*3.14159265358979/180.0;
        double percentage = ((TileWaterGenerator)tileEntity).getFillPercentage();

        //render floating pylons
//        renderAllPylons(x,y,z,timerHalf,theta);

        //render torus
        renderTorus(x+0.5,y+0.125+0.125*Math.sin(3*theta),z+0.5,0.4,0);

        //render the second torus if it is the advanced generator
        if (((TileWaterGenerator)tileEntity).getIsAdvanced())
        {
            renderTorus(x+0.5,y+0.125+0.125*Math.sin(3*theta),z+0.5,0.3,0);
        }

        //render fluid sphere
        if (percentage>0) {
            renderSphere(x + 0.5, y + 0.25, z + 0.5, 0.0, 0.125 * percentage, textureMap, Blocks.water.getIcon(0,0), false);
        }
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

    //renders the floating sphere. Note that x,y,z must already be adjusted before being used as inputs.
    //the rotation is always about the y-axis and the scale affects all 3 directions.
    protected void renderSphere(double x, double y, double z, double rotation, double scale, ResourceLocation texture, IIcon icon, boolean isAlpha)
    {
        GL11.glPushMatrix();
        if (isAlpha)
        {
            GL11.glEnable(GL11.GL_BLEND);
        }
        GL11.glTranslated(x,y,z);
        GL11.glRotated(rotation, 0.0, 1.0, 0.0);
        GL11.glScaled(scale,scale,scale);
        Tessellator t = Tessellator.instance;

        //bind the fluid's icon for the texture
        bindTexture(texture);

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

        if (isAlpha)
        {
            GL11.glDisable(GL11.GL_BLEND);
        }

        GL11.glPopMatrix();
    }

    protected void renderTorus(double x, double y, double z, double scale,int orientation)
    {

        GL11.glPushMatrix();
        if (orientation==1)
        {
            GL11.glRotated(90.0,1.0,0.0,0.0);
        }
        else if (orientation==2)
        {
            GL11.glRotated(90.0,0.0,0.0,1.0);
        }
        GL11.glTranslated(x,y,z);
        GL11.glScaled(scale,scale,scale);

        bindTexture(torusTexture);
        torus.renderAll();

        GL11.glPopMatrix();

    }
}
