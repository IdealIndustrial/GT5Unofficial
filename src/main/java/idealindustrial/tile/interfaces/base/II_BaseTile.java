package idealindustrial.tile.interfaces.base;

import gregtech.api.interfaces.IFastRenderedTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import idealindustrial.tile.IOType;
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

    boolean isActive();//todo move up
    void setActive(boolean active);

    void sendEvent(int id, int value);
    void issueTextureUpdate();


    void onPlaced();

    void receiveNeighbourIOConfigChange(IOType type);
}
