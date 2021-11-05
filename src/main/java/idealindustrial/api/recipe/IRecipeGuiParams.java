package idealindustrial.api.recipe;

import idealindustrial.impl.recipe.GuiArrowDefinition;
import idealindustrial.impl.recipe.GuiSlotDefinition;

public interface IRecipeGuiParams extends Iterable<GuiSlotDefinition> {
    GuiSlotDefinition[] getItemsIn();

    GuiSlotDefinition[] getItemsOut();

    GuiSlotDefinition[] getItemSpecial();

    GuiSlotDefinition[] getFluidsIn();

    GuiSlotDefinition[] getFluidsOut();

    GuiArrowDefinition getArrow();

}
