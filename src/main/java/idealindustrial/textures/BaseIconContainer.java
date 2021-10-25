package idealindustrial.textures;

import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class BaseIconContainer implements IconContainer, IRegistrableIcon {

    protected IIcon icon;
    protected final String name;

    public BaseIconContainer(String name) {
        this.name = name;
    }

    @Override
    public IIcon getIcon() {
        return icon;
    }

    @Override
    public void register(IIconRegister register) {
        icon = register.registerIcon(II_Paths.PATH_BLOCK_ICONS + name);
    }
}
