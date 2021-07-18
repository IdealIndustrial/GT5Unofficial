package gregtech.api.metatileentity.reimplement.basetile;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseTileEntity9_MachineProgress extends BaseTileEntity8_Covers implements IMachineProgress, IClickableTileEntity {

    protected boolean enabled = false, justEnabled = false, active = false;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        enabled = tag.getBoolean("enabled");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("enabled", enabled);

    }

    @Override
    public int getProgress() {
        if (alive()) {
            return metaTileEntity.getProgresstime();
        }
        return 0;
    }

    @Override
    public int getMaxProgress() {
        if (alive()) {
            return metaTileEntity.maxProgresstime();
        }
        return 0;
    }

    @Override
    public boolean increaseProgress(int aProgressAmountInTicks) {
        return false;//do nothing
    }

    @Override
    public boolean hasThingsToDo() {
        return getProgress() > 0;
    }

    @Override
    public boolean hasWorkJustBeenEnabled() {
        return alive() && justEnabled;
    }

    @Override
    public void enableWorking() {
        if (isDead()) {
            return;
        }
        justEnabled = true;
        enabled = true;
    }

    @Override
    public void disableWorking() {
        if (isDead()) {
            return;
        }
        enabled = false;
    }

    @Override
    public boolean isAllowedToWork() {
        return alive() && enabled;
    }

    @Override
    public byte getWorkDataValue() {
        return 0;//unused -> todo:delete
    }

    @Override
    public void setWorkDataValue(byte aValue) {
        //unused -> todo:delete
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void onLeftclick(EntityPlayer aPlayer) {
        try {
            if (aPlayer != null && alive()) metaTileEntity.onLeftclick((IGregTechTileEntity) this, aPlayer);
        } catch (Throwable e) {
            GT_Log.err.println("Encountered Exception while leftclicking TileEntity, the Game should've crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
            e.printStackTrace(GT_Log.err);
        }
    }

    @Override
    public boolean onRightclick(EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
        if (isClientSide()) {
            if (getCoverBehaviorAtSide(aSide).onCoverRightclickClient(aSide, this, aPlayer, aX, aY, aZ))
                return true;
            if (!getCoverBehaviorAtSide(aSide).isGUIClickable(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this))
                return false;
        }
        if (isServerSide()) {//todo rewrite this when we'll get better metaTools
            ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
            if (tCurrentItem != null) {
                
                if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sWrenchList)) {
                    if (aPlayer.isSneaking() && metaTileEntity instanceof GT_MetaTileEntity_BasicMachine && ((GT_MetaTileEntity_BasicMachine) metaTileEntity).setMainFacing(GT_Utility.determineWrenchingSide(aSide, aX, aY, aZ))) {
                        GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, aPlayer);
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(100), 1.0F, -1, xCoord, yCoord, zCoord);
                    } else if (metaTileEntity.onWrenchRightClick(aSide, GT_Utility.determineWrenchingSide(aSide, aX, aY, aZ), aPlayer, aX, aY, aZ)) {
                        GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, aPlayer);
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(100), 1.0F, -1, xCoord, yCoord, zCoord);
                    }
                    return true;
                }

                if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sScrewdriverList)) {
                    if (GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 200, aPlayer)) {
                        setCoverDataAtSide(aSide, getCoverBehaviorAtSide(aSide).onCoverScrewdriverclick(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this, aPlayer, aX, aY, aZ));
                        metaTileEntity.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(100), 1.0F, -1, xCoord, yCoord, zCoord);
                    }
                    return true;
                }
/*
                if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sHardHammerList)) {
                    if (GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, aPlayer)) {
                        mInputDisabled = !mInputDisabled;
                        if (mInputDisabled) mOutputDisabled = !mOutputDisabled;
                        GT_Utility.sendChatToPlayer(aPlayer, trans("086", "Auto-Input: ") + (mInputDisabled ? trans("087", "Disabled") : trans("088", "Enabled") + trans("089", "  Auto-Output: ") + (mOutputDisabled ? trans("087", "Disabled") : trans("088", "Enabled"))));
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(1), 1.0F, -1, xCoord, yCoord, zCoord);
                    }
                    return true;
                }*/

                if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sSoftHammerList)) {
                    if (GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, aPlayer)) {
                        if (enabled) disableWorking();
                        else enableWorking();
                        {
                            String tChat = trans("090", "Machine Processing: ") + (isAllowedToWork() ? trans("088", "Enabled") : trans("087", "Disabled"));
                            if (metaTileEntity != null && metaTileEntity.hasAlternativeModeText())
                                tChat = metaTileEntity.getAlternativeModeText();
                            GT_Utility.sendChatToPlayer(aPlayer, tChat);
                        }
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(101), 1.0F, -1, xCoord, yCoord, zCoord);
                    }
                    return true;
                }
