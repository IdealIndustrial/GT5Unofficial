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
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.render.CustomRenderer;
import idealindustrial.tile.IOType;
import idealindustrial.tile.covers.BaseCoverBehavior;
import idealindustrial.tile.covers.CoverRegistry;
import idealindustrial.tile.interfaces.base.BaseTile;
import idealindustrial.tile.interfaces.meta.MetaTile;
import idealindustrial.tile.meta.BaseMetaTile_Facing2Main;
import idealindustrial.tools.ToolRegistry;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.misc.II_StreamUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

import java.util.ArrayList;

import static idealindustrial.tile.TileEvents.BASE_ACTIVE;
import static idealindustrial.tile.base.BaseTileConstants.*;

public class BaseTileImpl extends BaseTileEntity implements BaseTile {

    protected MetaTile<?> metaTileEntity;
    protected int metaTileID;
    protected long timer = 0;

    protected int color;
    //texture cache, active/side/textureLayers
    protected ITexture[][][] textureCache = new ITexture[2][6][];

    protected boolean allowedToWork, active;

    protected int[] coverIDs = new int[6];
    protected long[] coverValues = new long[6];
    @SuppressWarnings("rawtypes")
    protected BaseCoverBehavior[] covers = new BaseCoverBehavior[6];


    @Override
    public boolean isInvalidTileEntity() {
        return metaTileEntity != null;
    }

    @Override
    public MetaTile<?> getMetaTile() {
        return metaTileEntity;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        if (metaTileEntity == null) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
        }
        super.writeToNBT(tag);
        tag.setBoolean("allowWork", allowedToWork);
        tag.setBoolean("active", active);
        tag.setIntArray("covers", coverIDs);
        II_StreamUtil.writeNBTLongArray(tag, coverValues, "coverValues");
        tag.setInteger("mID", metaTileID);
        tag.setTag("meta", metaTileEntity.saveToNBT(new NBTTagCompound()));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        setInitialValuesAsNBT(tag.getInteger("mID"));

        allowedToWork = tag.getBoolean("allowWork");
        active = tag.getBoolean("active");
        coverIDs = tag.getIntArray("covers");
        for (int i = 0; i < coverIDs.length; i++) {
            if (coverIDs[i] != 0) {
                covers[i] = CoverRegistry.behaviorFromID(coverIDs[i]);
            }
        }
        coverValues = II_StreamUtil.readNBTLongArray(tag, 6, "coverValues");


