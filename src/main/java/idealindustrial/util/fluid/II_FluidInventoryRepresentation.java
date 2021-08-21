package idealindustrial.util.fluid;

import net.minecraft.inventory.IInventory;

public interface II_FluidInventoryRepresentation extends IInventory {

   int getInSize();

   default int getOutSize() {
      return getSizeInventory() - getInSize();
   }
}
