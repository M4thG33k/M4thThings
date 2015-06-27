package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.tiles.TileMedQT;
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
public class MediumQuantumTankRenderer extends TileEntitySpecialRenderer{

    protected IModelCustom modelBlock;
    protected ResourceLocation modelTexture;
    protected ResourceLocation fluidTexture;
    protected IModelCustom modelCube;
    protected IModelCustom modelHexPlate;
    protected ResourceLocation hexTexture;

    public MediumQuantumTankRenderer()
    {
        modelBlock = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/fluidTank3.obj"));
        modelTexture = new ResourceLocation("m4ththings","models/fluidTank3.png");
        fluidTexture = TextureMap.locationBlocksTexture;
        modelCube = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/cube.obj"));
        modelHexPlate = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/sphereHexPlate.obj"));
        hexTexture = new ResourceLocation("m4ththings","models/sphereHexPlate.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        TileMedQT tank = (TileMedQT)tileEntity;
        double timer;
        double theta;
        if (Configurations.ENABLE_ROTATION)
        {
            timer = (double)(tank.getTimer());
            theta = timer*3.14159265/180.0;

        }
        else
        {
            timer = 0;
            theta = 0;
        }

        //note: we only render if the structure is built
        if (tank.isStructureBuilt()) {
            //Render the frame
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
            GL11.glScaled(3, 3, 3);
            bindTexture(modelTexture);
            modelBlock.renderAll();
            GL11.glPopMatrix();


            //render the sphere inside if there is any fluid contained
            double percentage = ((double) tank.getFluidAmount()) / ((double) tank.getCapacity());
            if (percentage > 0) {
                renderSphere(x + 0.5, y + 0.5 + 0.15 * percentage * Math.sin(theta), z + 0.5, timer, 3.0 * 0.75 * percentage, tank.getFluid().getFluid().getIcon());

            }

            if (Configurations.RENDER_PLATES) {
                //render the hex plates
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer, 30 * Math.sin(theta), 1.5);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 90, 30 * Math.sin(theta + 90), 1.5);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 180, 30 * Math.sin(theta + 180), 1.5);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 270, 30 * Math.sin(theta + 270), 1.5);
            }


        }
        else
        {
            //render a cube with the block texture
            renderCube(x+0.5,y+0.5,z+0.5,0.0,0.0,0.0,0.5,ModBlocks.blockMedQTController.getIcon(0,0));
        }
    }

    protected void renderHexPlate(double x, double y, double z, double rotationY, double rotationZ, double scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glScaled(scale,scale,scale);
        GL11.glRotated(rotationY,0.0,1.0,0.0);
        GL11.glRotated(rotationZ,0.0,0.0,1.0);
        bindTexture(hexTexture);
        modelHexPlate.renderAll();
        GL11.glPopMatrix();
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

    protected void renderCube(double x, double y, double z, double rotationX,double rotationY, double rotationZ, double scale, IIcon icon)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glRotated(rotationX,1.0,0.0,0.0);
        GL11.glRotated(rotationY,0.0,1.0,0.0);
        GL11.glRotated(rotationZ,0.0,0.0,1.0);
        GL11.glScaled(scale,scale,scale);
        bindTexture(fluidTexture);
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();

        t.addVertexWithUV(-1.0,1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(-1.0,1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(-1.0,-1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-1.0,-1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(-1.0,1.0,-1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(1.0,1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(1.0,-1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-1.0,-1.0,-1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(1.0,1.0,-1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(1.0,1.0,1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(1.0,-1.0,1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(1.0,-1.0,-1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(1.0,1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(-1.0,1.0,1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(-1.0,-1.0,1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(1.0,-1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(-1.0,-1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(-1.0,-1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(1.0,-1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(1.0,-1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(1.0,1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(1.0,1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(-1.0,1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-1.0,1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.draw();
        GL11.glPopMatrix();

    }


}
