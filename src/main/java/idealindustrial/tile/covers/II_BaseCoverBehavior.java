package idealindustrial.tile.covers;

import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface II_BaseCoverBehavior<BaseTileType extends II_BaseTile> {

    long update(long var, int side, BaseTileType tile);

    long getTickRate();

    ITexture getTexture();

    boolean onRightClick(long var, int side, BaseTileType tile, EntityPlayer player, ItemStack is, float hitX, float hitY, float hitZ);




}
