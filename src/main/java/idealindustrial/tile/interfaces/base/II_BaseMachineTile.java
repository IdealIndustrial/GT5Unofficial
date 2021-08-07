package idealindustrial.tile.interfaces.base;

import gregtech.api.metatileentity.IEnergyContainer;
import idealindustrial.tile.IOType;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.fluid.II_FluidInventoryRepresentation;
import idealindustrial.util.inventory.II_InternalInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.fluids.IFluidHandler;

public interface II_BaseMachineTile extends II_BaseTile, IFluidHandler, ISidedInventory, IEnergyContainer {
    Container getServerGUI(EntityPlayer player, int internalID);

    GuiContainer getClientGUI(EntityPlayer player, int internalID);

    int[] getInventorySizes();

    II_InternalInventory getIn();

    II_InternalInventory getOut();

    II_InternalInventory getSpecial();

    boolean hasFluidTank();

    II_FluidHandler getInTank();

    II_FluidHandler getOutTank();

    II_FluidInventoryRepresentation getFluidRepresentation();

    II_EnergyHandler getEnergyHandler();

    void overVoltage();

    boolean[] getIO(IOType type);

    boolean getIOatSide(int side, IOType type, boolean input);

    void onIOConfigurationChanged();

    void notifyOnIOConfigChange(IOType type);

}
