package idealindustrial.impl.recipe.editor;

import idealindustrial.impl.tile.gui.base.GenericGuiContainer;
import idealindustrial.impl.tile.gui.base.component.II_Slot;
import idealindustrial.impl.item.stack.CheckType;
import net.minecraft.inventory.IInventory;

import java.awt.*;

import static idealindustrial.impl.tile.gui.base.component.GuiTextures.SLOTS;

public class II_SlotWithCheckType extends II_Slot {
    CheckType checkType = CheckType.DIRECT;
    static Color[] colors = new Color[]{Color.WHITE, Color.CYAN, Color.GREEN};

    public II_SlotWithCheckType(IInventory inventory, int id, int x, int y, int texture) {
        super(inventory, id, x, y, texture);
    }

    @Override
    public void draw(int x, int y, GenericGuiContainer<?> container) {
        int id = texture;
        int textureX = SLOTS.idToTextureX(id), textureY = SLOTS.idToTextureY(id);
        drawTexturedModalRect(x, y, container.getZLevel(), textureX, textureY, 18, 18, colors[checkType.ordinal()]);
    }

    public void switchCheckType() {
        checkType = CheckType.values()[(checkType.ordinal() + 1) % CheckType.values().length];
    }
}

