package idealindustrial.api.tile.module;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.impl.tile.impl.TileFacing2Main;
import net.minecraft.nbt.NBTTagCompound;

public interface RecipeModule<R extends IMachineRecipe> extends TileModule {

   void onInInventoryModified(int id);

   short getProgress();

   RecipeMap<R> getRecipeMap();

   RecipeModule<R> reInit(TileFacing2Main<?> machine);

}
