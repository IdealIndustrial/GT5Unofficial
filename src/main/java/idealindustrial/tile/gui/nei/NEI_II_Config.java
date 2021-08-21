package idealindustrial.tile.gui.nei;

import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import idealindustrial.recipe.II_RecipeMap;
import idealindustrial.recipe.II_RecipeMaps;

public class NEI_II_Config implements IConfigureNEI {

    public void loadConfig() {
        for (II_RecipeMap<?> map : II_RecipeMaps.allRecipeMaps) {
            II_BasicNeiTemplateHandler handler = new II_BasicNeiTemplateHandler(map);
            GuiCraftingRecipe.craftinghandlers.add(handler);
            GuiUsageRecipe.usagehandlers.add(handler);

        }
    }

    public String getName() {
        return "II NEI Plugin";
    }

    public String getVersion() {
        return "(0.01a)";
    }
}
