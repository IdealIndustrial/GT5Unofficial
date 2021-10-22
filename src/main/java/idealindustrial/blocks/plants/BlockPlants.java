package idealindustrial.blocks.plants;

import idealindustrial.II_Core;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.blocks.base.Tile32kBlock;
import idealindustrial.blocks.ores.TileOres;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockPlants extends Tile32kBlock<TilePlants> {


    public BlockPlants() {
        super("block.plants", Material.grass);
        setCreativeTab(II_Core.II_MATERIAL_TAB);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.grass.getIcon(side, meta);
    }


    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean getTickRandomly() {
        return true;
    }

    @Override
    public int tickRate(World p_149738_1_) {
        return Plants.TICK_RATE;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null) {
            te.updateEntity();
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TilePlants) {
            return ((TilePlants) te).getDrops();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z);
    }

    @Override
    protected Class<TilePlants> getTileClass() {
        return TilePlants.class;
    }
}
