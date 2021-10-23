package idealindustrial.blocks.plants;

import idealindustrial.II_Core;
import idealindustrial.blocks.base.Tile32kBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockPlants extends Tile32kBlock<TilePlants> {
    public static BlockPlants INSTANCE;

    public BlockPlants() {
        super("block.plants", Material.plants);
        setCreativeTab(II_Core.II_MATERIAL_TAB);
        INSTANCE = this;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.grass.getIcon(side, meta);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        //todo remove, debug feature
        for (int i = 0; i < 1000; i++) {
            if (Plants.get(i) != null) {
                list.add(new ItemStack(item, 1, i));
            }
        }
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
        Block under = world.getBlock(x, y - 1, z);
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z) && (under == Blocks.grass || under == Blocks.dirt);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TilePlants) {
            PlantDef def = ((TilePlants) te).def;
            if (def != null && def.allowedSoil.apply(world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z))) {
                return;
            }
        }
        dropBlockAsItem(world, x, y, z, 0, 0);
        world.setBlock(x, y, z, Blocks.air);
    }

    @Override
    public boolean canPlayerPlace(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta) {
        PlantDef def = Plants.get(aMeta);
        if (def != null) {
            return def.allowedSoil.apply(aWorld.getBlock(aX, aY - 1, aZ), aWorld.getBlockMetadata(aX, aY - 1, aZ));
        }
        return super.canPlayerPlace(aStack, aPlayer, aWorld, aX, aY, aZ, side, hitX, hitY, hitZ, aMeta);
    }

    @Override
    protected Class<TilePlants> getTileClass() {
        return TilePlants.class;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        return 0;
    }

    @Override
    public int getLightValue() {
        return super.getLightValue();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
