package idealindustrial.teststuff;

import idealindustrial.blocks.MetaBlock_Item;
import idealindustrial.blocks.base.BaseBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestBlock extends BaseBlock implements ITileEntityProvider {
    public TestBlock() {
        super(MetaBlock_Item.class, "testBlockZBS", Material.iron);

    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        TestTile testTile = new TestTile(p_149915_1_);
        return testTile;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return -1;
    }
}
