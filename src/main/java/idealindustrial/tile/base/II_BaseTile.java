package idealindustrial.tile.base;

import gregtech.api.interfaces.IFastRenderedTileEntity;
import gregtech.api.interfaces.tileentity.IHasInventory;
import gregtech.api.metatileentity.IEnergyContainer;
import idealindustrial.tile.meta.IUpdatableTileEntity;
import idealindustrial.util.inventory.II_InternalInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;

public interface II_BaseTile extends IUpdatableTileEntity, IHasInventory, IFluidHandler, IEnergyContainer, IFastRenderedTileEntity, ISyncedTileEntity, IClickableTileEntity {
    int getMetaTileID();

    ArrayList<ItemStack> getDrops();

    boolean isActive();
    void setActive(boolean active);

    Container getServerGUI(EntityPlayer player, int internalID);
    GuiContainer getClientGUI(EntityPlayer player, int internalID);

    int[] getInventorySizes();

    II_InternalInventory getIn();
    II_InternalInventory getOut();
    II_InternalInventory getSpecial();
}
