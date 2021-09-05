package idealindustrial.tile.gui.base.component;

import idealindustrial.util.misc.II_Paths;

public class GuiTextures {

    public static final GuiTextureMap SLOTS = new GuiTextureMapImpl(II_Paths.SLOTS, 18, 18);
    public static final GuiTextureMap PROCESSING_ARROWS = new GuiTextureMapImpl(II_Paths.GUI_ARROWS, 20 * 2, 17);

    public static class SlotTextures {
        public static final int SLOT_DEFAULT = 0;
        public static final int SLOT_FLUID_DEFAULT = 2;
    }
}
