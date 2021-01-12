package idealindustial;

import codechicken.nei.NEIServerConfig;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustial.commands.CommandFixQuests;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import static gregtech.GT_Mod.gregtechproxy;

@Mod(modid = "iicore", name = "II_Core", version = "MC1710", useMetadata = false, dependencies = "after:gregtech")
public class II_Core {

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

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event) {
        System.out.println("gg");
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        System.out.println("gg");

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
            return false;
        }
        return true;
    }
}
