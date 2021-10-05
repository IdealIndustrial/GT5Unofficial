package idealindustrial.tile.impl.multi;

import gregtech.api.interfaces.ITexture;
import idealindustrial.recipe.IMachineRecipe;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.module.MultiMachineRecipedModule;
import idealindustrial.tile.module.RecipeModule;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class RecipedMultiMachineBase<H extends HostMachineTile, R extends IMachineRecipe> extends MultiMachineBase<H> {
    RecipeModule<R> module;

    public RecipedMultiMachineBase(H baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<R> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays);
        module = new MultiMachineRecipedModule<R>(this, stats, recipeMap);
    }

    protected RecipedMultiMachineBase(H baseTile, RecipedMultiMachineBase<H, R> copyFrom) {
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
        if (hostTile.isAllowedToWork()) {
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
