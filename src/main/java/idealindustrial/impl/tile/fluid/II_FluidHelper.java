package idealindustrial.impl.tile.fluid;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustrial.impl.item.GT_FluidDisplayItem;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.util.misc.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;

import java.util.HashMap;
import java.util.Map;

public class II_FluidHelper {

    /**
     * just interface to generify fluid item iteration
     * due to different items with fluids in minecraft it is not so fancy bit it works
     *
     * if you call some method like fill or drain with modulation flag
     * you should replace your container itemStack with getUpdated()
     */
    public interface II_FluidContainerItem {
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
            ItemStack filled = fillContainer(stack, source);
            if (filled == null) {
                return  0;
            }
            FluidStack fluidStack = getFluidForContainer(filled);
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
            FluidStack drained = getFluidForContainer(stack);
            if (drained != null && drained.amount > maxDrain) {
                return null;
            }
            if (doDrain && drained != null) {
                stack = getEmptyContainer(stack);
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
            return getFluidForContainer(stack);
        }
    }

    static Map<Fluid, FluidContainerData> fluid2data = ItemHelper.queryNonRehashable(new HashMap<>());
    static Map<HashedStack, FluidContainerData> filled2data = ItemHelper.queryMap(new HashMap<>());
    static Map<HashedStack, Map<Fluid, FluidContainerData>> empty2fluid2data = ItemHelper.queryMap(new HashMap<>());

    @SubscribeEvent
    public void onFluidContainerRegistration(FluidContainerRegistry.FluidContainerRegisterEvent event) {
        fluid2data.put(event.data.fluid.getFluid(), event.data);
        filled2data.put(new HashedStack(event.data.filledContainer), event.data);
        empty2fluid2data.computeIfAbsent(new HashedStack(event.data.filledContainer), hs -> ItemHelper.queryNonRehashable(new HashMap<>()))
                .put(event.data.fluid.getFluid(), event.data);
    }

    public static II_FluidContainerItem getContainerWrapper(ItemStack stack) {
        return new II_FluidContainerItemImpl(stack);
    }

    public static ItemStack fillContainer(ItemStack container, FluidStack fs) {
        Map<Fluid, FluidContainerData> fluid2data = container == null ? null : empty2fluid2data.get(new HashedStack(container));
        if (fluid2data == null) {
            return null;
        }
        FluidContainerData data = fs == null ? null : fluid2data.get(fs.getFluid());
        if (data != null) {
            return data.filledContainer.copy();
        }
        return null;
    }

    public static FluidStack getFluidForContainer(ItemStack filled) {
        FluidContainerData data = filled == null ? null : filled2data.get(new HashedStack(filled));
        if (data  != null) {
            return data.fluid.copy();
        }
        return null;
    }

    public static ItemStack getEmptyContainer(ItemStack filled) {
        FluidContainerData data =  filled == null ? null : filled2data.get(new HashedStack(filled));
        if (data  != null) {
            return data.emptyContainer.copy();
        }
        return null;
    }

    public static ItemStack getFluidDisplayStack(FluidStack aFluid) {
        if (aFluid == null) {
            return null;
        }
        ItemStack rStack;
        rStack = new ItemStack(GT_FluidDisplayItem.INSTANCE, 1, aFluid.getFluidID());
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setString("fname", aFluid.getFluid().getName());
        tNBT.setInteger("mFluidDisplayAmount", aFluid.amount);
        tNBT.setInteger("mFluidDisplayHeat", aFluid.getFluid().getTemperature(aFluid));
        tNBT.setBoolean("mFluidState", aFluid.getFluid().isGaseous(aFluid));
        rStack.setTagCompound(tNBT);
        return rStack;
    }

    public static FluidStack getFluidFromDisplayStack(ItemStack is) {
        if (is == null) {
            return null;
        }
        NBTTagCompound nbt = is.getTagCompound();
        return FluidRegistry.getFluidStack(nbt.getString("fname"), nbt.getInteger("mFluidDisplayAmount"));
    }
}
