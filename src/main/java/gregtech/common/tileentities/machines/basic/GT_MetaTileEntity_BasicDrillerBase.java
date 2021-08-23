package gregtech.common.tileentities.machines.basic;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.FakePlayer;
import org.lwjgl.Sys;

import static gregtech.api.enums.GT_Values.V;

public abstract class GT_MetaTileEntity_BasicDrillerBase extends GT_MetaTileEntity_BasicMachine {
    

    protected static final ItemStack MINING_PIPE = GT_ModHandler.getIC2Item("miningPipe", 0);
    protected static final Block MINING_PIPE_BLOCK = GT_Utility.getBlockFromStack(MINING_PIPE);
    protected static final Block MINING_PIPE_TIP_BLOCK = GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 0));

    int drillX, drillY, drillZ;
    boolean isPickingPipes;
    boolean waitMiningPipe = true;

    public GT_MetaTileEntity_BasicDrillerBase(int aID, String aName, String aNameRegional, int aTier, String[] aDescription, int aInSlots, int aOutSlots, String aGUIName, ITexture... aTextures){
        super(aID,aName,aNameRegional,aTier,1,aDescription,aInSlots,aOutSlots,aGUIName,"",aTextures);
    }

    public GT_MetaTileEntity_BasicDrillerBase(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, int aInputSlotCount, int aOutputSlotCount, String aGUIName) {
        super(aName,aTier,1,aDescription,aTextures,aInputSlotCount,aOutputSlotCount,aGUIName,"");
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (aStack.getItem() == MINING_PIPE.getItem());
    }

    public abstract boolean hasFreeSpace();

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if (aBaseMetaTileEntity.isAllowedToWork() && aBaseMetaTileEntity.isUniversalEnergyStored(getEnergy(mTier) * (getSpeed(mTier) - mProgresstime)) && hasFreeSpace()) {
                miningPipe:
                if (waitMiningPipe) {
                    mMaxProgresstime = 0;
                    for (int i = 0; i < mInputSlotCount; i++) {
                        ItemStack s = getInputAt(i);
                        if (s != null && s.getItem() == MINING_PIPE.getItem() && s.stackSize > 0) {
                            waitMiningPipe = false;
                            break miningPipe;
                        }
                    }
                    return;
                }
                aBaseMetaTileEntity.decreaseStoredEnergyUnits(getEnergy(mTier), true);
                mMaxProgresstime = getSpeed(mTier);
            } else {
                mMaxProgresstime = 0;
                return;
            }
            if (mProgresstime == getSpeed(mTier) - 1) {
                if (isPickingPipes) {
                    if (drillY == 0) {
                        aBaseMetaTileEntity.disableWorking();
                        isPickingPipes = false;
                    } else if (aBaseMetaTileEntity.getBlockOffset(0, drillY, 0) == MINING_PIPE_TIP_BLOCK || aBaseMetaTileEntity.getBlockOffset(0, drillY, 0) == MINING_PIPE_BLOCK) {
                        mOutputItems[0] = MINING_PIPE.copy();
                        mOutputItems[0].stackSize = 1;
                        aBaseMetaTileEntity.getWorld().setBlockToAir(aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord() + drillY, aBaseMetaTileEntity.getZCoord());
                        drillY++;
                    }
                    return;
                }
                if (drillY == 0) {
                    moveOneDown(aBaseMetaTileEntity);
                    return;
                }
                if (drillZ > getRadius(mTier)) {
                    moveOneDown(aBaseMetaTileEntity);
                    return;
                }
                while (drillZ <= getRadius(mTier)) {
                    while (drillX <= getRadius(mTier)) {
                        if(workBlock(aBaseMetaTileEntity))
                          return;
                        drillX++;
                    }
                    drillX = -getRadius(mTier);
                    drillZ++;
                }
            }
        }
    }
    
    public abstract int getRadius(int aTier);
    public abstract int getSpeed(int aTier);
    public abstract int getEnergy(int aTier);

    @Override
    public long maxEUStore() {
        return mTier == 1 ? 4096 : V[mTier] * 64;
    }


    public abstract boolean workBlock(IGregTechTileEntity aBaseMetaTileEntity);

    public abstract boolean moveOneDown(IGregTechTileEntity aBaseMetaTileEntity);

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("isPickingPipe", isPickingPipes);
        aNBT.setInteger("drillX", drillX);
        aNBT.setInteger("drillY", drillY);
        aNBT.setInteger("drillZ", drillZ);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        isPickingPipes = aNBT.getBoolean("isPickingPipe");
        drillX = aNBT.getInteger("drillX");
        drillY = aNBT.getInteger("drillY");
        drillZ = aNBT.getInteger("drillZ");
    }

    private FakePlayer mFakePlayer = null;

    protected FakePlayer getFakePlayer(IGregTechTileEntity aBaseTile) {
        if (mFakePlayer == null) mFakePlayer = GT_Utility.getFakePlayer(aBaseTile);
        mFakePlayer.setWorld(aBaseTile.getWorld());
        mFakePlayer.setPosition(aBaseTile.getXCoord(), aBaseTile.getYCoord(), aBaseTile.getZCoord());
        return mFakePlayer;
    }

}
