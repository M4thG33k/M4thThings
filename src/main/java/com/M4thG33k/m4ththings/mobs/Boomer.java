package com.M4thG33k.m4ththings.mobs;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Boomer extends EntityMob {

    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;

    public Boomer(World world) {
        super(world);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIBoomerSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    //returns true if the newer entity ai code should be run
    @Override
    public boolean isAIEnabled() {
        return true;
    }

    //the number of iterations the path finder will execute before giving up
    @Override
    public int getMaxSafePointTries() {
        return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    //called when the mob is falling. calculates and applies fall damage
    @Override
    protected void fall(float distance) {
        super.fall(distance);
        this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + distance * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) -1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(18, Byte.valueOf((byte) 0));
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);

        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
            tagCompound.setBoolean("powered", true);
        }

        tagCompound.setShort("Fuse", (short) this.fuseTime);
        tagCompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
        tagCompound.setBoolean("ignited", this.shouldExplode());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) (tagCompound.getBoolean("powered") ? 1 : 0)));

        if (tagCompound.hasKey("Fuse", 99)) {
            this.fuseTime = tagCompound.getShort("Fuse");
        }

        if (tagCompound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = tagCompound.getByte("ExplosionRadius");
        }

        if (tagCompound.hasKey("iginited")) {
            this.ignition();
        }
    }

    //called to updated the entity's position/logic
    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.shouldExplode()) {
                this.setBoomerState(1);
            }

            int i = this.getBoomerState();

            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound("boomerdead", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += 1;

            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explosionMaker();
            }
        }

        super.onUpdate();
    }

    //returns the sound this mob makes when it is hurt
    @Override
    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    //returns the sound this mob makes on death
    @Override
    protected String getDeathSound() {
        return "mob.creeper.death";
    }

    //called when the mob's health reaches 0
    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (damageSource.getEntity() instanceof EntitySkeleton) {
            int i = Item.getIdFromItem(Items.record_13);
            int j = Item.getIdFromItem(Items.record_wait);
            int k = i + this.rand.nextInt(j - i + 1);
            this.dropItem(Item.getItemById(k), 1);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        return true;
    }

    public boolean getPowered() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    @SideOnly(Side.CLIENT)
    public float getBoomerFlashIntensity(float intensity) {
        return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * intensity) / (float) (this.fuseTime - 2);
    }

    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }

    //returns the state of the boomer. -1 is idle, 1 is 'in fuse'
    public int getBoomerState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setBoomerState(int state)
    {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)state));
    }

    //called when a lightening bolt hits this
    @Override
    public void onStruckByLightning(EntityLightningBolt bolt)
    {
        super.onStruckByLightning(bolt);

        this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    }

    @Override
    protected boolean interact(EntityPlayer player)
    {
        ItemStack itemStack = player.inventory.getCurrentItem();

        if (itemStack != null && itemStack.getItem() == Items.flint_and_steel)
        {
            this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D,"fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            player.swingItem();

            if (!this.worldObj.isRemote)
            {
                this.ignition();
                itemStack.damageItem(1,player);
                return true;
            }
        }

        return super.interact(player);
    }

    private void explosionMaker()
    {
        if (!this.worldObj.isRemote)
        {
            boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

            if (this.getPowered())
            {
                this.worldObj.createExplosion(this,this.posX,this.posY,this.posZ, (float)(this.explosionRadius*2), flag);
            }
            else
            {
                this.worldObj.createExplosion(this,this.posX,this.posY,this.posZ, (float)this.explosionRadius,flag);
            }

            this.setDead();
        }
    }

    public boolean shouldExplode()
    {
        return this.dataWatcher.getWatchableObjectByte(18)!=0;
    }

    public void ignition()
    {
        this.dataWatcher.updateObject(18,Byte.valueOf((byte)1));
    }
}





















