package idealindustrial.tile.covers;

import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.base.II_BaseTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface II_CoverBehavior {

    long update(long var, int side, II_BaseTile tile);

    int getTickRate();

    ITexture getTexture();

    boolean onRightClick(long var, int side, II_BaseTile tile, EntityPlayer player, ItemStack is, float hitX, float hitY, float hitZ);




}
