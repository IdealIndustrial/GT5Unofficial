package idealindustrial.tile.meta;

import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.util.misc.II_DirUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Objects;
import java.util.stream.Stream;

import static idealindustrial.tile.II_TileEvents.FACING_MAIN;

/**
 * simple implementation of machine with one facing (wrench right click)
 * textures are 0 - down, 1 - up, 2 - side, 3 - outputFacing, 4 - mainFacing,  +5 for active.
 */
public abstract class II_BaseMetaTile_Facing2Main extends II_BaseMetaTile_Facing1Output {
     public int mainFacing;

    public II_BaseMetaTile_Facing2Main(II_BaseTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays == null ? new ITexture[10] : overlays);
    }

    public II_BaseMetaTile_Facing2Main(II_BaseTile baseTile) {
        super(baseTile);
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
        if (baseTile.isClientSide()) {
            return true;
        }
        if (!player.isSneaking()) {
            return super.onWrenchClick(player, item, side, hitX, hitY, hitZ);
        }
        int sideTo = II_DirUtil.determineWrenchingSide(side, hitX, hitY, hitZ);
        if (isValidFacing(sideTo)) {
            mainFacing = sideTo;
            baseTile.sendEvent(FACING_MAIN, sideTo);
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == FACING_MAIN) {
            mainFacing = value;
            baseTile.issueTextureUpdate();
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    protected boolean isValidFacing(int side) {
        return true;
    }
}
