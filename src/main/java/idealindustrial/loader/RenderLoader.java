package idealindustrial.loader;

import idealindustrial.render.GT_Renderer_Block;
import idealindustrial.render.MachineItemRenderer;
import idealindustrial.render.MetaGeneratedItem_Renderer;
import idealindustrial.render.MetaToolRenderer;

public class RenderLoader implements ILoader {
    @Override
    public boolean isClient() {
        return true;
    }


    public void load() {
        new MetaGeneratedItem_Renderer();
        new MachineItemRenderer();
        new GT_Renderer_Block();
        new MetaToolRenderer();
    }
}
