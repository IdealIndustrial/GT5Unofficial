package idealindustrial.impl.tile.gui.scheme;

import idealindustrial.impl.tile.gui.GuiRect;
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

    default boolean mouseMovedOrUp(int mx, int my, int button) {
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

    GuiRect getRect();

    void setRect(GuiRect rect);
}
