package com.M4thG33k.m4ththings.packets;

import com.M4thG33k.m4ththings.M4thThings;
import com.M4thG33k.m4ththings.particles.ParticleManager;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by M4thG33k on 6/30/2015.
 */
public class PacketFillingHandler implements IMessageHandler<PacketFilling,IMessage> {

    @Override
    public IMessage onMessage(PacketFilling message, MessageContext ctx)
    {
//        LogHelper.info("Received message from the server! " + message.getDirection() + ":" + message.getIsFilling());
//        LogHelper.info("I should be spawning a particle at: " + StringHelper.makeCoords(message.getX() + 1.0, message.getY() + 1.0, message.getZ() + 1.0));
//        Minecraft.getMinecraft().theWorld.spawnParticle("happyVillager", message.getX() + 1.0, message.getY() + 2.0, message.getZ() + 1.0, 0.0, 0.0, 0.0);
//        TileEntity tileEntity = (Minecraft.getMinecraft().theWorld.getTileEntity( message.getX(), message.getY() , message.getZ()));
//
//        if (Minecraft.getMinecraft().theWorld.isRemote && tileEntity!=null && tileEntity instanceof TileQuantumTank)
//        {
//            //((TileQuantumTank)tileEntity).tankFillParticles(message.getDirection(),message.getIsFilling(), FluidRegistry.getFluid(message.getFluidName()),message.getAmount(),message.getSize());
//            ParticleManager.tankFillParticles(Minecraft.getMinecraft().theWorld, message.getX(), message.getY(), message.getZ(), message.getDirection(), message.getIsFilling(), FluidRegistry.getFluid(message.getFluidName()), message.getAmount(), message.getSize());
//        }

        M4thThings.proxy.startParticleRendering(message);

        return null;
    }
}
