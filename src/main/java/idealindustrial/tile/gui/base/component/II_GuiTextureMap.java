package idealindustrial.tile.gui.base.component;

import net.minecraft.util.ResourceLocation;

public interface II_GuiTextureMap {

    int idToTextureX(int id);
    int idToTextureY(int id);

    ResourceLocation location();

}
