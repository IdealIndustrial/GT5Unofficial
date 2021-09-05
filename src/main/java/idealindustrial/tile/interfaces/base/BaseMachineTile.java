package idealindustrial.tile.interfaces.base;

import gregtech.api.metatileentity.IEnergyContainer;
import idealindustrial.tile.IOType;
import idealindustrial.util.energy.EnergyHandler;
import idealindustrial.util.fluid.FluidHandler;
import idealindustrial.util.fluid.FluidInventoryRepresentation;
import idealindustrial.util.inventory.InternalInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.fluids.IFluidHandler;

public interface BaseMachineTile extends BaseTile, IFluidHandler, ISidedInventory, IEnergyContainer {

    Container getServerGUI(EntityPlayer player, int internalID);

    GuiContainer getClientGUI(EntityPlayer player, int internalID);

    int[] getInventorySizes();

    InternalInventory getIn();

    InternalInventory getOut();

    InternalInventory getSpecial();

    boolean hasFluidTank();

    FluidHandler getInTank();

    FluidHandler getOutTank();

    FluidInventoryRepresentation getFluidRepresentation();

    EnergyHandler getEnergyHandler();

    void overVoltage();

    boolean[] getIO(IOType type);

    boolean calculateIOatSide(int side, IOType type, boolean input);

    void onIOConfigurationChanged();

    void notifyOnIOConfigChange(IOType type);

}
