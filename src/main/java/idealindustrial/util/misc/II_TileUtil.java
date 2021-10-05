package idealindustrial.util.misc;

import idealindustrial.II_Values;
import idealindustrial.tile.host.HostMachineTileImpl;
import idealindustrial.tile.host.HostPipeTileImpl;
import idealindustrial.tile.host.HostTileImpl;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.host.HostTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.fluid.*;
import idealindustrial.util.fluid.SingleFluidHandler;
import idealindustrial.util.fluid.MultiFluidHandler;
import idealindustrial.util.inventory.*;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.interfaces.InternalInventory;
import idealindustrial.util.inventory.interfaces.RecipedInventory;
import idealindustrial.util.worldgen.Vector3;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class II_TileUtil {


    @SuppressWarnings("unchecked")
    private static final Class<? extends TileEntity>[] tileClasses = new Class[]{HostTileImpl.class, HostMachineTileImpl.class, HostPipeTileImpl.class};
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

    public static void registerMetaTile(int id, Tile<?> tile) {
        II_Values.TILES[id] = tile;
        tile.getHost().setMetaTileID(id);
    }

    public static HostTile makeBaseTile() {
        return new HostTileImpl();
    }

    public static HostMachineTile makeBaseMachineTile() {
        return new HostMachineTileImpl();
    }

    public static Tile<?> getMetaTile(World world, Vector3 pos) {
        return getMetaTile(world, pos.x, pos.y, pos.z);
    }
    public static Tile<?> getMetaTile(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof HostTile) {
            return ((HostTile) tile).getMetaTile();
        }
        return null;
    }

    public static HostMachineTile getMachineTileAtSide(HostTile tile, int side) {
        TileEntity tileEntity = tile.getTileEntityAtSide(side);
        return tileEntity instanceof HostMachineTile ? (HostMachineTile) tileEntity : null;
    }

    public static Tile<?> getMetaTileAtSide(HostTile tile, int side) {
        TileEntity tileEntity = tile.getTileEntityAtSide(side);
        if (tileEntity instanceof HostTile) {
            return ((HostTile) tileEntity).getMetaTile();
        }
        return null;
    }

    public static <T extends HostTile> T getBaseTileOfClass(World world, int x, int y, int z, Class<T> clazz) {
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

    public static HostTile getBaseTile(IBlockAccess world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity instanceof HostTile ? (HostTile) tileEntity : null;
    }

    public static FluidHandler constructFluidHandler(int fluidCount, int capacity) {
        if (fluidCount == 0) {
            return EmptyTank.INSTANCE;
        }
        if (fluidCount == 1) {
            return new SingleFluidHandler(capacity);
        }
        return new MultiFluidHandler(fluidCount, capacity);
    }


    public static RecipedInventory constructRecipedInventory(int slotCount, int stackSize) {
        if (slotCount == 0) {
            return EmptyInventory.INSTANCE;
        }
       return new ArrayRecipedInventory(slotCount, stackSize);
    }

    public static InternalInventory constructInternalInventory(int slotCount, int stackSize) {
        if (slotCount == 0) {
            return EmptyInventory.INSTANCE;
        }
        return new ArrayRecipedInventory(slotCount, stackSize);
    }

}