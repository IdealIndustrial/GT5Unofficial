package idealindustrial.textures;

import net.minecraft.client.renderer.texture.IIconRegister;

import java.util.function.Consumer;

public class BlockIconWithCallback extends BlockIconContainer {
    Consumer<IconContainer> callback;

    public BlockIconWithCallback(String name, Consumer<IconContainer> callback) {
        super(name);
        this.callback = callback;
    }

    @Override
    public void register(IIconRegister register) {
        super.register(register);
        callback.accept(this);
    }
}
