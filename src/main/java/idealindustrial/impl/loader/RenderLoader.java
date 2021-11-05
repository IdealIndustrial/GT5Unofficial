package idealindustrial.impl.loader;

import idealindustrial.impl.render.GT_Renderer_Block;
import idealindustrial.impl.render.MachineItemRenderer;
import idealindustrial.impl.render.MetaGeneratedItem_Renderer;
import idealindustrial.impl.render.MetaToolRenderer;

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
