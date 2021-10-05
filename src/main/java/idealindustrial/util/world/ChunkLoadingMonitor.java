package idealindustrial.util.world;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
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
        return monitors.computeIfAbsent(world.provider.dimensionId, i -> new ChunkLoadingMonitor());
    }


    TLongObjectMap<List<BaseMultiMachine<?>>> loadListeners = new TLongObjectHashMap<>();
    TLongObjectMap<Set<BaseMultiMachine<?>>> unloadListeners = new TLongObjectHashMap<>();
    TLongSet loaded = new TLongHashSet();

    public int requestChunks(TLongSet chunks, BaseMultiMachine<?> machine) {
        int alreadyLoaded = 0;
        System.out.println("Requested: ");
        print(chunks);
        synchronized (mutex) {
            for (TLongIterator iterator = chunks.iterator(); iterator.hasNext(); ) {
                long chunk = iterator.next();
                if (loaded.contains(chunk)) {
                    alreadyLoaded++;
                    continue;
                }
                List<BaseMultiMachine<?>> listeners = getListeners(chunk);
                listeners.add(machine);

                Set<BaseMultiMachine<?>> set = this.unloadListeners.get(chunk);
                if (set == null) {
                    set = new HashSet<>();
                    this.unloadListeners.put(chunk, set);
                }
                set.add(machine);
            }
        }
        return alreadyLoaded;
    }

    public void removeTileFrom(TLongSet chunks, BaseMultiMachine<?> machine) {
        System.out.println("Removal: ");
        print(chunks);
        synchronized (mutex) {
            for (TLongIterator iterator = chunks.iterator(); iterator.hasNext(); ) {
                long chunk = iterator.next();
                Set<BaseMultiMachine<?>> set = this.unloadListeners.get(chunk);
                if (set != null) {
                    set.remove(machine);
                }
            }
        }
    }

    protected List<BaseMultiMachine<?>> getListeners(long chunk) {
        List<BaseMultiMachine<?>> listeners = this.loadListeners.get(chunk);
        if (listeners == null) {
            listeners = new ArrayList<>();
            this.loadListeners.put(chunk, listeners);
        }
        return listeners;
    }

    protected void chunkLoaded(int chunkX, int chunkY) {
        long id = II_Util.intsToLong(chunkX, chunkY);
        loaded.add(id);
        if (loadListeners.containsKey(id)) {
            notifyListAdded.addAll(loadListeners.get(id));
            loadListeners.remove(id);
        }
    }

    protected void chunkUnloaded(int chunkX, int chunkY) {
        long id = II_Util.intsToLong(chunkX, chunkY);
        loaded.remove(id);
        if (unloadListeners.containsKey(id)) {
            Set<BaseMultiMachine<?>> set = unloadListeners.get(id);
            set.removeIf(m -> m.getBase().isDead());
            notifyListRemoved.addAll(set);
            getListeners(id).addAll(set);
        }
    }

    private void print(TLongSet chunks) {
        for (TLongIterator iterator = chunks.iterator(); iterator.hasNext(); ) {
            long chunk = iterator.next();
            int x = II_Util.intAFromLong(chunk);
            int z = II_Util.intBFromLong(chunk);
            System.out.print(x + " " + z + ", ");
        }
    }


}
