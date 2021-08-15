package idealindustrial.tile.interfaces.meta;

import gregtech.api.interfaces.ITexture;
import idealindustrial.render.II_CustomRenderer;
import idealindustrial.tile.IOType;
import idealindustrial.tile.covers.II_BaseCoverBehavior;
import idealindustrial.tile.interfaces.ISyncedTileEntity;
import idealindustrial.tile.interfaces.IToolClickableTile;
import idealindustrial.tile.interfaces.IUpdatableTileEntity;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_InternalInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public interface II_MetaTile<BaseTileType extends II_BaseTile> extends IUpdatableTileEntity, ISyncedTileEntity, IToolClickableTile {

    ITexture[] provideTexture(boolean active, int side);

    BaseTileType getBase();

    II_MetaTile<BaseTileType> newMetaTile(BaseTileType baseTile);


    boolean hasRenderer();

    II_CustomRenderer getRenderer();

    boolean cacheCoverTexturesSeparately();

    default void onBlockChange() {
    }

    default void onPlaced() {
    }

    default void onRemoval() {
    }

    NBTTagCompound saveToNBT(NBTTagCompound nbt);

    void loadFromNBT(NBTTagCompound nbt);

    NBTTagCompound saveNBTtoDrop(NBTTagCompound nbt);

    Class<? extends II_BaseTile> getBaseTileClass();


    default boolean hasInventory() {
        return false;
    }

    default II_InternalInventory getInputsHandler() {
        return null;
    }

    default II_InternalInventory getOutputsHandler() {
        return null;
    }

    default II_InternalInventory getSpecialHandler() {
        return null;
    }

    default String getInventoryName() {
        return "";
    }

    default boolean isAccessAllowed(EntityPlayer aPlayer) {
        return false;
    }

    default int getInventoryStackLimit() {
        return 64;
    }

    default boolean hasFluidTank() {
        return false;
    }

    default II_FluidHandler getInputTank() {
        return null;
    }

    default II_FluidHandler getOutputTank() {
        return null;
    }

    default void onOpenGui() {
    }

    default void onCloseGui() {
    }

    default GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return null;
    }

    default Container getServerGUI(EntityPlayer player, int internalID) {
        return null;
    }


    default IInventory getFluidIORepresentation() {
        return null;
    }

    default boolean hasEnergy() {
        return false;
    }

    default II_EnergyHandler getEnergyHandler() {
        return null;
    }

    default void receiveNeighbourIOConfigChange(IOType type) {

    }

    default boolean allowCoverAtSide(II_BaseCoverBehavior<?> cover, int side) {
        return true;
    }

    default boolean getIOatSide(int side, IOType type, boolean input) {
        return true;
    }


    default void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB, List<AxisAlignedBB> outputAABB, Entity collider) {
        AxisAlignedBB axisalignedbb1 = getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
        if (axisalignedbb1 != null && inputAABB.intersectsWith(axisalignedbb1)) outputAABB.add(axisalignedbb1);
    }

    default AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        return AxisAlignedBB.getBoundingBox(aX, aY, aZ, aX + 1, aY + 1, aZ + 1);
    }


    default void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity collider) {

    }
}
