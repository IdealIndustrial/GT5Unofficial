package idealindustrial.impl.tile.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.impl.world.util.Vector3d;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;
import java.util.stream.Stream;

import static idealindustrial.impl.tile.TileEvents.FACING_MAIN;

/**
 * simple implementation of machine with one facing (wrench right click)
 * textures are 0 - down, 1 - up, 2 - side, 3 - outputFacing, 4 - mainFacing,  +5 for active.
 */
public abstract class TileFacing2Main<BaseTileType extends HostMachineTile> extends TileFacing1Output<BaseTileType> {
     public int mainFacing;

    public TileFacing2Main(BaseTileType baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays == null ? new ITexture[10] : overlays);
    }

    protected TileFacing2Main(BaseTileType baseTile, TileFacing2Main<?> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public ITexture[] provideTexture(boolean active, int side) {
        int index = side == mainFacing ? 4 : side == outputFacing ? 3 : II_DirUtil.directionToSide(side);
        if (active) {
            index += 5;
        }
        return Stream.of(baseTextures[index], overlays[index]).filter(Objects::nonNull).toArray(ITexture[]::new);
    }

    @Override
    public boolean onWrenchClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (hostTile.isClientSide()) {
            return true;
        }
        if (!player.isSneaking()) {
            return super.onWrenchClick(player, item, side, hitX, hitY, hitZ);
        }
        int sideTo = II_DirUtil.determineWrenchingSide(side, hitX, hitY, hitZ);
        if (isValidFacing(sideTo)) {
            mainFacing = sideTo;
            onMainFacingChanged();
            hostTile.sendEvent(FACING_MAIN, sideTo);
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == FACING_MAIN) {
            mainFacing = value;
            hostTile.issueTextureUpdate();
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    protected boolean isValidFacing(int side) {
        return true;
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        super.writeTile(stream);
        stream.writeByte(mainFacing);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        super.readTile(stream);
        mainFacing = stream.readByte();
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
        mainFacing =  nbt.getInteger("mainF");
    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        nbt.setInteger("mainF", mainFacing);
        return super.saveToNBT(nbt);
    }

    protected void onMainFacingChanged() {

    }

    @Override
    public void placedByPlayer(EntityPlayer player) {
        Vector3d playerPos = new Vector3d(player);
        Vector3d blockPos = new Vector3d(getHost());
        playerPos.minusM(blockPos);
        mainFacing = II_DirUtil.getPlacingFace(player.rotationYaw, player.rotationPitch, isValidFacing(0) && isValidFacing(1));
        hostTile.sendEvent(FACING_MAIN, mainFacing);
    }
}
