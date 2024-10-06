package gregtech.common.tileentities.machines.steam;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public abstract class GT_MetaTileEntity_MachineNoAutomation extends GT_MetaTileEntity_BasicTank {
    public int mProgressTime, mMaxProgressTime;

    public GT_MetaTileEntity_MachineNoAutomation(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String[] aDescription, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public GT_MetaTileEntity_MachineNoAutomation(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }


    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        int texture = aSide == 0 ? 0 : aSide == 1 ? 1 : 2;
        if (aActive) {
            texture += 4;
        }
        return mTextures[texture][aColorIndex + 1];
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (mMaxProgressTime > 0) {
            if (++mProgressTime >= mMaxProgressTime) {
                mMaxProgressTime = 0;
                mProgressTime = 0;
                finishRecipe();
                aBaseMetaTileEntity.setActive(false);
            }
        }
        else if (aBaseMetaTileEntity.isAllowedToWork() && (aBaseMetaTileEntity.hasInventoryBeenModified() || aBaseMetaTileEntity.hasWorkJustBeenEnabled() || aTick % 50 == 0)){
            checkRecipe();
            if (mMaxProgressTime > 0) {
                aBaseMetaTileEntity.setActive(true);
            }
        }
    }

    protected abstract boolean checkRecipe();

    protected abstract boolean finishRecipe();


    /**
     * texture indexes
     * 0 - down inactive
     * 1 - up inactive
     * 2 - side inactive
     * 3 - front inactive

     * 4 - down active
     * 5 - up active
     * 6 - side active
     * 7 - front active

     */
    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[8][17][];
        for (int color = 0; color < 17; color++) {
            ITexture[] baseTextures = getBaseTextures(color);
            for (int textureIndex = 0; textureIndex < 8; textureIndex++) {
                rTextures[textureIndex][color] = new ITexture[]{baseTextures[textureIndex], aTextures[textureIndex]};
            }
        }
        return rTextures;
    }

    @Override
    public boolean onWrenchRightClick(byte aSide, byte aWrenchingSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }


    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("mProgress", mProgressTime);
        aNBT.setInteger("mMaxProgress", mMaxProgressTime);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        mProgressTime = aNBT.getInteger("mProgress");
        mMaxProgressTime = aNBT.getInteger("mMaxProgress");
        super.loadNBTData(aNBT);
    }


    @Override
    public boolean doesFillContainers() {
        return false;
    }

    @Override
    public boolean doesEmptyContainers() {
        return false;
    }

    @Override
    public boolean canTankBeFilled() {
        return false;
    }

    @Override
    public boolean canTankBeEmptied() {
        return false;
    }

    @Override
    public boolean displaysItemStack() {
        return false;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }

    @Override
    public FluidTankInfo getInfo() {
        return super.getInfo();
    }

    protected abstract ITexture[] getBaseTextures(int color);

}
