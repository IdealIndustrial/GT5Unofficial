package idealindustrial.impl.fluid;

import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.impl.autogen.material.submaterial.render.RenderInfo;
import idealindustrial.impl.textures.TextureManager;
import net.minecraftforge.fluids.Fluid;

public class II_Fluid extends Fluid {

    int color;

    public II_Fluid(String fluidName) {
        super(fluidName);
    }

    public II_Fluid setRender(RenderInfo info, MatterState state) {
        color = info.getColorAsInt(state);
        TextureManager.INSTANCE.onPostIconLoad(() -> setIcons(info.getTextureSet().getFluidTexture(state).getIcon()));
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
