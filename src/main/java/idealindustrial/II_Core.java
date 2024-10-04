package idealindustrial;

import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustrial.commands.CommandFixMaterials;
import idealindustrial.commands.CommandFixQuests;
import idealindustrial.commands.DimTPCommand;
import idealindustrial.commands.ReloadRecipesCommand;
import idealindustrial.integration.ingameinfo.InGameInfoLoader;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

@Mod(modid = "iicore", name = "II_Core", version = "MC1710", useMetadata = false, dependencies = "after:gregtech")
public class II_Core {

    private static final String PACKAGES_URL = "https://github.com/IdealIndustrial/GT5Unofficial/packages/2134484";
    private static final Pattern PARSE_PATTERN = Pattern.compile("gt-ii-edition-(.*?)-.*?-(\\d+).jar", Pattern.MULTILINE);
    private static final String PROPERTY_VERSION_URL = "/version.properties";
    
    private static final String version = "1.19.2";
    private String auto_version = "??";
    private String auto_build = "??";
    private String auto_last_version = null;
    private String auto_last_build = null;
    private String first_line = "Current version: %1$s-%2$s";
    private String second_line = "Latest dev build: %1$s-%2$s";

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
        if (Loader.isModLoaded("InGameInfoXML") && FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            new InGameInfoLoader().load();
        }
    }

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event) {

    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void openGui(GuiOpenEvent event) {
        if (auto_last_version != null || !(event.gui instanceof net.minecraft.client.gui.GuiMainMenu || event.gui.getClass().getSimpleName().equals("GuiCustom"))) {
            return;
        }
        getCurrentFileVersion();
        getLatestBuild();

        first_line = String.format(first_line, auto_version, auto_build);
        second_line = String.format(second_line, auto_last_version, auto_last_build);
    }

    private void getLatestBuild() {
        try {
            URL url = new URL(PACKAGES_URL);
            InputStream con = url.openStream();
            String pageText = new String(ByteStreams.toByteArray(con));
            con.close();
            Matcher m = PARSE_PATTERN.matcher(pageText);
            if (m.find()) {
                if (m.groupCount() == 2) {
                    auto_last_version = m.group(1);
                    auto_last_build = m.group(2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCurrentFileVersion() {
        try {
            Properties p = new Properties();
            InputStream is = getClass().getResourceAsStream(PROPERTY_VERSION_URL);
            if (is != null) {
                p.load(is);
                is.close();
                auto_version = p.getProperty("version", auto_version).replace("-SNAPSHOT", "");
                String filename = getClass().getResource(PROPERTY_VERSION_URL).getFile();
                if (filename != null) {
                    Matcher m = PARSE_PATTERN.matcher(filename);
                    if (m.find()) {
                        if (m.groupCount() == 2) {
                            auto_version = m.group(1);
                            auto_build = m.group(2);
                        }
                    }
                }
            } else {
                //dev build
                auto_version = "dev";
                auto_build = "dev";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void renderScreenPost(GuiScreenEvent.DrawScreenEvent.Post event) throws IOException {
        if (event.gui instanceof net.minecraft.client.gui.GuiMainMenu || event.gui.getClass().getSimpleName().equals("GuiCustom")) {
            FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
            event.gui.drawString(fr, first_line, event.gui.width - fr.getStringWidth(first_line) - 1, 0, Color.ORANGE.getRGB());
            event.gui.drawString(fr, second_line, event.gui.width - fr.getStringWidth(second_line)- 1, fr.FONT_HEIGHT + 1, Color.ORANGE.getRGB());
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

            try {
                Class.forName("com.juanmuscaria.playercontainerfix.FMLCoreMod");
            } catch (ClassNotFoundException f) {
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
