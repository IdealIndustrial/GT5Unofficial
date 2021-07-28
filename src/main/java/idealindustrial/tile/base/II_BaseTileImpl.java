package idealindustrial.tile.base;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.net.GT_Packet_ByteStream;
import gregtech.api.net.GT_Packet_ExtendedBlockEvent;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.render.II_CustomRenderer;
import idealindustrial.tile.II_TileEvents;
import idealindustrial.tile.covers.II_CoverBehavior;
import idealindustrial.tile.covers.II_CoverRegistry;
import idealindustrial.tile.meta.II_BaseMetaTile_Facing1Output;
import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.tools.II_ToolRegistry;
import idealindustrial.util.energy.EUConsumer;
import idealindustrial.util.energy.EUProducer;
import idealindustrial.util.energy.II_EmptyEnergyHandler;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.fluid.II_EmptyTank;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.fluid.II_FluidInvReprImpl;
import idealindustrial.util.fluid.II_FluidInventoryRepresentation;
import idealindustrial.util.inventory.II_EmptyInventory;
import idealindustrial.util.inventory.II_InternalInventory;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static idealindustrial.tile.II_TileEvents.BASE_ACTIVE;
import static idealindustrial.tile.base.II_BaseTileConstants.*;

public class II_BaseTileImpl extends BaseTileEntity implements II_BaseTile {

    protected II_MetaTile metaTileEntity;
    protected int metaTileID;
    protected long timer = 0;
    boolean running = false;
    protected boolean inventoryModified = false;
    II_InternalInventory in, out;
    int[] slots;
    int inSize, outSize;
    boolean hasTank = false;
    II_FluidHandler inTank, outTank;
    int color;
    II_EnergyHandler handler = new II_EmptyEnergyHandler();
    //texture cache, active + covers/side/textureLayers
    ITexture[][][] textureCache = new ITexture[3][6][];

    boolean allowedToWork, active;

    int[] coverIDs = new int[6];
    long[] coverValues = new long[6];
    II_CoverBehavior[] covers = new II_CoverBehavior[6];

    @Override
    public boolean isInvalidTileEntity() {
        return metaTileEntity != null;
    }

    @Override
    public II_MetaTile getMetaTile() {
        return metaTileEntity;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        if (metaTileEntity == null) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
        }
        super.writeToNBT(tag);

        in.nbtSave(tag, "in");
        out.nbtSave(tag, "out");

        inTank.nbtSave(tag, "in");
        outTank.nbtSave(tag, "out");

        handler.nbtLoad(tag, "eu");
        tag.setIntArray("covers", coverIDs);
        II_StreamUtil.writeNBTLongArray(tag, coverValues, "coverValues");
        tag.setInteger("mID", metaTileID);
        tag.setTag("meta", metaTileEntity.saveToNBT(new NBTTagCompound()));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        setInitialValuesAsNBT(tag.getInteger("mID"));

        in.nbtLoad(tag, "in");
        out.nbtLoad(tag, "out");

        inTank.nbtLoad(tag, "in");
        outTank.nbtLoad(tag, "out");
        coverIDs = tag.getIntArray("covers");
        for (int i = 0; i < coverIDs.length; i++) {
            if (coverIDs[i] != 0) {
                covers[i] = II_CoverRegistry.behaviorFromID(coverIDs[i]);
            }
        }
        coverValues = II_StreamUtil.readNBTLongArray(tag, 6, "coverValues");
        handler.nbtLoad(tag, "eu");

