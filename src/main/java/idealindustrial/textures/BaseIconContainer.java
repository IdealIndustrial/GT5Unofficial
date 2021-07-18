package idealindustrial.textures;

import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public abstract class BaseIconContainer implements IIconContainer, IRegistrableIcon {

    protected IIcon icon, overlay;
    protected final String name;
    protected final boolean hasOverlay;

    public BaseIconContainer(String name, boolean hasOverlay) {
        this.name = name;
        this.hasOverlay = hasOverlay;
    }

    @Override
    public IIcon getIcon() {
        return icon;
    }

    @Override
    public IIcon getOverlayIcon() {
        return overlay;
    }

    @Override
    public void register(IIconRegister register) {
        icon = register.registerIcon(GT_Values.RES_PATH_BLOCK + name);
        if (hasOverlay) {
            overlay = register.registerIcon(GT_Values.RES_PATH_BLOCK + name + "_OVERLAY");
        }
    }
}
