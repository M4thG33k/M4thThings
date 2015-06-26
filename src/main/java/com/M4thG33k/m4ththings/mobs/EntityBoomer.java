package com.M4thG33k.m4ththings.mobs;

import com.M4thG33k.m4ththings.Explosions.M4thCustomExplosion;
import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.sql.Ref;

/**
 * Created by M4thG33k on 6/9/2015.
 */
public class EntityBoomer extends EntityCreeper {

    protected int lastActive;
    protected int passedTime;
    protected int fuseLength = 30;
    protected int radius = 3;
    protected int soundChoice = 3;

    public EntityBoomer(World world)
    {
        super(world);
        soundChoice = this.rand.nextInt(3)+1;
    }

    @Override
    protected void fall(float dist)
    {
        super.fall(dist);
        this.passedTime = (int)((float)this.passedTime + dist * 1.5F);

        if (this.passedTime > this.fuseLength - 5)
        {
            this.passedTime = this.fuseLength - 5;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float intensity)
    {
        return ((float)this.lastActive + (float)(this.passedTime - this.lastActive) * intensity) / (float)(this.fuseLength-2);
    }

    protected void makeExplosions()
    {
        if (!this.worldObj.isRemote)
        {
            boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

            if (this.getPowered())
            {
                M4thThings.explosionHandler.createExplosion(this.worldObj,this,this.posX,this.posY,this.posZ,(float)(this.radius*2),flag,soundChoice);
                //this.worldObj.createExplosion(this,this.posX,this.posY,this.posZ,(float)(this.radius * 2), flag);
            }
            else
            {
                M4thThings.explosionHandler.createExplosion(this.worldObj,this,this.posX,this.posY,this.posZ,(float)this.radius,flag,soundChoice);
                //this.worldObj.createExplosion(this,this.posX,this.posY,this.posZ,(float)this.radius,flag);
            }

            this.setDead();
        }
    }


    @Override
    public void onUpdate()
    {
        if (this.isEntityAlive()) {
            this.lastActive = this.passedTime;

            if (this.func_146078_ca()) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.passedTime == 0) {
                this.playFuseSound();
            }

            this.passedTime += i;

            if (this.passedTime < 0) {
                this.passedTime = 0;
            }

            if (this.passedTime >= this.fuseLength) {
                this.passedTime = this.fuseLength;
                this.makeExplosions();
            }
        }

        super.onUpdate();
    }

    public void playFuseSound()
    {
        switch (soundChoice)
        {
            case 0:
                this.playSound("creeper.primed", 1.0F, 0.5F);
                break;
            case 1:
                this.playSound(Reference.MOD_ID + ":" + "boomerlaugh", 1.0F, 1.0F);
                break;
            case 2:
                this.playSound(Reference.MOD_ID + ":" + "pacmanwarble",1.0F,1.0F);
                break;
            case 3:
                this.playSound(Reference.MOD_ID + ":" + "mexicanhatdance",0.8F,1.0F);
            default:
                this.playSound("creeper.primed", 1.0F, 0.5F);
                break;
        }
    }
}
