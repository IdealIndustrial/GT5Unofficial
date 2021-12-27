package idealindustrial.impl.tile.module;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.module.RecipeModule;
import idealindustrial.impl.tile.TileEvents;
import idealindustrial.impl.tile.impl.TileFacing2Main;
import idealindustrial.util.misc.II_Util;
import idealindustrial.util.parameter.RecipedMachineStats;

public class EventRecipedModule<R extends IMachineRecipe> extends BasicRecipeModule<R> {

    boolean eventReceived = false;

    public EventRecipedModule(TileFacing2Main<?> machine, RecipedMachineStats stats, RecipeMap<R> map) {
        super(machine, stats, map);
    }

    public EventRecipedModule(TileFacing2Main<?> machine, EventRecipedModule<R> module) {
        super(machine, module);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (!serverSide) {
            return;
        }
        if (maxProgress == 0) {
            if (eventReceived) {
                tryStartRecipe();
            }
            eventReceived = false;
            return;
        }
        if (progress <= maxProgress) {
            if (!onRunningTick()) {
                failProcessing();
                progress = 0;
            }
            progress++;
        } else {
            if (endProcessing(recipe)) {
                progress = maxProgress = usage = 0;
            }
        }
    }

    @Override
    public void receiveEvent(int id, int value) {
        if (id == TileEvents.MODULE_START_PROCESSING) {
            int mouse = II_Util.shortAFromInt(value);
            int hotkeys = II_Util.shortBFromInt(value);
            if (mouse == 0 && hotkeys == 0) {
                eventReceived = true;
            }
        }
    }

    @Override
    public RecipeModule<R> reInit(TileFacing2Main<?> machine) {
        return new EventRecipedModule<>(machine, this);
    }
}
