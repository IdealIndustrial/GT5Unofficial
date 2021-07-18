package idealindustrial.teststuff;

import idealindustrial.itemgen.blocks.II_MetaBlock_Item;
import idealindustrial.itemgen.blocks.base.II_Base_Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestBlock extends II_Base_Block implements ITileEntityProvider {
    public TestBlock() {
        super(II_MetaBlock_Item.class, "testBlockZBS", Material.iron);

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
