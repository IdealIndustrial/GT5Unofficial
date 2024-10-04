package gregtech.common.tileentities.machines.basic;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_PrimitiveMiner;
import gregtech.api.gui.GT_GUIContainer_PrimitiveMiner;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class GT_MetaTileEntity_PrimitiveMuscleMachine extends GT_MetaTileEntity_BasicMachine {

    public GT_MetaTileEntity_PrimitiveMuscleMachine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 1, 1, "", 4, 24, null, "");
    }

    public GT_MetaTileEntity_PrimitiveMuscleMachine(String aName) {
        super(aName, 4, 24);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PrimitiveMuscleMachine(this.mName);
    }

    /* private int powerPerClick = 8;
    private int hungryDurationPerOperation = 480;
    private int damagePerOperation = 25;
    private int progresstimePerOre = 480;*/
    public int getPowerPerClick() {
        return 8;
    }

    public int getHungryDurationPerOperation() {
        return 480;
    }

    public int getDamagePerOperation() {
        return 25;
    }

    public int getProgresstimePerOre() {
        return 480;
    }

    public int getDecreaseSteamPerOperation() {
        return 1;
    }

    public int getDecreaseFoodPerOperation() {
        return 1;
    }

    public boolean isDrillRequiredToWork() {
        return true;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    public boolean isUnstableBlock(Block aBlock) {
        return !aBlock.isOpaqueCube(); // true - if we cannot place here ladder or torch
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        //what a bullshit is going on here???
        if (aBaseMetaTileEntity.isServerSide()) {
            if (aBaseMetaTileEntity.hasInventoryBeenModified() && getProgresstime() < 0) {
                resetProgress();
                return;
            }
            if (getProgresstime() < maxProgresstime()) {
                increaseProgress(1);
            } else {
                if (getProgresstime() > 0 && aBaseMetaTileEntity.getMaxProgress() > 0) {
                    endProcess(aBaseMetaTileEntity);
                }
                resetProgress();
                if (!isReadyToDig()) {
//                    increaseProgress(-100);
                    resetProgress();

                } else {
                    BaseMetaTileEntity te = (BaseMetaTileEntity) getBaseMetaTileEntity();
                    if (findAndAddDmgToDrill(false) && hasEnoughEnergyToCheckRecipe()
                            && te.decreaseStoredSteam(getDecreaseSteamPerOperation(), false)) {
                        if (findAndAddDmgToDrill(true)) {
                            setMaxProgresstime(calcProgressTimeOnEnergyAmount(te));
                        }
                    }
                }
            }
            aBaseMetaTileEntity.setActive(getProgresstime() > 0);
        }
    }

    public boolean findAndAddDmgToDrill(boolean addDamage) {
        if (!isDrillRequiredToWork()) {
            return true;
        }
        int drillIdx = -1;
        for (int i = 0; i < 5; i++) {
            ItemStack its = mInventory[i];
            if (its != null) {
                if (drillIdx == -1 && its.getItemDamage() == 180) {
                    drillIdx = i;
                }
            }
        }
        if (addDamage) {
            if (GT_MetaGenerated_Tool.addDmgAndCheckIsDestroy(mInventory[drillIdx], getDamagePerOperation())) {
                mInventory[drillIdx] = null;
            }
        }
        return drillIdx != -1;
    }

    private int calcProgressTimeOnEnergyAmount(BaseMetaTileEntity te) {
        return getProgresstimePerOre() - Math.round((te.getStoredSteam() * getProgresstimePerOre() / 2f) / maxSteamStore());
    }

    public boolean isReadyToDig() {
        return false;
    }

    @Override
    protected boolean hasEnoughEnergyToCheckRecipe() {
        return getBaseMetaTileEntity().getStoredSteam() > 0;
    }

    protected void endProcess(IGregTechTileEntity aBaseMetaTileEntity) {
        //
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        BaseMetaTileEntity te = (BaseMetaTileEntity) getBaseMetaTileEntity();
        aNBT.setInteger("steamEnergy", (int) te.getStoredSteam());
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        BaseMetaTileEntity te = (BaseMetaTileEntity) getBaseMetaTileEntity();
        te.setStoredSteam(aNBT.getInteger("steamEnergy"));
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_PrimitiveMiner(aPlayerInventory, aBaseMetaTileEntity, getPowerPerClick(), getHungryDurationPerOperation(), getDecreaseFoodPerOperation());
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_PrimitiveMiner(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "PrimitiveMiner.png", getPowerPerClick(), getHungryDurationPerOperation(), getDecreaseFoodPerOperation());
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public byte getTileEntityBaseType() {
        return 0;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Muscle Machine"};
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10]};
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    protected void pushToOutputSlots(ArrayList<ItemStack> drops) {
        GT_Utility.pushToOutputSlots(mInventory, drops, 5);
    }

    public void doWorkSound(IGregTechTileEntity aBaseMetaTileEntity) {
        doWorkSound(aBaseMetaTileEntity, true);
    }

    public void doWorkSound(IGregTechTileEntity aBaseMetaTileEntity, boolean isSuccess) {
        GT_Utility.sendSoundToPlayers(aBaseMetaTileEntity.getWorld(),
                (String) GregTech_API.sSoundList.get(isSuccess ? 101 : 6), isSuccess ? 1.0f : 0.8f, -1.0F,
                aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
    }

    protected boolean decreaseInventoryItem(int idx) {
        return decreaseInventoryItem(idx, 1);
    }

    protected boolean decreaseInventoryItem(int idx, int count) {
        boolean success = false;
        if (idx > -1 && mInventory[idx] != null) {
            success = true;
            if (mInventory[idx].stackSize > count) {
                mInventory[idx].stackSize -= count;
            } else {
                mInventory[idx] = null;
            }
        }
        return success;
    }

    @Override
    public long maxEUStore() {
        return 0;
    }

    @Override
    public long maxSteamStore() {
        return 64;
    }

    @Override
    public boolean isElectric() {
        return false;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return aIndex > 0 && aIndex != OTHER_SLOT_COUNT + mInputSlotCount + mOutputItems.length;
    }
}
