package com.M4thG33k.m4ththings.Explosions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Iterator;

/**
 * Created by M4thG33k on 6/9/2015.
 */
public class ExplosionHandler {

    public static M4thCustomExplosion createExplosion(World world,Entity entity, double x, double y, double z, float size, boolean destroyBlocks,int explosionSoundChoice)
    {
        return newExplosion(world,entity,x,y,z,size,false,destroyBlocks,explosionSoundChoice);
    }

    public static M4thCustomExplosion newExplosion(World world, Entity entity, double x, double y, double z, float size, boolean par9, boolean destroyBlocks,int explosionSoundChoice)
    {
        M4thCustomExplosion explosion = new M4thCustomExplosion(world,entity,x,y,z,size);

        explosion.setExplosionSound(explosionSoundChoice);

        explosion.isFlaming = par9;
        explosion.isSmoking = destroyBlocks;
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world,explosion))
        {
            return explosion;
        }
        explosion.doExplosionA();
        explosion.doExplosionB(true);

        //new
        if (!destroyBlocks)
        {
            explosion.affectedBlockPositions.clear();
        }

        Iterator iterator = world.playerEntities.iterator();

        while (iterator.hasNext())
        {
            EntityPlayer entityPlayer = (EntityPlayer)iterator.next();

            if (entityPlayer.getDistanceSq(x,y,z) < 4096.0D)
            {
                ((EntityPlayerMP)entityPlayer).playerNetServerHandler.sendPacket(new S27PacketExplosion(x,y,z,size,explosion.affectedBlockPositions,(Vec3)explosion.func_77277_b().get(entityPlayer)));
            }
        }

        //end new
        return explosion;
    }
}
