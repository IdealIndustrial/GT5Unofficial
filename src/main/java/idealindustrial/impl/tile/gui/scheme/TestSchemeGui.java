package idealindustrial.impl.tile.gui.scheme;

import idealindustrial.impl.tile.gui.base.GenericContainer;
import idealindustrial.impl.tile.gui.base.GenericGuiContainer;
import idealindustrial.impl.world.util.Vector2;

public class TestSchemeGui extends GenericGuiContainer<GenericContainer> {
    public TestSchemeGui(GenericContainer container, String background) {
        super(container, background);

    }

    @Override
    public void initGui() {
        super.initGui();
        addWidget(new GuiNetScheme());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        widgets.forEach(w -> w.drawGuiContainerForegroundLayer(mx, my));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float tmp, int mx, int my) {
        widgets.forEach(w -> w.drawBackground(tmp, mx, my));
    }


    @Override
    protected void mouseClickMove(int mx, int my, int button, long timeSince) {
        super.mouseClickMove(mx, my, button, timeSince);
    }

    @Override
    protected void mouseMovedOrUp(int mx, int my, int button) {
        super.mouseMovedOrUp(mx, my, button);
    }

    @Override
    protected void mouseClicked(int mx, int my, int buttons) {
        super.mouseClicked(mx, my, buttons);
    }
}
