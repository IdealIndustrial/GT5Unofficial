package gregtech.common.gui;

import gregtech.GT_Mod;
import gregtech.api.enums.ItemList;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.gui.GT_Slot_RestrictedContents;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_ItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GT_Container_DataReader extends Container {

    public static int DataStickID = 1;

    int mRestrictedSlot;
    public GT_ItemInventory mInventory;

    public GT_Container_DataReader(InventoryPlayer aInventoryPlayer, ItemStack aTool) {
        bindPlayerInventory(aInventoryPlayer, 0, 90);
        mInventory = new GT_ItemInventory(3, false, "Data Reader", () -> saveToNBT(aInventoryPlayer.getCurrentItem()));
        if (aTool.getTagCompound() != null && GT_Mod.gregtechproxy.isServerSide())
            mInventory.loadFromNBT(aTool.getTagCompound());
        addSlotToContainer(new GT_Slot_RestrictedContents(mInventory, 0, 80, 84 + 68,
                aStack -> (ItemList.Tool_DataStick.isStackEqual(aStack, false, true)
                        || ItemList.Tool_CD.isStackEqual(aStack, false, true)
                        || ItemList.Tool_DataOrb.isStackEqual(aStack, false, true))));
        mRestrictedSlot = aInventoryPlayer.currentItem;

        addSlotToContainer(new GT_Slot_Holo(mInventory, 1, 2 * 18 + 8, 84 + 68, false, false, 0));
        addSlotToContainer(new GT_Slot_Holo(mInventory, 2, 6 * 18 + 8, 84 + 68, false, false, 0));

    }

    protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer, int xOffset, int yOffset) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9, xOffset + 8 + j * 18, yOffset + 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(aInventoryPlayer, i, xOffset + 8 + i * 18, yOffset + 142));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer aPlayer) {
        ItemStack aTool = aPlayer.getHeldItem();
        saveToNBT(aTool);

    }

    private void saveToNBT(ItemStack aTool) {
        if (GT_Mod.gregtechproxy.isServerSide() && aTool != null && (ItemList.Tool_DataReader_MV.isStackEqual(aTool, false, true) || ItemList.Tool_DataReader_EV.isStackEqual(aTool, false, true))) {
            NBTTagCompound tNbt = aTool.getTagCompound();
            if (tNbt == null)
                tNbt = new NBTTagCompound();
            mInventory.saveToNBT(tNbt);
            aTool.setTagCompound(tNbt);
        }
    }


    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex == (mRestrictedSlot + 27))
            return null;
        if (aShifthold == 2 && aMouseclick == mRestrictedSlot)
            return null;
        if (aShifthold == 1 || aShifthold == 6)
            return null;
        if (aSlotIndex == 37 || aSlotIndex == 38) {
            ItemStack aTool = aPlayer.getHeldItem();
            if (aTool != null && mInventory.getStackInSlot(0) != null && mInventory.getStackInSlot(0).getTagCompound() != null
                    && mInventory.getStackInSlot(0).getTagCompound().hasKey("pages")) {
                NBTTagList list = mInventory.getStackInSlot(0).getTagCompound().getTagList("pages", 8);
                if (list != null) {
                    int bookSize = list.tagCount();
                    NBTTagCompound tNBT = aTool.getTagCompound();
                    if (tNBT == null)
                        tNBT = new NBTTagCompound();
                    int i = tNBT.getInteger("page");
                    if (aSlotIndex == 37)
                        i--;
                    else
                        i++;
                    if (i < 0)
                        i = 0;
                    if (i >= bookSize)
                        i = bookSize - 1;
                    tNBT.setInteger("page", i);
                    aTool.setTagCompound(tNBT);
                }
            }


        } else if (aSlotIndex == 36) {
            ItemStack before = mInventory.getStackInSlot(0);
            ItemStack tmp = super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
            ItemStack after = mInventory.getStackInSlot(0);
            if (before == null ^ after == null || !GT_Utility.areStacksEqual(before, after, false)) {
                ItemStack aTool = aPlayer.getHeldItem();
                if (aTool == null) {
                    return tmp;
                }
                NBTTagCompound tNbt = aTool.getTagCompound();
                if (tNbt == null) {
                    tNbt = new NBTTagCompound();
                }
                tNbt.setInteger("page", 0);
                aTool.setTagCompound(tNbt);

            }
            return tmp;
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer aPlayer) {
        return ItemList.Tool_DataReader_MV.isStackEqual(aPlayer.getHeldItem(), false, true) || ItemList.Tool_DataReader_EV.isStackEqual(aPlayer.getHeldItem(), false, true);
    }

    @Override
    public void putStackInSlot(int par1, ItemStack par2ItemStack) {
        try {
            super.putStackInSlot(par1, par2ItemStack);
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
    }

    @Override
    public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack) {
        try {
            super.putStacksInSlots(par1ArrayOfItemStack);
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
    }

    @Override
    protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
        return super.mergeItemStack(p_75135_1_, p_75135_2_, p_75135_3_, p_75135_4_);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
        return super.transferStackInSlot(p_82846_1_, p_82846_2_);
    }
}
