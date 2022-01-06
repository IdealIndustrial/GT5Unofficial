package idealindustrial;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.impl.net.Network;
import idealindustrial.impl.fluid.II_Fluids;
import idealindustrial.impl.item.MetaGeneratedCellItem;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.oredict.OredictHandler;
import idealindustrial.impl.autogen.recipes.LoadManager;
import idealindustrial.impl.autogen.recipes.materialprocessing.AutogenRecipes;
import idealindustrial.impl.commands.*;
import idealindustrial.impl.loader.*;
import idealindustrial.impl.registries.FuelRegistry;
import idealindustrial.teststuff.RenderTest;
import idealindustrial.teststuff.TestBlock;
import idealindustrial.teststuff.TestRecipes;
import idealindustrial.teststuff.TestTile;
import idealindustrial.impl.tile.gui.II_GuiHandler;
import idealindustrial.impl.item.tools.ToolRegistry;
import idealindustrial.impl.tile.fluid.II_FluidHelper;
import idealindustrial.util.misc.ItemHelper;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.impl.world.ChunkLoadingMonitor;
import idealindustrial.impl.world.DimensionChunkData;
import idealindustrial.impl.world.WorldTickHandler;
import idealindustrial.impl.world.oregen.OreGenerator;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;

@Mod(modid = "iicore", name = "II_Core", version = "MC1710")
public class II_Core {
    public static final String MOD_ID = "iicore";
    @Mod.Instance(MOD_ID)
    public static II_Core INSTANCE;
    public static final CreativeTabs II_MAIN_TAB = new CreativeTabs("II Core") {
        @Override
        public Item getTabIconItem() {
            return Items.apple;
        }
    };

    public static final CreativeTabs II_MATERIAL_TAB = new CreativeTabs("Materials") {
        @Override
        public Item getTabIconItem() {
            return Items.redstone;
        }
    };

    private static final String version = "1.17.1";

    public RenderLoader renderLoader;
    public ItemsLoader itemsLoader;
    public BlocksLoader blocksLoader;
    public TileLoader tileLoader;
    public OredictHandler oredictLoader;
    public AutogenRecipes autogen;

    public II_Core() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(WorldTickHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(WorldTickHandler.INSTANCE);
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            renderLoader = new RenderLoader();
        }
        itemsLoader = new ItemsLoader();
        blocksLoader = new BlocksLoader();
        tileLoader = new TileLoader();
        oredictLoader = new OredictHandler();
        autogen = new AutogenRecipes();
        new Network();
        MinecraftForge.EVENT_BUS.register(oredictLoader);
        MinecraftForge.EVENT_BUS.register(new II_FluidHelper());
        oredictLoader.loadAlreadyNicelyLoadedByForgeOreDictsWithoutFuckingEvents();
        //INSTANCE = this;
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        System.out.println("gg");
    }

    @Mod.EventHandler
    public void onPreLoad(FMLPreInitializationEvent aEvent) {
        if (!checkEnvironment()) {
            CrashReport tCrashReport = new CrashReport("Wrong enviroment detected, please install BQfix for thermos: https://github.com/IdealIndustrial/Ideal-Industrial-Quests", new RuntimeException("no fix for better questing is detected"));
            throw new ReportedException(tCrashReport);
        }
        itemsLoader.preLoad();
        II_Fluids.INSTANCE.init();
        blocksLoader.preLoad();
        new MetaGeneratedCellItem();
        new ConfigsLoader(aEvent).run();

        new TestBlock();
        GameRegistry.registerTileEntity(TestTile.class, "testTile");
        ClientRegistry.bindTileEntitySpecialRenderer(TestTile.class, new RenderTest());
        II_GuiHandler.init();
        tileLoader.run();
        LoadManager.loadMachineConfigs(aEvent);
    }

    @Mod.EventHandler
    public void onLoad(FMLInitializationEvent aEvent) {
        if (renderLoader != null) {
            renderLoader.load();
        }
        oredictLoader.fireRegisterEvent();
    }


    @Mod.EventHandler
    public void onPostLoad(FMLPostInitializationEvent aEvent) {
        LoadManager.loadMaterialAutogenInfo();
        GameRegistry.registerWorldGenerator(new OreGenerator(), 100);
        try {
            ToolRegistry.initTools();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        oredictLoader.init();
        FuelRegistry.initMap();
        II_Materials.initMaterialLoops();
        LoadManager.loadRecipes();
        new TestRecipes().run();
        OreDict.printAll(System.out);
        LangHandler.dumpAll();
        LangHandler.pushLocalToMinecraft();

    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        ChunkLoadingMonitor.serverStop();
    }

    @SubscribeEvent
    public void onChunkLoading(ChunkEvent.Load event) {
        if (!event.world.isRemote) {
            ChunkLoadingMonitor.chunkLoaded(event.world, event.getChunk().xPosition, event.getChunk().zPosition);
        }
    }

    @SubscribeEvent
    public void onChunkUnloading(ChunkEvent.Unload event) {
        if (!event.world.isRemote) {
            ChunkLoadingMonitor.chunkUnloaded(event.world, event.getChunk().xPosition, event.getChunk().zPosition);
        }
    }


    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event) {
        if (!event.world.isRemote) {
            DimensionChunkData.chunkSave(event.world, event.getChunk().xPosition, event.getChunk().zPosition, event.getData());
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event) {
        if (!event.world.isRemote) {
            DimensionChunkData.chunkLoad(event.world, event.getChunk().xPosition, event.getChunk().zPosition, event.getData());
        }
    }

    private static boolean checkEnvironment() {
        try {
            Class.forName("thermos.Thermos");
        } catch (ClassNotFoundException e) {
            return true;
        }
        try {
            Class.forName("betterquesting.core.BetterQuesting");
        } catch (ClassNotFoundException e) {
            return true;
        }
        try {
            Class.forName("a.b.c.gambiarra.Plugin");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent aEvent) {
        aEvent.registerServerCommand(new CommandFixQuests());
        aEvent.registerServerCommand(new DimTPCommand());
//        aEvent.registerServerCommand(new ReloadRecipesCommand());
        aEvent.registerServerCommand(new CommandOpenEditor());

        //  aEvent.registerServerCommand(new CommandFixMaterials());

    }

    @Mod.EventHandler
    private void serverAboutToStart(final FMLServerAboutToStartEvent evt) {
        File iiSaveDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "IIM");
        if (!iiSaveDir.isDirectory() && !iiSaveDir.mkdir()) {
            throw new IllegalStateException("Cannot create IIM save folder");
        }
    }

    public static String getVersion() {
        return version;
    }

    @Mod.EventHandler
    public void onIDChangingEvent(FMLModIdMappingEvent event) {
        ItemHelper.onIDsCharge();
    }

}
