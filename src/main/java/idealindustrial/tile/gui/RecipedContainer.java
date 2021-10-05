package idealindustrial.tile.gui;

import idealindustrial.net.fields.NetworkedShort;
import idealindustrial.recipe.GuiSlotDefinition;
import idealindustrial.recipe.IRecipeGuiParams;
import idealindustrial.tile.gui.base.GenericContainer;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.impl.recipe.TileMachineReciped;
import idealindustrial.util.inventory.InternalInventoryWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class RecipedContainer extends GenericContainer {

    TileMachineReciped<?, ?> metaTile;
    IRecipeGuiParams params;
    @SuppressWarnings("unchecked")
    NetworkedShort progress = new NetworkedShort(1, this, crafters);

    public RecipedContainer(HostMachineTile tile, EntityPlayer player, IRecipeGuiParams params) {
        super(tile, player, false, false);
        this.params = params;
        addSlots();
        bindPlayerInventory(player.inventory, 0, 0);
        bindInventory = true;
        this.metaTile = (TileMachineReciped<?, ?>) tile.getMetaTile();

    }

    @Override
    public void addSlots() {
//        Arrays.stream(params.getItemsIn()).limit(tile.getIn().size()).forEach(d -> d.construct(tile, ));
//        int itemsIn = Math.min(tile.getIn().size(), params.itemsIn());
//        int itemsOut = Math.min(tile.getOut().size(), params.itemsOut());
//        addInventorySlotsPositioned(tile, 53, tile.getIn().size() <= 3 ? 25 : 34,
//                0, itemsIn, false,
//                ((inventory, slotId, x, y, i) -> new II_Slot(inventory, slotId, x, y, params.texturesItemsIn()[i])));
//
//        addInventorySlotsPositioned(tile, 107, tile.getIn().size() <= 3 ? 25 : 34,
//                itemsIn, itemsOut, true,
//                ((inventory, slotId, x, y, i) -> new II_SlotOutput(inventory, slotId, x, y, params.texturesItemsOut()[i])));
//
//        addSpecialSlots(tile.getSpecial());
//
//        int fluidsIn = Math.min(representation.getInSize(), params.fluidsIn());
//        int fluidsOut = Math.min(representation.getOutSize(), params.fluidsOut());
//        addInventorySlotsPositioned(representation, 53, 63,
//                0, fluidsIn, false,
//                ((inventory, slotId, x, y, i) -> new II_SlotHolo(inventory, slotId, x, y, params.texturesFluidsIn()[i])));
//
//        addInventorySlotsPositioned(representation, 107, 63,
//                fluidsIn,fluidsOut, true,
//                ((inventory, slotId, x, y, i) -> new II_SlotHolo(inventory, slotId, x, y, params.texturesFluidsOut()[i])));
        addAllSlots(tile, tile.getIn().size(), 0, params.getItemsIn());
        addAllSlots(tile, tile.getOut().size(), tile.getIn().size(), params.getItemsOut());
        addAllSlots(new InternalInventoryWrapper(tile.getSpecial()), tile.getSpecial().size(), 0, params.getItemSpecial());

        addAllSlots(representation, representation.getInSize(), 0, params.getFluidsIn());
        addAllSlots(representation, representation.getOutSize(),  representation.getInSize(), params.getFluidsOut());
    }

    protected void addAllSlots(IInventory inventory, int toAdd, int idOffset, GuiSlotDefinition[] definitions) {
        for (int i = 0; i < toAdd; i++) {
            addSlotToContainer(definitions[i].construct(inventory, idOffset + i));
        }
    }




    protected short getProgressBar() {
        return metaTile.getProgress();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        progress.set(getProgressBar());
    }

    @Override
    public void updateProgressBar(int id, int value) {
        super.updateProgressBar(id, value);
        progress.accept(id, value);
    }
}
