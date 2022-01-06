package idealindustrial.impl.blocks.base;

import cpw.mods.fml.common.FMLCommonHandler;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.textures.TextureUtil;
import idealindustrial.impl.textures.TextureUtil.MachineTextureConfig;
import idealindustrial.impl.tile.impl.multi.struct.StructBlockRegistry;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CasingBlock extends MetaBlock {

    ITexture[] textures;
    public CasingBlock(String unlocalizedName, Material material, String... engLocale) {
        super(CasingBlock_Item.class, unlocalizedName, material, engLocale);
        int enabled = engLocale.length;
        for (int i = 0; i < enabled; i++) {
            enable(i);
            StructBlockRegistry.registerCasing(this, i);
        }
        textures = TextureUtil.loadTextures(
                new MachineTextureConfig(Arrays.stream(engLocale).map(TextureUtil::asTextureName).toArray(String[]::new)),
                II_Paths.CASINGS_TEXTURES);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta > textures.length) {
            meta = 0;
        }
        return textures[meta].getIcon();
    }

    public ITexture getTexture(int meta) {
        return textures[meta];
    }

    @Override
    public void onBlockAdded(World w, int x, int y, int z) {
        super.onBlockAdded(w, x, y, z);
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            StructBlockRegistry.spreadUpdate(w, x, y, z);
        }
    }

    @Override
    public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
        super.breakBlock(w, x, y, z, block, meta);
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            StructBlockRegistry.spreadUpdate(w, x, y, z);
        }
    }

    public static class Builder {
        List<String> names = new ArrayList<>();
        String unlocal;
        Material material;
        Consumer<HashedBlock>[] consumers;

        public Builder(String unlocal, Material material) {
            this.unlocal = unlocal;
            this.material = material;
            consumers = new Consumer[16];
        }

        public Builder addBlock(String localName) {
            assert names.size() < 15;
            names.add(localName);
            return this;
        }

        public Builder onPostCreated(Consumer<HashedBlock> consumer) {
            consumers[names.size() - 1] = consumer;
            return this;
        }

        public CasingBlock construct() {
            CasingBlock casingBlock  = new CasingBlock(unlocal, material, names.toArray(new String[0]));
            for (int i = 0; i < consumers.length; i++) {
                if (consumers[i] == null) {
                    continue;
                }
                consumers[i].accept(new HashedBlock(casingBlock, i));
            }
            return casingBlock;
        }
    }
}
