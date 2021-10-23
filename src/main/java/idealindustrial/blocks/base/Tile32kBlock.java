package idealindustrial.blocks.base;

import idealindustrial.render.GT_Renderer_Block;
import idealindustrial.render.IFastRenderedTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class Tile32kBlock<T extends TileEntity & Tile32k & IFastRenderedTileEntity> extends BaseBlock implements ITileEntityProvider {
    protected T cachedTile;

    protected Tile32kBlock(Class<? extends ItemBlock> itemClass, String unlocalizedName, Material material) {
        super(itemClass, unlocalizedName, material);
        try {
            this.cachedTile = getTileClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot instantiate cached tiel for 32k block" + unlocalizedName, e);
        }
    }

    protected Tile32kBlock(String unlocalizedName, Material material) {
        this(BlockTile32kItem.class, unlocalizedName, material);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        try {
            return getTileClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot instantiate cached tiel for 32k block" + getUnlocalizedName(), e);
        }
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

    public String getUnlocalizedName() {
        return this.mUnlocalizedName;
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getDamageValue(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && getTileClass().isAssignableFrom(te.getClass())) {
            return getMeta((T) te);
        }
        return super.getDamageValue(world, x, y, z);
    }

    protected int getMeta(T tileEntity) {
        return tileEntity.getMeta();
    }

    protected abstract Class<T> getTileClass();

    public T getCachedTile() {
        return cachedTile;
    }

    public boolean canPlayerPlace(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta ) {
        return true;
    }

}
