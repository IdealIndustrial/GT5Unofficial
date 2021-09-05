package idealindustrial.tile.covers.implementations;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.IOType;
import idealindustrial.tile.base.PacketCover;
import idealindustrial.tile.covers.BaseCoverBehavior;
import idealindustrial.tile.interfaces.base.BaseTile;
import idealindustrial.util.misc.II_Math;
import idealindustrial.util.misc.II_Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class CoverChameleon implements BaseCoverBehavior<BaseTile> {

    protected static final ITexture defaultTexture = new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_FRONT_ACTIVE);
    @Override
    public ITexture getTexture(long var, int side, BaseTile tile) {
        if (var == 0) {
            return defaultTexture;
        }
        int id = II_Util.intAFromLong(var);
        int meta = II_Util.intBFromLong(var);
        Block block = Block.getBlockById(id);
        return new GT_CopiedBlockTexture(block, side, meta);
    }

    @Override
    public boolean onRightClick(long var, int side, BaseTile tile, EntityPlayer player, ItemStack is, float hitX, float hitY, float hitZ) {
        if (tile.isServerSide() && is != null && is.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock) is.getItem()).field_150939_a;
            int id = Block.getIdFromBlock(block);
            int meta = II_Math.clamp(is.getItemDamage(), 0, 15);
            long newVar =  II_Util.intsToLong(id, meta);
            tile.setCoverVarAtSide(side, newVar);
            GT_Values.NW.sendPacketToAllPlayersInRange(tile.getWorld(),
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
