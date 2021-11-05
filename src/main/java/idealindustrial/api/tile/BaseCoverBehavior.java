package idealindustrial.api.tile;

import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.tile.IOType;
import idealindustrial.api.tile.host.HostTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface BaseCoverBehavior<BaseTileType extends HostTile> {

    default long update(long var, int side, BaseTileType tile) {
        return var;
    }

    default long getTickRate() {
        return -1;
    }

    ITexture getTexture(long var, int side, BaseTileType tile);

    default boolean onRightClick(long var, int side, BaseTileType tile, EntityPlayer player, ItemStack is, float hitX, float hitY, float hitZ) {
        return true;
    }

    default boolean getIO(IOType type, boolean input) {
        return true;
    }




}
