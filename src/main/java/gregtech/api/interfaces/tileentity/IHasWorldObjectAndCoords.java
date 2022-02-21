package gregtech.api.interfaces.tileentity;

import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.item.stack.HashedBlockContainer;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * This is a bunch of Functions my TileEntities provide, to make life much easier, and to get rid of internal TileEntity stuff.
 * <p/>
 * This also makes access to adjacent TileEntities more Efficient.
 * <p/>
 * Note: It doesn't have to be a TileEntity in certain cases! And only certain cases, such as the Recipe checking of the findRecipe Function.
 */
public interface IHasWorldObjectAndCoords {
    World getWorld();

    int getXCoord();

    short getYCoord();

    int getZCoord();

    boolean isServerSide();

    boolean isClientSide();

    int getRandomNumber(int aRange);

    TileEntity getTileEntity(int aX, int aY, int aZ);

    TileEntity getTileEntityOffset(int aX, int aY, int aZ);

    TileEntity getTileEntityAtSide(int aSide);

    TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance);

    IInventory getIInventory(int aX, int aY, int aZ);

    IInventory getIInventoryOffset(int aX, int aY, int aZ);

    IInventory getIInventoryAtSide(byte aSide);

    IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance);

    IFluidHandler getITankContainer(int aX, int aY, int aZ);

    IFluidHandler getITankContainerOffset(int aX, int aY, int aZ);

    IFluidHandler getITankContainerAtSide(byte aSide);

    IFluidHandler getITankContainerAtSideAndDistance(byte aSide, int aDistance);

    Block getBlock(int x, int y, int z);

    default HashedBlock getHBlock(int x, int y, int z) {
        return new HashedBlock(getBlock(x, y, z), getMetaID(x, y, z));
    }

    default HashedBlock getHBlock(Vector3 pos) {
        return getHBlock(pos.x, pos.y, pos.z);
    }

    Block getBlockOffset(int aX, int aY, int aZ);

    default Block getBlockAtSide(byte aSide) {
        return getBlockAtSideAndDistance(aSide, 1);
    }

    Block getBlockAtSideAndDistance(byte aSide, int aDistance);

    default HashedBlock getHBlockOffset(int x, int y, int z) {
        Block block = getBlockOffset(x, y, z);
        int meta = getMetaIDOffset(x, y, z);
        return new HashedBlock(block, meta);
    }

    default boolean setBlock(int x, int y, int z, Block block, int meta, int flags) {
        return getWorld().setBlock(x, y, z, block, meta, flags);//todo move impl to BaseTileEntity
    }

    default boolean setBlock(Vector3 pos, Block block, int meta) {
        return setBlock(pos.x, pos.y, pos.z, block, meta, 3);
    }

    default boolean setBlock(Vector3 pos, Block block) {
        return setBlock(pos, block, 0);
    }

    default boolean setBlock(Vector3 pos, HashedBlock block) {
        return setBlock(pos, block.getBlock(), block.getMeta());
    }

    default boolean setBlock(Vector3 pos, HashedBlockContainer container) {
        return setBlock(pos, container.get());
    }

    byte getMetaID(int aX, int aY, int aZ);

    byte getMetaIDOffset(int aX, int aY, int aZ);

    byte getMetaIDAtSide(byte aSide);

    byte getMetaIDAtSideAndDistance(byte aSide, int aDistance);

    byte getLightLevel(int aX, int aY, int aZ);

    byte getLightLevelOffset(int aX, int aY, int aZ);

    byte getLightLevelAtSide(byte aSide);

    byte getLightLevelAtSideAndDistance(byte aSide, int aDistance);

    boolean getOpacity(int aX, int aY, int aZ);

    boolean getOpacityOffset(int aX, int aY, int aZ);

    boolean getOpacityAtSide(byte aSide);

    boolean getOpacityAtSideAndDistance(byte aSide, int aDistance);

    boolean getSky(int aX, int aY, int aZ);

    boolean getSkyOffset(int aX, int aY, int aZ);

    boolean getSkyAtSide(byte aSide);

    boolean getSkyAtSideAndDistance(byte aSide, int aDistance);

    boolean getAir(int aX, int aY, int aZ);

    boolean getAirOffset(int aX, int aY, int aZ);

    boolean getAirAtSide(byte aSide);

    boolean getAirAtSideAndDistance(byte aSide, int aDistance);

    BiomeGenBase getBiome();

    BiomeGenBase getBiome(int aX, int aZ);

    int getOffsetX(int aSide, int aMultiplier);

    short getOffsetY(int aSide, int aMultiplier);

    int getOffsetZ(int aSide, int aMultiplier);

    /**
     * Checks if the TileEntity is Invalid or Unloaded. Stupid Minecraft cannot do that btw.
     */
    boolean isDead();

    /**
     * Sends a Block Event to the Client TileEntity, the byte Parameters are only for validation as Minecraft doesn't properly write Packet Data.
     */
    void sendBlockEvent(byte aID, byte aValue);

    /**
     * @return the Time this TileEntity has been loaded.
     */
    long getTimer();

    /**
     * Sets the Light Level of this Block on a Scale of 0 - 15
     * It could be that it doesn't work. This is just for convenience.
     */
    void setLightValue(byte aLightValue);

    /**
     * Function of the regular TileEntity
     */
    void writeToNBT(NBTTagCompound aNBT);

    /**
     * Function of the regular TileEntity
     */
    void readFromNBT(NBTTagCompound aNBT);

    /**
     * Function of the regular TileEntity
     */
    boolean isInvalidTileEntity();

    /**
     * Opens the GUI with this ID of this MetaTileEntity
     */
    boolean openGUI(EntityPlayer aPlayer, int aID);

    /**
     * Opens the GUI with the ID = 0 of this TileEntity
     */
    boolean openGUI(EntityPlayer aPlayer);

    void markDirty();
}