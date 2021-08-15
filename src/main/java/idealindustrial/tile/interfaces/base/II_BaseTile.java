package idealindustrial.tile.interfaces.base;

import gregtech.api.interfaces.IFastRenderedTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import idealindustrial.tile.IOType;
import idealindustrial.tile.covers.II_BaseCoverBehavior;
import idealindustrial.tile.interfaces.IClickableTileEntity;
import idealindustrial.tile.interfaces.ISyncedTileEntity;
import idealindustrial.tile.interfaces.meta.II_MetaTile;
import idealindustrial.tile.interfaces.IUpdatableTileEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface II_BaseTile extends IUpdatableTileEntity, IHasWorldObjectAndCoords, IFastRenderedTileEntity, ISyncedTileEntity, IClickableTileEntity {
    int getMetaTileID();
    void setMetaTileID(int id);
    II_MetaTile<?> getMetaTile();

    ArrayList<ItemStack> getDrops();

    void sendEvent(int id, int value);
    void issueTextureUpdate();

    void onPlaced();

    void receiveNeighbourIOConfigChange(IOType type);

    boolean allowedToWork();
    void allowWork(boolean allow);
    boolean isActive();
    void setActive(boolean active);

    int getCoverIDAtSide(int side);
    II_BaseCoverBehavior<?> getCoverAtSide(int side);
    long getCoverVarAtSide(int side);

    void dropCoverAtSide(int side);
    void setCoverVarAtSide(int side, long value);
}
