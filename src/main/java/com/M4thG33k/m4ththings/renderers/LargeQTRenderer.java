package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.tiles.TileLargeQT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 6/25/2015.
 */
public class LargeQTRenderer extends MediumQuantumTankRenderer {

    protected IModelCustom torus;
    protected ResourceLocation torusTexture;

    public LargeQTRenderer()
    {
        super();
        torus = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/torus.obj"));
        torusTexture = new ResourceLocation("m4ththings","models/lightBlueTransparent.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        TileLargeQT tank = (TileLargeQT)tileEntity;
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

        //only render the tank if the structure is built
        if (tank.isStructureBuilt())
        {
            //render the frame
            GL11.glPushMatrix();
            GL11.glTranslated(x+0.5,y+0.5,z+0.5);
            GL11.glScaled(9.0,9.0,9.0);
            bindTexture(modelTexture);
            modelBlock.renderAll();
            GL11.glPopMatrix();

            if (Configurations.RENDER_PLATES) {
                //render the hex plates

                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer, 30 * Math.sin(theta), 4);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 90, 30 * Math.sin(theta + 90), 4);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 180, 30 * Math.sin(theta + 180), 4);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 270, 30 * Math.sin(theta + 270), 4);
            }

            //render the sphere if the tank has fluid
            double percentage = ((double)tank.getFluidAmount())/((double)tank.getCapacity());
            if (percentage>0)
            {
                renderSphere(x+0.5,y+0.5+0.45*percentage*Math.sin(theta),z+0.5,timer,9.0*0.75*percentage,tank.getFluid().getFluid().getIcon());
            }

            if (Configurations.RENDER_TORI) {
                //render the tori
                GL11.glEnable(GL11.GL_BLEND);
                renderTorus(x + 0.5, y - 2.5 + Math.sin(theta * 2), z + 0.5, 4.25);
                renderTorus(x + 0.5, y + 2.5 + Math.sin(theta * 4), z + 0.5, 4.25);
                renderTorus(x + 0.5, y + Math.sin(theta * 6), z + 0.5, 4.25);
                GL11.glDisable(GL11.GL_BLEND);
            }

        }
        else
        {
            //render the cube with the block texture
            renderCube(x+0.5,y+0.5,z+0.5,0.0,0.0,0.0,0.5,ModBlocks.blockMedQTController.getIcon(0,1));

            //render ghost blocks
            for (int i = -4; i <= 4; i++) {
                for (int j = -4; j <= 4; j++) {
                    for (int k = -4; k <= 4; k++) {
                        if (!(i==0 && j==0 && k==0))//avoid replacing the center
                        {

                            if (Math.abs(i)<=1 && Math.abs(j)<=1 && Math.abs(k)==4) //these locations refer to the top/bottom valve blocks
                            {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                                renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,ModBlocks.blockQTComponent.getIcon(0,1));
                            }
                            else if (i==0 && j==0 && Math.abs(k)==3) //these locations refer to the 2 interior valve blocks
                            {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                                renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,ModBlocks.blockQTComponent.getIcon(0,1));
                            }
//                            else //everything else should be glass
//                            {
////                                renderCube(x + 0.5 + i, y + 0.5 + k, z + 0.5 + j, 0.0625, glassIcon);
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,Blocks.glass.getIcon(0,0));
//                            }
                        }
                    }
                }
            }
        }
    }

    protected void renderTorus(double x, double y, double z, double scale)
    {

        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glScaled(scale,scale,scale);

        bindTexture(torusTexture);
        torus.renderAll();

        GL11.glPopMatrix();
        
    }

}
