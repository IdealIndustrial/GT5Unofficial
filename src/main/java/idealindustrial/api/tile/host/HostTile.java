package idealindustrial.api.tile.host;

import idealindustrial.api.tile.render.IFastRenderedTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import idealindustrial.impl.tile.IOType;
import idealindustrial.api.tile.BaseCoverBehavior;
import idealindustrial.impl.tile.host.WorldAction;
import idealindustrial.api.tile.IClickableTileEntity;
import idealindustrial.api.tile.ISyncedTileEntity;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.api.tile.IUpdatableTileEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface HostTile extends IUpdatableTileEntity, IHasWorldObjectAndCoords, IFastRenderedTileEntity, ISyncedTileEntity, IClickableTileEntity {
    int getMetaTileID();
    void setMetaTileID(int id);
    Tile<?> getMetaTile();

    ArrayList<ItemStack> getDrops();

    void syncTileEntity();

    void sendEvent(int id, int value);
    void sendEventToServer(int id, int value);
    void issueTextureUpdate();

    void onPlaced();
    void onWorldStateUpdated(Consumer<WorldAction> listener);

    void receiveNeighbourIOConfigChange(IOType type);

    boolean isAllowedToWork();
    void setAllowedToWork(boolean allow);
    boolean isActive();
    void setActive(boolean active);

    int getCoverIDAtSide(int side);
    BaseCoverBehavior<?> getCoverAtSide(int side);
    long getCoverVarAtSide(int side);

    void dropCoverAtSide(int side);
    void setCoverVarAtSide(int side, long value);
}
