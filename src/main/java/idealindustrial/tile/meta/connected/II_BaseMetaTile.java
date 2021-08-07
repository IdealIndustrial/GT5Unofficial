package idealindustrial.tile.meta.connected;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.api.interfaces.ITexture;
import idealindustrial.render.II_CustomRenderer;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.tile.interfaces.meta.II_MetaTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class II_BaseMetaTile<BaseTileType extends II_BaseTile> implements II_MetaTile<BaseTileType> {

    protected ITexture textureActive, textureInactive;
    protected BaseTileType baseTile;

    public II_BaseMetaTile(BaseTileType baseTile, ITexture textureInactive, ITexture textureActive) {
        this.baseTile = baseTile;
        this.textureInactive = textureInactive;
        this.textureActive = textureActive;
    }

    @Override
    public ITexture[] provideTexture(boolean active, int side) {
        return new ITexture[]{active ? textureActive : textureInactive};
    }

    @Override
    public BaseTileType getBase() {
        return baseTile;
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
    public void writeTile(ByteArrayDataOutput stream) {

    }

    @Override
    public void readTile(ByteArrayDataInput stream) {

    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        return false;
    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        return false;
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
    public Class<? extends II_BaseTile> getBaseTileClass() {
        return II_BaseTileImpl.class;
    }
}
