package idealindustrial.impl.tile.impl.recipe;

import idealindustrial.api.textures.ITexture;
import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.TileEvents;
import idealindustrial.impl.tile.gui.RecipedContainer;
import idealindustrial.impl.tile.gui.RecipedGuiContainer;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.impl.tile.impl.TileFacing2Main;
import idealindustrial.impl.tile.module.BasicRecipeModule;
import idealindustrial.api.tile.module.RecipeModule;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.function.Function;

public class TileMachineReciped<H extends HostMachineTile, R extends IMachineRecipe> extends TileFacing2Main<H> {
    RecipeModule<R> module;

    public TileMachineReciped(H baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<R> recipeMap,
                              RecipedMachineStats stats, Function<TileMachineReciped<H,R>,RecipeModule<R>> module) {
        super(baseTile, name, baseTextures, overlays);
        this.module = module.apply(this);
    }

    public TileMachineReciped(H baseTile, String name, ITexture[] baseTextures, ITexture[] overlays, RecipeMap<R> recipeMap, RecipedMachineStats stats) {
        this(baseTile, name, baseTextures, overlays, recipeMap, stats, (th) -> new BasicRecipeModule<>(th, stats, recipeMap));
    }


    public TileMachineReciped(H baseTile, TileMachineReciped<H, R> copyFrom) {
        super(baseTile, copyFrom);
        module = copyFrom.module.reInit(this);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        module.onPostTick(timer, serverSide);
    }

    @Override
    public RecipedContainer getServerGUI(EntityPlayer player, int internalID) {
        return new RecipedContainer(hostTile, player, module.getRecipeMap().getGuiParams());
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new RecipedGuiContainer(getServerGUI(player, internalID), II_Paths.PATH_GUI + "BasicGui.png");
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
    public boolean receiveClientEvent(int id, int value) {
        if (TileEvents.isTileModuleEvent(id)) {
            module.receiveEvent(id, value);
            return true;
        }
        return super.receiveClientEvent(id, value);
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
    public Tile<H> newMetaTile(H baseTile) {
        return new TileMachineReciped<H, R>(baseTile, this);
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
