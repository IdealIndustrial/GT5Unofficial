package idealindustrial.tile.covers.implementations;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.textures.CopiedIconContainer;
import idealindustrial.tile.IOType;
import idealindustrial.tile.base.II_PacketCover;
import idealindustrial.tile.covers.II_BaseCoverBehavior;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.util.misc.II_Math;
import idealindustrial.util.misc.II_Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class II_CoverChameleon implements II_BaseCoverBehavior<II_BaseTile> {

    protected static final ITexture defaultTexture = new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_FRONT_ACTIVE);
    @Override
    public ITexture getTexture(long var, int side, II_BaseTile tile) {
        if (var == 0) {
            return defaultTexture;
        }
        int id = II_Util.intAFromLong(var);
        int meta = II_Util.intBFromLong(var);
        Block block = Block.getBlockById(id);
        return new GT_CopiedBlockTexture(block, side, meta);
    }

    @Override
    public boolean onRightClick(long var, int side, II_BaseTile tile, EntityPlayer player, ItemStack is, float hitX, float hitY, float hitZ) {
        if (tile.isServerSide() && is != null && is.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock) is.getItem()).field_150939_a;
            int id = Block.getIdFromBlock(block);
            int meta = II_Math.clamp(is.getItemDamage(), 0, 15);
            long newVar =  II_Util.intsToLong(id, meta);
            tile.setCoverVarAtSide(side, newVar);
            GT_Values.NW.sendPacketToAllPlayersInRange(tile.getWorld(),
                    new II_PacketCover(tile, side, tile.getCoverIDAtSide(side), newVar),
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
