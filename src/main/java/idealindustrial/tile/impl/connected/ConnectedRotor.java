package idealindustrial.tile.impl.connected;

import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.tile.IOType;
import idealindustrial.tile.host.HostPipeTileRotatingImpl;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.host.HostTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.kinetic.KUPassThrough;
import idealindustrial.util.energy.kinetic.KUSplitter;
import idealindustrial.util.energy.kinetic.KineticEnergyHandler;
import idealindustrial.util.energy.kinetic.system.KineticSystem;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

import static idealindustrial.tile.TileEvents.ROTATION_SPEED;
import static idealindustrial.tile.TileEvents.ROTATION_SPEED_DIRECT;

public class ConnectedRotor extends ConnectedBase<HostTile> implements KUPassThrough {
    public KineticSystem system;
    int rotationSpeed;

    public ConnectedRotor(HostTile hostTile, II_Material material, Prefixes prefix, float thickness) {
        super(hostTile, EngLocalizer.getInstance().get(material, prefix),
                new GT_RenderedTexture(Materials.Iron.mIconSet.mTextures[TextureSet.INDEX_wire], material.getSolidRenderInfo().getColorAsArray()).maxBrightness(),
                new GT_RenderedTexture(Materials.Iron.mIconSet.mTextures[TextureSet.INDEX_wire], material.getSolidRenderInfo().getColorAsArray()).maxBrightness());
        this.thickness = thickness;
    }


    private ConnectedRotor(HostTile hostTile, String name, ITexture textureInactive, ITexture textureActive, float thickness) {
        super(hostTile, name, textureInactive, textureActive);
        this.thickness = thickness;
    }

    @Override
    public boolean canConnect(int side) {
        if (hostTile.isClientSide()) {
            return false;
        }
        Tile<?> tile = II_TileUtil.getMetaTileAtSide(hostTile, side);
        if (tile == null) {
            return false;
        }
        int connections = connectionCount();
        int oppositeSide = II_DirUtil.getOppositeSide(side);


        if (tile instanceof ConnectedRotor) {
            ConnectedRotor rotor = (ConnectedRotor) tile;
            if (rotor.isConnected(oppositeSide)) {
                return true;
            }
            if (connections != 0 && (connections != 1 || !isConnected(oppositeSide))) {
                return false;
            }
            int otherConnections = rotor.connectionCount();
            if (otherConnections == 0) {
                return true;
            }


            if (otherConnections == 1 || otherConnections == 2) {
                return rotor.isConnected(oppositeSide) || rotor.isConnected(side);
            }
            return false;

        }
        HostTile hostTile = tile.getHost();
        if (hostTile instanceof HostMachineTile && tile.hasKineticEnergy()) {
            boolean[] io = ((HostMachineTile) hostTile).getIO(IOType.Kinetic);
            boolean result = io[oppositeSide] || io[oppositeSide + 6];
            if (!result) {
                return false;
            }
            if (connections == 0 || connections == 1 && isConnected(oppositeSide)) {
                KineticEnergyHandler handler = ((HostMachineTile) hostTile).getKineticEnergyHandler();
                if (handler.getProducer(oppositeSide) != null) {
                    handler.getProducer(oppositeSide).onConnectionAppended();
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public ConnectedRotor newMetaTile(HostTile hostTile) {
        return new ConnectedRotor(hostTile, name, textureInactive, textureActive, thickness);
    }

    @Override
    public void onConnectionUpdate() {
        if (hostTile.isServerSide() && system != null) {
            system.invalidate();
        }
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {

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

    public void onSystemInvalidate() {
        system = null;
        hostTile.sendEvent(ROTATION_SPEED_DIRECT, 0);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        if (system != null) {
            system.invalidate();
        }
    }

    @Override
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
//        GT_Utility.sendChatToPlayer(player, "   "+ system);
        return super.onRightClick(player, item, side, hitX, hitY, hitZ);
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public void sendRotationSpeed(int speed) {
        hostTile.sendEvent(ROTATION_SPEED, speed);
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == ROTATION_SPEED) {
            setSpeed(new HashSet<>(), value, hostTile);
            return true;
        }
        if (id == ROTATION_SPEED_DIRECT) {
            rotationSpeed = 0;
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    public void setSpeed(Set<KUPassThrough> alreadyPassedSet, int speed, HostTile hostTile) {
        rotationSpeed = speed;
        KUPassThrough.super.setSpeed(alreadyPassedSet, speed, hostTile);
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
    public void receiveNeighbourIOConfigChange(IOType type) {
        if (type.is(IOType.Kinetic)) {
            updateConnections();
        }
    }

    @Override
    public void setSystem(KineticSystem system) {
        this.system = system;
    }
}
