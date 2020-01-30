package gregtech.api.objects;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GT_IconContainer implements IIconContainer {

    IIcon mIcon, mOverlayIcon;
    ResourceLocation mLocation;

    public GT_IconContainer(IIcon aIcon, IIcon aOverlay, ResourceLocation aLoaction){
        mIcon = aIcon;
        mOverlayIcon = aOverlay;
        mLocation = aLoaction;
    }

    @Override
    public IIcon getIcon() {
        return mIcon;
    }

    @Override
    public IIcon getOverlayIcon() {
        return mOverlayIcon;
    }

    @Override
    public ResourceLocation getTextureFile() {
        return mLocation;
    }
}
