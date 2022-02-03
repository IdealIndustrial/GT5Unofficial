package idealindustrial.api.tile.meta;

import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.render.CustomRenderer;
import idealindustrial.impl.tile.IOType;
import idealindustrial.api.tile.BaseCoverBehavior;
import idealindustrial.api.tile.ISyncedTileEntity;
import idealindustrial.api.tile.IToolClickableTile;
import idealindustrial.api.tile.IUpdatableTileEntity;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.impl.tile.energy.electric.EnergyHandler;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.InternalInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public interface Tile<H extends HostTile> extends IUpdatableTileEntity, ISyncedTileEntity, IToolClickableTile {

    ITexture[] provideTexture(boolean active, int side);

    H getHost();

    Tile<H> newMetaTile(H baseTile);

    boolean hasRenderer();

    CustomRenderer getRenderer();

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

    Class<? extends HostTile> getBaseTileClass();

    String getName();

    default boolean hasInventory() {
        return false;
    }

    default InternalInventory getInputsHandler() {
        return null;
    }

    default InternalInventory getOutputsHandler() {
        return null;
    }

    default InternalInventory getSpecialHandler() {
        return null;
    }

    default String getInventoryName() {
        return getName();
    }

    default boolean isAccessAllowed(EntityPlayer aPlayer) {
        return false;
    }

    default int getInventoryStackLimit() {
        return 64;
    }

    default void onInInventoryModified(int inventory) {
    }

    default boolean hasFluidTank() {
        return false;
    }

    default FluidHandler getInputTank() {
        return null;
    }

    default FluidHandler getOutputTank() {
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

    default EnergyHandler getEnergyHandler() {
        return null;
    }


    default void onBufferFull() {

    }

    default void receiveNeighbourIOConfigChange(IOType type) {

    }

    default boolean allowCoverAtSide(BaseCoverBehavior<?> cover, int side) {
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

    default void placedByPlayer(EntityPlayer player) {

    }


    default boolean transferStructureUpdate() {
        return false;
    }

    default void receiveStructureUpdate() {

    }

    default void onActiveUpdated(boolean active) {

    }
}
