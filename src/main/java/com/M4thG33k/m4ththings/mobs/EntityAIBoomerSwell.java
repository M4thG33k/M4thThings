package com.M4thG33k.m4ththings.mobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by M4thG33k on 6/9/2015.
 */
public class EntityAIBoomerSwell extends EntityAIBase {

    Boomer swellingBoomer;
    EntityLivingBase boomerAttackTarget;

    public EntityAIBoomerSwell(Boomer boomer)
    {
        this.swellingBoomer = boomer;
        this.setMutexBits(1);
    }

    //Returns whether the EntityAIBase should begin execution
    public boolean shouldExecute()
    {
        EntityLivingBase entityLivingBase = this.swellingBoomer.getAttackTarget();
        return this.swellingBoomer.getBoomerState()>0 || entityLivingBase != null && this.swellingBoomer.getDistanceSqToEntity(entityLivingBase) < 9.0D;
    }

    //Execute a one shot task or starting executing a continuous task
    public void startExecuting()
    {
        this.swellingBoomer.getNavigator().clearPathEntity();
        this.boomerAttackTarget = this.swellingBoomer.getAttackTarget();
    }

    //resets the task
    public void resetTask()
    {
        this.boomerAttackTarget = null;
    }

    //Updates the task
    public void updateTask()
    {
        if (this.boomerAttackTarget == null)
        {
            this.swellingBoomer.setBoomerState(-1);
        }
        else if (this.swellingBoomer.getDistanceSqToEntity(this.boomerAttackTarget) > 49.0D)
        {
            this.swellingBoomer.setBoomerState(-1);
        }
        else if (!this.swellingBoomer.getEntitySenses().canSee(this.boomerAttackTarget))
        {
            this.swellingBoomer.setBoomerState(-1);
        }
        else
        {
            this.swellingBoomer.setBoomerState(1);
        }
    }





}
