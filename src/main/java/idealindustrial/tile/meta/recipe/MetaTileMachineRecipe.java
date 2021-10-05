package idealindustrial.tile.meta.recipe;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.BasicMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.interfaces.meta.MetaTile;
import idealindustrial.util.parameter.RecipedMachineStats;

public class MetaTileMachineRecipe extends BaseMetaTileMachineReciped<BaseMachineTile, BasicMachineRecipe> {


    public MetaTileMachineRecipe(BaseMachineTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays,
                                 RecipeMap<BasicMachineRecipe> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays, recipeMap, stats);
    }

    public MetaTileMachineRecipe(BaseMachineTile baseTile, BaseMetaTileMachineReciped<BaseMachineTile, BasicMachineRecipe> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public MetaTile<BaseMachineTile> newMetaTile(BaseMachineTile baseTile) {
        return new MetaTileMachineRecipe(baseTile, this);
    }
}
