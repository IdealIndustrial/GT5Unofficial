package idealindustrial.recipe.editor;

import cpw.mods.fml.common.FMLCommonHandler;
import idealindustrial.recipe.GuiSlotDefinition;
import idealindustrial.recipe.IRecipeGuiParams;
import idealindustrial.recipe.RecipeMap;
import idealindustrial.tile.gui.base.GenericContainer;
import idealindustrial.tile.gui.base.component.GuiTextures;
import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.SlotHolo;
import idealindustrial.util.fluid.FluidInventoryRepresentation;
import idealindustrial.util.inventory.InternalInventoryWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class RecipeEditorContainer extends GenericContainer {

    IRecipeGuiParams params;

    public RecipeEditorContainer(RecipeMap<?> map, EntityPlayer player) {
        super(new EditorNetworkedInventory(map.getName(), map.getGuiParams()), player, false, false);
        params = map.getGuiParams();
        addSlots();
        this.bindInventory = true;
        bindPlayerInventory(player.inventory, 0, 0);
    }

    @Override
    public void addSlots() {
        addAllSlots(tile, tile.getIn().size(), 0, params.getItemsIn());
        addAllSlots(tile, tile.getOut().size(), tile.getIn().size(), params.getItemsOut());
        addAllSlots(new InternalInventoryWrapper(tile.getSpecial()), tile.getSpecial().size(), 0, params.getItemSpecial());

        addAllSlots(representation, representation.getInSize(), 0, params.getFluidsIn());
        addAllSlots(representation, representation.getOutSize(), representation.getInSize(), params.getFluidsOut());
    }

    protected void addAllSlots(IInventory inventory, int toAdd, int idOffset, GuiSlotDefinition[] definitions) {
        for (int i = 0; i < toAdd; i++) {
            if (inventory instanceof FluidInventoryRepresentation) {
                addSlotToContainer(new SlotHolo(inventory, idOffset + i, definitions[i].x, definitions[i].y, definitions[i].textureID));
            } else {
                addSlotToContainer(new II_SlotWithCheckType(inventory, idOffset + i, definitions[i].x, definitions[i].y, definitions[i].textureID));
            }
        }
    }

    @Override
    protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer, int xOffset, int yOffset) {
        int idOffset = 0;
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new II_Slot(aInventoryPlayer, i + idOffset, xOffset + 8 + i * 18, yOffset + 142, GuiTextures.SlotTextures.SLOT_DEFAULT));
        }
    }

    @Override
    public ItemStack slotClick(int index, int mouse, int hotkeys, EntityPlayer player) {
        if (hotkeys == 1) {
            if (FMLCommonHandler.instance().getEffectiveSide().isClient() && index >= 0 && index < inventorySlots.size() && inventorySlots.get(index) instanceof II_SlotWithCheckType) {
                II_SlotWithCheckType slot = (II_SlotWithCheckType) inventorySlots.get(index);
                slot.switchCheckType();
            }
            return player.getHeldItem();
        }
        return super.slotClick(index, mouse, hotkeys, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }

    public int getProgress() {
        return (int) ((System.currentTimeMillis() / 1000L) % 20L);
    }


}
