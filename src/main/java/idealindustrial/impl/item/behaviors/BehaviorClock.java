package idealindustrial.impl.item.behaviors;

import idealindustrial.api.tile.host.HostTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.blocks.ores.TileOres;
import idealindustrial.impl.blocks.plants.TilePlants;
import idealindustrial.api.items.IItemBehavior;
import idealindustrial.impl.tile.impl.multi.RecipedMultiMachineBase;
import idealindustrial.impl.tile.impl.multi.struct.IGuideRenderer;
import idealindustrial.impl.tile.impl.recipe.TileMachineReciped;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BehaviorClock implements IItemBehavior, IGuideRenderer {

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null) {
                te.updateEntity();
            }
            if (te instanceof TilePlants) {
                ((TilePlants) te).getPlantInfo().forEach(s -> II_Util.sendChatToPlayer(player, s));
            }
            if (te instanceof TileOres) {
                II_Util.sendChatToPlayer(player, "Is ore");
            }
            if (te instanceof HostTile) {
                Tile<?> tile = ((HostTile) te).getMetaTile();
                if (tile instanceof TileMachineReciped) {
                    II_Util.sendChatToPlayer(player, "Progress: " + ((TileMachineReciped<?, ?>) tile).getProgress() + " / 20");
                }

                if (tile instanceof RecipedMultiMachineBase) {
                    II_Util.sendChatToPlayer(player, "Progress: " +((RecipedMultiMachineBase<?, ?>) tile).getProgress()+ " / 20");
                }
            }
//            TileOres.replaceBlock(world, x, y, z, TileOres.getMeta(II_Materials.iron, Prefixes.ore));
        }
        return false;
    }

    @Override
    public void registerNewParticle(EntityFX particle) {

    }

    @Override
    public int getMaxAge() {
        return 100;
    }

    @Override
    public boolean isXInverted() {
        return false;
    }

    @Override
    public boolean isZInverted() {
        return false;
    }
}
