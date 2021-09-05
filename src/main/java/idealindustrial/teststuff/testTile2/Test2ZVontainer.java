package idealindustrial.teststuff.testTile2;

import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.gui.base.GenericContainer;
import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.SlotHolo;
import idealindustrial.tile.gui.base.component.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;

public class Test2ZVontainer extends GenericContainer {
    public Test2ZVontainer(BaseMachineTile tile, EntityPlayer player) {
        super(tile, player, true,true);
    }

    @Override
    public void addSlots() {
        addSlotToContainer(new II_Slot(tile, 0, 20, 20, 0));
        addSlotToContainer(new II_Slot(tile, 1, 38, 20, 0));

        addSlotToContainer(new SlotOutput(tile, 2,100, 20, 0));
        addSlotToContainer(new SlotOutput(tile, 3,118, 20, 0));

        addSlotToContainer(new SlotHolo(representation, 0, 20, 50, 2));
        addSlotToContainer(new SlotHolo(representation, 1, 38, 50, 2));
        addSlotToContainer(new SlotHolo(representation, 2, 100, 50, 2));
        addSlotToContainer(new SlotHolo(representation, 3, 118, 50, 2));
    }
}
