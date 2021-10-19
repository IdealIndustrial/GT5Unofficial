package idealindustrial.commands;

import gloomyfolken.hooklib.minecraft.HookLibPlugin;
import idealindustrial.hooks.HookLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class CommandFixMaterials extends CommandBase {


    private static boolean executing;
    private static int executeState = 0;
    private static final Map<Integer, Integer> subIDsMap = new HashMap<>();
    private static boolean wasExecutedAlready = false;
    private static File fileSave;

    static {
        Stream.of("381->379", "379->380", "365->368", "380->381").map(str -> str.split("->"))
                .forEach(strings -> subIDsMap.put(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public String getCommandName() {
        return "fixMaterials";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        if (!HookLoader.gtMats) {
            ics.addChatMessage(new ChatComponentText("First enable GT materials remap in II_Patches.cfg"));
            return;
        }

        if (wasExecutedAlready) {
            ics.addChatMessage(new ChatComponentText("Materials were already fixed"));
            return;
        }

        Method saveEverything;
        try {
            if (HookLibPlugin.getObfuscated()) {
                //noinspection JavaReflectionMemberAccess
                saveEverything = MinecraftServer.class.getDeclaredMethod("func_71267_a", boolean.class);
            }
            else {
                saveEverything = MinecraftServer.class.getDeclaredMethod("saveAllWorlds", boolean.class);
            }
            saveEverything.setAccessible(true);
        } catch (NoSuchMethodException e) {
            ics.addChatMessage(new ChatComponentText("Something went wrong, may be you're using patched minecraft server"));
            e.printStackTrace();
            return;
        }
        executing = true;
        executeState = 1;
        System.out.println("patches injected, saving all worlds");
        try {
            saveEverything.invoke(MinecraftServer.getServer(), false);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("failed to save all", e);
        }
        MinecraftServer.getServer().getConfigurationManager().saveAllPlayerData();
        System.out.println("removing dummy tags from items");
        executeState = 2;
        try {
            saveEverything.invoke(MinecraftServer.getServer(), false);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("failed to save all", e);
        }
        MinecraftServer.getServer().getConfigurationManager().saveAllPlayerData();
        System.out.println("remapped items successfully");
        executeState = 0;
        executing = false;
        wasExecutedAlready = true;
        try {
            writeFile(fileSave, true);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean isExecuting() {
        return executing;
    }

    public static NBTTagCompound processStack(ItemStack stack, NBTTagCompound nbt) {
        return nbt;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "fixes enderio gt material IDs";
    }

    public static void loadWorld(File iiDirectory) throws IOException {
        File worldSettings = new File(iiDirectory, "server.txt");
        if (!worldSettings.exists()) {
            try {
                worldSettings.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
                return;
            }
            writeFile(worldSettings, false);
        }
        Scanner scanner = new Scanner(worldSettings);
        try {
            wasExecutedAlready = Boolean.parseBoolean(scanner.nextLine().split("=")[1]);
        }
        catch (Exception e) {
            e.printStackTrace();
            writeFile(worldSettings, false);
        }
        fileSave = worldSettings;
    }

    private static void writeFile(File worldSettings, boolean wasExecutedAlready) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(worldSettings));
        writer.println("materialsFixed="+wasExecutedAlready);
        writer.println("IIM.ver=1.17.1");
        writer.flush();
        writer.close();
    }

}
