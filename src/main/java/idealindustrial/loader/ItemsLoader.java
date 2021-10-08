package idealindustrial.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ITexture;
import idealindustrial.II_Core;
import idealindustrial.autogen.implementation.MetaGeneratedItem_1;
import idealindustrial.autogen.implementation.behaviors.BehaviorGuideRenderer;
import idealindustrial.autogen.items.MetaBehaviorItem;
import idealindustrial.recipe.RecipeMaps;
import idealindustrial.teststuff.testTile.TestMachine;
import idealindustrial.teststuff.testTile2.TestMachine2;
import idealindustrial.teststuff.testmulti.TestMultiMachine1;
import idealindustrial.textures.TextureUtil;
import idealindustrial.textures.Textures;
import idealindustrial.tile.host.HostMachineTileImpl;
import idealindustrial.tile.host.HostPipeTileImpl;
import idealindustrial.tile.host.HostTileImpl;
import idealindustrial.tile.impl.connected.ConnectedCable;
import idealindustrial.tile.impl.multi.parts.Hatch_Item;
import idealindustrial.tile.impl.recipe.TileMachineRecipe;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.parameter.RecipedMachineStats;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

import static idealindustrial.util.misc.II_TileUtil.makeBaseMachineTile;
import static idealindustrial.util.misc.II_TileUtil.makeBaseTile;

public class ItemsLoader {

    MetaBehaviorItem behaviorItem1;

    public void preLoad() {
        new MetaGeneratedItem_1();
        registerMetaItems();



    }

    protected void registerMetaItems() {
        behaviorItem1 = new MetaBehaviorItem("item1");
        behaviorItem1.setCreativeTab(II_Core.II_MAIN_TAB);
        behaviorItem1.registerItem(1, "Test", null);
        behaviorItem1.registerItem(2, "Guide Renderer", new BehaviorGuideRenderer());
    }
}
