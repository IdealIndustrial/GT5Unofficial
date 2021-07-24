package appeng.util.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class II_Hackery {

    public static AESharedNBT getNBTHackery(NBTTagCompound nbtTagCompound, ItemStack is) {
        return (AESharedNBT) AESharedNBT.getSharedTagCompound(nbtTagCompound, is);
    }
}
