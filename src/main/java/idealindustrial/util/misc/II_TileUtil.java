package idealindustrial.util.misc;

import idealindustrial.II_Values;
import idealindustrial.tile.base.II_BaseMachineTileImpl;
import idealindustrial.tile.base.II_BasePipeTileImpl;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.tile.interfaces.meta.II_MetaTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class II_TileUtil {


    @SuppressWarnings("unchecked")
    private static final Class<? extends TileEntity>[] tileClasses = new Class[]{II_BaseTileImpl.class, II_BaseMachineTileImpl.class, II_BasePipeTileImpl.class};
    private static final Map<Class<? extends TileEntity>, Integer> classToMeta = new HashMap<>();

    static {
        for (int i = 0; i < tileClasses.length; i++) {
            classToMeta.put(tileClasses[i], i);
        }
    }

    public static Class<? extends TileEntity> metaToClass(int meta) {
        return tileClasses[meta];
    }

    public static int classToMeta(Class<? extends TileEntity> tileEntity) {
        Integer boxed = classToMeta.get(tileEntity);
        if (boxed == null) {
            assert false : "unknown base tile class: " + tileEntity.getName();
            return -1;
        }
        return boxed;
    }

    public static TileEntity provideTile(int meta) {
        Class<? extends TileEntity> tileClass = metaToClass(meta);
        try {
            Constructor<? extends TileEntity> constructor = tileClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void registerMetaTile(int id, II_MetaTile<?> metaTile) {
        II_Values.metaTiles[id] = metaTile;
        metaTile.getBase().setMetaTileID(id);
    }

    public static II_BaseTile makeBaseTile() {
        return new II_BaseTileImpl();
    }

    public static II_BaseMachineTile makeBaseMachineTile() {
        return new II_BaseMachineTileImpl();
    }

    public static II_MetaTile<?> getMetaTile(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof II_BaseTile) {
            return ((II_BaseTile) tile).getMetaTile();
        }
        return null;
    }

    public static II_BaseMachineTile getMachineTileAtSide(II_BaseTile tile, int side) {
        TileEntity tileEntity = tile.getTileEntityAtSide(side);
        return tileEntity instanceof II_BaseMachineTile ? (II_BaseMachineTile) tileEntity : null;
    }

    public static II_MetaTile<?> getMetaTileAtSide(II_BaseTile tile, int side) {
        TileEntity tileEntity = tile.getTileEntityAtSide(side);
        if (tileEntity instanceof II_BaseTile) {
            return ((II_BaseTile) tileEntity).getMetaTile();
        }
        return null;
    }

    public static <T extends II_BaseTile> T getBaseTileOfClass(World world, int x, int y, int z, Class<T> clazz) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return checkTileForClass(tileEntity, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T checkTileForClass(TileEntity tileEntity, Class<T> clazz) {
        if (tileEntity == null || !clazz.isAssignableFrom(tileEntity.getClass())) {
            return null;
        }
        return (T) tileEntity;
    }

    public static II_BaseTile getBaseTile(IBlockAccess world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity instanceof II_BaseTile ? (II_BaseTile) tileEntity : null;
    }
}
