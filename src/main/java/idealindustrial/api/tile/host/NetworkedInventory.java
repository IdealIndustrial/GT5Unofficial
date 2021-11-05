package idealindustrial.api.tile.host;

import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.fluid.FluidInventoryRepresentation;
import idealindustrial.api.tile.inventory.InternalInventory;
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