        NBTTagCompound metaNBT = tag.getCompoundTag("meta");
        metaTileEntity.loadFromNBT(metaNBT == null ? new NBTTagCompound() : metaNBT);
        if (metaTileEntity != null) {
            issueTextureUpdate();
        }

    }

    public void setInitialValuesAsNBT(int metaTileID) {
        setInitialValuesAsNBT(null, metaTileID);
    }

    @SuppressWarnings("unchecked")
    public void setInitialValuesAsNBT(NBTTagCompound nbt, int metaTileID) {
        this.metaTileID = metaTileID;
        setMetaTileEntity(II_Values.metaTiles[metaTileID].newMetaTile(this));
    }

    public boolean alive() {
        return metaTileEntity != null;
    }

    protected void setMetaTileEntity(MetaTile<?> metaTileEntity) {
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
    @SuppressWarnings("unchecked")
    public void onPreTick(long timer, boolean serverSide) {
        metaTileEntity.onPreTick(timer, serverSide);
        for (int i = 0; i < 6; i++) {
            if (covers[i] != null && covers[i].getTickRate() > 0 && timer % covers[i].getTickRate() == 0) {
                coverValues[i] = covers[i].update(coverValues[i], i, this);
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
    @SuppressWarnings("rawtypes")//shitty, extremely shitty
    public ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers) {
        int oldFacing = 0;
        if (metaTileEntity instanceof BaseMetaTile_Facing2Main) {//shitty but works for now
            oldFacing = ((BaseMetaTile_Facing2Main) metaTileEntity).mainFacing;
            ((BaseMetaTile_Facing2Main) metaTileEntity).mainFacing = aFacing;
        }
        ITexture[][] textures = new ITexture[6][];
        for (int side = 0; side < 6; side++) {
            textures[side] = provideTexture(aActive, side);
        }

        if (metaTileEntity instanceof BaseMetaTile_Facing2Main) {
            ((BaseMetaTile_Facing2Main) metaTileEntity).mainFacing = oldFacing;
        }
        return textures;//inventory
    }

    @Override
    public ITexture[][] getTextures(boolean covered) {
        return getTextures();
    }

    @Override
    public void rebakeMap() {
        if (metaTileEntity == null) {
            return;
        }
        textureCache = new ITexture[2][6][];
        for (int i = 0; i < 6; i++) {
            textureCache[0][i] = provideTexture(false, i);
            textureCache[1][i] = provideTexture(true, i);
        }
    }

    protected ITexture[] provideTexture(boolean active, int side) {
        if (covers[side] != null) {
            return new ITexture[]{getCoverTexture(side)};
        }
        return metaTileEntity.provideTexture(active, side);
    }

    public ITexture getCoverTexture(int side) {
        return covers[side] != null ? covers[side].getTexture(coverValues[side], side, this) : null;
    }

    @Override
    public int getMetaTileID() {
        return metaTileID;
    }

    @Override
    public void setMetaTileID(int metaTileID) {
        this.metaTileID = metaTileID;
        setMetaTileEntity((MetaTile<?>) II_Values.metaTiles[metaTileID]);
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        return null;//todo implement
    }

    @Override
    public void syncTileEntity() {
        ByteArrayDataOutput stream = ByteStreams.newDataOutput(10);
        writeTile(stream);
        GT_Values.NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_ByteStream(xCoord, yCoord, zCoord, stream), xCoord, zCoord);
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        stream.writeInt(metaTileID);
        stream.writeBoolean(active);
        II_StreamUtil.writeIntArray(coverIDs, stream);
        II_StreamUtil.writeLongArray(coverValues, stream);
        metaTileEntity.writeTile(stream);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        setInitialValuesAsNBT(stream.readInt());
        active = stream.readBoolean();
        coverIDs = II_StreamUtil.readIntArray(stream);
        coverValues = II_StreamUtil.readLongArray(stream);
        for (int side = 0; side < 6; side++) {
            if (coverIDs[side] == 0) {
                continue;
            }
            covers[side] = CoverRegistry.behaviorFromID(coverIDs[side]);
        }
        metaTileEntity.readTile(stream);
        issueTextureUpdate();
    }

    @Override
    public Packet getDescriptionPacket() {
        if (metaTileEntity != null) {
            syncTileEntity();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (metaTileEntity == null) {
            return false;
        }
        if (covers[side] != null && covers[side].onRightClick(coverValues[side], side, this, player, item, hitX, hitY, hitZ)) {
            return true;
        }
        if (isServerSide() && ToolRegistry.applyTool(metaTileEntity, player, item, side, hitX, hitY, hitZ)) {
            return true;
        }
        if (isServerSide() && covers[side] == null && player.getHeldItem() != null) {//todo ctrl alt m
            HashedStack hashHand = new HashedStack(player.getHeldItem());
            if (CoverRegistry.isCover(hashHand)) {
                BaseCoverBehavior<?> coverBehavior = CoverRegistry.behaviorFromStack(hashHand);
                if (metaTileEntity.allowCoverAtSide(coverBehavior, side)) {
                    if (!player.capabilities.isCreativeMode) {
                        player.getHeldItem().stackSize--;
                    }
                    if (player.getHeldItem().stackSize == 0) {
                        player.inventory.setItemStack(null);
                    }
                    setCoverAtSide(side, hashHand.hashCode(), coverBehavior);
                    return true;
                }
            }
        }
        return metaTileEntity.onRightClick(player, item, side, hitX, hitY, hitZ);
    }

    protected void setCoverAtSide(int side, int id, BaseCoverBehavior<?> cover) {
        coverValues[side] = 0;
        coverIDs[side] = id;
        covers[side] = cover;
        syncTileEntity();
    }

    //clientside sync method
    protected void setCoverAtSide(int side, int id, long coverValue) {
        coverValues[side] = coverValue;
        coverIDs[side] = id;
        covers[side] = CoverRegistry.behaviorFromID(id);
        issueTextureUpdate();
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
        if (id == BASE_ACTIVE) {
            active = intToBool(value);
            issueTextureUpdate();
            return true;
        }
        return metaTileEntity.receiveClientEvent(id, value);
    }


    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            if (isServerSide()) {
                sendEvent(EVENT_ACTIVE, boolToInt(active));
            }
        }
    }

    @Override
    public boolean isAllowedToWork() {
        return allowedToWork;
    }

    @Override
    public void setAllowedToWork(boolean allow) {
        if (!allowedToWork && allow) {
            allowedToWork = true;
            onEnabled();
        }
        allowedToWork = allow;
    }

    public void issueTextureUpdate() {
        rebakeMap();
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public boolean openGUI(EntityPlayer aPlayer, int aID) {
        if (aPlayer == null) return false;
        aPlayer.openGui(II_Core.INSTANCE, aID, worldObj, xCoord, yCoord, zCoord);
        return true;
    }


    @Override
    public CustomRenderer getCustomRenderer() {
        if (metaTileEntity == null) {//all client side startup things should always check mte for null
            return null;//cause minecraft sometimes does not send any description packets to client in time
        }
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

    @Override
    public void receiveNeighbourIOConfigChange(IOType type) {
        metaTileEntity.receiveNeighbourIOConfigChange(type);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTileImpl that = (BaseTileImpl) o;
        return xCoord == that.xCoord && yCoord == that.yCoord && zCoord == that.zCoord && worldObj == that.worldObj;
    }

    @Override
    public int hashCode() {
        return xCoord * 31 * 31 + yCoord * 31 + zCoord;
    }


    protected void onEnabled() {

    }

    @Override
    public int getCoverIDAtSide(int side) {
        return coverIDs[side];
    }

    @Override
    public BaseCoverBehavior<?> getCoverAtSide(int side) {
        return covers[side];
    }

    @Override
    public long getCoverVarAtSide(int side) {
        return coverValues[side];
    }

    @Override
    public void dropCoverAtSide(int side) {
        //todo impl
    }

    @Override
    public void setCoverVarAtSide(int side, long value) {
        coverValues[side] = value;
    }
}
