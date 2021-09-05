package idealindustrial.util.fluid;

import net.minecraft.inventory.IInventory;

public interface FluidInventoryRepresentation extends IInventory {

   int getInSize();

   default int getOutSize() {
      return getSizeInventory() - getInSize();
   }
}
