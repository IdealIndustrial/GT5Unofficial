package idealindustrial.items;

import net.minecraft.item.ItemStack;

public interface ItemElectricStats {

    long charge(ItemStack is, long amount, boolean doCharge);

    long discharge(ItemStack is, long amount, boolean doDischarge);

    boolean canAcceptVoltage(ItemStack is, long voltage);
}
