package idealindustrial.recipe;

public interface IRecipeGuiParams extends Iterable<GuiSlotDefinition> {
    GuiSlotDefinition[] getItemsIn();

    GuiSlotDefinition[] getItemsOut();

    GuiSlotDefinition[] getItemSpecial();

    GuiSlotDefinition[] getFluidsIn();

    GuiSlotDefinition[] getFluidsOut();

    GuiArrowDefinition getArrow();

}
