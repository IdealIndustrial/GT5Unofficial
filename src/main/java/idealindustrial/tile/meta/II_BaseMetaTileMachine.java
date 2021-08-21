package idealindustrial.tile.meta;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.api.interfaces.ITexture;
import gregtech.api.util.GT_Utility;
import idealindustrial.render.II_CustomRenderer;
import idealindustrial.tile.base.II_BaseMachineTileImpl;
import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.tile.interfaces.meta.II_MetaTile;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_InternalInventory;
import idealindustrial.util.misc.II_DirUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * base class for cuboid machines, has IO for Items, Fluids and Energies(tbi)
 * also has very simple textures
 * you should init IO handlers
 */
public abstract class II_BaseMetaTileMachine<BaseTileType extends II_BaseMachineTile> implements II_MetaTile<BaseTileType> {

    protected boolean hasInventory, hasTank, hasEnergy;
    protected II_InternalInventory inventoryIn, inventoryOut, inventorySpecial;
    protected II_FluidHandler tankIn, tankOut;
    protected II_EnergyHandler energyHandler;
    protected String name;
    protected BaseTileType baseTile;
    /**
     * texture arrays, 0 - down, 1 - top, 2 - side, 3 - down active, 5 - top active...
     */
    protected ITexture[] baseTextures, overlays;

    public II_BaseMetaTileMachine(BaseTileType baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        assert baseTextures != null && overlays != null;
        this.name = name;
        this.baseTile = baseTile;
        this.baseTextures = baseTextures;
        this.overlays = overlays;
    }

    @Override
    public boolean hasInventory() {
        return hasInventory;
    }

    @Override
    public II_InternalInventory getInputsHandler() {
        return inventoryIn;
    }

    @Override
    public II_InternalInventory getOutputsHandler() {
        return inventoryOut;
    }

    @Override
    public II_InternalInventory getSpecialHandler() {
        return inventorySpecial;
    }

    @Override
    public boolean hasFluidTank() {
        return hasTank;
    }

    @Override
    public II_FluidHandler getInputTank() {
        return tankIn;
    }

    @Override
    public II_FluidHandler getOutputTank() {
        return tankOut;
    }

    @Override
    public void onOpenGui() {

    }

    @Override
    public void onCloseGui() {

    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public String getInventoryName() {
        return name;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public ITexture[] provideTexture(boolean active, int side) {
        int index = II_DirUtil.directionToSide(side) + (active ? 0 : 3);
        return Stream.of(baseTextures[index], overlays[index]).filter(Objects::nonNull).toArray(ITexture[]::new);
    }

    @Override
    public BaseTileType getBase() {
        return baseTile;
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {

    }

    @Override
    public void onPreTick(long timer, boolean serverSide) {

    }

    @Override
    public void onTick(long timer, boolean serverSide) {

    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {

    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {

    }

    @Override
    public void readTile(ByteArrayDataInput stream) {

    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        if (baseTile.isServerSide()) {
            getBase().setAllowedToWork(!getBase().isAllowedToWork());
            GT_Utility.sendChatToPlayer(player, "Processing " + (baseTile.isAllowedToWork() ? "Enabled" : "Disabled"));
        }
        return true;
    }

    @Override
    public boolean onWrenchClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer player, ItemStack item, int side) {
        return false;
    }

    @Override
    public boolean onCrowbarClick(EntityPlayer player, ItemStack item, int side) {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public boolean onLeftClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        return false;
    }

    @Override
    public IInventory getFluidIORepresentation() {
        return null;
    }

    @Override
    public boolean hasRenderer() {
        return false;
    }

    @Override
    public II_CustomRenderer getRenderer() {
        return null;
    }

    @Override
    public boolean cacheCoverTexturesSeparately() {
        return false;
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return null;
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return null;
    }

    @Override
    public void onBlockChange() {

    }

    @Override
    public void onPlaced() {

    }

    @Override
    public void onRemoval() {

    }

    @Override
    public NBTTagCompound saveToNBT(NBTTagCompound nbt) {
        return nbt;
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound saveNBTtoDrop(NBTTagCompound nbt) {
        return nbt;
    }

    @Override
    public Class<? extends II_BaseTile> getBaseTileClass() {
        return II_BaseMachineTileImpl.class;
    }

    @Override
    public boolean hasEnergy() {
        return hasEnergy;
    }

    @Override
    public II_EnergyHandler getEnergyHandler() {
        return energyHandler;
    }
}
