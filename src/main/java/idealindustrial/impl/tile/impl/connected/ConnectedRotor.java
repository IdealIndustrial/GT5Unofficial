package idealindustrial.impl.tile.impl.connected;

import cpw.mods.fml.common.FMLCommonHandler;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.energy.kinetic.KineticTile;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.impl.textures.RenderedTexture;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.energy.kinetic.system.KineticSystem;
import idealindustrial.impl.tile.host.HostPipeTileRotatingImpl;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import static idealindustrial.api.tile.energy.kinetic.KineticTile.localPoint;
import static idealindustrial.impl.tile.TileEvents.ROTATION_SPEED_DIRECT;
import static idealindustrial.util.misc.II_DirUtil.getOppositeSide;
import static idealindustrial.util.misc.II_TileUtil.getMetaTileAtSide;

public class ConnectedRotor extends ConnectedBase<HostTile>  implements KineticTile {
    int rotationSpeed;

    public ConnectedRotor(HostTile hostTile, II_Material material, Prefixes prefix, float thickness) {
        super(hostTile, EngLocalizer.getInstance().get(material, prefix),
                new RenderedTexture(material.getRenderInfo().getTextureSet().forPrefix(prefix), material.getRenderInfo().getColorAsArray(MatterState.Solid)).maxBrightness(),
                new RenderedTexture(material.getRenderInfo().getTextureSet().forPrefix(prefix), material.getRenderInfo().getColorAsArray(MatterState.Solid)).maxBrightness());
        this.thickness = thickness;
    }


    private ConnectedRotor(HostTile hostTile, String name, ITexture textureInactive, ITexture textureActive, float thickness) {
        super(hostTile, name, textureInactive, textureActive);
        this.thickness = thickness;
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (serverSide && (timer & 4) == 4) {
            sendSpeed(rotationSpeed);
            rotationSpeed = 0;
        }
    }

    @Override
    public boolean canConnect(int side) {
        return true;
    }


    @Override
    public ConnectedRotor newMetaTile(HostTile hostTile) {
        return new ConnectedRotor(hostTile, name, textureInactive, textureActive, thickness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return hostTile.equals(((ConnectedRotor) o).hostTile);
    }

    @Override
    public int hashCode() {
        return hostTile.hashCode();
    }


    public int getRotationSpeed() {
        return rotationSpeed;
    }


    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == ROTATION_SPEED_DIRECT) {
            rotationSpeed = value;
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    void sendSpeed(int speed) {
        hostTile.sendEvent(ROTATION_SPEED_DIRECT, speed);
    }


    @Override
    public Class<? extends HostTile> getBaseTileClass() {
        return HostPipeTileRotatingImpl.class;
    }

    public int getDirection() {
        for (int i = 0; i < 6; i++) {
            if (isConnected(i)) {
                return i / 2;
            }
        }
        return 0;
    }


    @Override
    public boolean onWrenchClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (hostTile.isClientSide()) {
            return true;
        }
//        rotationSpeed = 0;
//        for (int i = 0; i < 6; i++) {
//            if (isConnected(i)) {
//                sendUsePower(new TLongHashSet(), i, 0, -1);
//            }
//        }
        sendSpeed(0);
        rotationSpeed = 0;
        disconnectAll();
        int wrenchingSide = II_DirUtil.determineWrenchingSide(side, hitX, hitY, hitZ);
        setConnected(wrenchingSide);
        setConnected(getOppositeSide(wrenchingSide));
        updateConnections();
        II_Util.sendChatToPlayer(player, "Connected");
        return true;
    }

    @Override
    public boolean autoConnect() {
        return false;
    }

    @Override
    public long powerUsage(TLongSet passed, int side, int speed) {
        localPoint.setPosition(hostTile);
        if (!isConnected(side) || !passed.add(localPoint.toLong())) {
            return 0;
        }
        int opSide = getOppositeSide(side);
        Tile<?> other = getMetaTileAtSide(hostTile, opSide);
        if (isConnected(side) && other instanceof KineticTile && II_TileUtil.canInteract(side, IOType.Kinetic, other.getHost())) {
            return ((KineticTile) other).powerUsage(passed, side, speed);
        }
        return 0;
    }

    @Override
    public void usePower(TLongSet passed, int side, int speed, double satisfaction) {
        localPoint.setPosition(hostTile);
        if (!isConnected(side) || !passed.add(localPoint.toLong())) {
            return;
        }
        rotationSpeed = speed;
        sendUsePower(passed, side, speed, satisfaction);

    }

    void sendUsePower(TLongSet passed, int side, int speed, double satisfaction) {
        int opSide = getOppositeSide(side);
        Tile<?> other = getMetaTileAtSide(hostTile, opSide);
        if (other instanceof KineticTile && II_TileUtil.canInteract(side, IOType.Kinetic, other.getHost())) {
            ((KineticTile) other).usePower(passed, side, speed, satisfaction);
        }
    }
}
