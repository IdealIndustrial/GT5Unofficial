package idealindustrial.blocks.plants;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import idealindustrial.blocks.base.Tile32k;
import idealindustrial.render.CustomRenderer;
import idealindustrial.render.IFastRenderedTileEntity;
import idealindustrial.textures.ITexture;
import idealindustrial.tile.interfaces.ISyncedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;

import java.util.ArrayList;

public class TilePlants extends TileEntity implements Tile32k, IFastRenderedTileEntity, ISyncedTileEntity {

    PlantDef def;

    @Override
    public void setValuesFromMeta(int meta) {
        def = Plants.get(meta);
    }

    @Override
    public int getMeta() {
        return Plants.getID(def);
    }

    @Override
    public ITexture[][] getTextures() {
        return new ITexture[0][];
    }

    @Override
    public ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers) {
        return new ITexture[0][];
    }

    @Override
    public ITexture[][] getTextures(boolean tCovered) {
        return new ITexture[0][];
    }

    @Override
    public CustomRenderer getCustomRenderer() {
        return new CustomRenderer() {
            @Override
            public boolean renderWorldBlock(IBlockAccess world, IFastRenderedTileEntity tileEntity, int x, int y, int z, Block block, RenderBlocks renderBlocks) {
                return false;
            }

            @Override
            public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Block block, RenderBlocks renderBlocks, int meta) {

            }
        };
    }

    @Override
    public void rebakeMap() {

    }

    public ArrayList<ItemStack> getDrops() {
        return new ArrayList<>();
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        stream.writeInt(getMeta());
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        setValuesFromMeta(stream.readInt());
    }
}
