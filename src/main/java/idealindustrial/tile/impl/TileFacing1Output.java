package idealindustrial.tile.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.api.interfaces.ITexture;
import gregtech.api.util.GT_Utility;
import idealindustrial.tile.IOType;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.util.misc.II_DirUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import static idealindustrial.tile.TileEvents.FACING_OUTPUT;

/**
 * simple implementation of machine with one facing (wrench right click)
 * textures are 0 - down, 1 - up, 2 - side, 3 - outputFacing, +4 for active.
 */
public abstract class TileFacing1Output<H extends HostMachineTile> extends TileMachineBase<H> {
    public int outputFacing;
    List<IntConsumer> outChangeListeners = new ArrayList<>();

    public TileFacing1Output(H hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(hostTile, name, baseTextures, overlays);
    }

    protected TileFacing1Output(H hostTile, TileFacing1Output<?> copyFrom) {
        super(hostTile, copyFrom);
    }


    @Override
    public ITexture[] provideTexture(boolean active, int side) {
        int index = side == outputFacing ? 3 : II_DirUtil.directionToSide(side);
        if (active) {
            index += 4;
        }
        return overlays[index] == null ? new ITexture[]{baseTextures[index]} : new ITexture[]{baseTextures[index], overlays[index]};
    }

    @Override
    public boolean onWrenchClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (hostTile.isClientSide()) {
            return true;
        }
        if (hostTile.isServerSide() && energyHandler != null) {
            GT_Utility.sendChatToPlayer(player, "EU Stored: " + energyHandler.getStored());
            GT_Utility.sendChatToPlayer(player, "Face: " + outputFacing);
        }
        int sideTo = II_DirUtil.determineWrenchingSide(side, hitX, hitY, hitZ);
        if (isValidFacing(sideTo)) {
            outputFacing = sideTo;
            onOutputFacingChanged();
            hostTile.sendEvent(FACING_OUTPUT, sideTo);
            return true;
        }
        return false;
    }

    @Override//test stuff todo: remove
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (hostTile.isServerSide() && energyHandler != null) {
            GT_Utility.sendChatToPlayer(player, "EU Stored: " + energyHandler.getStored());
        }
        return super.onRightClick(player, item, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == FACING_OUTPUT) {
            outputFacing = value;
            hostTile.issueTextureUpdate();
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    protected boolean isValidFacing(int side) {
        return true;
    }

    protected void onOutputFacingChanged() {
        if (getOutputIOType() != null) {
            hostTile.onIOConfigurationChanged(getOutputIOType());
            hostTile.notifyOnIOConfigChange(getOutputIOType());
        }
        for (IntConsumer listener : outChangeListeners) {
            listener.accept(outputFacing);
        }
    }

    protected IOType getOutputIOType() {
        return null;
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        super.writeTile(stream);
        stream.writeByte(outputFacing);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        super.readTile(stream);
        outputFacing = stream.readByte();
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
        outputFacing =  nbt.getInteger("outF");
    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        nbt.setInteger("outF", outputFacing);
        return super.saveToNBT(nbt);
    }

    public void onOutputFacingChanged(IntConsumer listener) {
        outChangeListeners.add(listener);
    }
}
