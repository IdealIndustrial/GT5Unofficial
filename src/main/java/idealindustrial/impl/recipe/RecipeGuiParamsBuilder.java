package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.impl.tile.gui.base.component.GuiTextures;
import idealindustrial.impl.tile.gui.base.component.II_Slot;
import idealindustrial.impl.tile.gui.base.component.SlotHolo;
import idealindustrial.impl.tile.gui.base.component.SlotOutput;

import java.util.Arrays;
import java.util.stream.Stream;

public class RecipeGuiParamsBuilder {

    GuiSlotDefinition[] itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut;
    GuiArrowDefinition arrow;

    public RecipeGuiParamsBuilder(int itemsIn, int itemsOut, int itemsSpecial, int fluidsIn, int fluidsOut) {
        this.itemsIn = Stream.generate(GuiSlotDefinition::new).limit(itemsIn).toArray(GuiSlotDefinition[]::new);
        this.itemsOut = Stream.generate(GuiSlotDefinition::new).limit(itemsOut).toArray(GuiSlotDefinition[]::new);
        this.itemsSpecial = Stream.generate(GuiSlotDefinition::new).limit(itemsSpecial).toArray(GuiSlotDefinition[]::new);

        Stream.of(this.itemsIn, this.itemsSpecial).forEach(ar -> Arrays.stream(ar).forEach(def -> def.slotSupplier = II_Slot::new));
        Arrays.stream(this.itemsOut).forEach(def -> def.slotSupplier = SlotOutput::new);

        this.fluidsIn = Stream.generate(GuiSlotDefinition::new).limit(fluidsIn).toArray(GuiSlotDefinition[]::new);
        this.fluidsOut = Stream.generate(GuiSlotDefinition::new).limit(fluidsOut).toArray(GuiSlotDefinition[]::new);
        Stream.of(this.fluidsIn, this.fluidsOut).forEach(ar -> Arrays.stream(ar).forEach(def -> def.slotSupplier = SlotHolo::new));

        setGlobalItemTexture(GuiTextures.SlotTextures.SLOT_DEFAULT);
        setGlobalFluidTexture(GuiTextures.SlotTextures.SLOT_FLUID_DEFAULT);
        setMachineRecipeSlotCoords();

        setArrowTexture(0);
    }

    public RecipeGuiParamsBuilder setGlobalItemTexture(int texture) {
        Stream.of(itemsIn, itemsOut, itemsSpecial).forEach(ar -> Arrays.stream(ar).forEach(def -> def.textureID = texture));
        return this;
    }

    public RecipeGuiParamsBuilder setGlobalFluidTexture(int texture) {
        Stream.of(fluidsIn, fluidsOut).forEach(ar -> Arrays.stream(ar).forEach(def -> def.textureID = texture));
        return this;
    }

    public RecipeGuiParamsBuilder setMachineRecipeSlotCoords() {
        addInventorySlotsPositioned(53, itemsIn.length <= 3 ? 25 : 34, false, itemsIn);
        addInventorySlotsPositioned(107, itemsOut.length <= 3 ? 25 : 34, true, itemsOut);
        addInventorySlotsPositioned(53, 63, false, fluidsIn);
        addInventorySlotsPositioned(107, 63, true, fluidsOut);
        return this;
    }

    public RecipeGuiParamsBuilder setArrow(int x, int y, int texture, int direction) {
        arrow = new GuiArrowDefinition(x, y, texture, direction);
        return this;
    }

    public RecipeGuiParamsBuilder setArrowTexture(int texture) {
        return setArrow(76, 26, texture, 1);//todo check dir
    }

    protected void addInventorySlotsPositioned(int startX, int startY, boolean xIncrease, GuiSlotDefinition[] definitions) {
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

    public IRecipeGuiParams construct() {
        return new RecipeGuiParamsImpl(itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut, arrow);
    }
}
