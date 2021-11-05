package idealindustrial.teststuff;

import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.api.textures.IconContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestTile extends TileEntity {
    static {
        addMapping(TileEntity.class, "testTileII");
    }
    public TestTile(World world) {
        worldObj = world;
    }

    public IconContainer getTexture() {
        return II_Materials.iron.getRenderInfo().getTextureSet().block;
    }
}
