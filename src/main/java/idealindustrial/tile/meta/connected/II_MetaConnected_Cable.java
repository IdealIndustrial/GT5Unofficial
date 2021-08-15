package idealindustrial.tile.meta.connected;

import gregtech.GT_Mod;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Client;
import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.tile.interfaces.meta.II_MetaTile;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.energy.system.II_CableSystem;
import idealindustrial.util.energy.system.IInfoEnergyPassThrough;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class II_MetaConnected_Cable extends II_BaseMetaConnected<II_BaseTile> {
    public II_CableSystem system;
    protected long voltage, amperage, loss;

    public II_MetaConnected_Cable(II_BaseTile baseTile, Materials material, long voltage, long amperage, long loss, float thickness) {
        super(baseTile, new GT_RenderedTexture(material.mIconSet.mTextures[TextureSet.INDEX_wire], material.mRGBa),
                new GT_RenderedTexture(material.mIconSet.mTextures[TextureSet.INDEX_wire]));
        this.voltage = voltage;
        this.amperage = amperage;
        this.loss = loss;
        this.thickness = thickness;
    }


    private II_MetaConnected_Cable(II_BaseTile baseTile, ITexture textureInactive, ITexture textureActive, long voltage, long amperage, long loss, float thickness) {
        super(baseTile, textureInactive, textureActive);
        this.voltage = voltage;
        this.amperage = amperage;
        this.loss = loss;
        this.thickness = thickness;
    }

    @Override
    public boolean canConnect(int side) {
        if (baseTile.isClientSide()) {
            return false;
        }
        II_MetaTile<?> metaTile = II_TileUtil.getMetaTileAtSide(baseTile, side);
        if (metaTile == null) {
            return false;
        }
        if (metaTile instanceof II_MetaConnected_Cable) {
            return true;
        }
        II_BaseTile baseTile = metaTile.getBase();
        if (baseTile instanceof II_BaseMachineTile && metaTile.hasEnergy()) {
            int opSide = II_DirUtil.getOppositeSide(side);
            II_EnergyHandler handler = ((II_BaseMachineTile) baseTile).getEnergyHandler();
            return handler.getConsumer(opSide) != null || handler.getProducer(opSide) != null;
        }
        return false;
    }


    @Override
    public II_MetaConnected_Cable newMetaTile(II_BaseTile baseTile) {
        return new II_MetaConnected_Cable(baseTile, textureInactive, textureActive, voltage, amperage, loss, thickness);
    }

    @Override
    public void onConnectionUpdate() {
        if (baseTile.isServerSide() && system != null) {
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
        return baseTile.equals(((II_MetaConnected_Cable) o).baseTile);
    }

    @Override
    public int hashCode() {
        return baseTile.hashCode();
    }

    //checks V and A and burns cable if necessary
    public void checkEnergy(long voltage, long amperage) {
        if (voltage > this.voltage || amperage > this.amperage) {
            burnCable();
        }
    }

    protected void burnCable() {
        baseTile.getWorld().setBlock(getBase().getXCoord(), getBase().getYCoord(), getBase().getZCoord(), Blocks.fire);
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
        if (baseTile.isClientSide()) {
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
