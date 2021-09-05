package idealindustrial.tile.gui.base.component;

import net.minecraft.util.ResourceLocation;

public interface GuiTextureMap {

    int idToTextureX(int id);
    int idToTextureY(int id);

    ResourceLocation location();

}
