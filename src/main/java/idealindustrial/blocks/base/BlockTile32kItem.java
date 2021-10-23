package idealindustrial.blocks.base;

import idealindustrial.blocks.ores.TileOres;
import idealindustrial.tile.interfaces.meta.Tile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockTile32kItem extends ItemBlock {
    public static List<BlockTile32kItem> instances = new ArrayList<>();
    public BlockTile32kItem(Block p_i45328_1_) {
        super(p_i45328_1_);
        setMaxDamage(0);
        setHasSubtypes(true);
        instances.add(this);
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }


    public boolean placeBlockAt(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta) {
        short tDamage = (short) getDamage(aStack);
        if (tDamage >= 0 && ((Tile32kBlock<?>) field_150939_a).canPlayerPlace(aStack, aPlayer, aWorld, aX, aY, aZ, side, hitX, hitY, hitZ, tDamage)) {
            if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, 0, 3)) {
                return false;
            }
            Tile32k tTileEntity = (Tile32k) aWorld.getTileEntity(aX, aY, aZ);
            if (tTileEntity != null) {
                tTileEntity.setValuesFromMeta(tDamage);
            }
        } else if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, tDamage, 3)) {
            return false;
        }
        if (aWorld.getBlock(aX, aY, aZ) == this.field_150939_a) {
            this.field_150939_a.onBlockPlacedBy(aWorld, aX, aY, aZ, aPlayer, aStack);
            this.field_150939_a.onPostBlockPlaced(aWorld, aX, aY, aZ, tDamage);
        }
        return true;
    }

    @Override
    public void registerIcons(IIconRegister register) {
    }

    public String getUnlocalizedName(ItemStack aStack) {
        return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
    }

}
