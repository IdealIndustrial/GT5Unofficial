package idealindustrial.impl.tile.impl.multi;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.blocks.II_Blocks;
import idealindustrial.impl.recipe.BasicMachineRecipe;
import idealindustrial.impl.recipe.BasicRecipeMap;
import idealindustrial.impl.recipe.RecipeGuiParamsBuilder;
import idealindustrial.impl.recipe.RecipeMaps;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.impl.textures.TextureUtil;
import idealindustrial.impl.tile.gui.base.component.SlotHoloEvent;
import idealindustrial.impl.tile.impl.multi.struct.MachineShapeBuilder;
import idealindustrial.impl.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.parameter.RecipedMachineStats;

import static idealindustrial.impl.blocks.II_Blocks.CasingBlocks.PrimitiveFurnaceCasing;

public class PrimitiveMultiFurnace extends RecipedMultiMachineBase<HostMachineTile, BasicMachineRecipe> {

    static RecipeMap<BasicMachineRecipe> map = RecipeMaps.getMap("Primitive Furnace Recipes", (name) -> {
        IRecipeGuiParams params = new RecipeGuiParamsBuilder(3, 1, 0, 0, 0, 0)
                .construct();
        return new BasicRecipeMap<>(name, true, false, params, BasicMachineRecipe.class);
    });

    public PrimitiveMultiFurnace(HostMachineTile baseTile) {
        super(baseTile, "Primitive Furnace",
                II_StreamUtil.arrayOf(PrimitiveFurnaceCasing.getTexture(), new ITexture[10]),
                TextureUtil.loadMultiMachineTextures("Primitive Furnace"),
                map,
                new RecipedMachineStats(0, map.getGuiParams(), 64, 0, 0, 0));
    }

    public PrimitiveMultiFurnace(HostMachineTile baseTile, RecipedMultiMachineBase<HostMachineTile, BasicMachineRecipe> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new PrimitiveMultiFurnace(baseTile, this);
    }

    @Override
    protected MultiMachineShape getStructure() {
        return MachineShapeBuilder.start().addShape(new String[][]{
                {
                        "TTTT",
                        "TTTT",
                        "TTTT"
                },
                {
                        "XXXX",
                        "XaaX",
                        "XcXX"
                },
                {
                        "BBBB",
                        "BBBB",
                        "BBBB"
                }
        }).define('X', blockPredicate(PrimitiveFurnaceCasing))
                .define('T', blockPredicate(PrimitiveFurnaceCasing).withMin(10).or(hatchPredicate(HatchType.ItemIn)))
                .define('B', blockPredicate(PrimitiveFurnaceCasing).withMin(10).or(hatchPredicate(HatchType.ItemOut)))
                .setInversions(true, false).added().create();
    }
}
