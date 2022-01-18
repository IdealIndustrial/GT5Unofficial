package idealindustrial.impl.tile.gui.scheme;

import net.minecraft.nbt.NBTTagCompound;

public interface GuiWidget {

    default void drawGuiContainerForegroundLayer(int mx, int my) {
    }

    default void drawBackground(float tmp, int mx, int my) {
    }


    //true for stop event and processing
    default boolean mouseClickMove(int mx, int my, int button, long timeSince) {
        return false;
    }

    default boolean mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        return false;
    }

    default boolean mouseClicked(int mx, int my, int buttons) {
        return false;
    }

    default void clientNBTSave(NBTTagCompound nbt) {

    }

    default void clientNBTLoad(NBTTagCompound nbt) {

    }

    default void serverNBTSave(NBTTagCompound nbt) {

    }

    default void serverNBTLoad(NBTTagCompound nbt) {

    }
}
