package idealindustrial.impl.loader;

import idealindustrial.impl.render.GT_Renderer_Block;
import idealindustrial.impl.render.MachineItemRenderer;
import idealindustrial.impl.render.MetaItem_Renderer;
import idealindustrial.impl.render.MetaToolRenderer;

public class RenderLoader implements ILoader {
    @Override
    public boolean isClient() {
        return true;
    }


    public void load() {
        new MetaItem_Renderer();
        new MachineItemRenderer();
        new GT_Renderer_Block();
        new MetaToolRenderer();
    }
}
