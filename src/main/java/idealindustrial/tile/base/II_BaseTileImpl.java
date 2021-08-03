package idealindustrial.tile.base;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.net.GT_Packet_ByteStream;
import gregtech.api.net.GT_Packet_ExtendedBlockEvent;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.render.II_CustomRenderer;
import idealindustrial.tile.II_TileEvents;
import idealindustrial.tile.covers.II_CoverBehavior;
import idealindustrial.tile.covers.II_CoverRegistry;
import idealindustrial.tile.meta.II_BaseMetaTile_Facing1Output;
import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.tools.II_ToolRegistry;
import idealindustrial.util.energy.EUConsumer;
import idealindustrial.util.energy.EUProducer;
import idealindustrial.util.energy.II_EmptyEnergyHandler;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.fluid.II_EmptyTank;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.fluid.II_FluidInvReprImpl;
import idealindustrial.util.fluid.II_FluidInventoryRepresentation;
import idealindustrial.util.inventory.II_EmptyInventory;
import idealindustrial.util.inventory.II_InternalInventory;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static idealindustrial.tile.II_TileEvents.BASE_ACTIVE;
import static idealindustrial.tile.base.II_BaseTileConstants.*;

public class II_BaseTileImpl extends BaseTileEntity implements II_BaseTile {

    protected II_MetaTile metaTileEntity;
    protected int metaTileID;
    protected long timer = 0;

    protected int color;
    //texture cache, active + covers/side/textureLayers
    protected ITexture[][][] textureCache = new ITexture[3][6][];

    protected boolean allowedToWork, active;

    protected int[] coverIDs = new int[6];
    protected long[] coverValues = new long[6];
    protected II_CoverBehavior[] covers = new II_CoverBehavior[6];


    @Override
    public boolean isInvalidTileEntity() {
        return metaTileEntity != null;
    }

    @Override
    public II_MetaTile getMetaTile() {
        return metaTileEntity;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        if (metaTileEntity == null) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
        }
        super.writeToNBT(tag);
        tag.setIntArray("covers", coverIDs);
        II_StreamUtil.writeNBTLongArray(tag, coverValues, "coverValues");
        tag.setInteger("mID", metaTileID);
        tag.setTag("meta", metaTileEntity.saveToNBT(new NBTTagCompound()));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        setInitialValuesAsNBT(tag.getInteger("mID"));


        coverIDs = tag.getIntArray("covers");
        for (int i = 0; i < coverIDs.length; i++) {
            if (coverIDs[i] != 0) {
                covers[i] = II_CoverRegistry.behaviorFromID(coverIDs[i]);
            }
        }
        coverValues = II_StreamUtil.readNBTLongArray(tag, 6, "coverValues");