/*
                if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sSolderingToolList)) {
                    byte tSide = GT_Utility.determineWrenchingSide(aSide, aX, aY, aZ);
                    if (metaTileEntity.onSolderingToolRightClick(aSide, tSide, aPlayer, aX, aY, aZ)) {
                        //logic handled internally
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(103), 1.0F, -1, xCoord, yCoord, zCoord);
                    } else if (GT_ModHandler.useSolderingIron(tCurrentItem, aPlayer)) {
                        mStrongRedstone ^= (1 << tSide);
                        GT_Utility.sendChatToPlayer(aPlayer, trans("091", "Redstone Output at Side ") + tSide + trans("092", " set to: ") + ((mStrongRedstone & (1 << tSide)) != 0 ? trans("093", "Strong") : trans("094", "Weak")));
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(103), 3.0F, -1, xCoord, yCoord, zCoord);
                        issueBlockUpdate();
                    }
                    return true;
                }*/

                if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sWireCutterList)) {
                    byte tSide = GT_Utility.determineWrenchingSide(aSide, aX, aY, aZ);
                    if (metaTileEntity.onWireCutterRightClick(aSide, tSide, aPlayer, aX, aY, aZ)) {
                        //logic handled internally
                        GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(100), 1.0F, -1, xCoord, yCoord, zCoord);
                    }
                    return true;
                }

                if (getCoverIDAtSide(aSide) == 0) {
                    if (GregTech_API.sCovers.containsKey(new GT_ItemStack(tCurrentItem))) {
                        if (GregTech_API.getCoverBehavior(tCurrentItem).isCoverPlaceable(aSide, new GT_ItemStack(tCurrentItem), this) && metaTileEntity.allowCoverOnSide(aSide, new GT_ItemStack(tCurrentItem))) {
                            setCoverItemAtSide(aSide, tCurrentItem);
                            if (!aPlayer.capabilities.isCreativeMode) tCurrentItem.stackSize--;
                            GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(100), 1.0F, -1, xCoord, yCoord, zCoord);
                        }
                        return true;
                    }
                } else {
                    if (GT_Utility.isStackInList(tCurrentItem, GregTech_API.sCrowbarList)) {
                        if (GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, aPlayer)) {
                            GT_Utility.sendSoundToPlayers(worldObj, GregTech_API.sSoundList.get(0), 1.0F, -1, xCoord, yCoord, zCoord);
                            dropCover(aSide, aSide, false);
                        }
                        return true;
                    }
                }
            }

            if (getCoverBehaviorAtSide(aSide).onCoverRightclick(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this, aPlayer, aX, aY, aZ))
                return true;
            if (onItemRightClick(aPlayer, tCurrentItem)) {
                return true;
            }

            if (!getCoverBehaviorAtSide(aSide).isGUIClickable(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this))
                return false;
            
        }

        try {
            if (alive()) return metaTileEntity.onRightclick((IGregTechTileEntity) this, aPlayer, aSide, aX, aY, aZ);
        } catch (Throwable e) {
            GT_Log.err.println("Encountered Exception while rightclicking TileEntity, the Game should've crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
            e.printStackTrace(GT_Log.err);
        }

        return true;
    }
    
    protected boolean tryToolClick(EntityPlayer player, ItemStack stack, float x, float y, float z) {
        return true;
    }
    
    protected boolean onItemRightClick(EntityPlayer player, ItemStack item) {
        if (getColorization() >= 0 && GT_Utility.areStacksEqual(new ItemStack(Items.water_bucket, 1), item)) {
            item.func_150996_a(Items.bucket);
            setColorization((byte) (getColorization() >= 16 ? -2 : -1));
            return true;
        }
        return false;
    }
}
