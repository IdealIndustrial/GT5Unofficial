package idealindustrial.textures;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.IIconRegister;

import java.util.function.Consumer;

public class BlockIconWithCallback extends BlockIconContainer {
    Consumer<IIconContainer> callback;

    public BlockIconWithCallback(String name, boolean hasOverlay, Consumer<IIconContainer> callback) {
        super(name, hasOverlay);
        this.callback = callback;
    }

    @Override
    public void register(IIconRegister register) {
        super.register(register);
        callback.accept(this);
    }
}
