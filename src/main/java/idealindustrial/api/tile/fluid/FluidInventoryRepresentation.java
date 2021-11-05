package idealindustrial.api.tile.fluid;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.FluidStack;

public interface FluidInventoryRepresentation extends IInventory {

   int getInSize();

   default int getOutSize() {
      return getSizeInventory() - getInSize();
   }

   FluidStack[] parseInputsClient();
   FluidStack[] parseOutputsClient();


}
