package idealindustrial.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import idealindustrial.itemgen.implementation.II_MetaGeneratedItem_1;
import idealindustrial.teststuff.testTile.II_TestMachine;
import idealindustrial.teststuff.testTile2.II_TestMachine2;
import idealindustrial.tile.II_Block_Machines;
import idealindustrial.tile.base.II_BaseMachineTileImpl;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;
import idealindustrial.util.misc.II_TileUtil;

public class II_ItemsLoader {

    public void preLoad() {
        new II_MetaGeneratedItem_1();

        II_TileUtil.registerMetaTile(1, new II_TestMachine(II_TileUtil.makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(2, new II_TestMachine2(II_TileUtil.makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(3, new II_MetaConnected_Cable(II_TileUtil.makeBaseTile(), Materials.Tin, 32, 1, 1));
        II_TileUtil.registerMetaTile(4, new II_MetaConnected_Cable(II_TileUtil.makeBaseTile(), Materials.Copper, 128, 1, 1));
        II_TileUtil.registerMetaTile(5, new II_MetaConnected_Cable(II_TileUtil.makeBaseTile(), Materials.Silver, 512, 2, 1));
        GameRegistry.registerTileEntity(II_BaseTileImpl.class, "ii.tile");
        GameRegistry.registerTileEntity(II_BaseMachineTileImpl.class, "ii.machine_tile");
    }
}
