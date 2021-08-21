package idealindustrial;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.commands.CommandFixMaterials;
import idealindustrial.commands.CommandFixQuests;
import idealindustrial.commands.DimTPCommand;
import idealindustrial.commands.ReloadRecipesCommand;
import idealindustrial.integration.ingameinfo.InGameInfoLoader;
import idealindustrial.itemgen.fluids.II_Fluids;
import idealindustrial.itemgen.implementation.II_MetaGeneratedCellItem;
import idealindustrial.itemgen.material.II_Materials;
import idealindustrial.itemgen.oredict.II_OreDict;
import idealindustrial.itemgen.recipes.II_AutogenRecipes;
import idealindustrial.loader.II_BlocksLoader;
import idealindustrial.loader.II_ItemsLoader;
import idealindustrial.itemgen.oredict.II_OredictHandler;
import idealindustrial.loader.II_Render;
import idealindustrial.recipe.II_RecipeMap;
import idealindustrial.recipe.II_RecipeMaps;
import idealindustrial.teststuff.RenderTest;
import idealindustrial.teststuff.TestBlock;
import idealindustrial.teststuff.TestTile;
import idealindustrial.tile.gui.II_GuiHandler;
import idealindustrial.tools.II_ToolRegistry;
import idealindustrial.util.lang.II_Lang;
import idealindustrial.util.world.II_WorldTickHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;
import java.io.IOException;

@Mod(modid = "iicore", name = "II_Core", version = "MC1710", useMetadata = false, dependencies = "after:gregtech")
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

    private static final String version = "1.17.1";

    II_Render renderLoader;
    II_ItemsLoader itemsLoader;
    II_BlocksLoader blocksLoader;
    II_OredictHandler oredictLoader;
    II_AutogenRecipes autogen;

    public II_Core() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(II_WorldTickHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(II_WorldTickHandler.INSTANCE);
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            renderLoader = new II_Render();
        }
        itemsLoader = new II_ItemsLoader();
        blocksLoader = new II_BlocksLoader();
        oredictLoader = new II_OredictHandler();
        autogen = new II_AutogenRecipes();

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
        blocksLoader.preLoad();
        II_Fluids.INSTANCE.init();
        new II_MetaGeneratedCellItem();

        new TestBlock();
        GameRegistry.registerTileEntity(TestTile.class, "testTile");
        ClientRegistry.bindTileEntitySpecialRenderer(TestTile.class, new RenderTest());
        II_GuiHandler.init();
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
        try {
            II_ToolRegistry.initTools();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        II_Lang.dumpAll();
        II_Lang.pushLocalToMinecraft();
        oredictLoader.init();
        II_Materials.initMaterialLoops();
        autogen.init();
        II_OreDict.printAll(System.out);
        II_RecipeMaps.optimize();
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {

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
}
