package idealindustrial.tile.meta.connected;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.api.interfaces.ITexture;
import gregtech.api.util.GT_Utility;
import idealindustrial.render.II_CustomRenderer;
import idealindustrial.tile.IOType;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.util.misc.II_DirUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.stream.IntStream;

import static idealindustrial.tile.II_TileEvents.CONNECTIONS;

/**
 * basic class for cable systems tiles
 * has textures, 0 - inactive, 1 - active;
 */
public abstract class II_BaseMetaConnected<BaseTileType extends II_BaseTile> extends II_BaseMetaTile<BaseTileType> {
    protected int connections = 0;//shitty bitmask to save some space
    protected float thickness = 0.5f;

    public II_BaseMetaConnected(BaseTileType baseTile, ITexture textureInactive, ITexture textureActive) {
        super(baseTile, textureInactive, textureActive);
    }

    @Override
    public boolean hasRenderer() {
        return true;
    }

    @Override
    public II_CustomRenderer getRenderer() {
        return II_MetaConnectedRenderer.INSTANCE;
    }

    public boolean isConnected(int side) {
        return ((1 << side) & connections) != 0;
    }

    public void setConnected(int side) {
        connections |= 1 << side;
    }

    public void setDisconnected(int side) {
        connections &= ~(1 << side);
    }

    public void set(int side, boolean connected) {
        if (connected) {
            setConnected(side);
        } else {
            setDisconnected(side);
        }
    }

    public float getThickness() {
        return thickness;
    }

    @Override//todo move from base class
    public boolean onWrenchClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (baseTile.isClientSide()) {
            return true;
        }
        int sideTo = II_DirUtil.determineWrenchingSide(side, hitX, hitY, hitZ);
        GT_Utility.sendChatToPlayer(player, "side: " + side);
        set(sideTo, !isConnected(sideTo));
        syncClient();
        return true;
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        stream.writeInt(connections);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        connections = stream.readInt();
    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        nbt.setInteger("connections", connections);
        return super.saveToNBT(nbt);
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        connections = nbt.getInteger("connections");
        super.loadFromNBT(nbt);
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == CONNECTIONS) {
            connections = value;
            baseTile.issueTextureUpdate();
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    public void syncClient() {
        baseTile.sendEvent(CONNECTIONS, connections);
    }


    @Override
    public boolean cacheCoverTexturesSeparately() {
        return true;
    }

    @Override
    public void onBlockChange() {
        if (baseTile.isServerSide()) {
            updateConnections();
        }
    }

    @Override
    public void onPlaced() {
        updateConnections();
    }

    @Override
    public void receiveNeighbourIOConfigChange(IOType type) {
        if (type == IOType.ENERGY) {
            updateConnections();
        }
    }

    public void updateConnections() {
        int oldConnections = connections;
        for (int i = 0; i < 6; i++) {
            if (canConnect(i)) {
                setConnected(i);
            }
            else {
                setDisconnected(i);
            }
        }
        if (oldConnections != connections) {
            syncClient();
            onConnectionUpdate();
        }

    }

    public abstract boolean canConnect(int side);

    public void onConnectionUpdate() {

    }


    //definitely not fast, todo: replace
    public int connectionCount() {
        return IntStream.iterate(0, i -> i + 1).limit(6).map(i -> isConnected(i) ? 1 : 0).sum();
    }
}
