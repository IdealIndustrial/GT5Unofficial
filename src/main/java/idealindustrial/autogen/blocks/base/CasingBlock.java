package idealindustrial.autogen.blocks.base;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import idealindustrial.textures.TextureUtil;
import idealindustrial.tile.impl.multi.struct.StructBlockRegistry;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class CasingBlock extends MetaBlock {

    ITexture[] textures;
    public CasingBlock(String unlocalizedName, Material material, int enabled) {
        super(CasingBlock_Item.class, unlocalizedName, material);
        for (int i = 0; i < enabled; i++) {
            enable(i);
            StructBlockRegistry.registerCasing(this, i);
        }
        textures = TextureUtil.loadTextures(new TextureUtil.BlockTextureConfig(enabled), II_Paths.CASINGS_TEXTURES + unlocalizedName + "/");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta > textures.length) {
            meta = 0;
        }
        return textures[meta].getContainer().getIcon();
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
}
