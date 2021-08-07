package idealindustrial.tile.meta;

import gregtech.api.interfaces.ITexture;
import gregtech.api.util.GT_Utility;
import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.util.energy.II_OutputFacingEnergyHandler;
import idealindustrial.util.misc.II_DirUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import static idealindustrial.tile.II_TileEvents.FACING_OUTPUT;

/**
 * simple implementation of machine with one facing (wrench right click)
 * textures are 0 - down, 1 - up, 2 - side, 3 - outputFacing, +4 for active.
 */
public abstract class II_BaseMetaTile_Facing1Output<B extends II_BaseMachineTile> extends II_BaseMetaTileMachine<B> {
    public int outputFacing;

    public II_BaseMetaTile_Facing1Output(B baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
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
        if (baseTile.isClientSide()) {
            return true;
        }
        if (baseTile.isServerSide() && energyHandler != null) {
            GT_Utility.sendChatToPlayer(player, "EU Stored: " + energyHandler.stored);
            GT_Utility.sendChatToPlayer(player, "Face: " + outputFacing);
        }
        int sideTo = II_DirUtil.determineWrenchingSide(side, hitX, hitY, hitZ);
        if (isValidFacing(sideTo)) {
            outputFacing = sideTo;
            onOutputFacingChanged();
            baseTile.sendEvent(FACING_OUTPUT, sideTo);
            return true;
        }
        return false;
    }

    @Override//test stuff todo: remove
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (baseTile.isServerSide() && energyHandler != null) {
            GT_Utility.sendChatToPlayer(player, "EU Stored: " + energyHandler.stored);
        }
        return super.onRightClick(player, item, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == FACING_OUTPUT) {
            outputFacing = value;
            baseTile.issueTextureUpdate();
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    protected boolean isValidFacing(int side) {
        return true;
    }

    protected void onOutputFacingChanged() {

    }

}
