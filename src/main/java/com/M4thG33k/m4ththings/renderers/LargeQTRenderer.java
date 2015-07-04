package com.M4thG33k.m4ththings.renderers;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.tiles.TileLargeQT;
import com.M4thG33k.m4ththings.utility.Location;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
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
        Location[] importValves = tank.getImportValves();
        Location[] exportValves = tank.getExportValves();
        int numImportValves = tank.getImportValveIndex();
        int numExportValves = tank.getExportValveIndex();
        double timer;
        double theta;
        int orientation = tank.getOrientation();
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
            if (orientation==1)
            {
                GL11.glRotated(90.0,1.0,0.0,0.0);
            }
            else if (orientation==2)
            {
                GL11.glRotated(90.0,0.0,0.0,1.0);
            }
            GL11.glScaled(9.0,9.0,9.0);
            bindTexture(modelTexture);
            modelBlock.renderAll();
            GL11.glPopMatrix();

            if (Configurations.RENDER_PLATES) {
                //render the hex plates

                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer, 30 * Math.sin(theta), 4,0);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 90, 30 * Math.sin(theta + 90), 4,0);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 180, 30 * Math.sin(theta + 180), 4,0);
                renderHexPlate(x + 0.5, y + 0.5, z + 0.5, -timer - 270, 30 * Math.sin(theta + 270), 4,0);
            }

            //render the sphere if the tank has fluid
            double percentage = ((double)tank.getFluidAmount())/((double)tank.getCapacity());
            if (percentage>0)
            {
                renderSphere(x+0.5,y+0.5+0.45*percentage*Math.sin(theta),z+0.5,timer,9.0*0.75*percentage,tank.getFluid().getFluid().getIcon(),false);
            }

            //render the import valve overlays
            for (int i=0;i<numImportValves;i++)
            {
                renderSpecialValve(x, y, z, importValves[i].getLocation(), orientation, 0);
            }
            //render the export valve overlays
            for (int i =0;i<numExportValves;i++)
            {
                renderSpecialValve(x,y,z,exportValves[i].getLocation(),orientation,1);
            }

//            if (Configurations.RENDER_TORI) {
//                //render the tori
//                GL11.glEnable(GL11.GL_BLEND);
//                renderTorus(x + 0.5, y - 2.5 + Math.sin(theta * 2), z + 0.5, 4.25,orientation);
//                renderTorus(x + 0.5, y + 2.5 + Math.sin(theta * 4), z + 0.5, 4.25,orientation);
//                renderTorus(x + 0.5, y + Math.sin(theta * 6), z + 0.5, 4.25,orientation);
//                GL11.glDisable(GL11.GL_BLEND);
//            }

            //render the glass tank
//            renderSphere(x+0.5,y+0.5,z+0.5,0,9.0*0.9,ModBlocks.blockTextureDummy.getIcon(0,0),true);

        }
        else
        {
            //render the cube with the block texture
            IIcon icon;
            if (tank.getControllerRangeError())
            {
                icon = ModBlocks.blockMedQTController.getIcon(0,3);
            }
            else
            {
                icon = ModBlocks.blockMedQTController.getIcon(0, 1);
            }
            renderCube(x + 0.5, y + 0.5, z + 0.5, 0.0, 0.0, 0.0, 0.5,icon);

            //render ghost blocks
            renderGhostBlocks(x,y,z,theta,timer);

        }
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
    
    protected void renderGhostBlocks(double x, double y, double z, double theta, double timer)
    {
        IIcon blockIcon = ModBlocks.blockQTComponent.getIcon(0,1);
        //render ghost blocks
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                for (int k = -4; k <= 4; k++) {
                    if (!(i==0 && j==0 && k==0))//avoid replacing the center
                    {

                        if (Math.abs(i)<=1 && Math.abs(j)<=1 && Math.abs(k)==4) //these locations refer to the top/bottom valve blocks
                        {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                            renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,blockIcon);
                        }
                        else if (i==0 && j==0 && Math.abs(k)==3) //these locations refer to the 2 interior valve blocks
                        {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                            renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,blockIcon);
                        }
                        else if (Math.abs(i)<=1 && Math.abs(k)<=1 && Math.abs(j)==4) //these locations refer to the top/bottom valve blocks for N-S
                        {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                            renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,blockIcon);
                        }
                        else if (i==0 && k==0 && Math.abs(j)==3) //these locations refer to the 2 interior valve blocks for N-S
                        {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                            renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,blockIcon);
                        }
                        else if (Math.abs(k)<=1 && Math.abs(j)<=1 && Math.abs(i)==4) //these locations refer to the top/bottom valve blocks for E-W
                        {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                            renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,blockIcon);
                        }
                        else if (k==0 && j==0 && Math.abs(i)==3) //these locations refer to the 2 interior valve blocks for E-W
                        {
//                                renderCube(x+0.5+i,y+0.5+k,z+0.5+j,0.0625,valveIcon);
                            renderCube(x+0.5+i,y+0.5+k+0.1*Math.sin(theta),z+0.5+j,timer,timer,timer,0.0625,blockIcon);
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
