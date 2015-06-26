package com.M4thG33k.m4ththings.guis;

import com.M4thG33k.m4ththings.containers.ContainerBaseChest;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileBaseChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * Created by M4thG33k on 6/2/2015.
 */
public class GuiBaseChest extends GuiContainer {

    public GuiBaseChest(InventoryPlayer inventoryPlayer, TileBaseChest tileEntity)
    {
        super(new ContainerBaseChest(inventoryPlayer,tileEntity));
        this.xSize = 176;
        this.ySize = 167;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p1, int p2)
    {
        fontRendererObj.drawString("Base Chest",8,6,4210752);

        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"),8,ySize-96+2,4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p1, int p2, int p3)
    {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID +":" + "textures/client/gui/BaseChest.png");
        GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
        this.mc.renderEngine.bindTexture(resourceLocation);
        int x = (width-xSize)/2;
        int y = (height-ySize)/2;
        this.drawTexturedModalRect(x,y,0,0,xSize,ySize);

    }




}
