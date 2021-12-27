package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IRecipeGuiParams;

import java.util.Arrays;
import java.util.Iterator;

public class RecipeGuiParamsImpl implements IRecipeGuiParams {

    GuiSlotDefinition[] itemsIn, itemsOut, itemSpecial, fluidsIn, fluidsOut, holo;
    GuiSlotDefinition[][] arrays;
    GuiArrowDefinition arrow;

    public RecipeGuiParamsImpl(GuiSlotDefinition[] itemsIn, GuiSlotDefinition[] itemsOut, GuiSlotDefinition[] itemSpecial, GuiSlotDefinition[] fluidsIn, GuiSlotDefinition[] fluidsOut, GuiSlotDefinition[] holo,GuiArrowDefinition arrow) {
        this.itemsIn = itemsIn;
        this.itemsOut = itemsOut;
        this.itemSpecial = itemSpecial;
        this.fluidsIn = fluidsIn;
        this.fluidsOut = fluidsOut;
        this.holo = holo;
        this.arrow = arrow;
        this.arrays = new GuiSlotDefinition[][]{this.itemsIn, this.itemsOut, this.itemSpecial, this.fluidsIn, this.fluidsOut, this.holo};
    }


    @Override
    public GuiSlotDefinition[] getItemsIn() {
        return itemsIn;
    }

    @Override
    public GuiSlotDefinition[] getItemsOut() {
        return itemsOut;
    }

    @Override
    public GuiSlotDefinition[] getItemSpecial() {
        return itemSpecial;
    }

    @Override
    public GuiSlotDefinition[] getFluidsIn() {
        return fluidsIn;
    }

    @Override
    public GuiSlotDefinition[] getFluidsOut() {
        return fluidsOut;
    }

    @Override
    public GuiSlotDefinition[] getHoloSlots() {
        return holo;
    }

    @Override
    public GuiArrowDefinition getArrow() {
        return arrow;
    }

    @Override
    public Iterator<GuiSlotDefinition> iterator() {
        return Arrays.stream(arrays).flatMap(Arrays::stream).iterator();
    }
}
