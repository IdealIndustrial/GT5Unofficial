package idealindustrial.textures;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.IIconRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TextureManager {

    public static final TextureManager INSTANCE = new TextureManager();
    static {
        Textures.init();
    }

    List<IRegistrableIcon> items = new ArrayList<>(), blocks = new ArrayList<>();
    List<Runnable> postIconLoad = new ArrayList<>();

    public IIconContainer blockTexture(String name) {
        BlockIconContainer container = new BlockIconContainer(name, false);
        blocks.add(container);
        return container;
    }

    public IIconContainer blockTexture(String name, Consumer<IIconContainer> callback){
        BlockIconContainer icon = new BlockIconWithCallback(name, false, callback);
        blocks.add(icon);
        return icon;
    }

    public IIconContainer itemTexture(String name) {
        ItemIconContainer container = new ItemIconContainer(name, true);
        items.add(container);
        return container;
    }

    public IIconContainer materialBlockTexture(String materialName) {
        return blockTexture("materialblocks/" + materialName);
    }

    public void onPostIconLoad(Runnable runnable) {
        if (postIconLoad == null) {
            runnable.run();
            return;
        }
        postIconLoad.add(runnable);
    }

    public void initBlocks(IIconRegister register) {
        blocks.forEach(b -> b.register(register));
        postIconLoad.forEach(Runnable::run);
    }

    public void initItems(IIconRegister register) {
        items.forEach(b -> b.register(register));
    }
}
