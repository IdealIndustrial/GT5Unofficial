package idealindustial;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

public class II_Core {

    public static void onPreLoad(FMLPreInitializationEvent aEvent) {
        if (!checkEnvironment()) {
            CrashReport tCrashReport = new CrashReport("Wrong enviroment detected, please install BQfix for thermos: https://github.com/IdealIndustrial/Ideal-Industrial-Quests", new RuntimeException("no fix for better questing is detected"));
            throw new ReportedException(tCrashReport);
        }
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
