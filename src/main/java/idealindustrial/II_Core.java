package idealindustrial;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustrial.commands.CommandFixMaterials;
import idealindustrial.commands.CommandFixQuests;
import idealindustrial.commands.DimTPCommand;
import idealindustrial.commands.ReloadRecipesCommand;
import idealindustrial.integration.ingameinfo.InGameInfoLoader;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;
import java.io.IOException;

@Mod(modid = "iicore", name = "II_Core", version = "MC1710", useMetadata = false, dependencies = "after:gregtech")
public class II_Core {
    private static final String version = "1.17.1";

    public II_Core() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);

    }

    @Mod.EventHandler
    public void onPreLoad(FMLPreInitializationEvent aEvent) {
        if (!checkEnvironment()) {
            CrashReport tCrashReport = new CrashReport("Wrong enviroment detected, please install BQfix for thermos: https://github.com/IdealIndustrial/Ideal-Industrial-Quests", new RuntimeException("no fix for better questing is detected"));
            throw new ReportedException(tCrashReport);
        }

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("InGameInfoXML")) {
            new InGameInfoLoader().load();
        }
    }

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event) {

    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {

    }

    private static boolean checkEnvironment() {
        try {
            Class.forName("thermos.Thermos");
        }
        catch (ClassNotFoundException e) {
            return true;
        }
        try {
            Class.forName("betterquesting.core.BetterQuesting");
        }
        catch (ClassNotFoundException e){
            return true;
        }
    
        try {
            Class.forName("a.b.c.gambiarra.Plugin");
		}
		catch (ClassNotFoundException e) {
        
			try {
				Class.forName("com.juanmuscaria.playercontainerfix.FMLCoreMod");
			}
			catch (ClassNotFoundException f) {
				return false; 
			}		
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
