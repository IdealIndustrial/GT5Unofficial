package idealindustrial.loader;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.textures.ITexture;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.recipe.RecipeMaps;
import idealindustrial.render.RotatingTileRenderer;
import idealindustrial.teststuff.testTile.TestMachine;
import idealindustrial.teststuff.testTile2.TestMachine2;
import idealindustrial.teststuff.testmulti.TestMultiMachine1;
import idealindustrial.textures.TextureUtil;
import idealindustrial.textures.Textures;
import idealindustrial.tile.host.HostMachineTileImpl;
import idealindustrial.tile.host.HostPipeTileImpl;
import idealindustrial.tile.host.HostPipeTileRotatingImpl;
import idealindustrial.tile.host.HostTileImpl;
import idealindustrial.tile.impl.connected.ConnectedCable;
import idealindustrial.tile.impl.connected.ConnectedRotor;
import idealindustrial.tile.impl.kinetic.KUGeneratorBase;
import idealindustrial.tile.impl.kinetic.KUMachineBase;
import idealindustrial.tile.impl.kinetic.TileKUSplitter;
import idealindustrial.tile.impl.multi.parts.Hatch_Energy;
import idealindustrial.tile.impl.multi.parts.Hatch_Item;
import idealindustrial.tile.impl.recipe.TileMachineRecipe;
import idealindustrial.blocks.ores.TileOres;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.util.parameter.RecipedMachineStats;

import static idealindustrial.util.misc.II_TileUtil.makeBaseMachineTile;
import static idealindustrial.util.misc.II_TileUtil.makeBaseTile;

public class TileLoader implements Runnable {
    @Override
    public void run() {
        II_TileUtil.registerMetaTile(1, new TestMachine(makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(2, new TestMachine2(makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(3, new ConnectedCable(makeBaseTile(), II_Materials.tin, Prefixes.cable01, 32, 1, 1, 0.5f));
        II_TileUtil.registerMetaTile(4, new ConnectedCable(makeBaseTile(), II_Materials.copper, Prefixes.cable01, 128, 1, 1, 0.3f));
        II_TileUtil.registerMetaTile(5, new ConnectedCable(makeBaseTile(), II_Materials.iron, Prefixes.cable01, 512, 2, 1, 0.8f));
        II_TileUtil.registerMetaTile(6, new TileMachineRecipe(makeBaseMachineTile(),"bender",
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[1], new ITexture[10]),
                TextureUtil.loadTextures(TextureUtil.facing2Configuration, II_Paths.RECIPE_MACHINE_TEXTURES + "testmachine/"),
                RecipeMaps.benderRecipes,
                new RecipedMachineStats(1,1, 1, 64, 0, 0, 0, 1, 10_000)));
        II_TileUtil.registerMetaTile(7, new TestMultiMachine1(makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(8, new Hatch_Item.InputBus(makeBaseMachineTile(), "input hatch", 1));
        II_TileUtil.registerMetaTile(9, new Hatch_Item.OutputBus(makeBaseMachineTile(), "output hatch", 1));
        II_TileUtil.registerMetaTile(10, new Hatch_Energy.EnergyHatch(makeBaseMachineTile(), "Energy Hatch", 1));
        II_TileUtil.registerMetaTile(11, KUMachineBase.testMachine());
        II_TileUtil.registerMetaTile(12, KUGeneratorBase.testMachine());
        II_TileUtil.registerMetaTile(13, new ConnectedRotor(makeBaseTile(), II_Materials.tin, Prefixes.cable01, 0.3f));
        II_TileUtil.registerMetaTile(14, TileKUSplitter.testMachine());

        GameRegistry.registerTileEntity(HostTileImpl.class, "ii.tile");
        GameRegistry.registerTileEntity(HostMachineTileImpl.class, "ii.machine_tile");
        GameRegistry.registerTileEntity(HostPipeTileImpl.class, "ii.pipe_tile");
        GameRegistry.registerTileEntity(HostPipeTileRotatingImpl.class, "ii.pipe_rotation_tile");
        GameRegistry.registerTileEntity(TileOres.class, "ii.tile_ores");

        ClientRegistry.bindTileEntitySpecialRenderer(HostPipeTileRotatingImpl.class, new RotatingTileRenderer());
    }
}
