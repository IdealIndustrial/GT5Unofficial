package idealindustrial.tile.meta;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.base.ISyncedTileEntity;
import idealindustrial.tile.base.IToolClickableTile;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_BaseInventory;
import idealindustrial.util.inventory.II_InternalInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public interface II_MetaTile extends IUpdatableTileEntity, ISyncedTileEntity, IToolClickableTile {


    boolean hasInventory();
    II_InternalInventory getInputsHandler();
    II_InternalInventory getOutputsHandler();
    II_InternalInventory getSpecialHandler();


    boolean hasFluidTank();
    II_FluidHandler getInputTank();
    II_FluidHandler getOutputTank();

    void onOpenGui();
    void onCloseGui();
    int getInventoryStackLimit();
    String getInventoryName();
    boolean isAccessAllowed(EntityPlayer aPlayer);
    GuiContainer getClientGUI(EntityPlayer player, int internalID);
    Container getServerGUI(EntityPlayer player, int internalID);

    ITexture[] provideTexture(boolean active, int side);
    II_BaseTile getBase();
    II_MetaTile newMetaTile(II_BaseTile baseTile);

    IInventory getFluidIORepresentation();

}
