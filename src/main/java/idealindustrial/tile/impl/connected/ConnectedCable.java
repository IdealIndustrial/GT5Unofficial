package idealindustrial.tile.impl.connected;

import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.tile.IOType;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.host.HostTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.electric.EnergyHandler;
import idealindustrial.util.energy.electric.system.CableSystem;
import idealindustrial.util.energy.electric.system.IInfoEnergyPassThrough;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ConnectedCable extends ConnectedBase<HostTile> {
    public CableSystem system;
    protected long voltage, amperage, loss;

    public ConnectedCable(HostTile hostTile, II_Material material, Prefixes prefix, long voltage, long amperage, long loss, float thickness) {
        super(hostTile, EngLocalizer.getInstance().get(material, prefix),
                new GT_RenderedTexture(Materials.Iron.mIconSet.mTextures[TextureSet.INDEX_wire], material.getSolidRenderInfo().getColorAsArray()),
                new GT_RenderedTexture(Materials.Iron.mIconSet.mTextures[TextureSet.INDEX_wire], material.getSolidRenderInfo().getColorAsArray()));
        this.voltage = voltage;
        this.amperage = amperage;
        this.loss = loss;
        this.thickness = thickness;
    }


    private ConnectedCable(HostTile hostTile, String name, ITexture textureInactive, ITexture textureActive, long voltage, long amperage, long loss, float thickness) {
        super(hostTile, name, textureInactive, textureActive);
        this.voltage = voltage;
        this.amperage = amperage;
        this.loss = loss;
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
        if (tile instanceof ConnectedCable) {
            return true;
        }
        HostTile hostTile = tile.getHost();
        if (hostTile instanceof HostMachineTile && tile.hasEnergy()) {
            int opSide = II_DirUtil.getOppositeSide(side);
            boolean[] io = ((HostMachineTile) hostTile).getIO(IOType.ENERGY);
            boolean result =  io[opSide] || io[opSide + 6];
            EnergyHandler handler = ((HostMachineTile) hostTile).getEnergyHandler();
            if (result && handler.getProducer(opSide) != null) {
                handler.getProducer(opSide).onConnectionAppended();
            }
            return true;
        }
        return false;
    }


    @Override
    public ConnectedCable newMetaTile(HostTile hostTile) {
        return new ConnectedCable(hostTile, name, textureInactive, textureActive, voltage, amperage, loss, thickness);
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

    public long getLoss() {
        return loss;
    }

    public long getVoltage() {
        return voltage;
    }

    public long getAmperage() {
        return amperage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return hostTile.equals(((ConnectedCable) o).hostTile);
    }

    @Override
    public int hashCode() {
        return hostTile.hashCode();
    }

    //checks V and A and burns cable if necessary
    public void checkEnergy(long voltage, long amperage) {
        if (voltage > this.voltage || amperage > this.amperage) {
            burnCable();
        }
    }

    protected void burnCable() {
        hostTile.getWorld().setBlock(getHost().getXCoord(), getHost().getYCoord(), getHost().getZCoord(), Blocks.fire);
    }

    public void onSystemInvalidate() {
        system = null;
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
    }

    @Override
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (hostTile.isClientSide()) {
            return true;
        }
        GT_Utility.sendChatToPlayer(player, "system: " + system);
        if (system != null) {
            IInfoEnergyPassThrough info = system.getInfo(this);
            if (info != null) {//debug check, should always be true
                GT_Utility.sendChatToPlayer(player, "Calculating Voltage and Amperage");
                system.submitTask(20, player, info);
            }
        }
        return super.onRightClick(player, item, side, hitX, hitY, hitZ);
    }

    public IInfoEnergyPassThrough getInfo() {
        if (system == null) {
            return null;
        }
        return system.getInfo(this);
    }

}
