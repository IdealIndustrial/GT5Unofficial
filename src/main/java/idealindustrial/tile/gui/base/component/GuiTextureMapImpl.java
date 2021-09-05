package idealindustrial.tile.gui.base.component;

import net.minecraft.util.ResourceLocation;

public class GuiTextureMapImpl implements GuiTextureMap {

    private static final int mapTextureSize = 256;
    ResourceLocation location;
    int textureXSize,  textureYSize;
    int rowCount, columnCount;
    public GuiTextureMapImpl(String location, int textureXSize, int textureYSize) {
        this.location = new ResourceLocation(location);
        this.textureXSize = textureXSize;
        this.textureYSize = textureYSize;
        rowCount = mapTextureSize / textureYSize;
        columnCount = mapTextureSize / textureXSize;

    }

    @Override
    public int idToTextureX(int id) {
        int column = id % columnCount;
        return column * textureXSize;
    }

    @Override
    public int idToTextureY(int id) {
        int row = id / columnCount;
        return row * textureYSize;
    }

    @Override
    public ResourceLocation location() {
        return location;
    }
}
