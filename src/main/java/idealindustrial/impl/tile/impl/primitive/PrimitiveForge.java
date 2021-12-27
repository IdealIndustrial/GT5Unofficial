package idealindustrial.impl.tile.impl.primitive;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.api.tile.module.RecipeModule;
import idealindustrial.impl.tile.impl.recipe.TileMachineReciped;
import idealindustrial.util.parameter.RecipedMachineStats;

import java.util.function.Function;

public class PrimitiveForge extends TileMachineReciped<HostMachineTile, IMachineRecipe> {

    public PrimitiveForge(HostMachineTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<IMachineRecipe> recipeMap, RecipedMachineStats stats, Function<TileMachineReciped<HostMachineTile, IMachineRecipe>, RecipeModule<IMachineRecipe>> module) {
        super(baseTile, name, baseTextures, overlays, recipeMap, stats, module);
    }

    public PrimitiveForge(HostMachineTile baseTile, TileMachineReciped<HostMachineTile, IMachineRecipe> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new PrimitiveForge(baseTile, this);
    }
}
