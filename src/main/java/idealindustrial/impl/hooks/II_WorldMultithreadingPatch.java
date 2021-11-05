package idealindustrial.impl.hooks;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import net.minecraft.crash.CrashReport;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class II_WorldMultithreadingPatch {

    static Field tickCounter, configManager, tickTime, tickTables;

    static {
        Class<? extends MinecraftServer> serverClass = MinecraftServer.class;
        try {
            tickCounter = serverClass.getDeclaredField("tickCounter");
            tickCounter.setAccessible(true);
            configManager = serverClass.getDeclaredField("serverConfigManager");
            configManager.setAccessible(true);
            tickTime = serverClass.getDeclaredField("worldTickTimes");
            tickTime.setAccessible(true);
            tickTables = serverClass.getDeclaredField("tickables");
            tickTables.setAccessible(true);
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void updateTimeLightAndEntities(MinecraftServer server) throws IllegalAccessException {
        server.theProfiler.startSection("levels");
        net.minecraftforge.common.chunkio.ChunkIOExecutor.tick();
        int i;
        int tickCounter = ((int) II_WorldMultithreadingPatch.tickCounter.get(server));
        Integer[] ids = DimensionManager.getIDs(tickCounter % 200 == 0);
        Hashtable<Integer, long[]> worldTickTimes = (Hashtable<Integer, long[]>) tickTime.get(server);
        ServerConfigurationManager serverConfigurationManager = ((ServerConfigurationManager) configManager.get(server));
        for (int id : ids) {
            preEntitiesUpdateWork(serverConfigurationManager, id, tickCounter);
        }

        List<Thread> threads = new ArrayList<>(ids.length);

        for (int id : ids) {
            startThread(worldTickTimes, tickCounter, id, threads);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int id : ids) {
            postEntitiesUpdateWork(id);
        }

        server.theProfiler.endStartSection("dim_unloading");
        DimensionManager.unloadWorlds(worldTickTimes);
        server.theProfiler.endStartSection("connection");
        server.func_147137_ag().networkTick();
        server.theProfiler.endStartSection("players");
        serverConfigurationManager.sendPlayerInfoToAllPlayers();
        server.theProfiler.endStartSection("tickables");

        List tickables = (List) tickTables.get(server);
        for (i = 0; i < tickables.size(); ++i) {
            ((IUpdatePlayerListBox) tickables.get(i)).update();
        }

        server.theProfiler.endSection();
    }

    private static void preEntitiesUpdateWork(ServerConfigurationManager configurationManager, int id, int tickCounter) {
        WorldServer worldserver = DimensionManager.getWorld(id);

        if (tickCounter % 20 == 0) {
            configurationManager.sendPacketToAllPlayersInDimension(new S03PacketTimeUpdate(worldserver.getTotalWorldTime(), worldserver.getWorldTime(), worldserver.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), worldserver.provider.dimensionId);
        }

        FMLCommonHandler.instance().onPreWorldTick(worldserver);
    }

    private static void postEntitiesUpdateWork(int id) {
        WorldServer worldserver = DimensionManager.getWorld(id);
        FMLCommonHandler.instance().onPostWorldTick(worldserver);
        worldserver.getEntityTracker().updateTrackedEntities();
    }

    private static void startThread(Hashtable<Integer, long[]> worldTickTimes, int tickCounter, int id, List<Thread> threads) {
        Thread thread = new Thread(new Updater(worldTickTimes, tickCounter, id), "Server thread updater-"+id);
        thread.start();
        threads.add(thread);
    }

    private static class Updater implements Runnable {

        Hashtable<Integer, long[]> worldTickTimes;
        int tickCounter, id;

        public Updater(Hashtable<Integer, long[]> worldTickTimes, int tickCounter, int id) {
            this.worldTickTimes = worldTickTimes;
            this.tickCounter = tickCounter;
            this.id = id;
        }

        @Override
        public void run() {
            updateEntities(worldTickTimes, tickCounter, id);
        }
    }

    private static void updateEntities(Hashtable<Integer, long[]> worldTickTimes, int tickCounter, int id) {
        long j = System.nanoTime();
        WorldServer worldserver = DimensionManager.getWorld(id);
        CrashReport crashreport;

        try {
            worldserver.tick();
        } catch (Throwable throwable1) {
            crashreport = CrashReport.makeCrashReport(throwable1, "Exception ticking world");
            worldserver.addWorldInfoToCrashReport(crashreport);
            throw new ReportedException(crashreport);
        }

        try {
            worldserver.updateEntities();
        } catch (Throwable throwable) {
            crashreport = CrashReport.makeCrashReport(throwable, "Exception ticking world entities");
            worldserver.addWorldInfoToCrashReport(crashreport);
            throw new ReportedException(crashreport);
        }
        synchronized (worldTickTimes) {
            worldTickTimes.get(id)[tickCounter % 100] = System.nanoTime() - j;
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static Side getEffectiveSide(FMLCommonHandler handler) {
        Thread thr = Thread.currentThread();
        if (thr.getName().startsWith("Server thread")) {
            return Side.SERVER;
        }

        return Side.CLIENT;
    }



}
