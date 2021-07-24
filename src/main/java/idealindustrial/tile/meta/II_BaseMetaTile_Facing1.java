package idealindustrial.tile.meta;

import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.util.misc.II_DirUtil;

/**
 * simple implementation of machine with one facing (wrench right click)
 * textures are 0 - down, 1 - up, 2 - side, 3 - facing, +4 for active.
 */
public abstract class II_BaseMetaTile_Facing1 extends II_BaseMetaTile {

    protected int facing;

    public II_BaseMetaTile_Facing1(II_BaseTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
    }

    public II_BaseMetaTile_Facing1(II_BaseTile baseTile) {
        super(baseTile);
    }

    @Override
    public ITexture[] provideTexture(boolean active, int side) {
        int index = side == facing ? 3 : II_DirUtil.directionToSide(side);
        if (active) {
            index += 4;
        }
        return overlays[index] == null ? new ITexture[]{baseTextures[index]} : new ITexture[]{baseTextures[index], overlays[index]};
    }
}
