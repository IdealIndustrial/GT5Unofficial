package idealindustrial.tile.ores;

import idealindustrial.II_Core;
import idealindustrial.blocks.base.BaseBlock;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.render.GT_Renderer_Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockOres extends BaseBlock implements ITileEntityProvider {


    public BlockOres() {
        super(ItemOres.class, "block.ores", Material.rock);
        setCreativeTab(II_Core.II_MATERIAL_TAB);
    }

    @Override
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_) {
        return super.getIcon(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_, p_149673_5_);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileOres();
    }


    public boolean hasTileEntity(int aMeta) {
        return true;
    }


    public int getRenderType() {
        if (GT_Renderer_Block.INSTANCE == null) {
            return super.getRenderType();
        }
        return GT_Renderer_Block.INSTANCE.mRenderID;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.stone.getIcon(side, meta);
    }

    public String getUnlocalizedName() {
        return this.mUnlocalizedName;
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileOres) {
            return ((TileOres) te).getMeta();
        }
        return super.getDamageValue(world, x, y, z);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return super.getPickBlock(target, world, x, y, z, player);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        Prefixes[] prefixes = new Prefixes[]{Prefixes.ore, Prefixes.oreSmall};
        for (II_Material material : II_Materials.allMaterials) {
            for (Prefixes prefix : prefixes) {
                if (material.isEnabled(prefix)) {
                    list.add(new ItemStack(item, 1, TileOres.getMeta(material, prefix)));
                }
            }
        }
    }

    //todo remove, x-ray fix for debug
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return true;
    }
}
