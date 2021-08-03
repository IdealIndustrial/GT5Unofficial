package idealindustrial.tile.base;

import gregtech.api.interfaces.IFastRenderedTileEntity;
import gregtech.api.interfaces.tileentity.IHasInventory;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.metatileentity.IEnergyContainer;
import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.tile.meta.IUpdatableTileEntity;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.fluid.II_FluidInventoryRepresentation;
import idealindustrial.util.inventory.II_InternalInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;

public interface II_BaseTile extends IUpdatableTileEntity, IHasWorldObjectAndCoords, IFastRenderedTileEntity, ISyncedTileEntity, IClickableTileEntity {
    int getMetaTileID();
    void setMetaTileID(int id);
    II_MetaTile getMetaTile();

    ArrayList<ItemStack> getDrops();

    boolean isActive();
    void setActive(boolean active);

    void sendEvent(int id, int value);
    void issueTextureUpdate();


    void onPlaced();


}
