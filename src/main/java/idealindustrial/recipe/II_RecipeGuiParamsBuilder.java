package idealindustrial.recipe;

import idealindustrial.tile.gui.base.component.II_GuiTextures;
import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.II_SlotHolo;
import idealindustrial.tile.gui.base.component.II_SlotOutput;

import java.util.Arrays;
import java.util.stream.Stream;

public class II_RecipeGuiParamsBuilder {

    II_GuiSlotDefinition[] itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut;
    II_GuiArrowDefinition arrow;

    public II_RecipeGuiParamsBuilder(int itemsIn, int itemsOut, int itemsSpecial, int fluidsIn, int fluidsOut) {
        this.itemsIn = Stream.generate(II_GuiSlotDefinition::new).limit(itemsIn).toArray(II_GuiSlotDefinition[]::new);
        this.itemsOut = Stream.generate(II_GuiSlotDefinition::new).limit(itemsOut).toArray(II_GuiSlotDefinition[]::new);
        this.itemsSpecial = Stream.generate(II_GuiSlotDefinition::new).limit(itemsSpecial).toArray(II_GuiSlotDefinition[]::new);

        Stream.of(this.itemsIn, this.itemsSpecial).forEach(ar -> Arrays.stream(ar).forEach(def -> def.slotSupplier = II_Slot::new));
        Arrays.stream(this.itemsOut).forEach(def -> def.slotSupplier = II_SlotOutput::new);

        this.fluidsIn = Stream.generate(II_GuiSlotDefinition::new).limit(fluidsIn).toArray(II_GuiSlotDefinition[]::new);
        this.fluidsOut = Stream.generate(II_GuiSlotDefinition::new).limit(fluidsOut).toArray(II_GuiSlotDefinition[]::new);
        Stream.of(this.fluidsIn, this.fluidsOut).forEach(ar -> Arrays.stream(ar).forEach(def -> def.slotSupplier = II_SlotHolo::new));

        setGlobalItemTexture(II_GuiTextures.SlotTextures.SLOT_DEFAULT);
        setGlobalFluidTexture(II_GuiTextures.SlotTextures.SLOT_FLUID_DEFAULT);
        setMachineRecipeSlotCoords();

        setArrowTexture(0);
    }

    public II_RecipeGuiParamsBuilder setGlobalItemTexture(int texture) {
        Stream.of(itemsIn, itemsOut, itemsSpecial).forEach(ar -> Arrays.stream(ar).forEach(def -> def.textureID = texture));
        return this;
    }

    public II_RecipeGuiParamsBuilder setGlobalFluidTexture(int texture) {
        Stream.of(fluidsIn, fluidsOut).forEach(ar -> Arrays.stream(ar).forEach(def -> def.textureID = texture));
        return this;
    }

    public II_RecipeGuiParamsBuilder setMachineRecipeSlotCoords() {
        addInventorySlotsPositioned(53, itemsIn.length <= 3 ? 25 : 34, false, itemsIn);
        addInventorySlotsPositioned(107, itemsOut.length <= 3 ? 25 : 34, true, itemsOut);
        addInventorySlotsPositioned(53, 63, false, fluidsIn);
        addInventorySlotsPositioned(107, 63, true, fluidsOut);
        return this;
    }

    public II_RecipeGuiParamsBuilder setArrow(int x, int y, int texture, int direction) {
        arrow = new II_GuiArrowDefinition(x, y, texture, direction);
        return this;
    }

    public II_RecipeGuiParamsBuilder setArrowTexture(int texture) {
        return setArrow(76, 26, texture, 1);//todo check dir
    }

    protected void addInventorySlotsPositioned(int startX, int startY, boolean xIncrease, II_GuiSlotDefinition[] definitions) {
        int multiplier = xIncrease ? 1 : -1;
        int alreadyAdded = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++) {
                if (alreadyAdded >= definitions.length) {
                    return;
                }
                definitions[alreadyAdded].x = startX + multiplier * x * 18;
                definitions[alreadyAdded].y = startY + y * 18;
                alreadyAdded++;
            }
        }
    }

    public II_RecipeGuiParams construct() {
        return new II_RecipeGuiParamsImpl(itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut, arrow);
    }
}
