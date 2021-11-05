package idealindustrial.util.misc;

import idealindustrial.II_Values;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.impl.tile.fluid.EmptyTank;
import idealindustrial.impl.tile.host.HostMachineTileImpl;
import idealindustrial.impl.tile.host.HostPipeTileImpl;
import idealindustrial.impl.tile.host.HostPipeTileRotatingImpl;
import idealindustrial.impl.tile.host.HostTileImpl;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.fluid.SingleFluidHandler;
import idealindustrial.impl.tile.fluid.MultiFluidHandler;
import idealindustrial.impl.tile.inventory.*;
import idealindustrial.impl.tile.inventory.EmptyInventory;
import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@II_EventListener
public class II_TileUtil {


    @SuppressWarnings("unchecked")
    private static final Class<? extends TileEntity>[] tileClasses = new Class[]{
            HostTileImpl.class, HostMachineTileImpl.class, HostPipeTileImpl.class, HostPipeTileRotatingImpl.class
    };
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

    public static Tile<?> registerMetaTile(int id, Tile<?> tile) {
        II_Values.TILES[id] = tile;
        tile.getHost().setMetaTileID(id);
        return tile;
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

    @LocalizeEvent
    public static void localizeTiles() {
        Tile<?>[] tiles = II_Values.TILES;
        for (int i = 0, tilesLength = tiles.length; i < tilesLength; i++) {
            Tile<?> tile = tiles[i];
            if (tile == null) {
                continue;
            }
            LangHandler.add("ii.itemmachine." + i + ".name",tile.getInventoryName());
        }
    }

}
