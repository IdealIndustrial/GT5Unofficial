package idealindustrial.hooks;

import appeng.client.gui.implementations.GuiMEMonitorable;
import codechicken.nei.BookmarkPanel;
import extracells.gui.GuiFluidTerminal;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import net.bdew.neiaddons.appeng.AddonAppeng;
import net.bdew.neiaddons.appeng.AppEngGuiHandler;
import net.bdew.neiaddons.appeng.SetFakeSlotCommandHandler;
import net.bdew.neiaddons.appeng.SlotHelper;
import net.bdew.neiaddons.network.ClientHandler;
import net.bdew.neiaddons.network.PacketHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class II_NEIAddonsPatch {

    public static int MAX_STACK_SIZE = 1000;

    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ALWAYS)
    public static void handle(SetFakeSlotCommandHandler handler, NBTTagCompound data, EntityPlayerMP player) {
        ItemStack stack = ItemStack.loadItemStackFromNBT(data.getCompoundTag("item"));
        stack.stackSize = data.getCompoundTag("item").getInteger("cnt");
        int slotNum = data.getInteger("slot");
        Container cont = player.openContainer;
        if (AddonAppeng.clsBaseContainer.isInstance(cont)) {
            Slot slot = cont.getSlot(slotNum);
            if (AddonAppeng.clsSlotFake.isInstance(slot) && SlotHelper.isSlotEnabled(slot)) {
                slot.putStack(stack);
            }
        }
    }

    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ALWAYS)
    public static boolean handleDragNDrop(AppEngGuiHandler handler, GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button) {
        if (AddonAppeng.clsBaseGui.isInstance(gui)) {
            int slotNum = -1;
            Slot slot = null;

            for(int k = 0; k < gui.inventorySlots.inventorySlots.size(); ++k) {
                slot = (Slot)gui.inventorySlots.inventorySlots.get(k);
                if (isMouseOverSlot(handler, gui, slot, mousex, mousey)) {
                    slotNum = k;
                    break;
                }
            }

            if (slotNum > 0 && AddonAppeng.clsSlotFake.isInstance(slot) && SlotHelper.isSlotEnabled(slot)) {
                if (ClientHandler.enabledCommands.contains("SetAE2FakeSlot")) {
                    NBTTagCompound data = new NBTTagCompound();
                    data.setInteger("slot", slotNum);
                    NBTTagCompound item = new NBTTagCompound();
                    item.setInteger("cnt", Math.min(MAX_STACK_SIZE, draggedStack.stackSize));
                    draggedStack.writeToNBT(item);
                    data.setTag("item", item);
                    PacketHelper.sendToServer("SetAE2FakeSlot", data);
                    return true;
                }

                Minecraft.getMinecraft().thePlayer
                        .addChatComponentMessage(
                                (new ChatComponentTranslation("bdew.neiaddons.noserver"))
                                        .setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.RED)));
            }
        }

        return false;
    }

    private static Method isMouseOver = null;

    private static Method getMouseOver() {
        if (isMouseOver == null) {
            Class<?> c = AppEngGuiHandler.class;
            try {
                Method method = c.getDeclaredMethod("isMouseOverSlot", GuiContainer.class, Slot.class, int.class, int.class);
                method.setAccessible(true);
                isMouseOver = method;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return isMouseOver;
    }
    private static boolean isMouseOverSlot( AppEngGuiHandler handler, GuiContainer gui, Slot slot, int mouseX, int mouseY) {
        try {
            return (boolean) getMouseOver().invoke(handler, gui, slot, mouseX, mouseY);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return false;
    }


}
