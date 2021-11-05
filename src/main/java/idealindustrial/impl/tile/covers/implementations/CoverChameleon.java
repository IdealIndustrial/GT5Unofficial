package idealindustrial.impl.tile.covers.implementations;

import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.textures.Textures;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.host.PacketCover;
import idealindustrial.api.tile.BaseCoverBehavior;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.util.misc.II_Math;
import idealindustrial.util.misc.II_Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import static idealindustrial.impl.net.Network.NW;

public class CoverChameleon implements BaseCoverBehavior<HostTile> {

    protected static final ITexture defaultTexture = Textures.input;
    @Override
    public ITexture getTexture(long var, int side, HostTile tile) {
        if (var == 0) {
            return defaultTexture;
        }
        int id = II_Util.intAFromLong(var);
        int meta = II_Util.intBFromLong(var);
        Block block = Block.getBlockById(id);
        return defaultTexture;
    }

    @Override
    public boolean onRightClick(long var, int side, HostTile tile, EntityPlayer player, ItemStack is, float hitX, float hitY, float hitZ) {
        if (tile.isServerSide() && is != null && is.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock) is.getItem()).field_150939_a;
            int id = Block.getIdFromBlock(block);
            int meta = II_Math.clamp(is.getItemDamage(), 0, 15);
            long newVar =  II_Util.intsToLong(id, meta);
            tile.setCoverVarAtSide(side, newVar);
            NW.sendPacketToAllPlayersInRange(tile.getWorld(),
                    new PacketCover(tile, side, tile.getCoverIDAtSide(side), newVar),
                    tile.getXCoord(), tile.getZCoord());
            return true;
        }
        return false;
    }

    @Override
    public boolean getIO(IOType type, boolean input) {
        return false;
    }
}
