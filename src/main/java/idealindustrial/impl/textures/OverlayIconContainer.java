package idealindustrial.impl.textures;

import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class OverlayIconContainer extends ItemIconContainer {
    IIcon overlay;

    public OverlayIconContainer(String name) {
        super(name);
    }

    @Override
    public void register(IIconRegister register) {
        super.register(register);
        overlay = register.registerIcon(II_Paths.PATH_BLOCK_ICONS + name + "_o");
    }

    public IIcon getOverlay() {
        return overlay;
    }
}
