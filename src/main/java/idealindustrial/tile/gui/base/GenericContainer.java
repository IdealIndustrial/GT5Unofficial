package idealindustrial.tile.gui.base;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.gui.GT_Slot_Output;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.util.fluid.FluidHandler;
import idealindustrial.util.fluid.EmptyFluidRepresentation;
import idealindustrial.util.fluid.II_FluidHelper;
import idealindustrial.util.fluid.FluidInventoryRepresentation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GenericContainer extends Container {

    public BaseMachineTile tile;
    public EntityPlayer player;
    public boolean bindInventory;
    public FluidInventoryRepresentation representation;

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return player.getDistance(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()) < 5d;
    }

    public GenericContainer(BaseMachineTile tile, EntityPlayer player, boolean addSlots, boolean bindInventory) {
        this.tile = tile;
        if (tile.hasFluidTank()) {
            representation = tile.getFluidRepresentation();
        }
        else {
            representation = EmptyFluidRepresentation.INSTANCE;
        }
        this.player = player;
        if (addSlots) {
            addSlots();
        }
        this.bindInventory = bindInventory;
        if (bindInventory) {
            bindPlayerInventory(player.inventory, 0, 0);
        }


    }

    public void addSlots() {

    }

    protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer, int xOffset, int yOffset) {

        int idOffset = 0;
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(aInventoryPlayer, i + idOffset, xOffset + 8 + i * 18, yOffset + 142));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9 + idOffset, xOffset + 8 + j * 18, yOffset + 84 + i * 18));
            }
        }


    }


    @Override
    public ItemStack slotClick(int index, int mouse, int hotkeys, EntityPlayer player) {
        tile.markDirty();
        if (index >= getFluidSlotStart() && index < getFluidSlotStart() + getFluidSlotCount()) {
            return processFluids(index, mouse, hotkeys, player);
        }
        try {
            if (hotkeys == 5) {
                return super.slotClick(index, mouse, hotkeys, player);
            } else {
                return slotClick1(index, mouse, hotkeys, player);
            }

        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
            e.printStackTrace();
        }
        return null;
    }

    public ItemStack processFluids(int index, int mouse, int hotkeys, EntityPlayer player) {
        int internalIndex = index - getFluidSlotStart();
        FluidHandler handler;
        if (internalIndex < representation.getInSize()) {
            handler = tile.getInTank();
        } else {
            handler = tile.getOutTank();
            internalIndex -= representation.getInSize();
        }
        return processFluidInventory(handler, index, internalIndex, mouse, hotkeys, player);
    }

    public ItemStack processFluidInventory(FluidHandler handler, int index, int internalIndex, int mouse, int hotkeys, EntityPlayer player) {
        ItemStack held = player.inventory.getItemStack();
        if (held == null || held.stackSize != 1 || mouse > 1 || FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            return null;
        }

//        if (held.getItem() instanceof IFluidContainerItem) {
//            IFluidContainerItem item = (IFluidContainerItem) held.getItem();
        II_FluidHelper.II_FluidContainerItem item = II_FluidHelper.getContainerWrapper(held);
        FluidStack itemFluid = item.getFluid();
        FluidStack tileFluid = handler.get(internalIndex);
        if (mouse == 0) {//fill item
            if (tileFluid == null) {
                return null;
            }
            int filled = item.fill(tileFluid, true);
            if (filled > 0) {
                if ((tileFluid.amount -= filled) == 0) {
                    tileFluid = null;
                }
                held = item.getUpdated();
                player.inventory.setItemStack(held);
                handler.set(internalIndex, tileFluid);
                return held;
            }
            return null;
        } else {//fill tile
            if (itemFluid == null) {
                return null;
            }
            if (tileFluid == null) {
                FluidStack drained = item.drain(handler.capacity(), true);
                if (drained != null) {
                    handler.set(internalIndex, drained);
                    held = item.getUpdated();
                    player.inventory.setItemStack(held);
                }
            } else if (tileFluid.getFluid() == itemFluid.getFluid()) {
                int space = Math.max(handler.capacity() - tileFluid.amount, 0);
                FluidStack drained = item.drain(space, true);
                if (drained != null && drained.amount > 0) {
                    tileFluid.amount += drained.amount;
                    handler.set(internalIndex, tileFluid);
                    held = item.getUpdated();
                    player.inventory.setItemStack(held);
                }
            } else {
                return null;
            }
            player.inventory.setItemStack(held);
            return held;
        }

//        }
//        return null;
    }

    public ItemStack slotClick1(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_) {
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = p_75144_4_.inventory;
        int i1;
        ItemStack itemstack3;


//        else if (this.field_94536_g != 0)
//        {
//            this.func_94533_d();
//        }
//        else
        {
            Slot slot2;
            int l1;
            ItemStack itemstack5;

            if ((p_75144_3_ == 0 || p_75144_3_ == 1) && (p_75144_2_ == 0 || p_75144_2_ == 1)) {
                if (p_75144_1_ == -999) {
                    if (inventoryplayer.getItemStack() != null && p_75144_1_ == -999) {
                        if (p_75144_2_ == 0) {
                            p_75144_4_.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
                            inventoryplayer.setItemStack((ItemStack) null);
                        }

                        if (p_75144_2_ == 1) {
                            p_75144_4_.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);

                            if (inventoryplayer.getItemStack().stackSize == 0) {
                                inventoryplayer.setItemStack((ItemStack) null);
                            }
                        }
                    }
                } else if (p_75144_3_ == 1) {
                    if (p_75144_1_ < 0) {
                        return null;
                    }

                    slot2 = (Slot) this.inventorySlots.get(p_75144_1_);

                    if (slot2 != null && slot2.canTakeStack(p_75144_4_)) {
                        itemstack3 = this.transferStackInSlot(p_75144_4_, p_75144_1_);

                        if (itemstack3 != null) {
                            Item item = itemstack3.getItem();
                            itemstack = itemstack3.copy();

                            if (slot2.getStack() != null && slot2.getStack().getItem() == item) {
                                this.retrySlotClick(p_75144_1_, p_75144_2_, true, p_75144_4_);
                            }
                        }
                    }
                } else {
                    if (p_75144_1_ < 0) {
                        return null;
                    }

                    slot2 = (Slot) this.inventorySlots.get(p_75144_1_);

                    if (slot2 != null) {
                        itemstack3 = slot2.getStack();
                        ItemStack itemstack4 = inventoryplayer.getItemStack();

                        if (itemstack3 != null) {
                            itemstack = itemstack3.copy();
                        }

                        if (itemstack3 == null) {
                            if (itemstack4 != null && slot2.isItemValid(itemstack4)) {
                                l1 = p_75144_2_ == 0 ? itemstack4.stackSize : 1;

                                if (l1 > slot2.getSlotStackLimit()) {
                                    l1 = slot2.getSlotStackLimit();
                                }

                                if (itemstack4.stackSize >= l1) {
                                    slot2.putStack(itemstack4.splitStack(l1));
                                }

                                if (itemstack4.stackSize == 0) {
                                    inventoryplayer.setItemStack((ItemStack) null);
                                }
                            }
                        } else if (slot2.canTakeStack(p_75144_4_)) {
                            if (itemstack4 == null) {
                                l1 = p_75144_2_ == 0 ? itemstack3.stackSize : (itemstack3.stackSize + 1) / 2;
                                itemstack5 = slot2.decrStackSize(l1);
                                inventoryplayer.setItemStack(itemstack5);

                                if (itemstack3.stackSize == 0) {
                                    slot2.putStack((ItemStack) null);
                                }

                                slot2.onPickupFromSlot(p_75144_4_, inventoryplayer.getItemStack());
                            } else if (slot2.isItemValid(itemstack4)) {
                                if (itemstack3.getItem() == itemstack4.getItem() && itemstack3.getItemDamage() == itemstack4.getItemDamage() && ItemStack.areItemStackTagsEqual(itemstack3, itemstack4)) {
                                    l1 = p_75144_2_ == 0 ? itemstack4.stackSize : 1;

                                    if (l1 > slot2.getSlotStackLimit() - itemstack3.stackSize) {
                                        l1 = slot2.getSlotStackLimit() - itemstack3.stackSize;
                                    }

                                    if (l1 > itemstack4.getMaxStackSize() - itemstack3.stackSize) {
                                        l1 = itemstack4.getMaxStackSize() - itemstack3.stackSize;
                                    }

                                    itemstack4.splitStack(l1);

                                    if (itemstack4.stackSize == 0) {
                                        inventoryplayer.setItemStack((ItemStack) null);
                                    }

                                    itemstack3.stackSize += l1;
                                    slot2.putStack(itemstack3);

                                } else if (itemstack4.stackSize <= slot2.getSlotStackLimit()) {
                                    slot2.putStack(itemstack4);
                                    inventoryplayer.setItemStack(itemstack3);
                                }
                            } else if (itemstack3.getItem() == itemstack4.getItem() && itemstack4.getMaxStackSize() > 1 && (!itemstack3.getHasSubtypes() || itemstack3.getItemDamage() == itemstack4.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemstack3, itemstack4)) {
                                l1 = itemstack3.stackSize;

                                if (l1 > 0 && l1 + itemstack4.stackSize <= itemstack4.getMaxStackSize()) {
                                    itemstack4.stackSize += l1;
                                    itemstack3 = slot2.decrStackSize(l1);

                                    if (itemstack3.stackSize == 0) {
                                        slot2.putStack((ItemStack) null);
                                    }

                                    slot2.onPickupFromSlot(p_75144_4_, inventoryplayer.getItemStack());
                                }
                            }
                        }

                        slot2.onSlotChanged();
                    }
                }
            } else if (p_75144_3_ == 2 && p_75144_2_ >= 0 && p_75144_2_ < 9) {
                slot2 = (Slot) this.inventorySlots.get(p_75144_1_);

                if (slot2.canTakeStack(p_75144_4_)) {
                    itemstack3 = inventoryplayer.getStackInSlot(p_75144_2_);
                    boolean flag = itemstack3 == null || slot2.inventory == inventoryplayer && slot2.isItemValid(itemstack3);
                    l1 = -1;

                    if (!flag) {
                        l1 = inventoryplayer.getFirstEmptyStack();
                        flag = l1 > -1;
                    }

                    if (slot2.getHasStack() && flag) {
                        itemstack5 = slot2.getStack();
                        inventoryplayer.setInventorySlotContents(p_75144_2_, itemstack5.copy());

                        if ((slot2.inventory != inventoryplayer || !slot2.isItemValid(itemstack3)) && itemstack3 != null) {
                            if (l1 > -1) {
                                inventoryplayer.addItemStackToInventory(itemstack3);
                                slot2.decrStackSize(itemstack5.stackSize);
                                slot2.putStack((ItemStack) null);
                                slot2.onPickupFromSlot(p_75144_4_, itemstack5);
                            }
                        } else {
                            slot2.decrStackSize(itemstack5.stackSize);
                            slot2.putStack(itemstack3);
                            slot2.onPickupFromSlot(p_75144_4_, itemstack5);
                        }
                    } else if (!slot2.getHasStack() && itemstack3 != null && slot2.isItemValid(itemstack3)) {
                        inventoryplayer.setInventorySlotContents(p_75144_2_, (ItemStack) null);
                        slot2.putStack(itemstack3);
                    }
                }
            } else if (p_75144_3_ == 3 && p_75144_4_.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && p_75144_1_ >= 0) {
                slot2 = (Slot) this.inventorySlots.get(p_75144_1_);

                if (slot2 != null && slot2.getHasStack()) {
                    itemstack3 = slot2.getStack().copy();
                    itemstack3.stackSize = itemstack3.getMaxStackSize();
                    inventoryplayer.setItemStack(itemstack3);
                }
            } else if (p_75144_3_ == 4 && inventoryplayer.getItemStack() == null && p_75144_1_ >= 0) {
                slot2 = (Slot) this.inventorySlots.get(p_75144_1_);

                if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(p_75144_4_)) {
                    itemstack3 = slot2.decrStackSize(p_75144_2_ == 0 ? 1 : slot2.getStack().stackSize);
                    slot2.onPickupFromSlot(p_75144_4_, itemstack3);
                    p_75144_4_.dropPlayerItemWithRandomChoice(itemstack3, true);
                }
            } else if (p_75144_3_ == 6 && p_75144_1_ >= 0) {
                slot2 = (Slot) this.inventorySlots.get(p_75144_1_);
                itemstack3 = inventoryplayer.getItemStack();

                if (itemstack3 != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(p_75144_4_))) {
                    i1 = p_75144_2_ == 0 ? 0 : this.inventorySlots.size() - 1;
                    l1 = p_75144_2_ == 0 ? 1 : -1;

                    for (int i2 = 0; i2 < 2; ++i2) {
                        for (int j2 = i1; j2 >= 0 && j2 < this.inventorySlots.size() && itemstack3.stackSize < itemstack3.getMaxStackSize(); j2 += l1) {
                            Slot slot3 = (Slot) this.inventorySlots.get(j2);

                            if (slot3.getHasStack() && func_94527_a(slot3, itemstack3, true) && slot3.canTakeStack(p_75144_4_) && this.func_94530_a(itemstack3, slot3) && (i2 != 0 || slot3.getStack().stackSize != slot3.getStack().getMaxStackSize())) {
                                int k1 = Math.min(itemstack3.getMaxStackSize() - itemstack3.stackSize, slot3.getStack().stackSize);
                                ItemStack itemstack2 = slot3.decrStackSize(k1);
                                itemstack3.stackSize += k1;

                                if (itemstack2.stackSize <= 0) {
                                    slot3.putStack((ItemStack) null);
                                }

                                slot3.onPickupFromSlot(p_75144_4_, itemstack2);
                            }
                        }
                    }
                }

                this.detectAndSendChanges();
            }
        }

        return itemstack;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        tile.markDirty();

        if (getSlotCount() > 0 && !(slotObject instanceof GT_Slot_Holo) && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = GT_Utility.copy(stackInSlot);

            //TileEntity -> Player
            if (slot < getAllSlotCount()) {
                if (bindInventory)
                    if (!mergeItemStack(stackInSlot, getAllSlotCount(), getAllSlotCount() + 36, true)) {
                        return null;
                    }
                //Player -> TileEntity
            } else if (!mergeItemStack(stackInSlot, getShiftClickStartIndex(), getShiftClickStartIndex() + getShiftClickSlotCount(), false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }
        }
        return stack;
    }

    @Override
    protected boolean mergeItemStack(ItemStack aStack, int aStartIndex, int aSlotCount, boolean par4) {
        boolean var5 = false;
        int var6 = aStartIndex;

        tile.markDirty();

        if (par4) {
            var6 = aSlotCount - 1;
        }

        Slot var7;
        ItemStack var8;

        if (aStack.isStackable()) {
            while (aStack.stackSize > 0 && (!par4 && var6 < aSlotCount || par4 && var6 >= aStartIndex)) {
                var7 = (Slot) this.inventorySlots.get(var6);
                var8 = var7.getStack();
                if (!(var7 instanceof GT_Slot_Holo) && !(var7 instanceof GT_Slot_Output) && var8 != null && var8.getItem() == aStack.getItem() && (!aStack.getHasSubtypes() || aStack.getItemDamage() == var8.getItemDamage()) && ItemStack.areItemStackTagsEqual(aStack, var8)) {
                    int var9 = var8.stackSize + aStack.stackSize;
                    if (var8.stackSize < tile.getInventoryStackLimit()) {
                        if (var9 <= aStack.getMaxStackSize()) {
                            aStack.stackSize = 0;
                            var8.stackSize = var9;
                            var7.onSlotChanged();
                            var7.putStack(var8);
                            var5 = true;
                        } else if (var8.stackSize < aStack.getMaxStackSize()) {
                            aStack.stackSize -= aStack.getMaxStackSize() - var8.stackSize;
                            var8.stackSize = aStack.getMaxStackSize();
                            var7.onSlotChanged();
                            var7.putStack(var8);
                            var5 = true;
                        }
                    }
                }

                if (par4) {
                    --var6;
                } else {
                    ++var6;
                }
            }
        }
        if (aStack.stackSize > 0) {
            if (par4) {
                var6 = aSlotCount - 1;
            } else {
                var6 = aStartIndex;
            }

            while (!par4 && var6 < aSlotCount || par4 && var6 >= aStartIndex) {
                var7 = (Slot) this.inventorySlots.get(var6);
                var8 = var7.getStack();

                if (var8 == null) {
                    int var10 = Math.min(aStack.stackSize, tile.getInventoryStackLimit());
                    var7.putStack(GT_Utility.copyAmount(var10, aStack));
                    var7.onSlotChanged();
                    aStack.stackSize -= var10;
                    var5 = true;
                    break;
                }

                if (par4) {
                    --var6;
                } else {
                    ++var6;
                }
            }
        }

        return var5;
    }

    public int getShiftClickSlotCount() {
        return tile.getIn().size();
    }

    public int getShiftClickStartIndex() {
        return 0;
    }

    public int getAllSlotCount() {
        return getSlotCount();
    }

    public int getSlotCount() {
        return tile.getIn().size() + tile.getOut().size() + tile.getSpecial().size();
    }

    public int getFluidSlotStart() {
        return getAllSlotCount();
    }

    public int getFluidSlotCount() {
        return representation == null ? 0 : representation.getSizeInventory();
    }

    @Override
    public short getNextTransactionID(InventoryPlayer p_75136_1_) {
        return super.getNextTransactionID(p_75136_1_);
    }
}
