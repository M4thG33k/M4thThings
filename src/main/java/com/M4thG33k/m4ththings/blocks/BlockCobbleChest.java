package com.M4thG33k.m4ththings.blocks;

import com.M4thG33k.m4ththings.creativetabs.CreativeTabM4thThings;
import com.M4thG33k.m4ththings.reference.Configurations;
import com.M4thG33k.m4ththings.reference.Reference;
import com.M4thG33k.m4ththings.tiles.TileCobbleChest;
import com.M4thG33k.m4ththings.utility.ChatHelper;
import com.M4thG33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by M4thG33k on 5/28/2015.
 */
public class BlockCobbleChest extends BlockContainer {

    public IIcon[] icons = new IIcon[5];


    public BlockCobbleChest(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        this.setBlockTextureName(Reference.MOD_ID + ":" + "blockCobbleChest");
        this.setBlockName(Reference.MOD_ID + "_" + "blockCobbleChest");
    }

    public BlockCobbleChest() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabM4thThings.M4THTHINGS_TAB);
        this.setBlockTextureName(Reference.MOD_ID + ":" + "blockCobbleChest");
        this.setBlockName(Reference.MOD_ID + "_" + "blockCobbleChest");
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCobbleChest();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote) {
            return true;
        }
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity == null || !(tileEntity instanceof TileCobbleChest)) {
            return false;
        }
        TileCobbleChest tEnt = (TileCobbleChest) tileEntity;
        ItemStack held = player.getHeldItem();
        if (player.isSneaking() && held == null) {
            ChatHelper.sayMessage(world, player, "This chest currently holds " + tEnt.getCobble(0) + " cobblestone.");
            return true;

        }
        if (!player.isSneaking() && held!=null)
        {
            if (held.getItem() == Item.getItemFromBlock(Blocks.obsidian))
            {
                if (tEnt.makeReinforced())
                {
                    held.stackSize -= 1;
                    ChatHelper.sayMessage(world, player, "This chest isn't going anywhere for a while...");
                    return true;
                }
            }
        }
        if (!player.isSneaking()) {
            for (int i = 0; i < 36; i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if (slot != null && slot.getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
                    player.inventory.setInventorySlotContents(i, tEnt.addCobble(slot));
                }
            }
            ((EntityPlayerMP) player).sendContainerToPlayer(player.openContainer);
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
        dropItems(world,x,y,z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    protected void dropItems(World world, int x, int y, int z)
    {
        Random rand = new Random();

        TileEntity tEnt = world.getTileEntity(x,y,z);
        if (!(tEnt instanceof TileCobbleChest) || world.isRemote)
        {
            return;
        }

        TileCobbleChest tile = (TileCobbleChest)tEnt;

        int cobbleToDrop = Math.min(Configurations.MAX_DROPPED_COBBLE, tile.getCobble(0));

        float factor = 0.05F;

        while (cobbleToDrop >= 64)
        {
            float rx = rand.nextFloat() * 0.5F + 0.1F;
            float ry = rand.nextFloat() * 0.5F + 0.5F;
            float rz = rand.nextFloat() * 0.5F + 0.1F;

            EntityItem entityItem = new EntityItem(world,x+rx,y+ry,z+rz, new ItemStack(Item.getItemFromBlock(Blocks.cobblestone),64));
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor;
            entityItem.motionZ = rand.nextGaussian() * factor;
            world.spawnEntityInWorld(entityItem);
            cobbleToDrop -= 64;
        }

        if (cobbleToDrop > 0)
        {
            float rx = rand.nextFloat() * 0.5F + 0.1F;
            float ry = rand.nextFloat() * 0.5F + 0.5F;
            float rz = rand.nextFloat() * 0.5F + 0.1F;

            EntityItem entityItem = new EntityItem(world,x+rx,y+ry,z+rz, new ItemStack(Item.getItemFromBlock(Blocks.cobblestone),cobbleToDrop));
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor;
            entityItem.motionZ = rand.nextGaussian() * factor;
            world.spawnEntityInWorld(entityItem);
        }
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        if (world.isRemote || !(world.getTileEntity(x,y,z) instanceof TileCobbleChest) || !(player.getHeldItem()==null || player.getHeldItem().getItem()==Item.getItemFromBlock(Blocks.cobblestone)))
        {
            return;
        }

        TileCobbleChest tile = (TileCobbleChest)world.getTileEntity(x,y,z);
        ItemStack stack = null;
        if (player.isSneaking())
        {
            stack = tile.decrStackSize(0,1);
        }
        else
        {
            stack = tile.popCobbleFromInternalBuffer(64);
            if (stack==null)
            {
                stack = tile.decrStackSize(0,64);
            }
            else if (stack.stackSize<64)
            {
                int deficit = 64-stack.stackSize;
                ItemStack tempStack = tile.decrStackSize(0,deficit);
                stack.stackSize += tempStack.stackSize;
            }
        }

        if (stack==null)
        {
            return;
        }

        Random rand = new Random();
        float factor = 0.01F;
        EntityItem entityItem = new EntityItem(world,x+rand.nextFloat()*0.8F+0.1F,y+rand.nextFloat()*0.8F+0.1F,z+rand.nextFloat()*0.8F+0.1F,stack);
        entityItem.motionX = rand.nextGaussian() * factor;
        entityItem.motionY = rand.nextGaussian() * factor;
        entityItem.motionZ = rand.nextGaussian() * factor;

        world.spawnEntityInWorld(entityItem);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (side==0 || side==1)
        {
            return this.icons[2];
        }
        return this.icons[1];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.icons[0] = reg.registerIcon(this.textureName);
        this.icons[1] = reg.registerIcon(this.textureName+"Sides");
        this.icons[2] = reg.registerIcon(this.textureName+"Top");
        this.icons[3] = reg.registerIcon(this.textureName+"ObsidianSides");
        this.icons[4] = reg.registerIcon(this.textureName+"ObsidianTop");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int iconLocation = 0;
        TileEntity tEnt = blockAccess.getTileEntity(x,y,z);
        if (!(tEnt instanceof TileCobbleChest))
        {
            return this.icons[iconLocation];
        }

        if (side==0 || side==1)
        {
            iconLocation += 1;
        }
        else
        {
            iconLocation += 2;
        }

        if (((TileCobbleChest)tEnt).getReinforced())
        {
            iconLocation += 2;
        }

        return this.icons[iconLocation];
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        TileEntity tEnt = world.getTileEntity(x,y,z);
        if (!(tEnt instanceof TileCobbleChest))
        {
            return 2.0f;
        }

        if (((TileCobbleChest)tEnt).getReinforced())
        {
            return 50.0f;
        }
        return 2.0f;
    }
}
