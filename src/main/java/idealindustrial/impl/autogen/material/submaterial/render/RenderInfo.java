package idealindustrial.impl.autogen.material.submaterial.render;

import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.util.misc.II_StreamUtil;

import java.awt.*;

public class RenderInfo {

    protected Color[] colors = II_StreamUtil.arrayOf(Color.WHITE, new Color[4]);
    protected TextureSet textureSet;

    public RenderInfo(TextureSet set) {
        this.textureSet = set;
    }

    public Color getColor(MatterState state) {
        return colors[state.ordinal()];
    }

    public int getColorAsInt(MatterState state) {
        Color color = getColor(state);
        return (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
    }

    public int[] getColorAsArray(MatterState state) {
        Color color = getColor(state);
        assert color != null;
        return new int[]{color.getRed(), color.getGreen(), color.getBlue(), 0};
    }

    public TextureSet getTextureSet() {
        return textureSet;
    }

    public void setColor(MatterState state, Color color) {
        colors[state.ordinal()] = color;
    }

}
