package idealindustrial.util.fluid;

import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class II_FluidHelper {

    /**
     * just interface to generify fluid item iteration
     * due to different items with fluids in minecraft it is not so fancy bit it works
     *
     * if you call some method like fill or drain with modulation flag
     * you should replace your container itemStack with getUpdated()
     */
    interface II_FluidContainerItem {
        int fill(FluidStack source, boolean doFill);
        FluidStack drain(int maxDrain, boolean doDrain);
        ItemStack getUpdated();

        FluidStack getFluid();
    }

    public static class II_FluidContainerItemImpl implements II_FluidContainerItem {

        protected ItemStack stack;
        protected IFluidContainerItem containerItem;

        public II_FluidContainerItemImpl(ItemStack stack) {
            this.stack = stack;
            if (stack.getItem() instanceof IFluidContainerItem) {
                containerItem = (IFluidContainerItem) stack.getItem();
            }
        }

        @Override
        public int fill(FluidStack source, boolean doFill) {
            if (containerItem != null) {
                return containerItem.fill(stack, source, doFill);
            }
            ItemStack filled = GT_Utility.fillFluidContainer(source, stack, false, false);
            if (filled == null) {
                return  0;
            }
            FluidStack fluidStack =  GT_Utility.getFluidForFilledItem(stack, false);
            if (doFill && fluidStack != null) {
                stack = filled;
            }
            return fluidStack == null ? 0 : fluidStack.amount;
        }

        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (containerItem != null) {
                return containerItem.drain(stack, maxDrain, doDrain);
            }
            FluidStack drained = GT_Utility.getFluidForFilledItem(stack, false);
            if (doDrain && drained != null) {
                stack = GT_Utility.getContainerForFilledItem(stack, false);
            }
            return drained;
        }

        @Override
        public ItemStack getUpdated() {
            return stack;
        }

        @Override
        public FluidStack getFluid() {
            if (containerItem != null) {
                return containerItem.getFluid(stack);
            }
            return GT_Utility.getFluidForFilledItem(stack, false);
        }
    }

    public static II_FluidContainerItem getContainerWrapper(ItemStack stack) {
        return new II_FluidContainerItemImpl(stack);
    }
}
