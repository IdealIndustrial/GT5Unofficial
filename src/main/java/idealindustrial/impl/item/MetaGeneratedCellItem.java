package idealindustrial.impl.item;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.impl.loader.ItemsLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

import static idealindustrial.util.misc.MiscValues.cellToStateMap;

public class MetaGeneratedCellItem extends MetaGeneratedItem {

    public static final Prefixes[] prefixes = new Prefixes[]{Prefixes.cell, Prefixes.gasCell, Prefixes.plasmaCell};
    public static ItemStack cell = ItemsLoader.cell;
    public MetaGeneratedCellItem() {
        super("metagenerated.cells", II_Materials.materialsK1, prefixes);
        foreachEnabled(i -> {
            Fluid fluid = material(i).getLiquidInfo().get(cellToStateMap.get(prefix(i))).getFluid();
            assert fluid != null;
            FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(this, 1, i), cell);
        });
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        int damage = is.getItemDamage();
        MatterState state = cellToStateMap.get(prefix(damage));
        II_Material material = material(damage);
        if (material == null || state == null || material.getLiquidInfo().get(state) == null || material.getLiquidInfo().get(state).getBlock() == null) {
            return super.onItemUse(is, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
        }
        Block blockLiquid = material.getLiquidInfo().get(state).getBlock();
        Block block = world.getBlock(x, y, z);

        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            p_77648_7_ = 1;
        }
        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z))
        {
            if (p_77648_7_ == 0)
            {
                --y;
            }

            if (p_77648_7_ == 1)
            {
                ++y;
            }

            if (p_77648_7_ == 2)
            {
                --z;
            }

            if (p_77648_7_ == 3)
            {
                ++z;
            }

            if (p_77648_7_ == 4)
            {
                --x;
            }

            if (p_77648_7_ == 5)
            {
                ++x;
            }
        }

        if (is.stackSize == 0)
        {
            return false;
        }
        else if (!player.canPlayerEdit(x, y, z, p_77648_7_, is))
        {
            return false;
        }
        else if (y == 255 && blockLiquid.getMaterial().isSolid())
        {
            return false;
        }
        else if (world.canPlaceEntityOnSide(blockLiquid, x, y, z, false, p_77648_7_, player, is))
        {
            int i1 = this.getMetadata(is.getItemDamage());
            int j1 = blockLiquid.onBlockPlaced(world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, i1);

            if (placeBlockAt(blockLiquid, is, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0))
            {
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), blockLiquid.stepSound.func_150496_b(), (blockLiquid.stepSound.getVolume() + 1.0F) / 2.0F, blockLiquid.stepSound.getPitch() * 0.8F);
                --is.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean placeBlockAt(Block block, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {

        if (!world.setBlock(x, y, z, block, metadata, 3))
        {
            return false;
        }

        if (world.getBlock(x, y, z) == block)
        {
            block.onBlockPlacedBy(world, x, y, z, player, stack);
            block.onPostBlockPlaced(world, x, y, z, metadata);
        }

        return true;
    }
}
