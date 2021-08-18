package idealindustrial.tile.meta.recipe;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.II_MachineEnergyParams;
import idealindustrial.recipe.II_Recipe;
import idealindustrial.recipe.II_RecipeMap;
import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.meta.II_BaseMetaTile_Facing2Main;
import idealindustrial.util.energy.II_InputEnergyHandler;
import idealindustrial.util.inventory.II_RecipedInventory;
import idealindustrial.util.misc.II_RecipedMachineStats;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public abstract class II_BaseMetaTileMachineReciped<BaseTileType extends II_BaseMachineTile, RecipeType extends II_Recipe> extends II_BaseMetaTile_Facing2Main<BaseTileType> {
    protected long progress, maxProgress, usage;
    protected boolean shouldCheckRecipe = false;
    protected II_RecipeMap<RecipeType> recipeMap;
    protected II_MachineEnergyParams params;
    protected RecipeType recipe;
    protected II_RecipedMachineStats stats;

    public II_BaseMetaTileMachineReciped(BaseTileType baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, II_RecipeMap<RecipeType> recipeMap, II_RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays);
        this.stats = stats;
        this.recipeMap = recipeMap;
        this.params = new II_MachineEnergyParams(stats);
        this.hasEnergy = true;
        this.energyHandler = new II_InputEnergyHandler(baseTile, (long) (stats.energyCapacity * 0.1), stats.energyCapacity, stats.voltageIn, stats.amperageIn);
        this.hasTank = stats.hasTank();
        this.tankIn = stats.tankIn();
        this.tankOut = stats.tankOut();
        this.hasInventory = true;
        this.inventoryIn = stats.inventoryIn();
        this.inventoryOut = stats.inventoryOut();
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (maxProgress == 0) {
            if (shouldCheckRecipe) {
                shouldCheckRecipe = false;
                tryStartRecipe();
            }
            return;
        }
        if (progress++ <= maxProgress) {
            if (!onRunningTick()) {
                failProcessing();
                baseTile.setActive(false);
            }
        } else {
            endProcessing(recipe);
            progress = maxProgress = usage = 0;
            tryStartRecipe();
        }
    }

    protected void tryStartRecipe() {
        RecipeType recipe = checkRecipe();
        if (recipe == null) {
            return;
        }
        this.recipe = recipe;
        if (startProcessing(recipe)) {
            baseTile.setActive(true);
        }
    }

    protected RecipeType checkRecipe() {
        RecipeType found = recipeMap.findRecipe((II_RecipedInventory) inventoryIn, tankIn, params);
        usage = found.recipeParams().amperage * found.recipeParams().voltage;
        return found;
    }

    protected boolean onRunningTick() {
        if (usage == 0) {
            return true;
        }
        return energyHandler.drain(usage, true);
    }

    protected boolean startProcessing(RecipeType recipe) {
        return true;
    }

    protected void endProcessing(RecipeType recipe) {

    }

    protected void failProcessing() {

    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return super.getServerGUI(player, internalID);
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return super.getClientGUI(player, internalID);
    }
}
