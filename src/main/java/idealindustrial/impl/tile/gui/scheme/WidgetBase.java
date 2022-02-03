package idealindustrial.impl.tile.gui.scheme;

import idealindustrial.impl.tile.gui.GuiRect;

public abstract class WidgetBase implements GuiWidget {
    GuiRect rect;

    public GuiRect getRect() {
        return rect;
    }

    public void setRect(GuiRect rect) {
        this.rect = rect;
    }
}
