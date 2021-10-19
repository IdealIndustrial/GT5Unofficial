package idealindustrial.textures;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
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

    public IconContainer blockTexture(String name) {
        BlockIconContainer container = new BlockIconContainer(name, false);
        blocks.add(container);
        return container;
    }

    public IconContainer blockTexture(String name, Consumer<IconContainer> callback){
        BlockIconContainer icon = new BlockIconWithCallback(name, false, callback);
        blocks.add(icon);
        return icon;
    }

    public IconContainer itemTexture(String name) {
        ItemIconContainer container = new ItemIconContainer(name, false);
        items.add(container);
        return container;
    }

    public IconContainer materialBlockTexture(String materialName) {
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
        if (textureID < 0) {
            return NetworkedTexture.load(textureID);
        }
        return networkedTextureMap.get(textureID);
    }
}
