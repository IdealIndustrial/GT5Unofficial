package idealindustrial.impl.tile.gui.base.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonCallback extends GuiButton {
    Callback callback;
    public GuiButtonCallback(int id, int x, int y, int width, int height, String text, Callback callback) {
        super(id, x, y, width, height, text);
        this.callback = callback;
    }

    @Override
    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
        if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
            callback.onPressed();
            return true;
        }
        return false;
    }

    public interface Callback {
        void onPressed();
    }
}
