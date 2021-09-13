package idealindustrial.textures;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import net.minecraft.client.renderer.texture.IIconRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TextureManager {

    public static final TextureManager INSTANCE = new TextureManager();
    static {
        Textures.init();
    }

    List<IRegistrableIcon> items = new ArrayList<>(), blocks = new ArrayList<>();
    List<Runnable> postIconLoad = new ArrayList<>();
    Map<Integer, INetworkedTexture> networkedTextureMap = new HashMap<>();

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
        ItemIconContainer container = new ItemIconContainer(name, false);
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

    protected int registerNetworkedTexture(INetworkedTexture networkedTexture) {
        assert Loader.instance().getLoaderState().ordinal() <= LoaderState.POSTINITIALIZATION.ordinal();
        int id = networkedTextureMap.size();
        networkedTextureMap.put(id, networkedTexture);
        return id;
    }

    public INetworkedTexture getNetworkedTexture(int textureID) {
        return networkedTextureMap.get(textureID);
    }
}
