package idealindustrial.teststuff.testTile2;

import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.tile.gui.base.II_GenericContainer;
import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.II_SlotHolo;
import idealindustrial.tile.gui.base.component.II_SlotOutput;
import net.minecraft.entity.player.EntityPlayer;

public class II_Test2ZVontainer extends II_GenericContainer {
    public II_Test2ZVontainer(II_BaseMachineTile tile, EntityPlayer player) {
        super(tile, player, true);
    }

    @Override
    public void addSlots() {
        addSlotToContainer(new II_Slot(tile, 0, 20, 20, 0));
        addSlotToContainer(new II_Slot(tile, 1, 38, 20, 0));

        addSlotToContainer(new II_SlotOutput(tile, 2,100, 20, 0));
        addSlotToContainer(new II_SlotOutput(tile, 3,118, 20, 0));

        addSlotToContainer(new II_SlotHolo(representation, 0, 20, 50, 2));
        addSlotToContainer(new II_SlotHolo(representation, 1, 38, 50, 2));
        addSlotToContainer(new II_SlotHolo(representation, 2, 100, 50, 2));
        addSlotToContainer(new II_SlotHolo(representation, 3, 118, 50, 2));
    }
}
