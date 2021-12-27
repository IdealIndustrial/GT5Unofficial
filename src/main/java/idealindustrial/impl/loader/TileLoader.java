package idealindustrial.impl.loader;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.impl.blocks.plants.TilePlants;
import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.recipe.RecipeMaps;
import idealindustrial.impl.render.RotatingTileRenderer;
import idealindustrial.impl.tile.Tiles;
import idealindustrial.teststuff.testTile.TestMachine;
import idealindustrial.teststuff.testTile2.TestMachine2;
import idealindustrial.teststuff.testmulti.TestMultiMachine1;
import idealindustrial.impl.textures.TextureUtil;
import idealindustrial.impl.textures.Textures;
import idealindustrial.impl.tile.host.HostMachineTileImpl;
import idealindustrial.impl.tile.host.HostPipeTileImpl;
import idealindustrial.impl.tile.host.HostPipeTileRotatingImpl;
import idealindustrial.impl.tile.host.HostTileImpl;
import idealindustrial.impl.tile.impl.connected.ConnectedCable;
import idealindustrial.impl.tile.impl.connected.ConnectedRotor;
import idealindustrial.impl.tile.impl.kinetic.KUGeneratorBase;
import idealindustrial.impl.tile.impl.kinetic.KUMachineBase;
import idealindustrial.impl.tile.impl.kinetic.TileKUSplitter;
import idealindustrial.impl.tile.impl.multi.parts.Hatch_Energy;
import idealindustrial.impl.tile.impl.multi.parts.Hatch_Item;
import idealindustrial.impl.tile.impl.recipe.TileMachineBasicRecipe;
import idealindustrial.impl.blocks.ores.TileOres;
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
        II_TileUtil.registerMetaTile(6, new TileMachineBasicRecipe(makeBaseMachineTile(),"bender",
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[1], new ITexture[10]),
                TextureUtil.loadTextures(TextureUtil.facing2Configuration, II_Paths.RECIPE_MACHINE_TEXTURES + "testmachine/"),
                RecipeMaps.benderRecipes,
                new RecipedMachineStats(1,1, 1, 0, 64, 0, 0, 0, 1, 10_000)));
        II_TileUtil.registerMetaTile(7, new TestMultiMachine1(makeBaseMachineTile()));
        II_TileUtil.registerMetaTile(8, new Hatch_Item.InputBus(makeBaseMachineTile(), "input hatch", 1));
        II_TileUtil.registerMetaTile(9, new Hatch_Item.OutputBus(makeBaseMachineTile(), "output hatch", 1));
        II_TileUtil.registerMetaTile(10, new Hatch_Energy.EnergyHatch(makeBaseMachineTile(), "Energy Hatch", 1));
        II_TileUtil.registerMetaTile(11, KUMachineBase.testMachine());
        II_TileUtil.registerMetaTile(12, KUGeneratorBase.testMachine());
        II_TileUtil.registerMetaTile(13, new ConnectedRotor(makeBaseTile(), II_Materials.tin, Prefixes.cable01, 0.3f));
        II_TileUtil.registerMetaTile(14, TileKUSplitter.testMachine());
        II_TileUtil.registerMetaTile(15, Tiles.makePrimitiveForge());
        II_TileUtil.registerMetaTile(16, Tiles.makePrimitiveAnvil());




        GameRegistry.registerTileEntity(HostTileImpl.class, "ii.tile");
        GameRegistry.registerTileEntity(HostMachineTileImpl.class, "ii.machine_tile");
        GameRegistry.registerTileEntity(HostPipeTileImpl.class, "ii.pipe_tile");
        GameRegistry.registerTileEntity(HostPipeTileRotatingImpl.class, "ii.pipe_rotation_tile");
        GameRegistry.registerTileEntity(TileOres.class, "ii.tile_ores");
        GameRegistry.registerTileEntity(TilePlants.class, "ii.tile_plants");

        ClientRegistry.bindTileEntitySpecialRenderer(HostPipeTileRotatingImpl.class, new RotatingTileRenderer());
    }
}
