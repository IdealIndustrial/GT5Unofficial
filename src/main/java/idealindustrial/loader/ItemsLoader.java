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
import idealindustrial.tile.base.BaseMachineTileImpl;
import idealindustrial.tile.base.BaseTileImpl;
import idealindustrial.tile.base.BasePipeTileImpl;
import idealindustrial.tile.meta.connected.MetaConnected_Cable;
import idealindustrial.tile.meta.recipe.MetaTileMachineRecipe;
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

        II_TileUtil.registerMetaTile(1, new TestMachine(makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(2, new TestMachine2(makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(3, new MetaConnected_Cable(makeBaseTile(), Materials.Tin, 32, 1, 1, 0.5f));
        II_TileUtil.registerMetaTile(4, new MetaConnected_Cable(makeBaseTile(), Materials.Copper, 128, 1, 1, 0.3f));
        II_TileUtil.registerMetaTile(5, new MetaConnected_Cable(makeBaseTile(), Materials.Silver, 512, 2, 1, 0.8f));
        II_TileUtil.registerMetaTile(6, new MetaTileMachineRecipe(makeBaseMachineTile(),"bender",
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[1], new ITexture[10]),
                TextureUtil.loadTextures(TextureUtil.facing2Configuration, II_Paths.PATH_RECIPE_MACHINE_TEXTURES + "testmachine/"),
                RecipeMaps.benderRecipes,
                new RecipedMachineStats(1,1, 1, 64, 0, 0, 0, 1, 10_000)));
        II_TileUtil.registerMetaTile(7, new TestMultiMachine1(makeBaseMachineTile()));
        GameRegistry.registerTileEntity(BaseTileImpl.class, "ii.tile");
        GameRegistry.registerTileEntity(BaseMachineTileImpl.class, "ii.machine_tile");
        GameRegistry.registerTileEntity(BasePipeTileImpl.class, "ii.pipe_tile");


    }

    protected void registerMetaItems() {
        behaviorItem1 = new MetaBehaviorItem("item1");
        behaviorItem1.setCreativeTab(II_Core.II_MAIN_TAB);
        behaviorItem1.registerItem(1, "Test", null);
        behaviorItem1.registerItem(2, "Guide Renderer", new BehaviorGuideRenderer());
    }
}
