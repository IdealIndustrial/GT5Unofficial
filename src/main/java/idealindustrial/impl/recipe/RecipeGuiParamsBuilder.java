package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.tile.gui.SlotSupplier;
import idealindustrial.impl.tile.gui.base.component.GuiTextures;
import idealindustrial.impl.tile.gui.base.component.II_Slot;
import idealindustrial.impl.tile.gui.base.component.SlotHolo;
import idealindustrial.impl.tile.gui.base.component.SlotOutput;

import java.util.Arrays;
import java.util.stream.Stream;

public class RecipeGuiParamsBuilder {

    public static final IRecipeGuiParams EMPTY_PARAMS = new RecipeGuiParamsBuilder(0, 0, 0, 0,0).construct();

    GuiSlotDefinition[] itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut, holo;
    GuiArrowDefinition arrow;

    public RecipeGuiParamsBuilder(int itemsIn, int itemsOut, int itemsSpecial, int fluidsIn, int fluidsOut) {
        this(itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut, 0);
    }

    public RecipeGuiParamsBuilder(int itemsIn, int itemsOut, int itemsSpecial, int fluidsIn, int fluidsOut, int holo) {
        this.itemsIn = Stream.generate(GuiSlotDefinition::new).limit(itemsIn).toArray(GuiSlotDefinition[]::new);
        this.itemsOut = Stream.generate(GuiSlotDefinition::new).limit(itemsOut).toArray(GuiSlotDefinition[]::new);
        this.itemsSpecial = Stream.generate(GuiSlotDefinition::new).limit(itemsSpecial).toArray(GuiSlotDefinition[]::new);
        this.holo = Stream.generate(GuiSlotDefinition::new).limit(holo).toArray(GuiSlotDefinition[]::new);

        Stream.of(this.itemsIn, this.itemsSpecial).forEach(ar -> Arrays.stream(ar).forEach(def -> def.slotSupplier = II_Slot::new));
        Arrays.stream(this.itemsOut).forEach(def -> def.slotSupplier = SlotOutput::new);

        this.fluidsIn = Stream.generate(GuiSlotDefinition::new).limit(fluidsIn).toArray(GuiSlotDefinition[]::new);
        this.fluidsOut = Stream.generate(GuiSlotDefinition::new).limit(fluidsOut).toArray(GuiSlotDefinition[]::new);
        Stream.of(this.fluidsIn, this.fluidsOut).forEach(ar -> Arrays.stream(ar).forEach(def -> def.slotSupplier = SlotHolo::new));

        setGlobalItemTexture(GuiTextures.SlotTextures.SLOT_DEFAULT);
        setGlobalFluidTexture(GuiTextures.SlotTextures.SLOT_FLUID_DEFAULT);
        setMachineRecipeSlotCoords(53, 107, 25, 63);

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

    public RecipeGuiParamsBuilder setMachineRecipeSlotCoords(int inputX, int outputX, int itemY, int fluidY) {
        int[] rowOffsets = new int[]{0, 9, -18};
        addInventorySlotsPositioned(inputX, itemY + rowOffsets[(itemsIn.length - 1) / 3], false, itemsIn);
        addInventorySlotsPositioned(outputX, itemY + rowOffsets[(itemsOut.length - 1) / 3], true, itemsOut);
        addInventorySlotsPositioned(inputX, fluidY, false, fluidsIn);
        addInventorySlotsPositioned(outputX, fluidY, true, fluidsOut);
        return this;
    }

    public RecipeGuiParamsBuilder setArrow(int x, int y, int texture, int direction) {
        arrow = new GuiArrowDefinition(x, y, texture, direction);
        return this;
    }

    public RecipeGuiParamsBuilder setArrowTexture(int texture) {
        return setArrow(76, 26, texture, 1);//todo check dir
    }

    public enum SlotType {
        ItemsIn, ItemsOut, ItemsSpecial, FluidsIn, FluidsOut, Holo
    }

    public RecipeGuiParamsBuilder moveSlot(SlotType type, int id, int x, int y) {
        GuiSlotDefinition[][] ar = new GuiSlotDefinition[][]{itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut, holo};//todo: store this array
        GuiSlotDefinition slot = ar[type.ordinal()][id];
        slot.x = x;
        slot.y = y;
        return this;
    }

    public RecipeGuiParamsBuilder setSlot(SlotType type, int id, int x, int y, int texture, SlotSupplier slotSupplier) {
        GuiSlotDefinition[][] ar = new GuiSlotDefinition[][]{itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut, holo};
        ar[type.ordinal()][id] = new GuiSlotDefinition(x, y, texture, slotSupplier);
        return this;
    }

    protected void addInventorySlotsPositioned(int startX, int startY, boolean xIncrease, GuiSlotDefinition[] definitions) {
        int multiplier = xIncrease ? 1 : -1;
        int alreadyAdded = 0;
        for (int y = 0; y < 3; y++) {
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
        return new RecipeGuiParamsImpl(itemsIn, itemsOut, itemsSpecial, fluidsIn, fluidsOut, holo, arrow);
    }

}
