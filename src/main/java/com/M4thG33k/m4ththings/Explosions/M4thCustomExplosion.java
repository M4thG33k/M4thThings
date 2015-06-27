package com.M4thG33k.m4ththings.Explosions;

import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.utility.ChatHelper;
import com.M4thG33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityHugeExplodeFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by M4thG33k on 6/9/2015.
 */
public class M4thCustomExplosion extends Explosion {

    private World worldObj;
    private Random explosionRNG = new Random();
    private String soundString;
    private float soundVolume;
    private float soundPitch;

    public M4thCustomExplosion(World world, Entity entity, double x, double y, double z, float size)
    {
        super(world,entity,x,y,z,size);
        this.worldObj = world;
        this.setExplosionSound(0);
    }

    public void setExplosionSound(int choice)
    {
        switch (choice)
        {
            //default explosion sound
            case 0:
                soundString = "random.explode";
                soundVolume = 4.0F;
                soundPitch = (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat())-0.2F)-0.7F;
                break;
            //Gears of War Boomer
            case 1:
                soundString = Reference.MOD_ID + ":" + "boomerboom";
                soundVolume = 4.0F;
                soundPitch = 1.0F;
                break;
            //Pacman
            case 2:
                soundString = Reference.MOD_ID + ":" + "pacmandeath";
                soundVolume = 1.0F;
                soundPitch = 1.0F;
                break;
            //Mexican hat dance (ends with a standard explosion)
            case 3:
                soundString = "random.explode";
                soundVolume = 4.0F;
                soundPitch = (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat())-0.2F)-0.7F;
                break;
            default:
                soundString = "random.explode";
                soundVolume = 4.0F;
                soundPitch = (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat())-0.2F)-0.7F;
                break;
        }
    }


    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    @Override
    public void doExplosionB(boolean p_77279_1_) {

        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, soundString, soundVolume, soundPitch);

        if (this.explosionSize >= 2.0F && this.isSmoking) {
//            LogHelper.info("I'm spawning a HUGE explosion particle at " + this.explosionX + ", " + this.explosionY + ", " + this.explosionZ);
            //try something new
            EntityFX partExpl = new EntityHugeExplodeFX(this.worldObj,this.explosionX,this.explosionY,this.explosionZ,1.0D,1.0D,1.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(partExpl);

            //this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);

        } else {
//            LogHelper.info("I'm spawning a large explosion particle at " + this.explosionX + ", " + this.explosionY + ", " + this.explosionZ);
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        Block block;

        if (this.isSmoking) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(i, j, k);

                if (p_77279_1_) {
                    double d0 = (double) ((float) i + this.worldObj.rand.nextFloat());
                    double d1 = (double) ((float) j + this.worldObj.rand.nextFloat());
                    double d2 = (double) ((float) k + this.worldObj.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = (double) MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double) this.explosionSize + 0.1D);
                    d7 *= (double) (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
                    this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
                }

                if (block.getMaterial() != Material.air) {
                    if (block.canDropFromExplosion(this)) {
                        block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0F / this.explosionSize, 0);
                    }

                    block.onBlockExploded(this.worldObj, i, j, k, this);
                }
            }
        }

    }

}
