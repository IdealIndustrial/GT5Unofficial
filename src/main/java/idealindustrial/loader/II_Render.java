package idealindustrial.loader;

import idealindustrial.render.II_MachineItemRenderer;
import idealindustrial.render.II_MetaGeneratedItem_Renderer;

public class II_Render implements II_Loader {
    @Override
    public boolean isClient() {
        return true;
    }


    public void load() {
        new II_MetaGeneratedItem_Renderer();
        new II_MachineItemRenderer();
    }
}
