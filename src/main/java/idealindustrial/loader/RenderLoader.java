package idealindustrial.loader;

import idealindustrial.render.MachineItemRenderer;
import idealindustrial.render.MetaGeneratedItem_Renderer;

public class RenderLoader implements ILoader {
    @Override
    public boolean isClient() {
        return true;
    }


    public void load() {
        new MetaGeneratedItem_Renderer();
        new MachineItemRenderer();
    }
}
