package idealindustrial.teststuff.testTile;

import idealindustrial.impl.tile.gui.base.GenericContainer;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.impl.tile.gui.base.component.II_Slot;
import idealindustrial.impl.tile.gui.base.component.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;

public class TestContainer extends GenericContainer {
    public TestContainer(HostMachineTile tile, EntityPlayer player) {
        super(tile, player, true,true);
    }

    @Override
    public void addSlots() {
        addSlotToContainer(new II_Slot(tile, 0, 20, 20, 0));
        addSlotToContainer(new SlotOutput(tile, 1,50, 50, 0));
    }
}
