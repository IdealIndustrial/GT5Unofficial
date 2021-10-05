package idealindustrial.tile.gui.base;

import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerNxN extends GenericContainer {
    public int n, texture;

    public ContainerNxN(BaseMachineTile tile, EntityPlayer player, int n, int slotTexture) {
        super(tile, player, false, false);
        this.texture = slotTexture;
        this.n = n;
        addSlots();
        this.bindInventory = true;
        bindPlayerInventory(player.inventory, 0, 0);
        assert tile.getIn() != null && tile.getIn().size() == n * n || tile.getOut() != null && tile.getOut().size() == n * n;
    }

    @Override
    public void addSlots() {
        int x = 50, y = 20;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                addSlotToContainer(new II_Slot(tile, i * n + j, x + j * 18, y + i * 18, texture));
            }
        }
    }

    @Override
    public int getShiftClickSlotCount() {
        return n * n;
    }

    @Override
    public int getShiftClickStartIndex() {
        return 0;
    }
}
