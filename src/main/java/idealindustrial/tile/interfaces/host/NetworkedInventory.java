package idealindustrial.tile.interfaces.host;

import idealindustrial.util.fluid.FluidHandler;
import idealindustrial.util.fluid.FluidInventoryRepresentation;
import idealindustrial.util.inventory.interfaces.InternalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public interface NetworkedInventory extends IInventory {
    InternalInventory getIn();

    InternalInventory getOut();

    InternalInventory getSpecial();

    boolean hasFluidTank();

    FluidHandler getInTank();

    FluidHandler getOutTank();

    FluidInventoryRepresentation getFluidRepresentation();

    default boolean canInteractWith(EntityPlayer player) {
        return isUseableByPlayer(player);
    }
}
