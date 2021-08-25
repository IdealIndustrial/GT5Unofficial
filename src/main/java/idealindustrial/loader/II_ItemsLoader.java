package idealindustrial.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ITexture;
import idealindustrial.autogen.implementation.II_MetaGeneratedItem_1;
import idealindustrial.recipe.II_RecipeMaps;
import idealindustrial.teststuff.testTile.II_TestMachine;
import idealindustrial.teststuff.testTile2.II_TestMachine2;
import idealindustrial.textures.II_TextureUtil;
import idealindustrial.textures.II_Textures;
import idealindustrial.tile.base.II_BaseMachineTileImpl;
import idealindustrial.tile.base.II_BasePipeTileImpl;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;
import idealindustrial.tile.meta.recipe.II_MetaTileMachineRecipe;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.parameter.II_RecipedMachineStats;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

public class II_ItemsLoader {

    public void preLoad() {
        new II_MetaGeneratedItem_1();

        II_TileUtil.registerMetaTile(1, new II_TestMachine(II_TileUtil.makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(2, new II_TestMachine2(II_TileUtil.makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(3, new II_MetaConnected_Cable(II_TileUtil.makeBaseTile(), Materials.Tin, 32, 1, 1, 0.5f));
        II_TileUtil.registerMetaTile(4, new II_MetaConnected_Cable(II_TileUtil.makeBaseTile(), Materials.Copper, 128, 1, 1, 0.3f));
        II_TileUtil.registerMetaTile(5, new II_MetaConnected_Cable(II_TileUtil.makeBaseTile(), Materials.Silver, 512, 2, 1, 0.8f));
        II_TileUtil.registerMetaTile(6, new II_MetaTileMachineRecipe(II_TileUtil.makeBaseMachineTile(),"bender",
                II_StreamUtil.arrayOf(II_Textures.baseTiredTextures[1], new ITexture[10]),
                II_TextureUtil.loadTextures(II_TextureUtil.facing2Configuration, II_Paths.PATH_RECIPE_MACHINE_TEXTURES + "testmachine/"),
                II_RecipeMaps.benderRecipes,
                new II_RecipedMachineStats(1,1, 1, 64, 0, 0, 0, 1, 10_000)));
        GameRegistry.registerTileEntity(II_BaseTileImpl.class, "ii.tile");
        GameRegistry.registerTileEntity(II_BaseMachineTileImpl.class, "ii.machine_tile");
        GameRegistry.registerTileEntity(II_BasePipeTileImpl.class, "ii.pipe_tile");
    }
}
