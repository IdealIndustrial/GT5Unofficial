package idealindustrial.impl.tile.gui.scheme;

import idealindustrial.impl.tile.gui.base.GenericContainer;
import idealindustrial.impl.tile.gui.base.GenericGuiContainer;
import net.minecraft.inventory.Slot;

public class TestSchemeGui extends GenericGuiContainer<GenericContainer> {
    public TestSchemeGui(GenericContainer container, String background) {
        super(container, background);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        super.drawGuiContainerForegroundLayer(mx, my);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float tmp, int mx, int my) {
        super.drawGuiContainerBackgroundLayer(tmp, mx, my);
    }


    @Override
    protected void mouseClickMove(int mx, int my, int button, long timeSince) {
        super.mouseClickMove(mx, my, button, timeSince);
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }

    @Override
    protected void mouseClicked(int mx, int my, int buttons) {
        super.mouseClicked(mx, my, buttons);
    }
}
