package idealindustrial.tile.host;

import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.interfaces.host.HostPipeTile;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class HostPipeTileImpl extends HostTileImpl implements HostPipeTile {

    public HostPipeTileImpl() {
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
        if (tile == null) {
            return;
        }
        textureCache = new ITexture[3][6][];
        for (int i = 0; i < 6; i++) {
            textureCache[0][i] = tile.provideTexture(false, i);
            textureCache[1][i] = tile.provideTexture(true, i);
            textureCache[2][i] = new ITexture[]{getCoverTexture(i)};
        }
    }

    @Override
    public void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB, List<AxisAlignedBB> outputAABB, Entity collider) {
        tile.addCollisionBoxesToList(aWorld, aX, aY, aZ, inputAABB, outputAABB, collider);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        return tile.getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
    }

    @Override
    public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity collider) {
        tile.onEntityCollidedWithBlock(aWorld, aX, aY, aZ, collider);
    }


}
