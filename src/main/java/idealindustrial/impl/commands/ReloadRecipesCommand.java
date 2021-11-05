package idealindustrial.impl.commands;

import codechicken.nei.api.GT_NEIItemStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ChatComponentText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ReloadRecipesCommand extends CommandBase {

    //list for loader classes, only empty constructors are accepted
    private static final List<Class<? extends Runnable>> runnableLoaders = new ArrayList<>();

    private static final List<Class<? extends Runnable>> currentRunnableLoaders = new ArrayList<>();

    //map for tab addings
    private static final Map<String, Class> classMap = new HashMap<>();

    static {
        runnableLoaders.forEach(l -> classMap.put(l.getSimpleName(), l));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public String getCommandName() {
        return "rr";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        if (args.length == 0) {
            clearRecipeMaps();
            try {
                executeAll();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            invalidateNEICaches();

        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                currentRunnableLoaders.clear();
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                Class cl = classMap.get(args[1]);
                if (cl == null) {
                    ics.addChatMessage(new ChatComponentText("Unknown class"));
                } else {
                    if (Runnable.class.isAssignableFrom(cl)) {
                        currentRunnableLoaders.add(cl);
                        ics.addChatMessage(new ChatComponentText("added"));
                    }
                }
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] args) {
        if (args.length == 1) {
            //clear
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                List<String> ret = new ArrayList<>();
                for (String s : classMap.keySet()) {
                    if (s.startsWith(args[1])) {
                        ret.add(s);
                    }
                }
                return ret;
            }
        }
        return null;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "reload recipes /rr - reloads all recipe classes";
    }

    private void clearRecipeMaps() {
        ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
        tList.clear();
    }

    private void executeAll() throws IllegalAccessException, InstantiationException {
        for (Class<? extends Runnable> cl : currentRunnableLoaders) {
            Runnable runnable = cl.newInstance();
            runnable.run();
        }

    }

    private void invalidateNEICaches() {

        ShapedRecipeHandler.recipesMap.clear();
        ShapedRecipeHandler.usesMap.clear();
        try {
            Class cl = ShapedRecipeHandler.class;
            Field rMaps = cl.getDeclaredField("recipesMap");
            Field uMaps = cl.getDeclaredField("usesMap");
            Map<GT_NEIItemStack, List<IRecipe>> rM = (Map<GT_NEIItemStack, List<IRecipe>>) rMaps.get(null);
            Map<GT_NEIItemStack, List<IRecipe>> uM = (Map<GT_NEIItemStack, List<IRecipe>>) uMaps.get(null);
            rM.clear();
            uM.clear();
        } catch (Exception ignored) {

        }
    }

}
