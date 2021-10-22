package idealindustrial;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.common.GT_Network;
import idealindustrial.autogen.fluids.II_Fluids;
import idealindustrial.autogen.implementation.MetaGeneratedCellItem;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.oredict.OreDict;
import idealindustrial.autogen.oredict.OredictHandler;
import idealindustrial.autogen.recipes.RecipeManager;
import idealindustrial.autogen.recipes.materialprocessing.AutogenRecipes;
import idealindustrial.commands.*;
import idealindustrial.integration.ingameinfo.InGameInfoLoader;
import idealindustrial.loader.*;
import idealindustrial.teststuff.RenderTest;
import idealindustrial.teststuff.TestBlock;
import idealindustrial.teststuff.TestTile;
import idealindustrial.tile.gui.II_GuiHandler;
import idealindustrial.tools.ToolRegistry;
import idealindustrial.util.item.ItemHelper;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.world.ChunkLoadingMonitor;
import idealindustrial.util.world.WorldTickHandler;
import idealindustrial.util.worldgen.oregen.OreGenerator;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ReportedException;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Mod(modid = "iicore", name = "II_Core", version = "MC1710", useMetadata = false)
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

    RenderLoader renderLoader;
    ItemsLoader itemsLoader;
    BlocksLoader blocksLoader;
    TileLoader tileLoader;
    OredictHandler oredictLoader;
    AutogenRecipes autogen;

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
        new GT_Network();
        MinecraftForge.EVENT_BUS.register(oredictLoader);
        oredictLoader.loadAlreadyNicelyLoadedByForgeOreDictsWithoutFuckingEvents();
        //INSTANCE = this;
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        new InGameInfoLoader().load();
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
        tileLoader.run();;
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
        GameRegistry.registerWorldGenerator(new OreGenerator(), 100);
        try {
            ToolRegistry.initTools();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        LangHandler.dumpAll();
        LangHandler.pushLocalToMinecraft();
        oredictLoader.init();
        II_Materials.initMaterialLoops();
        RecipeManager.load();
        OreDict.printAll(System.out);

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
        aEvent.registerServerCommand(new ReloadRecipesCommand());
        aEvent.registerServerCommand(new CommandOpenEditor());

        //  aEvent.registerServerCommand(new CommandFixMaterials());

    }

    @Mod.EventHandler
    private void serverAboutToStart(final FMLServerAboutToStartEvent evt) {
        File iiSaveDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "IIM");
        if (!iiSaveDir.isDirectory() && !iiSaveDir.mkdir()) {
            throw new IllegalStateException("Cannot create IIM save folder");
        }
        try {
            CommandFixMaterials.loadWorld(iiSaveDir);
        } catch (IOException exception) {
            exception.printStackTrace();
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
