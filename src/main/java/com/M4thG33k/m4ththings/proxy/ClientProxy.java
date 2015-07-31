package com.M4thG33k.m4ththings.proxy;

import com.M4thG33k.m4ththings.init.ModBlocks;
import com.M4thG33k.m4ththings.init.ModRenderers;
import com.M4thG33k.m4ththings.interfaces.IM4thNBTSync;
import com.M4thG33k.m4ththings.packets.PacketFilling;
import com.M4thG33k.m4ththings.packets.PacketNBT;
import com.M4thG33k.m4ththings.particles.ParticleManager;
import com.M4thG33k.m4ththings.renderers.LargeQTRenderer;
import com.M4thG33k.m4ththings.renderers.MediumQuantumTankRenderer;
import com.M4thG33k.m4ththings.renderers.QuantumTankItemRenderer;
import com.M4thG33k.m4ththings.renderers.QuantumTankRenderer;
import com.M4thG33k.m4ththings.tiles.TileLargeQT;
import com.M4thG33k.m4ththings.tiles.TileMedQT;
import com.M4thG33k.m4ththings.tiles.TileQuantumTank;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class ClientProxy extends CommonProxy{

    public static int renderPass;
    public static int  frozenDiamondRenderType;

    @Override
    public void registerRenderers()
    {
        super.registerRenderers();

        ModRenderers.init();
//        ClientRegistry.bindTileEntitySpecialRenderer(TileCobbleChest.class, new CobbleChestRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileBaseTank.class, new BaseTankRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumTank.class, new QuantumTankRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileMedQT.class, new MediumQuantumTankRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileLargeQT.class, new LargeQTRenderer());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockQuantumTank),new QuantumTankItemRenderer(new QuantumTankRenderer(),new TileQuantumTank()));
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockBaseTank),new BaseTankItemRenderer(new BaseTankRenderer(),new TileBaseTank()));
    }

    @Override
    public void startParticleRendering(PacketFilling message) {
        World world = Minecraft.getMinecraft().theWorld;

        TileEntity tileEntity = world.getTileEntity(message.getX(),message.getY(),message.getZ());

        if (tileEntity!=null && tileEntity instanceof TileQuantumTank)
        {
            ParticleManager.tankFillParticles(world,message.getX(),message.getY(),message.getZ(),message.getDirection(),message.getIsFilling(), FluidRegistry.getFluid(message.getFluidName()),message.getAmount(),message.getSize());
        }
    }

    @Override
    public void handleNBTPacket(PacketNBT message) {
        if (message==null)
        {
            return;
        }

        TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.location[0],message.location[1],message.location[2]);

        if (tileEntity!=null && tileEntity instanceof IM4thNBTSync)
        {
            ((IM4thNBTSync)tileEntity).receiveNBTPacket(message.tagCompound);
        }
    }
}