        NBTTagCompound metaNBT = tag.getCompoundTag("meta");
        metaTileEntity.loadFromNBT(metaNBT == null ? new NBTTagCompound() : metaNBT);

    }

    public void setInitialValuesAsNBT(int metaTileID) {
        setInitialValuesAsNBT(null, metaTileID);
    }

    public void setInitialValuesAsNBT(NBTTagCompound nbt, int metaTileID) {
        this.metaTileID = metaTileID;
        setMetaTileEntity(II_Values.metaTiles[metaTileID].newMetaTile(this));
    }

    public boolean alive() {
        return !isDead;
    }

    protected void setMetaTileEntity(II_MetaTile metaTileEntity) {
        this.metaTileEntity = metaTileEntity;
        if (metaTileEntity.hasInventory()) {
            in = metaTileEntity.getInputsHandler();
            out = metaTileEntity.getOutputsHandler();
            inSize = in.size();
            outSize = out.size();
            slots = IntStream.iterate(0, i -> i + 1).limit(inSize + outSize).toArray();
        } else {
            in = out = II_EmptyInventory.INSTANCE;
            slots = new int[0];
        }

        if (metaTileEntity.hasFluidTank()) {
            hasTank = true;
            inTank = metaTileEntity.getInputTank();
            outTank = metaTileEntity.getOutputTank();
        } else {
            inTank = outTank = II_EmptyTank.INSTANCE;
        }
        handler = new II_EmptyEnergyHandler();//todo this
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            rebakeMap();
        }
    }


    @Override
    public long getTimer() {
        return timer;
    }

    @Override
    public void setLightValue(byte aLightValue) {
        //legacy. Can be ignored todo: remove
    }


    @Override
    public void updateEntity() {
        if (metaTileEntity == null) {
            if (!worldObj.isRemote) {
                worldObj.removeTileEntity(xCoord, yCoord, zCoord);
                worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
                isDead = true;
            }
            return;
        }
        running = true;
        super.updateEntity();
        boolean serverSide = isServerSide();
        if (timer == 0) {
            onFirstTick(timer, serverSide);
        }
        onPreTick(timer, serverSide);
        onTick(timer, serverSide);
        onPostTick(timer, serverSide);
        running = false;
        timer++;
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {
        metaTileEntity.onFirstTick(timer, serverSide);
    }

    @Override
    public void onPreTick(long timer, boolean serverSide) {
        metaTileEntity.onPreTick(timer, serverSide);
        for (int i = 0; i < 6; i++) {
            if (covers[i] != null && covers[i].getTickRate() > 0 && timer % covers[i].getTickRate() == 0) {
               coverValues[i] = covers[i].update(coverValues[i],  i, this);
            }
        }
    }

    @Override
    public void onTick(long timer, boolean serverSide) {
        metaTileEntity.onTick(timer, serverSide);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        metaTileEntity.onPostTick(timer, serverSide);
        inventoryModified = false;
    }

    @Override
    public boolean hasInventoryBeenModified() {
        return inventoryModified;
    }

    @Override
    public boolean isValidSlot(int i) {
        return false;//todo remove
    }

    @Override
    public boolean addStackToSlot(int aIndex, ItemStack aStack) {//todo reomve
        if (GT_Utility.isStackInvalid(aStack)) return true;
        if (aIndex < 0 || aIndex >= getSizeInventory()) return false;
        ItemStack tStack = getStackInSlot(aIndex);
        if (GT_Utility.isStackInvalid(tStack)) {
            setInventorySlotContents(aIndex, aStack);
            return true;
        }
        aStack = GT_OreDictUnificator.get(aStack);
        if (GT_Utility.areStacksEqual(tStack, aStack) && tStack.stackSize + aStack.stackSize <= Math.min(aStack.getMaxStackSize(), getInventoryStackLimit())) {
            tStack.stackSize += aStack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public boolean addStackToSlot(int aIndex, ItemStack aStack, int aAmount) {
        return addStackToSlot(aIndex, GT_Utility.copyAmount(aAmount, aStack));//todo remove
    }


    @Override
    public int getSizeInventory() {
        if (alive()) {
            return inSize + outSize;
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (alive()) {
            assert index >= 0 && index < inSize + outSize;
            if (index >= inSize) {
                return out.get(index - inSize);
            }
            ItemStack is = in.get(index);
            return is;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (alive()) {
            assert index >= 0 && index < inSize + outSize;
            if (index >= inSize) {
                return out.reduce(index - inSize, amount);
            }
            return in.reduce(index, amount);
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventoryModified = true;
        if (alive()) {
            assert index >= 0 && index < inSize + outSize;
            if (index >= inSize) {
                out.set(index - inSize, stack);
                return;
            }
            in.set(index, stack);
        }
    }

    @Override
    public String getInventoryName() {
        if (alive())
            return metaTileEntity.getInventoryName();
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }


    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return alive() && timer > 5 && getTileEntityOffset(0, 0, 0) == this && aPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }


    @Override
    public int getInventoryStackLimit() {
        if (alive())
            return metaTileEntity.getInventoryStackLimit();
        return 64;
    }

    @Override
    public void openInventory() {
        if (alive())
            metaTileEntity.onOpenGui();
    }

    @Override
    public void closeInventory() {
        if (alive())
            metaTileEntity.onCloseGui();
    }

    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return alive();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int aSide) {
        if (alive() && letsItemsOut(aSide) && letsItemsOut(aSide))
            return slots;
        return new int[0];
    }

    /**
     * Can put aStack into Slot at Side
     */
    @Override
    public boolean canInsertItem(int aIndex, ItemStack aStack, int aSide) {
        return alive() && letsItemsIn(aSide) && aIndex < in.size() && in.allowInput(aIndex, aStack);
    }

    /**
     * Can pull aStack out of Slot from Side
     */
    @Override
    public boolean canExtractItem(int aIndex, ItemStack aStack, int aSide) {
        return alive() && letsItemsOut(aSide) && aIndex >= inSize && aIndex < inSize + outSize;
    }

    protected boolean letsItemsIn(int side) {
        return true;
    }

    protected boolean letsItemsOut(int side) {
        return true;
    }

    @Override
    public int fill(ForgeDirection aSide, FluidStack aFluid, boolean doFill) {
        if (allowIn(aSide))
            return inTank.fill(aSide, aFluid, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection aSide, FluidStack aFluid, boolean doDrain) {
        if (allowOut(aSide))
            return outTank.drain(aSide, aFluid, doDrain);
        return null;
    }


    @Override
    public FluidStack drain(ForgeDirection aSide, int maxDrain, boolean doDrain) {
        if (allowOut(aSide))
            return outTank.drain(aSide, maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection aSide, Fluid fluid) {
        if (allowIn(aSide))
            return inTank.canFill(aSide, fluid);
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection aSide, Fluid aFluid) {
        if (allowOut(aSide))
            return outTank.canDrain(aSide, aFluid);
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection aSide) {
        if (hasTank && alive() && (aSide == ForgeDirection.UNKNOWN || (letsFluidIn(aSide.ordinal()) && letsFluidOut(aSide.ordinal()))))
            return outTank.getTankInfo(aSide);
        return new FluidTankInfo[]{};
    }

    private boolean allowIn(ForgeDirection aSide) {
        return hasTank && timer > 5 && alive() && (aSide == ForgeDirection.UNKNOWN || letsFluidIn(aSide.ordinal()));
    }

    private boolean allowOut(ForgeDirection aSide) {
        return hasTank && timer > 5 && alive() && (aSide == ForgeDirection.UNKNOWN || (letsFluidOut(aSide.ordinal())));
    }


    protected boolean letsFluidIn(int side) {
        return true;
    }

    protected boolean letsFluidOut(int side) {
        return true;
    }

    @Override
    public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
        return 0;
    }

    @Override
    public boolean inputsEnergyFrom(byte aSide) {
        return alive() && lestEnergyIn(aSide) && getConsumer(aSide) != null;
    }

    @Override
    public boolean inputsEnergyFrom(byte aSide, boolean waitForActive) {
        return inputsEnergyFrom(aSide);
    }

    @Override
    public boolean outputsEnergyTo(byte aSide) {
        return alive() && lestEnergyOut(aSide) && getProducer(aSide) != null;
    }

    @Override
    public boolean outputsEnergyTo(byte aSide, boolean waitForActive) {
        return outputsEnergyTo(aSide);
    }

    @Override
    public byte getColorization() {
        return (byte) color;
    }

    @Override
    public byte setColorization(byte aColor) {
        return (byte) (color = aColor);
    }

    @Override
    public EUProducer getProducer(int side) {
        return handler.getProducer(side);
    }

    @Override
    public EUConsumer getConsumer(int side) {
        return handler.getConsumer(side);
    }

    protected boolean lestEnergyIn(int side) {
        return false;
    }

    protected boolean lestEnergyOut(int side) {
        return false;
    }

    @Override
    public ITexture[][] getTextures() {
        return textureCache[active ? 1 : 0];
    }

    @Override
    public ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers) {
        int oldFacing = 0;
        if (metaTileEntity instanceof II_BaseMetaTile_Facing1Output) {//shitty but works for now
            oldFacing = ((II_BaseMetaTile_Facing1Output) metaTileEntity).outputFacing;
            ((II_BaseMetaTile_Facing1Output) metaTileEntity).outputFacing = aFacing;
        }
        ITexture[][] textures = new ITexture[6][];
        for (int side = 0; side < 6; side++) {
            textures[side] = provideTexture(aActive, side, true);
        }

        if (metaTileEntity instanceof II_BaseMetaTile_Facing1Output) {
            ((II_BaseMetaTile_Facing1Output) metaTileEntity).outputFacing = oldFacing;
        }
        return textures;//inventory
    }

    @Override
    public ITexture[][] getTextures(boolean covered) {
        if (covered) {
            return textureCache[2];
        }
        else {
            return getTextures();
        }
    }

    @Override
    public void rebakeMap() {
        if (metaTileEntity == null) {
            return;
        }
        if (metaTileEntity.cacheCoverTexturesSeparately()) {
            textureCache = new ITexture[3][6][];
            for (int i = 0; i < 6; i++) {
                textureCache[0][i] = provideTexture(false, i, true);
                textureCache[1][i] = provideTexture(true, i, true);
                textureCache[2][i] = new ITexture[]{coverAtSide(i)};
            }
        }
        else {
            textureCache = new ITexture[2][6][];
            for (int i = 0; i < 6; i++) {
                textureCache[0][i] = provideTexture(false, i, true);
                textureCache[1][i] = provideTexture(true, i, true);
            }
        }
    }

    protected ITexture[] provideTexture(boolean active, int side, boolean covered) {
        if (covered && covers[side] != null) {
            return new ITexture[]{coverAtSide(side)};
        }
        return metaTileEntity.provideTexture(active, side);
    }

    public ITexture coverAtSide(int side) {
        return covers[side] != null ? covers[side].getTexture() : null;
    }

    @Override
    public int getMetaTileID() {
        return metaTileID;
    }

    @Override
    public void setMetaTileID(int metaTileID) {
        this.metaTileID = metaTileID;
        setMetaTileEntity(II_Values.metaTiles[metaTileID]);
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        return null;//todo implement
    }

    public void syncTileEntity() {
        ByteArrayDataOutput stream = ByteStreams.newDataOutput(10);
        writeTile(stream);
        GT_Values.NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_ByteStream(xCoord, yCoord, zCoord, stream), xCoord, zCoord);
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        stream.writeInt(metaTileID);
        stream.writeBoolean(active);
        metaTileEntity.writeTile(stream);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        setInitialValuesAsNBT(stream.readInt());
        active = stream.readBoolean();
        metaTileEntity.readTile(stream);
    }

    @Override
    public Packet getDescriptionPacket() {
        if (metaTileEntity != null) {
            syncTileEntity();
        }
        return null;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (metaTileEntity == null) {
            return false;
        }
        if (covers[side] != null && covers[side].onRightClick(coverValues[side], side, this, player, item, hitX, hitY, hitZ)) {
            return true;
        }
        if(isServerSide() && II_ToolRegistry.applyTool(metaTileEntity, player, item, side, hitX, hitY, hitZ)) {
            return true;
        }
        if (isServerSide() && !player.isSneaking() && getServerGUI(player, 0) != null) {
            openGUI(player, 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLeftClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    public void sendEvent(int id, int value) {
        GT_Values.NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_ExtendedBlockEvent(xCoord, (short) yCoord, zCoord, id, value), xCoord, zCoord);
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        switch (id) {
            case BASE_ACTIVE:
                active = intToBool(value);
                issueTextureUpdate();
                return true;

        }
        return metaTileEntity.receiveClientEvent(id, value);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            if (isServerSide()) {
                sendEvent(EVENT_ACTIVE, boolToInt(active));
            }
        }
    }

    public void issueTextureUpdate() {
        rebakeMap();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return metaTileEntity.getServerGUI(player, internalID);
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return metaTileEntity.getClientGUI(player, internalID);
    }

    @Override
    public boolean openGUI(EntityPlayer aPlayer, int aID) {
        if (aPlayer == null) return false;
        aPlayer.openGui(II_Core.INSTANCE, aID, worldObj, xCoord, yCoord, zCoord);
        return true;
    }

    @Override
    public int[] getInventorySizes() {
        return new int[0];
    }

    @Override
    public II_InternalInventory getIn() {
        return in;
    }

    @Override
    public II_InternalInventory getOut() {
        return out;
    }

    @Override
    public II_InternalInventory getSpecial() {
        return metaTileEntity.getSpecialHandler();
    }

    @Override
    public II_FluidInventoryRepresentation getFluidRepresentation() {
        return new II_FluidInvReprImpl(II_Util.nonNull(inTank.getFluids()), II_Util.nonNull(outTank.getFluids()));
    }

    @Override
    public boolean hasFluidTank() {
        return hasTank;
    }

    @Override
    public II_FluidHandler getInTank() {
        return inTank;
    }

    @Override
    public II_FluidHandler getOutTank() {
        return outTank;
    }

    @Override
    public II_CustomRenderer getCustomRenderer() {
        return metaTileEntity.hasRenderer() ? metaTileEntity.getRenderer() : null;
    }

    @Override
    public void onAdjacentBlockChange(int aX, int aY, int aZ) {
        super.onAdjacentBlockChange(aX, aY, aZ);
        if (metaTileEntity != null) {
            metaTileEntity.onBlockChange();
        }
    }

    @Override
    public void onPlaced() {
        if (metaTileEntity != null) {
            metaTileEntity.onPlaced();
        }
    }
}
