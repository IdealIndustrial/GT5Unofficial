package idealindustrial.impl.tile.gui.nei;

import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.impl.recipe.RecipeMaps;

public class NEI_II_Config implements IConfigureNEI {

    public void loadConfig() {
        for (RecipeMap<?> map : RecipeMaps.allRecipeMaps) {
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
