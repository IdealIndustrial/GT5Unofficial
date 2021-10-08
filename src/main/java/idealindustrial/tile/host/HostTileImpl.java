package idealindustrial.tile.host;

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
import idealindustrial.tile.interfaces.host.HostTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.tools.ToolRegistry;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.misc.II_StreamUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static idealindustrial.tile.TileEvents.BASE_ACTIVE;
import static idealindustrial.tile.host.HostTileConstants.*;

public class HostTileImpl extends BaseTileEntity implements HostTile {

    protected Tile<?> tile;
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

    protected List<Consumer<WorldAction>> worldChangeListeners = new ArrayList<>();


    @Override
    public boolean isInvalidTileEntity() {
        return tile != null;
    }

    @Override
    public Tile<?> getMetaTile() {
        return tile;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        if (tile == null) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
        }
        super.writeToNBT(tag);
        tag.setBoolean("allowWork", allowedToWork);
        tag.setBoolean("active", active);
        tag.setIntArray("covers", coverIDs);
        II_StreamUtil.writeNBTLongArray(tag, coverValues, "coverValues");
        tag.setInteger("mID", metaTileID);
        tag.setTag("meta", tile.saveToNBT(new NBTTagCompound()));
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
        tile.loadFromNBT(metaNBT == null ? new NBTTagCompound() : metaNBT);
        if (tile != null) {
            issueTextureUpdate();
        }

    }

    public void setInitialValuesAsNBT(int metaTileID) {
        setInitialValuesAsNBT(null, metaTileID);
    }

    @SuppressWarnings("unchecked")
    public void setInitialValuesAsNBT(NBTTagCompound nbt, int metaTileID) {
        this.metaTileID = metaTileID;
        setMetaTileEntity(II_Values.TILES[metaTileID].newMetaTile(this));
    }

    public boolean alive() {
        return tile != null;
    }

    protected void setMetaTileEntity(Tile<?> tile) {
        this.tile = tile;
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
        if (tile == null) {
            if (!worldObj.isRemote) {
                worldObj.removeTileEntity(xCoord, yCoord, zCoord);
                worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
                isDead = true;
            }
            return;
        }
        super.updateEntity();
        boolean serverSide = isServerSide();
        try {
            if (timer == 0) {
                onFirstTick(timer, serverSide);
            }
            onPreTick(timer, serverSide);
            onTick(timer, serverSide);
            onPostTick(timer, serverSide);
            timer++;
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {
        tile.onFirstTick(timer, serverSide);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onPreTick(long timer, boolean serverSide) {
        tile.onPreTick(timer, serverSide);
        for (int i = 0; i < 6; i++) {
            if (covers[i] != null && covers[i].getTickRate() > 0 && timer % covers[i].getTickRate() == 0) {
                coverValues[i] = covers[i].update(coverValues[i], i, this);
            }
        }
    }

    @Override
    public void onTick(long timer, boolean serverSide) {
        tile.onTick(timer, serverSide);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        tile.onPostTick(timer, serverSide);
    }

    @Override
    public ITexture[][] getTextures() {
        return textureCache[active ? 1 : 0];
    }

    @Override
    @SuppressWarnings("rawtypes")//shitty, extremely shitty
    public ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers) {
        int oldFacing = 0;
        if (tile instanceof TileFacing2Main) {//shitty but works for now
            oldFacing = ((TileFacing2Main) tile).mainFacing;
            ((TileFacing2Main) tile).mainFacing = aFacing;
        }
        ITexture[][] textures = new ITexture[6][];
        for (int side = 0; side < 6; side++) {
            textures[side] = provideTexture(aActive, side);
        }

        if (tile instanceof TileFacing2Main) {
            ((TileFacing2Main) tile).mainFacing = oldFacing;
        }
        return textures;//inventory
    }

    @Override
    public ITexture[][] getTextures(boolean covered) {
        return getTextures();
    }

    @Override
    public void rebakeMap() {
        if (tile == null) {
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
        return tile.provideTexture(active, side);
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
        setMetaTileEntity((Tile<?>) II_Values.TILES[metaTileID]);
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
        tile.writeTile(stream);
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
        tile.readTile(stream);
        issueTextureUpdate();
    }

    @Override
    public Packet getDescriptionPacket() {
        if (tile != null) {
            syncTileEntity();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (tile == null) {
            return false;
        }
        if (covers[side] != null && covers[side].onRightClick(coverValues[side], side, this, player, item, hitX, hitY, hitZ)) {
            return true;
        }
        if (isServerSide() && ToolRegistry.applyTool(tile, player, item, side, hitX, hitY, hitZ)) {
            return true;
        }
        if (isServerSide() && covers[side] == null && player.getHeldItem() != null) {//todo ctrl alt m
            HashedStack hashHand = new HashedStack(player.getHeldItem());
            if (CoverRegistry.isCover(hashHand)) {
                BaseCoverBehavior<?> coverBehavior = CoverRegistry.behaviorFromStack(hashHand);
                if (tile.allowCoverAtSide(coverBehavior, side)) {
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
        return tile.onRightClick(player, item, side, hitX, hitY, hitZ);
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
        return tile.receiveClientEvent(id, value);
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
        if (tile == null) {//all client side startup things should always check mte for null
            return null;//cause minecraft sometimes does not send any description packets to client in time
        }
        return tile.hasRenderer() ? tile.getRenderer() : null;
    }

    @Override
    public void onWorldStateUpdated(Consumer<WorldAction> listener) {
        worldChangeListeners.add(listener);
    }

    @Override
    public void onAdjacentBlockChange(int aX, int aY, int aZ) {
        super.onAdjacentBlockChange(aX, aY, aZ);
        if (tile != null) {
            tile.onBlockChange();
            worldChangeListeners.forEach(c -> c.accept(WorldAction.OnAdjacentBlockChange));
        }
    }

    @Override
    public void onPlaced() {
        if (tile != null) {
            tile.onPlaced();
            worldChangeListeners.forEach(c -> c.accept(WorldAction.OnPlaced));
        }
    }

    @Override
    public void invalidate() {
        if (tile != null) {
            tile.onRemoval();
            worldChangeListeners.forEach(c -> c.accept(WorldAction.OnRemoval));
        }
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {
        if (tile != null) {
            tile.onRemoval();
            worldChangeListeners.forEach(c -> c.accept(WorldAction.OnRemoval));
        }
        super.onChunkUnload();
    }

    @Override
    public void receiveNeighbourIOConfigChange(IOType type) {
        tile.receiveNeighbourIOConfigChange(type);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostTileImpl that = (HostTileImpl) o;
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
