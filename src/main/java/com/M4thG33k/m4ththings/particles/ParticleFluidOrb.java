package com.M4thG33k.m4ththings.particles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.ParticleUtilities;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 7/1/2015.
 */
public class ParticleFluidOrb extends EntityFX {

    private IIcon icon;

    protected ParticleFluidOrb(World world, double x, double y, double z)
    {
        super(world,x,y,z);
//        this.particleAlpha = 0.8F;
//        this.setSize(0.5f,0.5f);
        this.particleRed = this.particleGreen = 0.0f;
        this.particleBlue = 1.0f;
        this.particleTextureJitterX = this.particleTextureJitterY = 0.0f;
//        this.particleScale = 0.125f;
//        this.particleMaxAge = 10;
//        this.particleAge = 0;
    }

    public ParticleFluidOrb(World world, double x, double y, double z, double velX, double velY, double velZ,Fluid fluid,int tankSize,int life)
    {
        this(world,x,y,z);
        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;
        this.icon = fluid.getIcon();
        this.particleScale = 0.125f * (1+tankSize*0.5f);
        this.particleMaxAge = life;

    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

//        this.motionY -= 0.04D * (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ); //removed for test
//        this.motionX *= 0.9800000190734863D;
//        this.motionY *= 0.9800000190734863D;
//        this.motionZ *= 0.9800000190734863D;
//
//        if (this.onGround)
//        {
//            this.motionX *= 0.699999988079071D;
//            this.motionZ *= 0.699999988079071D;
//        }
    }

    @Override
    public void moveEntity(double xMotion, double yMotion, double zMotion) {
        this.posX += xMotion;
        this.posY += yMotion;
        this.posZ += zMotion;
    }

    @Override
    public void renderParticle(Tessellator tess, float partialTicks, float x, float y, float z, float u, float v)
    {
//        tess.draw();


        double umin = icon.getMinU();
        double umax = icon.getMaxU();
        double vmin = icon.getMinV();
        double vmax = icon.getMaxV();

        float scale = 0.1F * this.particleScale;
        float xAdj = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float yAdj = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float zAdj = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

//        LogHelper.info("Adjustments made: " + StringHelper.makeCoords(xAdj,yAdj,zAdj));
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        tess.startDrawingQuads();

        tess.addVertexWithUV((double)(xAdj - x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj - z*scale - v*scale),umax,vmax);
        tess.addVertexWithUV((double)(xAdj - x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj - z*scale + v*scale),umax,vmin);
        tess.addVertexWithUV((double)(xAdj + x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj + z*scale + v*scale),umin,vmin);
        tess.addVertexWithUV((double)(xAdj + x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj + z*scale - v*scale),umin,vmax);

        tess.draw();
        GL11.glPopMatrix();

//        tess.startDrawingQuads();

    }


    @Override
    public int getFXLayer() {
        return 3;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity p_70114_1_) {
        return super.getCollisionBox(p_70114_1_);
    }
}
