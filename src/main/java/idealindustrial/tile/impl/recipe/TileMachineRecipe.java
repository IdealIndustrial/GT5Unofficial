package idealindustrial.tile.impl.recipe;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.BasicMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
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
