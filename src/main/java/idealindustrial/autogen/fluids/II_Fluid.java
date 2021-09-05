package idealindustrial.autogen.fluids;

import idealindustrial.autogen.material.submaterial.render.RenderInfo;
import idealindustrial.textures.TextureManager;
import net.minecraftforge.fluids.Fluid;

public class II_Fluid extends Fluid {

    int color;

    public II_Fluid(String fluidName) {
        super(fluidName);
    }

    public II_Fluid setRender(RenderInfo info) {
        color = info.getColorAsInt();
        TextureManager.INSTANCE.onPostIconLoad(() -> setIcons(info.getTexture().getIcon()));
        return this;
    }

    @Override
    public int getColor() {
        return color;
    }


    @Override
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }
}
