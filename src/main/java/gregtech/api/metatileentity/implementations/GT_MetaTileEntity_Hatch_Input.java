package gregtech.api.metatileentity.implementations;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_Hatch_Input extends GT_MetaTileEntity_Hatch {
    public GT_Recipe_Map mRecipeMap = null;
    private boolean isCreative = false;

    public GT_MetaTileEntity_Hatch_Input(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 3, new String[]{"Fluid Input for Multiblocks",  "Capacity: "+ countCapacity(aTier) + "L"});
    }

    public GT_MetaTileEntity_Hatch_Input(int aID, String aName, String aNameRegional, int aTier, boolean isHatchCreative) {
        super(aID, aName, aNameRegional, aTier, 3, new String[]{"Fluid Input for Multiblocks",  "Capacity: "+ countCapacity(aTier) + "L"});
        isCreative = isHatchCreative;
    }

    public GT_MetaTileEntity_Hatch_Input(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 3, aDescription, aTextures);
    }

    public GT_MetaTileEntity_Hatch_Input(String aName, int aTier, String aDescription, ITexture[][][] aTextures, boolean isHatchCreative) {
        super(aName, aTier, 3, aDescription, aTextures);
        isCreative = isHatchCreative;
    }

    public GT_MetaTileEntity_Hatch_Input(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 3, aDescription, aTextures);
    }

    public GT_MetaTileEntity_Hatch_Input(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, boolean isHatchCreative) {
        super(aName, aTier, 3, aDescription, aTextures);
        isCreative = isHatchCreative;
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_IN)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_IN)};
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if(isCreative) {
            FluidStack fs = getFluid();
            if(fs != null && fs.amount > 0 && fs.amount < 512000) {
                fs.amount = 512000;
                setFillableStack(fs);
            }
        }
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Hatch_Input(mName, mTier, mDescriptionArray, mTextures, isCreative);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public boolean doesFillContainers() {
        return true;
    }

    @Override
    public boolean doesEmptyContainers() {
        return true;
    }

    @Override
    public boolean canTankBeFilled() {
        return true;
    }

    @Override
    public boolean canTankBeEmptied() {
        return true;
    }

    @Override
    public boolean displaysItemStack() {
        return true;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }

    public void updateSlots() {
        if (mInventory[getInputSlot()] != null && mInventory[getInputSlot()].stackSize <= 0)
            mInventory[getInputSlot()] = null;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        ItemStack iss = GT_ModHandler.getIC2Item("cell",1);
        ItemStack is = GT_Utility.fillFluidContainer(aFluid, iss,false,true);
        return mRecipeMap == null || mRecipeMap.containsInput(aFluid) || mRecipeMap.containsInput(is);
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aSide == aBaseMetaTileEntity.getFrontFacing() && aIndex == 1;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return aSide == aBaseMetaTileEntity.getFrontFacing() && aIndex == 0 && (mRecipeMap == null || mRecipeMap.containsInput(aStack) || mRecipeMap.containsInput(GT_Utility.getFluidForFilledItem(aStack, true)));
    }

    public static int countCapacity (int aTier) {
        int x;
    
        if (aTier > 3) {
            x = (aTier - 3) * 8;
        } else {
            x =  aTier + 1;
        }    
        return 8000 * x;
    }

    @Override
    public int getCapacity() {
        return countCapacity(mTier);
    }

    @Override
    public int getTankPressure() {
        return -100;
    }
}
