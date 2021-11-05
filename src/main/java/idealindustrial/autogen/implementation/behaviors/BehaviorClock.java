package idealindustrial.autogen.implementation.behaviors;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.blocks.ores.TileOres;
import idealindustrial.blocks.plants.TilePlants;
import idealindustrial.items.IItemBehavior;
import idealindustrial.tile.impl.multi.struct.IGuideRenderer;
import idealindustrial.util.misc.II_Util;
import idealindustrial.util.nbt.NBTFieldBool;
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
}
