package idealindustrial.tile.module;

import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.MachineEnergyParams;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.tile.impl.multi.RecipedMultiMachineBase;
import idealindustrial.util.inventory.StupidMultipartInv;
import idealindustrial.util.parameter.RecipedMachineStats;

public class MultiMachineRecipedModule<R extends IMachineRecipe> extends BasicRecipeModule<R> {

    public MultiMachineRecipedModule(RecipedMultiMachineBase<?, R> machine, RecipeMap<R> map) {
        this.recipeMap = map;
        this.machineStats = new RecipedMachineStats(0, 0, 0, 0, 0, 0, 0, 0, 0);
        this.machine = machine;
        this.baseTile = machine.getHost();
        this.params = new MachineEnergyParams(machineStats);
    }

    public MultiMachineRecipedModule(RecipedMultiMachineBase<?, R> machine, BasicRecipeModule<R> module) {
        this(machine, module.recipeMap);
    }

    @SuppressWarnings("unchecked")
    public void onAssembled() {
        RecipedMultiMachineBase<?, R> machine = (RecipedMultiMachineBase<?, R>) this.machine;
        energyHandler = machine.energyHandler;
        inventoryIn = machine.inventoryIn;
        inventoryOut = machine.inventoryOut;
        tankIn = machine.tankIn;
        tankOut = machine.tankOut;
        this.params = energyHandler.getParams();
    }

    @Override
    protected void tryStartRecipe() {
        ((StupidMultipartInv) inventoryIn).combine();
        super.tryStartRecipe();
    }

    @Override
    protected boolean startProcessing(R recipe) {
        if (!super.startProcessing(recipe)) {
            return false;
        }
        ((StupidMultipartInv) inventoryIn).checkSizes();
        return true;
    }

    @Override
    protected boolean hasEnoughSpace(R recipe) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeModule<R> reInit(TileFacing2Main<?> machine) {
        return new MultiMachineRecipedModule<R>((RecipedMultiMachineBase<?, R>) machine, recipeMap);
    }
}