package com.M4thG33k.m4ththings.tiles;

import com.M4thG33k.m4ththings.utility.LogHelper;
import com.M4thG33k.m4ththings.utility.StringHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

/**
 * Created by M4thG33k on 6/23/2015.
 */
public class TileQTComponent extends TileEntity{

//    protected int parX=0;
//    protected int parY=0;
//    protected int parZ=0;

    protected int[] parentLoc;


    public TileQTComponent()
    {
        super();
        parentLoc = null;
    }

    public void setParentCoordinates(int x,int y, int z)
    {
//        parX = x;
//        parY = y;
//        parZ = z;

        if (parentLoc==null)
        {
            parentLoc = new int[3];
        }
        parentLoc[0] = x;
        parentLoc[1] = y;
        parentLoc[2] = z;
    }


    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);

        int parX=0;
        int parY=0;
        int parZ=0;

        if (tagCompound.hasKey("parX"))
        {
            parX = tagCompound.getInteger("parX");
//            LogHelper.info("Has X! " + parX);
        }

        if (tagCompound.hasKey("parY"))
        {
            parY = tagCompound.getInteger("parY");
//            LogHelper.info("Has Y! " + parY);
        }

        if (tagCompound.hasKey("parZ"))
        {
            parZ = tagCompound.getInteger("parZ");
//            LogHelper.info("Has Z! " + parZ);
        }

        setParentCoordinates(parX,parY,parZ);

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) //todo figure out why parent data isn't being saved...
    {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("parX",parentLoc[0]);
        tagCompound.setInteger("parY",parentLoc[1]);
        tagCompound.setInteger("parZ",parentLoc[2]);

//        LogHelper.info("For (" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + "), Writing location as: " + tagCompound.getInteger("parX") + ", " + tagCompound.getInteger("parY") + ", " + tagCompound.getInteger("parZ") + ")");
    }

    public int[] getParentCoordinates()
    {
        return parentLoc;
    }

    public TileEntity getParentTile()
    {
        if (parentLoc==null)
        {
            LogHelper.info("HELP! I can't find my parent!");
            return null;
        }
        if (parentLoc[0]==0 && parentLoc[1]==0 && parentLoc[2]==0)
        {
            LogHelper.info("Ru-ro! My parent is a loser. (Zeros)");
            return null;
        }
//        LogHelper.info("Looking for TE at: " + StringHelper.makeCoords(parentLoc[0],parentLoc[1],parentLoc[2]));
        TileEntity tileEntity = worldObj.getTileEntity(parentLoc[0],parentLoc[1],parentLoc[2]);
        if (tileEntity==null)
        {
            LogHelper.info("getParentTile() is returning null!");
        }
        return tileEntity;
    }

    public TileMedQT getParentTank()
    {
        TileEntity tileEntity = getParentTile();
        if (tileEntity instanceof TileMedQT)
        {
            return (TileMedQT)tileEntity;
        }
        LogHelper.info("ERROR (TileQTComponent): the parent of this block is not a controller!!");
        if (tileEntity==null)
        {
            LogHelper.info("The tile is, indeed, null...");
        }
        else
        {
            LogHelper.info("Instead, we have: " + tileEntity.getClass().toString());
        }
        return null;
    }

    public void prepareSync()
    {
//        LogHelper.info("Preparing sync for QTComponent at: " + StringHelper.makeCoords(xCoord,yCoord,zCoord));
        worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
        markDirty();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();

        this.writeToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,1,data);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

}
