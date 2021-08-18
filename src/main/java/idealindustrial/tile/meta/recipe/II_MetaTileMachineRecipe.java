package idealindustrial.tile.meta.recipe;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.II_BasicRecipe;
import idealindustrial.recipe.II_RecipeMap;
import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.interfaces.meta.II_MetaTile;
import idealindustrial.util.misc.II_RecipedMachineStats;

public class II_MetaTileMachineRecipe extends II_BaseMetaTileMachineReciped<II_BaseMachineTile, II_BasicRecipe> {


    public II_MetaTileMachineRecipe(II_BaseMachineTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays,
                                    II_RecipeMap<II_BasicRecipe> recipeMap, II_RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays, recipeMap, stats);
    }

    @Override
    public II_MetaTile<II_BaseMachineTile> newMetaTile(II_BaseMachineTile baseTile) {
        return new II_MetaTileMachineRecipe(baseTile, name, baseTextures, overlays, recipeMap, stats);
    }
}
