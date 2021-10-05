package idealindustrial.tile.module;

import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.MachineEnergyParams;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.meta.BaseMetaTile_Facing2Main;
import idealindustrial.tile.meta.multi.BaseMultiMachine;
import idealindustrial.tile.meta.multi.BaseRecipedMultiMachine;
import idealindustrial.util.inventory.MultipartRecipedInternalInventory;
import idealindustrial.util.inventory.StupidMultipartInv;
import idealindustrial.util.parameter.RecipedMachineStats;

import java.util.stream.Collectors;

public class MultiMachineRecipedModule<R extends IMachineRecipe> extends BasicRecipeModule<R> {

    public MultiMachineRecipedModule(BaseRecipedMultiMachine<?, R> machine, RecipedMachineStats stats, RecipeMap<R> map) {
        this.recipeMap = map;
        this.machineStats = stats;
        this.machine = machine;
        this.baseTile = machine.getBase();
        this.params = new MachineEnergyParams(stats);
    }

    public MultiMachineRecipedModule(BaseRecipedMultiMachine<?, R> machine, BasicRecipeModule<R> module) {
        this(machine, module.machineStats, module.recipeMap);
    }

    @SuppressWarnings("unchecked")
    public void onAssembled() {
        BaseRecipedMultiMachine<?, R> machine = (BaseRecipedMultiMachine<?, R>) this.machine;
        if (machine.inputBuses.size() > 0 || machine.outputBuses.size() > 0) {
            machine.hasInventory = false;
            machine.inventoryIn = new StupidMultipartInv(machine.inputBuses.stream().map(b -> b.getBase().getIn()).collect(Collectors.toList()));
            machine.inventoryOut = new StupidMultipartInv(machine.inputBuses.stream().map(b -> b.getBase().getOut()).collect(Collectors.toList()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeModule<R> reInit(BaseMetaTile_Facing2Main<?> machine) {
        return new MultiMachineRecipedModule<R>((BaseRecipedMultiMachine<?, R>) machine, machineStats, recipeMap);
    }
}
