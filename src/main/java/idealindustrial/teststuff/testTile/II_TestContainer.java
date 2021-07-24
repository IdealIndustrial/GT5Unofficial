package idealindustrial.teststuff.testTile;

import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.gui.base.II_GenericContainer;
import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.II_SlotOutput;
import net.minecraft.entity.player.EntityPlayer;

public class II_TestContainer extends II_GenericContainer {
    public II_TestContainer(II_BaseTile tile, EntityPlayer player) {
        super(tile, player, true);
    }

    @Override
    public void addSlots() {
        addSlotToContainer(new II_Slot(tile, 0, 20, 20, 0));
        addSlotToContainer(new II_SlotOutput(tile, 1,50, 50, 0));
    }
}
