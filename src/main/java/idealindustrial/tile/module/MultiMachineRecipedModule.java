package idealindustrial.tile.module;

import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.MachineEnergyParams;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.tile.impl.multi.RecipedMultiMachineBase;
import idealindustrial.util.inventory.StupidMultipartInv;
import idealindustrial.util.parameter.RecipedMachineStats;

import java.util.stream.Collectors;

public class MultiMachineRecipedModule<R extends IMachineRecipe> extends BasicRecipeModule<R> {

    public MultiMachineRecipedModule(RecipedMultiMachineBase<?, R> machine, RecipedMachineStats stats, RecipeMap<R> map) {
        this.recipeMap = map;
        this.machineStats = stats;
        this.machine = machine;
        this.baseTile = machine.getHost();
        this.params = new MachineEnergyParams(stats);
    }

    public MultiMachineRecipedModule(RecipedMultiMachineBase<?, R> machine, BasicRecipeModule<R> module) {
        this(machine, module.machineStats, module.recipeMap);
    }

    @SuppressWarnings("unchecked")
    public void onAssembled() {
        RecipedMultiMachineBase<?, R> machine = (RecipedMultiMachineBase<?, R>) this.machine;
        if (machine.inputBuses.size() > 0 || machine.outputBuses.size() > 0) {
            machine.hasInventory = false;
            machine.inventoryIn = new StupidMultipartInv(machine.inputBuses.stream().map(b -> b.getHost().getIn()).collect(Collectors.toList()));
            machine.inventoryOut = new StupidMultipartInv(machine.inputBuses.stream().map(b -> b.getHost().getOut()).collect(Collectors.toList()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeModule<R> reInit(TileFacing2Main<?> machine) {
        return new MultiMachineRecipedModule<R>((RecipedMultiMachineBase<?, R>) machine, machineStats, recipeMap);
    }
}
