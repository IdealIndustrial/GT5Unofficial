package idealindustrial.tile.base;

import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.interfaces.base.II_BasePipeTile;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class II_BasePipeTileImpl extends II_BaseTileImpl implements II_BasePipeTile {

    public II_BasePipeTileImpl() {
        textureCache = new ITexture[3][6][];
    }

    @Override
    public ITexture[][] getTextures() {
        return super.getTextures();
    }

    @Override
    public ITexture[][] getTextures(boolean covered) {
        if (covered) {
            return textureCache[2];
        }
        return getTextures();
    }

    @Override
    public void rebakeMap() {
        if (metaTileEntity == null) {
            return;
        }
        textureCache = new ITexture[3][6][];
        for (int i = 0; i < 6; i++) {
            textureCache[0][i] = metaTileEntity.provideTexture(false, i);
            textureCache[1][i] = metaTileEntity.provideTexture(true, i);
            textureCache[2][i] = new ITexture[]{getCoverTexture(i)};
        }
    }

    @Override
    public void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB, List<AxisAlignedBB> outputAABB, Entity collider) {
        metaTileEntity.addCollisionBoxesToList(aWorld, aX, aY, aZ, inputAABB, outputAABB, collider);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        return metaTileEntity.getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
    }

    @Override
    public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity collider) {
        metaTileEntity.onEntityCollidedWithBlock(aWorld, aX, aY, aZ, collider);
    }


}
