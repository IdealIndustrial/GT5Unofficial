package idealindustrial.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.itemgen.implementation.II_MetaGeneratedItem_1;
import idealindustrial.teststuff.testTile.II_TestMachine;
import idealindustrial.tile.II_Block_Machines;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.util.misc.II_TileUtil;

public class II_ItemsLoader {

    public void preLoad() {
        new II_MetaGeneratedItem_1();
        new II_Block_Machines();
        II_TileUtil.registerMetaTile(1, new II_TestMachine(II_TileUtil.makeBaseTile()));
        GameRegistry.registerTileEntity(II_BaseTileImpl.class, "ii.tile");
    }
}
