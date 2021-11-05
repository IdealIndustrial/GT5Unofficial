package idealindustrial.impl.tile.impl.multi;

import idealindustrial.api.textures.ITexture;
import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.impl.tile.module.MultiMachineRecipedModule;
import idealindustrial.api.tile.module.RecipeModule;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class RecipedMultiMachineBase<H extends HostMachineTile, R extends IMachineRecipe> extends MultiMachineBase<H> {
    RecipeModule<R> module;

    public RecipedMultiMachineBase(H baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<R> recipeMap, RecipedMachineStats stats) {
        super(baseTile, name, baseTextures, overlays);
        module = new MultiMachineRecipedModule<R>(this, recipeMap);
    }

    protected RecipedMultiMachineBase(H baseTile, RecipedMultiMachineBase<H, R> copyFrom) {
        super(baseTile, copyFrom);
        this.module = copyFrom.module.reInit(this);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
        if (serverSide && assembled) {
            module.onPostTick(timer, serverSide);
        }
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

    @Override
    protected void onAssembled() {
        super.onAssembled();
        System.out.println("Assembled");
        ((MultiMachineRecipedModule<R>) module).onAssembled();//todo make interface
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
