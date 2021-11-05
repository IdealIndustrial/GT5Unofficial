package idealindustrial.impl.tile.module;

import idealindustrial.api.tile.module.RecipeModule;
import idealindustrial.impl.recipe.BasicMachineRecipe;
import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.impl.recipe.MachineEnergyParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.impl.tile.impl.TileFacing1Output;
import idealindustrial.impl.tile.impl.TileFacing2Main;
import idealindustrial.impl.tile.energy.electric.EnergyHandler;
import idealindustrial.impl.tile.energy.electric.InputEnergyHandler;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.impl.tile.inventory.EmptyInventory;
import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.util.json.JsonUtil;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.nbt.NBTTagCompound;

public class BasicRecipeModule<R extends IMachineRecipe> implements RecipeModule<R> {

    protected long progress, maxProgress, usage;
    protected boolean inputUpdated = false, outputUpdated = false, idleMode = false, outputIsFull = false;
    protected RecipeMap<R> recipeMap;
    protected MachineEnergyParams params;
    protected R recipe;
    protected TileFacing1Output<?> machine;
    protected HostMachineTile baseTile;
    protected InternalInventory inventoryIn, inventoryOut;
    protected FluidHandler tankIn, tankOut;
    protected EnergyHandler energyHandler;
    protected RecipedMachineStats machineStats;

    public BasicRecipeModule(TileFacing2Main<?> machine, RecipedMachineStats stats, RecipeMap<R> map) {
        this.recipeMap = map;
        this.machineStats = stats;
        this.machine = machine;
        this.baseTile = machine.getHost();
        this.params = new MachineEnergyParams(stats);
        machine.hasEnergy = true;
        energyHandler = machine.energyHandler = new InputEnergyHandler(baseTile, (long) (stats.energyCapacity * 0.1), stats.energyCapacity, stats.voltageIn, stats.amperageIn);
        machine.hasTank = stats.hasTank();
        tankIn = machine.tankIn = stats.tankIn();
        tankOut = machine.tankOut = stats.tankOut();
        machine.hasInventory = true;
        inventoryIn = machine.inventoryIn = stats.inventoryIn();
        inventoryOut = machine.inventoryOut = stats.inventoryOut();
        machine.inventorySpecial = EmptyInventory.INSTANCE;
    }

    public BasicRecipeModule(TileFacing2Main<?> machine, BasicRecipeModule<R> module) {
        this(machine, module.machineStats, module.recipeMap);
    }

    protected BasicRecipeModule() {
        //for overriding
    }

    @Override
    public RecipeModule<R> reInit(TileFacing2Main<?> machine) {
        return new BasicRecipeModule<>(machine, machineStats, recipeMap);
    }

    @Override
    public RecipeMap<R> getRecipeMap() {
        return recipeMap;
    }

    @Override
    public void saveToNBT(String prefix, NBTTagCompound nbt) {
        nbt.setLong(prefix + "progress", progress);
        if (recipe != null) {
            nbt.setString(prefix + "recipe", recipeToString(recipe));
        }
    }

    @Override
    public void loadFromNBT(String prefix, NBTTagCompound nbt) {
        progress = nbt.getLong("progress");
        String recipe = nbt.getString("recipe");
        if (!recipe.equals("")) {
            this.recipe = recipeFromString(recipe);
            setRecipe(this.recipe);
        }
        if (this.recipe == null) {
            inputUpdated = true;
        }
    }

    @Override
    public void onInInventoryModified(int id) {
        if (id == 0) {
            inputUpdated = true;
        } else if (id == 1) {
            outputUpdated = true;
        }
    }

    public short getProgress() {
        return (short) (20d * ((double) progress / maxProgress));
    }


    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (!serverSide) {
            return;
        }
        if (idleMode) {
            if (!canContinueProcessing()) {
                return;
            }
            idleMode = false;
        }
        if (maxProgress == 0) {
            if (inputUpdated && baseTile.isAllowedToWork()) {
                inputUpdated = false;
                tryStartRecipe();
            }
            return;
        }
        if (progress <= maxProgress) {
            if (!onRunningTick()) {
                failProcessing();
                baseTile.setActive(false);
                progress = 0;
                idleMode = true;
            }
            progress++;
        } else {
            if (endProcessing(recipe)) {
                progress = maxProgress = usage = 0;
                if (baseTile.isAllowedToWork()) {
                    tryStartRecipe();
                }
            }
        }
    }

    protected void tryStartRecipe() {
        R recipe = checkRecipe();
        if (recipe == null) {
            return;
        }
        this.recipe = recipe;
        if (startProcessing(recipe)) {
            baseTile.setActive(true);
        }
    }

    protected R checkRecipe() {
        R found = recipeMap.findRecipe((RecipedInventory) inventoryIn, tankIn, params);
        if (found != null) {
            usage = found.recipeParams().amperage * found.recipeParams().voltage;
        }
        return found;
    }

    protected boolean onRunningTick() {
        if (usage == 0) {
            return true;
        }
        return energyHandler.drain(usage, true) == usage;
    }

    protected boolean startProcessing(R recipe) {
        maxProgress = recipe.recipeParams().duration;
        return recipe.isInputEqualStacks((RecipedInventory) inventoryIn, tankIn, true);
    }

    protected boolean endProcessing(R recipe) {
        baseTile.setActive(false);
        if (!hasEnoughSpace(recipe)) {
            return false;
        }
        for (II_ItemStack stack : recipe.getOutputs()) {
            inventoryOut.insert(stack.copy(), true);
            outputUpdated = true;
        }
        return true;
    }

    protected void failProcessing() {

    }

    protected boolean canContinueProcessing() {
        return energyHandler.isAlmostFull();
    }

    protected boolean hasEnoughSpace(R recipe) {
        if (outputUpdated) {
            outputIsFull = !inventoryOut.canStore(recipe.getOutputs());
            outputUpdated = false;
        }
        return !outputIsFull;
    }


    protected String recipeToString(R recipe) {
        //        System.out.println(str);
        return JsonUtil.recipeDefaultGson.toJson(recipe);
    }

    @SuppressWarnings("unchecked")
    protected R recipeFromString(String recipe) {
        return (R) JsonUtil.recipeDefaultGson.fromJson(recipe, BasicMachineRecipe.class);
    }

    protected void setRecipe(R recipe) {
        this.maxProgress = recipe.recipeParams().duration;
        this.usage = recipe.recipeParams().voltage * recipe.recipeParams().amperage;
    }
}
