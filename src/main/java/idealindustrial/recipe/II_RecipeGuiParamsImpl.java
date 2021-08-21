package idealindustrial.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class II_RecipeGuiParamsImpl implements II_RecipeGuiParams {

    II_GuiSlotDefinition[] itemsIn, itemsOut, itemSpecial, fluidsIn, fluidsOut;
    II_GuiSlotDefinition[][] arrays;
    II_GuiArrowDefinition arrow;

    public II_RecipeGuiParamsImpl(II_GuiSlotDefinition[] itemsIn, II_GuiSlotDefinition[] itemsOut, II_GuiSlotDefinition[] itemSpecial, II_GuiSlotDefinition[] fluidsIn, II_GuiSlotDefinition[] fluidsOut, II_GuiArrowDefinition arrow) {
        this.itemsIn = itemsIn;
        this.itemsOut = itemsOut;
        this.itemSpecial = itemSpecial;
        this.fluidsIn = fluidsIn;
        this.fluidsOut = fluidsOut;
        this.arrow = arrow;
        this.arrays = new II_GuiSlotDefinition[][]{this.itemsIn, this.itemsOut, this.itemSpecial, this.fluidsIn, this.fluidsOut};
    }


    @Override
    public II_GuiSlotDefinition[] getItemsIn() {
        return itemsIn;
    }

    @Override
    public II_GuiSlotDefinition[] getItemsOut() {
        return itemsOut;
    }

    @Override
    public II_GuiSlotDefinition[] getItemSpecial() {
        return itemSpecial;
    }

    @Override
    public II_GuiSlotDefinition[] getFluidsIn() {
        return fluidsIn;
    }

    @Override
    public II_GuiSlotDefinition[] getFluidsOut() {
        return fluidsOut;
    }

    @Override
    public II_GuiArrowDefinition getArrow() {
        return arrow;
    }

    @Override
    public Iterator<II_GuiSlotDefinition> iterator() {
        return Arrays.stream(arrays).flatMap(Arrays::stream).iterator();
    }
}
