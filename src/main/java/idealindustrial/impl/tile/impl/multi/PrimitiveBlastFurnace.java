package idealindustrial.impl.tile.impl.multi;

import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.recipe.BasicMachineRecipe;
import idealindustrial.impl.recipe.BasicRecipeMap;
import idealindustrial.impl.recipe.RecipeGuiParamsBuilder;
import idealindustrial.impl.recipe.RecipeMaps;
import idealindustrial.impl.textures.TextureUtil;
import idealindustrial.impl.tile.impl.multi.struct.MachineShapeBuilder;
import idealindustrial.impl.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.init.Blocks;

import static idealindustrial.impl.blocks.II_Blocks.CasingBlocks.PrimitiveBlastFurnaceCasing;
import static idealindustrial.impl.tile.impl.multi.MultiMachineBase.HatchType.ItemIn;
import static idealindustrial.impl.tile.impl.multi.MultiMachineBase.HatchType.ItemOut;

public class PrimitiveBlastFurnace extends RecipedMultiMachineBase<HostMachineTile, BasicMachineRecipe> {

    static String engName = "Primitive Blast Furnace";

    static RecipeMap<BasicMachineRecipe> map = RecipeMaps.getMap(engName + " Recipes", (name) -> {
        IRecipeGuiParams params = new RecipeGuiParamsBuilder(3, 4, 0, 0, 0, 0)
                .construct();
        return new BasicRecipeMap<>(name, true, false, params, BasicMachineRecipe.class);
    });

    public PrimitiveBlastFurnace(HostMachineTile baseTile) {
        super(baseTile, engName,
                II_StreamUtil.arrayOf(PrimitiveBlastFurnaceCasing.getTexture(), new ITexture[10]),
                TextureUtil.loadMultiMachineTextures(engName),
                map,
                new RecipedMachineStats(0, map.getGuiParams(), 64, 0, 0, 0));
    }

    public PrimitiveBlastFurnace(HostMachineTile baseTile, RecipedMultiMachineBase<HostMachineTile, BasicMachineRecipe> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new PrimitiveBlastFurnace(baseTile, this);
    }

    @Override
    public void onActiveUpdated(boolean active) {
        shape.execute(this, ((ch, pos) -> {
            if (ch != 'L') {
                return;
            }
            hostTile.setBlock(pos, active ? Blocks.lava : Blocks.air);
        }));
    }

    @Override
    protected MultiMachineShape getStructure() {
        return MachineShapeBuilder.start().addShape(new String[][]{
                        {
                                "XXX",
                                "XaX",
                                "XXX"
                        },
                        {
                                "XXX",
                                "XaX",
                                "XXX"
                        },
                        {
                                "XXX",
                                "XLX",
                                "XcX"
                        },
                        {
                                "BBB",
                                "BBB",
                                "BBB"
                        }
                }).define('X', blockPredicate(PrimitiveBlastFurnaceCasing))
                .define('B', blockPredicate(PrimitiveBlastFurnaceCasing).withMin(6).or(hatchPredicate(ItemOut, ItemIn)))
                .define('L', lambdaPredicate((world, x, y, z) ->
                                world.getBlock(x, y, z).isAir(world, x, y, z) || getWorld().getBlock(x, y, z) == Blocks.lava
                        )
                )
                .added().create();
    }
}
