package idealindustrial.tile.meta.multi;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.module.MultiMachineRecipedModule;
import idealindustrial.tile.module.RecipeModule;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseRecipedMultiMachine<BaseTileType extends BaseMachineTile, RecipeType extends IMachineRecipe> extends BaseMultiMachine<BaseTileType> {
    RecipeModule<RecipeType> module;

    public BaseRecipedMultiMachine(BaseTileType baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<RecipeType> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays);
        module = new MultiMachineRecipedModule<RecipeType>(this, stats, recipeMap);
    }

    protected BaseRecipedMultiMachine(BaseTileType baseTile, BaseRecipedMultiMachine<BaseTileType, RecipeType> copyFrom) {
        super(baseTile, copyFrom);
        this.module = copyFrom.module.reInit(this);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        module.onPostTick(timer, serverSide);
    }

    @Override
    public void onInInventoryModified(int id) {
        module.onInInventoryModified(id);
    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        super.onSoftHammerClick(player, item, side);
        if (baseTile.isAllowedToWork()) {
            module.onInInventoryModified(0);
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
        return module.getProgress();
    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        module.saveToNBT("R", nbt);
        return super.saveToNBT(nbt);
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
        module.loadFromNBT("R", nbt);
    }



}
