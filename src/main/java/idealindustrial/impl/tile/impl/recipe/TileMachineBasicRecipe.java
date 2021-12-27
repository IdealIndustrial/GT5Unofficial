package idealindustrial.impl.tile.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.util.parameter.RecipedMachineStats;

public class TileMachineBasicRecipe extends TileMachineReciped<HostMachineTile, IMachineRecipe> {



    @SuppressWarnings("unchecked")
    public TileMachineBasicRecipe(HostMachineTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays,
                                  RecipeMap<? extends IMachineRecipe> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays, (RecipeMap<IMachineRecipe>) recipeMap, stats);
    }

    public TileMachineBasicRecipe(HostMachineTile baseTile, TileMachineReciped<HostMachineTile, IMachineRecipe> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new TileMachineBasicRecipe(baseTile, this);
    }
}
