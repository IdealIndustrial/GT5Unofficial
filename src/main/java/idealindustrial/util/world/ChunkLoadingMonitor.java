package idealindustrial.util.world;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.TLongSet;
import idealindustrial.tile.meta.multi.BaseMultiMachine;
import idealindustrial.util.misc.II_Util;
import net.minecraft.world.World;

import java.util.*;

public class ChunkLoadingMonitor {
    static Map<Integer, ChunkLoadingMonitor> monitors = new HashMap<>();
    static List<BaseMultiMachine<?>> notifyListAdded = new ArrayList<>(), notifyListRemoved = new ArrayList<>();
    static final Object mutex = new Object();//now one general mutex, may be it will be good to replace it with some smaller

    public static void tickStart() {
        synchronized (mutex) {
            for (BaseMultiMachine<?> machine : notifyListAdded) {
                machine.chunkAdded();
            }
            notifyListAdded.clear();
            for (BaseMultiMachine<?> machine : notifyListRemoved) {
                machine.chunkRemoved();
            }
            notifyListRemoved.clear();
        }
    }


    public static void chunkLoaded(World world, int chunkX, int chunkY) {
        synchronized (mutex) {
            ChunkLoadingMonitor monitor = monitors.get(world.provider.dimensionId);
            if (monitor != null) {
                monitor.chunkLoaded(chunkX, chunkY);
            }
        }
    }

    public static void chunkUnloaded(World world, int chunkX, int chunkY) {
        synchronized (mutex) {
            ChunkLoadingMonitor monitor = monitors.get(world.provider.dimensionId);
            if (monitor != null) {
                monitor.chunkUnloaded(chunkX, chunkY);
            }
        }
    }

    public static ChunkLoadingMonitor getMonitor(World world) {
        return monitors.get(world.provider.dimensionId);
    }


    TLongObjectMap<List<BaseMultiMachine<?>>> listeners = new TLongObjectHashMap<>();
    TLongObjectMap<Set<BaseMultiMachine<?>>> allListeners = new TLongObjectHashMap<>();

    public void requestChunks(TLongSet chunks, BaseMultiMachine<?> machine) {
        synchronized (mutex) {
            for (TLongIterator iterator = chunks.iterator(); iterator.hasNext(); ) {
                long chunk = iterator.next();
                List<BaseMultiMachine<?>> listeners = getListeners(chunk);
                listeners.add(machine);

                Set<BaseMultiMachine<?>> set = this.allListeners.get(chunk);
                if (set == null) {
                    set = new HashSet<>();
                    this.allListeners.put(chunk, set);
                }
                set.add(machine);
            }
        }

    }

    protected List<BaseMultiMachine<?>> getListeners(long chunk) {
        List<BaseMultiMachine<?>> listeners = this.listeners.get(chunk);
        if (listeners == null) {
            listeners = new ArrayList<>();
            this.listeners.put(chunk, listeners);
        }
        return listeners;
    }

    protected void chunkLoaded(int chunkX, int chunkY) {
        long id = II_Util.intsToLong(chunkX, chunkY);
        if (listeners.containsKey(id)) {
            notifyListAdded.addAll(listeners.get(id));
            listeners.remove(id);
        }
    }

    protected void chunkUnloaded(int chunkX, int chunkY) {
        long id = II_Util.intsToLong(chunkX, chunkY);
        if (allListeners.containsKey(id)) {
            Set<BaseMultiMachine<?>> set = allListeners.get(id);
            set.removeIf(m -> m.getBase().isDead());
            notifyListRemoved.addAll(set);
            getListeners(id).addAll(set);
        }
    }


}
