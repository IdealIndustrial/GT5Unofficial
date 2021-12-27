package idealindustrial.impl.tile.gui.base.component;

import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.net.GT_Packet_ExtendedBlockEvent;
import idealindustrial.II_Core;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.api.tile.host.NetworkedInventory;
import idealindustrial.impl.net.Network;
import idealindustrial.util.misc.II_Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class SlotHoloEvent extends SlotHolo {
    int event;//todo replace with callback
    public SlotHoloEvent(IInventory inventory, int id, int x, int y, int texture, int event) {
        super(inventory, id, x, y, texture);
        this.event = event;
    }


    public void slotClicked(NetworkedInventory tile, EntityPlayer player, int mouse, int hotkeys) {
        if (tile instanceof HostTile) {
           HostTile host = (HostTile) tile;
           host.receiveClientEvent(event, II_Util.shortsToInt(mouse, hotkeys));
        }
    }
}
