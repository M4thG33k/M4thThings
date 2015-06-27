package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
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
 * Created by M4thG33k on 6/22/2015.
 */
public class QuantumTankRenderer extends TileEntitySpecialRenderer {

    private IModelCustom fluidPorts;
    private ResourceLocation fluidPortTexture;
    private ResourceLocation fluidTexture;

    public QuantumTankRenderer()
    {
        fluidPorts = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/quantumTank.obj"));
        fluidPortTexture = new ResourceLocation("m4ththings","models/fluidTank3.png");
        fluidTexture = TextureMap.locationBlocksTexture;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        TileQuantumTank tank = (TileQuantumTank)tileEntity;
        //Render the frame
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5,y+0.5,z+0.5);
        bindTexture(fluidPortTexture);
        fluidPorts.renderAll();
        GL11.glPopMatrix();


        //render the sphere inside if there is any fluid contained
        double percentage = ((double)tank.getFluidAmount())/((double)tank.getCapacity());
        if (percentage>0)
        {
            renderSphere(x+0.5,y+0.5+0.05*percentage*Math.sin(((double)tank.getTimer()*3.14519/180)),z+0.5,(double)tank.getTimer(),0.75*percentage,tank.getFluid().getFluid().getIcon());
        }
    }

    //renders the floating sphere. Note that x,y,z must already be adjusted before being used as inputs.
    //the rotation is always about the y-axis and the scale affects all 3 directions.
    protected void renderSphere(double x, double y, double z, double rotation, double scale, IIcon icon)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glRotated(rotation, 0.0, 1.0, 0.0);
        GL11.glScaled(scale,scale,scale);
        Tessellator t = Tessellator.instance;

        //bind the fluid's icon for the texture
        bindTexture(fluidTexture);

//        t.startDrawing(GL11.GL_TRIANGLES);
//        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.5,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.5,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,0.485471,0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189946,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178907,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.485471,-0.119658,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.374255,-0.331561,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,-0.411492,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.060268,-0.496355,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.060268,-0.496355,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.177302,-0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.284032,-0.411492,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,-0.331562,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,-0.119658,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,-0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,-0.485471,-0.110549,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,-0.374255,-0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,-0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,-0.060268,-0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,-0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,-0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,0.284032,-0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,-0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,-0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,-0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,-0.485471,-0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,-0.164304,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.23445,-0.374255,-0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,-0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,-0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,-0.060268,-0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,-0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330579,0.177302,-0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,0.284032,-0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.23445,0.374255,-0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,-0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,-0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,-0.485471,-0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,-0.374255,-0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,-0.284032,-0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,-0.177302,-0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,-0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,-0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431922,0.177302,-0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,0.284032,-0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,-0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,-0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,-0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.119658,-0.485471,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,-0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.331562,-0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.411492,-0.284032,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,-0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.496355,-0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.496355,0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.467508,0.177302,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.331562,0.374255,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.119658,0.485471,0.0,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,-0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,-0.060268,0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.458572,0.060268,0.189947,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.431921,0.177302,0.178908,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.11055,0.485471,0.045791,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,-0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,-0.060268,0.350976,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.350976,0.060268,0.350976,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.164305,0.442728,0.164305,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.485471,0.119658,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.374255,0.331561,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,-0.284032,0.411492,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.177302,0.467508,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,-0.060268,0.496355,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.060268,0.496355,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.0,0.177302,0.467508,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.284032,0.411492,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.374255,0.331561,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.485471,0.119658,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(0.0,0.442728,0.232362,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,-0.485471,0.110549,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,-0.442728,0.214674,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,-0.374255,0.306323,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,-0.284032,0.380169,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,-0.177302,0.431921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,-0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.189947,0.060268,0.458572,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.178908,0.177302,0.431921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.157471,0.284032,0.380169,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.126883,0.374255,0.306323,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.045791,0.485471,0.11055,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.088921,0.442728,0.214674,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,-0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,-0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,-0.374255,0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,-0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,-0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,-0.060268,0.350975,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.350976,0.060268,0.350975,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.330578,0.177302,0.330578,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.290969,0.284032,0.290969,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.234449,0.374255,0.234449,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.084611,0.485471,0.084611,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.164304,0.442728,0.164304,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.119658,-0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.110549,-0.485471,0.045791,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.232362,-0.442728,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,-0.442728,0.088921,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.331561,-0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,-0.374255,0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.411492,-0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,-0.284032,0.157471,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.467508,-0.177302,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,-0.177302,0.178907,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.496354,-0.060268,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,-0.060268,0.189946,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.458572,0.060268,0.189946,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.496354,0.060268,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.431921,0.177302,0.178907,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.467508,0.177302,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.380169,0.284032,0.157471,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.411492,0.284032,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.331561,0.374255,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.306323,0.374255,0.126883,icon.getMaxU(),icon.getMinV());
//
//        t.addVertexWithUV(-0.119658,0.485471,0.0,icon.getMinU(),icon.getMaxV());
//        t.addVertexWithUV(-0.214674,0.442728,0.088921,icon.getMaxU(),icon.getMaxV());
//        t.addVertexWithUV(-0.232362,0.442728,0.0,icon.getMaxU(),icon.getMinV());
//
//        t.draw();

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


        GL11.glPopMatrix();
    }
}
