package idealindustrial.impl.tile.impl.recipe;

import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.recipe.BasicMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.util.parameter.RecipedMachineStats;

public class TileMachineRecipe extends TileMachineReciped<HostMachineTile, BasicMachineRecipe> {


    public TileMachineRecipe(HostMachineTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays,
                             RecipeMap<BasicMachineRecipe> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays, recipeMap, stats);
    }

    public TileMachineRecipe(HostMachineTile baseTile, TileMachineReciped<HostMachineTile, BasicMachineRecipe> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new TileMachineRecipe(baseTile, this);
    }
}
