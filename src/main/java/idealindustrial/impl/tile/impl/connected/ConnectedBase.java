package idealindustrial.impl.tile.impl.connected;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.FMLCommonHandler;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.render.CustomRenderer;
import idealindustrial.impl.tile.host.HostPipeTileImpl;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.stream.IntStream;

import static idealindustrial.impl.tile.TileEvents.CONNECTIONS;

/**
 * basic class for cable systems tiles
 * has textures, 0 - inactive, 1 - active;
 */
public abstract class ConnectedBase<H extends HostTile> extends TileBase<H> {
    protected int connections = 0;//shitty bitmask to save some space
    protected float thickness = 0.5f;

    public ConnectedBase(H baseTile, String name, ITexture textureInactive, ITexture textureActive) {
        super(baseTile, name, textureInactive, textureActive);
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {
        super.onFirstTick(timer, serverSide);
        if (serverSide) {
            updateConnections();
        }
    }

    @Override
    public boolean hasRenderer() {
        return true;
    }

    @Override
    public CustomRenderer getRenderer() {
        return MetaConnectedRenderer.INSTANCE;
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
        if (hostTile.isClientSide()) {
            return true;
        }
        int sideTo = II_DirUtil.determineWrenchingSide(side, hitX, hitY, hitZ);
        II_Util.sendChatToPlayer(player, "side: " + side);
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
//        nbt.setInteger("connections", connections);
        return super.saveToNBT(nbt);
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
//        connections = nbt.getInteger("connections");
        super.loadFromNBT(nbt);
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == CONNECTIONS) {
            connections = value;
            hostTile.issueTextureUpdate();
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    public void syncClient() {
        hostTile.sendEvent(CONNECTIONS, connections);
    }


    @Override
    public boolean cacheCoverTexturesSeparately() {
        return true;
    }

    @Override
    public void onBlockChange() {
        if (hostTile.isServerSide()) {
            updateConnections();
        }
    }

    @Override
    public void onPlaced() {
        updateConnections();
    }



    public void updateConnections() {
        int oldConnections = connections;
        connections = 0;
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

    @Override
    public Class<? extends HostTile> getBaseTileClass() {
        return HostPipeTileImpl.class;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient() && false)
            return AxisAlignedBB.getBoundingBox(aX, aY, aZ, aX + 1, aY + 1, aZ + 1);
        else
            return getActualCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
    }

    private AxisAlignedBB getActualCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        float tSpace = (1f - thickness) / 2;
        float tSide0 = tSpace;
        float tSide1 = 1f - tSpace;
        float tSide2 = tSpace;
        float tSide3 = 1f - tSpace;
        float tSide4 = tSpace;
        float tSide5 = 1f - tSpace;

        if (hostTile.getCoverIDAtSide(0) != 0) {
            tSide0 = tSide2 = tSide4 = 0;
            tSide3 = tSide5 = 1;
        }
        if (hostTile.getCoverIDAtSide(1) != 0) {
            tSide2 = tSide4 = 0;
            tSide1 = tSide3 = tSide5 = 1;
        }
        if (hostTile.getCoverIDAtSide(2) != 0) {
            tSide0 = tSide2 = tSide4 = 0;
            tSide1 = tSide5 = 1;
        }
        if (hostTile.getCoverIDAtSide(3) != 0) {
            tSide0 = tSide4 = 0;
            tSide1 = tSide3 = tSide5 = 1;
        }
        if (hostTile.getCoverIDAtSide(4) != 0) {
            tSide0 = tSide2 = tSide4 = 0;
            tSide1 = tSide3 = 1;
        }
        if (hostTile.getCoverIDAtSide(5) != 0) {
            tSide0 = tSide2 = 0;
            tSide1 = tSide3 = tSide5 = 1;
        }

        int tConn = connections;
        if ((tConn & (1 << ForgeDirection.DOWN.ordinal())) != 0) tSide0 = 0f;
        if ((tConn & (1 << ForgeDirection.UP.ordinal())) != 0) tSide1 = 1f;
        if ((tConn & (1 << ForgeDirection.NORTH.ordinal())) != 0) tSide2 = 0f;
        if ((tConn & (1 << ForgeDirection.SOUTH.ordinal())) != 0) tSide3 = 1f;
        if ((tConn & (1 << ForgeDirection.WEST.ordinal())) != 0) tSide4 = 0f;
        if ((tConn & (1 << ForgeDirection.EAST.ordinal())) != 0) tSide5 = 1f;

        return AxisAlignedBB.getBoundingBox(aX + tSide4, aY + tSide0, aZ + tSide2, aX + tSide5, aY + tSide1, aZ + tSide3);
    }

    @Override
    public void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB, List<AxisAlignedBB> outputAABB, Entity collider) {
        super.addCollisionBoxesToList(aWorld, aX, aY, aZ, inputAABB, outputAABB, collider);
        if (FMLCommonHandler.instance().getEffectiveSide().isClient() && false) {
            AxisAlignedBB aabb = getActualCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
            if (inputAABB.intersectsWith(aabb)) outputAABB.add(aabb);
        }
    }
}
