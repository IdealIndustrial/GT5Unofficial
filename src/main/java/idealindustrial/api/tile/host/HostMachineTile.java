package idealindustrial.api.tile.host;

import idealindustrial.impl.tile.IOType;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.energy.electric.EnergyHandler;
import idealindustrial.api.tile.energy.kinetic.KineticEnergyHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.function.Consumer;

public interface HostMachineTile extends HostTile, IFluidHandler, ISidedInventory, NetworkedInventory {

    Container getServerGUI(EntityPlayer player, int internalID);

    GuiContainer getClientGUI(EntityPlayer player, int internalID);

    int[] getInventorySizes();

    EnergyHandler getEnergyHandler();

    KineticEnergyHandler getKineticEnergyHandler();

    void reloadIOContainers(Tile<?> tile);

    void overVoltage();

    boolean[] getIO(IOType type);

    boolean calculateIOatSide(int side, IOType type, boolean input);

    void onIOConfigurationChanged(IOType type);

    void onIOConfigurationChanged(Consumer<IOType> listener);

    void notifyOnIOConfigChange(IOType type);

    void placedByPlayer(EntityPlayer player);

}
