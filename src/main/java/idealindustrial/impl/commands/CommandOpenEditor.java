package idealindustrial.impl.commands;

import idealindustrial.II_Core;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.impl.recipe.RecipeMaps;
import idealindustrial.impl.recipe.editor.RecipeEditorContainer;
import idealindustrial.impl.recipe.editor.RecipeEditorGuiContainer;
import idealindustrial.util.misc.II_Util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandOpenEditor extends CommandBase {
    static List<String> maps = new ArrayList<>();
    static {
        try {
            for (Field f : RecipeMaps.class.getDeclaredFields()) {
                if (RecipeMap.class.isAssignableFrom(f.getType())) {
                    maps.add(f.getName());
                }
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getCommandName() {
        return "editor";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "Used to open recipe editor";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }


    @SuppressWarnings("rawtypes")
    @Override
    public List addTabCompletionOptions(ICommandSender player, String[] args) {
        String filter = args == null || args.length == 0 ? "" : args[0];
        return maps.stream().filter(s -> s.startsWith(filter)).collect(Collectors.toList());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = (EntityPlayer) sender;
        if (args.length == 0) {
            II_Util.sendChatToPlayer(player, "To few argument for a command");
            return;
        }
        String machine = args[0];
        RecipeMap<?> map = RecipeMaps.getMap(machine);
        if (map == null) {
            II_Util.sendChatToPlayer(player, "No such recipe map");
            return;
        }

        player.openGui(II_Core.MOD_ID, 1000 + RecipeMaps.name2id.get(machine), player.worldObj, (int)player.posX, (int)player.posY, (int) player.posZ);
    }

    public static RecipeEditorContainer getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        RecipeMap<?> map = RecipeMaps.id2map.get(ID - 1000);
        return new RecipeEditorContainer(map, player);
    }

    public static Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        RecipeMap<?> map = RecipeMaps.id2map.get(ID - 1000);
        return new RecipeEditorGuiContainer(map, player);
    }
}
