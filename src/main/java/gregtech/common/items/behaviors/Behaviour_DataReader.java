package gregtech.common.items.behaviors;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.enums.ItemList;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_Container_DataReader;
import gregtech.common.gui.GT_GUIContainer_DataReader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_DataReader
        extends Behaviour_HasGui {

    int mTier;

    public Behaviour_DataReader(int aTier) {
        mTier = aTier;
    }

    @Override
    public Object getClientGui(EntityPlayer aPlayer, ItemStack aHeldItem, int aData) {
        return new GT_GUIContainer_DataReader(aHeldItem, aPlayer);
    }

    @Override
    public Object getServerGui(EntityPlayer aPlayer, ItemStack aHeldItem, int aData) {
        return new GT_Container_DataReader(aPlayer.inventory, aHeldItem);
    }

    @Override
    public void onUpdate(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, Entity aPlayer, int aTimer, boolean aIsInHand) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            return;
        NBTTagCompound nbt = aStack == null ? null : aStack.getTagCompound();
        if (nbt == null)
            return;
        if (nbt.getInteger("prog") > 0) {
            int tCharge = nbt.getInteger("GT.ItemCharge");
            tCharge -= 8 * (1 << (2 * (mTier - 2)));
            if (tCharge < 0) {
                nbt.removeTag("prog");
                tCharge = 0;
            }
            nbt.setInteger("GT.ItemCharge", tCharge);
            //remove energy
            ItemStack tStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("s0"));
            if (ItemList.Tool_DataStick.isStackEqual(tStack, false, true) || ItemList.Tool_CD.isStackEqual(tStack, false, true)) {
                int tProgress = 0;
                nbt.setInteger("prog", tProgress = nbt.getInteger("prog") + 1);
                if (tProgress >= 1000 / (1 << (mTier - 2))) {
                    if (GT_Utility.ItemNBT.getBookTitle(tStack).equals("Raw Prospection Data")) {
                        GT_Utility.ItemNBT.setBookTitle(tStack, "Analyzed Prospection Data");
                        GT_Utility.ItemNBT.convertProspectionData(tStack);
                        nbt.removeTag("prog");
                        nbt.setTag("s0", tStack.writeToNBT(new NBTTagCompound()));
                        Container container = ((EntityPlayer) aPlayer).openContainer;
                        if (container instanceof GT_Container_DataReader) {//if gui is open we should replace stack with new stack or it will save old stack
                            ((GT_Container_DataReader) container).mInventory.setInventorySlotContents(0, tStack);
                        }
                    }

                }
            }
            else {
                nbt.setInteger("prog", 0);
            }
        } else {
            ItemStack tStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("s0"));
            if ((ItemList.Tool_DataStick.isStackEqual(tStack, false, true) || ItemList.Tool_CD.isStackEqual(tStack, false, true))) {
                if (GT_Utility.ItemNBT.getBookTitle(tStack).equals("Raw Prospection Data")) {
                    nbt.setInteger("prog", 1);
                }
            } else {
                nbt.removeTag("prog");
            }

        }
        aStack.setTagCompound(nbt);
    }

    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        int tProgress = 0;
        if (aStack != null && aStack.getTagCompound() != null && (tProgress = aStack.getTagCompound().getInteger("prog")) > 0) {
            aList.add("Analyzing data, progress: " + tProgress / 20 + "/" + 50 / (1 << (mTier - 2)));
            String s = aList.get(2);
            aList.set(2, aList.get(3));
            aList.set(3, s);
        }
        return super.getAdditionalToolTips(aItem, aList, aStack);
    }
}
