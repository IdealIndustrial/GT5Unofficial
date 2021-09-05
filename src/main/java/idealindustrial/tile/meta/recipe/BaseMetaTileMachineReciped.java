package idealindustrial.tile.meta.recipe;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.BasicMachineRecipe;
import idealindustrial.recipe.MachineEnergyParams;
import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.gui.RecipedContainer;
import idealindustrial.tile.gui.RecipedGuiContainer;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.meta.BaseMetaTile_Facing2Main;
import idealindustrial.util.energy.InputEnergyHandler;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.RecipedInventory;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.json.JsonUtil;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseMetaTileMachineReciped<BaseTileType extends BaseMachineTile, RecipeType extends IMachineRecipe> extends BaseMetaTile_Facing2Main<BaseTileType> {
    protected long progress, maxProgress, usage;
    protected boolean inputUpdated = false, outputUpdated = false, idleMode = false, outputIsFull = false;
    protected RecipeMap<RecipeType> recipeMap;
    protected MachineEnergyParams params;
    protected RecipeType recipe;
    protected RecipedMachineStats stats;
    protected int[] slotTextures;

    public BaseMetaTileMachineReciped(BaseTileType baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<RecipeType> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays);
        this.stats = stats;
        this.recipeMap = recipeMap;
        this.params = new MachineEnergyParams(stats);
        this.hasEnergy = true;
        this.energyHandler = new InputEnergyHandler(baseTile, (long) (stats.energyCapacity * 0.1), stats.energyCapacity, stats.voltageIn, stats.amperageIn);
        this.hasTank = stats.hasTank();
        this.tankIn = stats.tankIn();
        this.tankOut = stats.tankOut();
        this.hasInventory = true;
        this.inventoryIn = stats.inventoryIn();
        this.inventoryOut = stats.inventoryOut();
        this.inventorySpecial = EmptyInventory.INSTANCE;
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
        RecipeType found = recipeMap.findRecipe((RecipedInventory) inventoryIn, tankIn, params);
        if (found != null) {
            usage = found.recipeParams().amperage * found.recipeParams().voltage;
        }
        return found;
    }

    protected boolean onRunningTick() {
        if (usage == 0) {
            return true;
        }
        return energyHandler.drain(usage, true);
    }

    protected boolean startProcessing(RecipeType recipe) {
        maxProgress = recipe.recipeParams().duration;
        return recipe.isInputEqualStacks((RecipedInventory) inventoryIn, tankIn, true);
    }

    protected boolean endProcessing(RecipeType recipe) {
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

    protected boolean hasEnoughSpace(RecipeType recipe) {
        if (outputUpdated) {
            outputIsFull = !inventoryOut.canStore(recipe.getOutputs());
            outputUpdated = false;
        }
        return !outputIsFull;
    }


    @Override
    public RecipedContainer getServerGUI(EntityPlayer player, int internalID) {
        return new RecipedContainer(baseTile, player, recipeMap.getGuiParams());
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new RecipedGuiContainer(getServerGUI(player, internalID), II_Paths.PATH_GUI + "BasicGui.png");
    }

    @Override
    public void onInInventoryModified(int id) {
        if (id == 0) {
            inputUpdated = true;
        } else if (id == 1) {
            outputUpdated = true;
        }
    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        super.onSoftHammerClick(player, item, side);
        if (baseTile.isAllowedToWork()) {
            inputUpdated = true;
        }
        return true;
    }

    /**
     * value that represents the machine progress clamped between 0 and 20
     * used to render progress arrow on client
     *
     * @return progress representation
     */
    public short getProgress() {
        return (short) (20d * ((double) progress / maxProgress));
    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        nbt.setLong("progress", progress);
        if (recipe != null) {
            nbt.setString("recipe", recipeToString(recipe));
        }
        return super.saveToNBT(nbt);
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
        progress = nbt.getLong("progress");
        String recipe = nbt.getString("recipe");
        if (!recipe.equals("")) {
            this.recipe = recipeFromString(recipe);
            setRecipe(this.recipe);
        }
    }

    protected String recipeToString(RecipeType recipe) {
        String str = JsonUtil.recipeDefaultGson.toJson(recipe);
        System.out.println(str);
        return str;
    }

    protected RecipeType recipeFromString(String recipe) {
        return (RecipeType) JsonUtil.recipeDefaultGson.fromJson(recipe, BasicMachineRecipe.class);
    }

    protected void setRecipe(RecipeType recipe) {
        this.maxProgress = recipe.recipeParams().duration;
        this.usage = recipe.recipeParams().voltage * recipe.recipeParams().amperage;
    }
}
