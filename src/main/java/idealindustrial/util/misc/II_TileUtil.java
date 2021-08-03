package idealindustrial.util.misc;

import idealindustrial.II_Values;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tile.meta.II_MetaTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class II_TileUtil {

    public static void registerMetaTile(int id, II_MetaTile metaTile) {
        II_Values.metaTiles[id] = metaTile;
        metaTile.getBase().setMetaTileID(id);
    }

    public static II_BaseTile makeBaseTile() {
        return new II_BaseTileImpl();
    }

    public static II_MetaTile getMetaTile(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof II_BaseTile) {
            return ((II_BaseTile) tile).getMetaTile();
        }
        return null;
    }

    public static II_MetaTile getMetaTileAtSide(II_BaseTile tile, int side) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        return getMetaTile(tile.getWorld(), tile.getXCoord() + dir.offsetX, tile.getYCoord() + dir.offsetY, tile.getZCoord() + dir.offsetZ);
    }
}