        NBTTagCompound metaNBT = tag.getCompoundTag("meta");
        metaTileEntity.loadFromNBT(metaNBT == null ? new NBTTagCompound() : metaNBT);

    }

    public void setInitialValuesAsNBT(int metaTileID) {
        setInitialValuesAsNBT(null, metaTileID);
    }

    public void setInitialValuesAsNBT(NBTTagCompound nbt, int metaTileID) {
        this.metaTileID = metaTileID;
        setMetaTileEntity(II_Values.metaTiles[metaTileID].newMetaTile(this));
    }

    public boolean alive() {
        return !isDead;
    }

    protected void setMetaTileEntity(II_MetaTile metaTileEntity) {
        this.metaTileEntity = metaTileEntity;
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            rebakeMap();
        }
    }


    @Override
    public long getTimer() {
        return timer;
    }

    @Override
    public void setLightValue(byte aLightValue) {
        //legacy. Can be ignored todo: remove
    }


    @Override
    public void updateEntity() {
        if (metaTileEntity == null) {
            if (!worldObj.isRemote) {
                worldObj.removeTileEntity(xCoord, yCoord, zCoord);
                worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
                isDead = true;
            }
            return;
        }
        super.updateEntity();
        boolean serverSide = isServerSide();
        if (timer == 0) {
            onFirstTick(timer, serverSide);
        }
        onPreTick(timer, serverSide);
        onTick(timer, serverSide);
        onPostTick(timer, serverSide);
        timer++;
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {
        metaTileEntity.onFirstTick(timer, serverSide);
    }

    @Override
    public void onPreTick(long timer, boolean serverSide) {
        metaTileEntity.onPreTick(timer, serverSide);
        for (int i = 0; i < 6; i++) {
            if (covers[i] != null && covers[i].getTickRate() > 0 && timer % covers[i].getTickRate() == 0) {
               coverValues[i] = covers[i].update(coverValues[i],  i, this);
            }
        }
    }

    @Override
    public void onTick(long timer, boolean serverSide) {
        metaTileEntity.onTick(timer, serverSide);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        metaTileEntity.onPostTick(timer, serverSide);
    }

    @Override
    public ITexture[][] getTextures() {
        return textureCache[active ? 1 : 0];
    }

    @Override
    public ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers) {
        int oldFacing = 0;
        if (metaTileEntity instanceof II_BaseMetaTile_Facing1Output) {//shitty but works for now
            oldFacing = ((II_BaseMetaTile_Facing1Output) metaTileEntity).outputFacing;
            ((II_BaseMetaTile_Facing1Output) metaTileEntity).outputFacing = aFacing;
        }
        ITexture[][] textures = new ITexture[6][];
        for (int side = 0; side < 6; side++) {
            textures[side] = provideTexture(aActive, side, true);
        }

        if (metaTileEntity instanceof II_BaseMetaTile_Facing1Output) {
            ((II_BaseMetaTile_Facing1Output) metaTileEntity).outputFacing = oldFacing;
        }
        return textures;//inventory
    }

    @Override
    public ITexture[][] getTextures(boolean covered) {
        if (covered) {
            return textureCache[2];
        }
        else {
            return getTextures();
        }
    }

    @Override
    public void rebakeMap() {
        if (metaTileEntity == null) {
            return;
        }
        if (metaTileEntity.cacheCoverTexturesSeparately()) {
            textureCache = new ITexture[3][6][];
            for (int i = 0; i < 6; i++) {
                textureCache[0][i] = provideTexture(false, i, true);
                textureCache[1][i] = provideTexture(true, i, true);
                textureCache[2][i] = new ITexture[]{coverAtSide(i)};
            }
        }
        else {
            textureCache = new ITexture[2][6][];
            for (int i = 0; i < 6; i++) {
                textureCache[0][i] = provideTexture(false, i, true);
                textureCache[1][i] = provideTexture(true, i, true);
            }
        }
    }

    protected ITexture[] provideTexture(boolean active, int side, boolean covered) {
        if (covered && covers[side] != null) {
            return new ITexture[]{coverAtSide(side)};
        }
        return metaTileEntity.provideTexture(active, side);
    }

    public ITexture coverAtSide(int side) {
        return covers[side] != null ? covers[side].getTexture() : null;
    }

    @Override
    public int getMetaTileID() {
        return metaTileID;
    }

    @Override
    public void setMetaTileID(int metaTileID) {
        this.metaTileID = metaTileID;
        setMetaTileEntity(II_Values.metaTiles[metaTileID]);
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        return null;//todo implement
    }

    public void syncTileEntity() {
        ByteArrayDataOutput stream = ByteStreams.newDataOutput(10);
        writeTile(stream);
        GT_Values.NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_ByteStream(xCoord, yCoord, zCoord, stream), xCoord, zCoord);
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        stream.writeInt(metaTileID);
        stream.writeBoolean(active);
        metaTileEntity.writeTile(stream);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        setInitialValuesAsNBT(stream.readInt());
        active = stream.readBoolean();
        metaTileEntity.readTile(stream);
    }

    @Override
    public Packet getDescriptionPacket() {
        if (metaTileEntity != null) {
            syncTileEntity();
        }
        return null;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (metaTileEntity == null) {
            return false;
        }
        if (covers[side] != null && covers[side].onRightClick(coverValues[side], side, this, player, item, hitX, hitY, hitZ)) {
            return true;
        }
        return isServerSide() && II_ToolRegistry.applyTool(metaTileEntity, player, item, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onLeftClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    public void sendEvent(int id, int value) {
        GT_Values.NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_ExtendedBlockEvent(xCoord, (short) yCoord, zCoord, id, value), xCoord, zCoord);
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        switch (id) {
            case BASE_ACTIVE:
                active = intToBool(value);
                issueTextureUpdate();
                return true;

        }
        return metaTileEntity.receiveClientEvent(id, value);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            if (isServerSide()) {
                sendEvent(EVENT_ACTIVE, boolToInt(active));
            }
        }
    }

    public void issueTextureUpdate() {
        rebakeMap();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public boolean openGUI(EntityPlayer aPlayer, int aID) {
        if (aPlayer == null) return false;
        aPlayer.openGui(II_Core.INSTANCE, aID, worldObj, xCoord, yCoord, zCoord);
        return true;
    }


    @Override
    public II_CustomRenderer getCustomRenderer() {
        return metaTileEntity.hasRenderer() ? metaTileEntity.getRenderer() : null;
    }

    @Override
    public void onAdjacentBlockChange(int aX, int aY, int aZ) {
        super.onAdjacentBlockChange(aX, aY, aZ);
        if (metaTileEntity != null) {
            metaTileEntity.onBlockChange();
        }
    }

    @Override
    public void onPlaced() {
        if (metaTileEntity != null) {
            metaTileEntity.onPlaced();
        }
    }

    @Override
    public void invalidate() {
        if (metaTileEntity != null) {
            metaTileEntity.onRemoval();
        }
        super.invalidate();
    }
}
