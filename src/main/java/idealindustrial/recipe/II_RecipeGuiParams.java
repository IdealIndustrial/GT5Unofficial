package idealindustrial.recipe;

import java.util.stream.Stream;

public interface II_RecipeGuiParams extends Iterable<II_GuiSlotDefinition> {
    II_GuiSlotDefinition[] getItemsIn();

    II_GuiSlotDefinition[] getItemsOut();

    II_GuiSlotDefinition[] getItemSpecial();

    II_GuiSlotDefinition[] getFluidsIn();

    II_GuiSlotDefinition[] getFluidsOut();

    II_GuiArrowDefinition getArrow();

}
