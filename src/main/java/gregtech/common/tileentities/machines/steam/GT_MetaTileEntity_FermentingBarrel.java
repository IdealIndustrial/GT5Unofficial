package gregtech.common.tileentities.machines.steam;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

public class GT_MetaTileEntity_FermentingBarrel extends GT_MetaTileEntity_MachineNoAutomation {

    public GT_Recipe.GT_Recipe_Map mRecipeMap = GT_Recipe.GT_Recipe_Map.sFermentingBarrelRecipes;
    public GT_Recipe mLastRecipe;
    public FluidStack mNextFluidStack;
    public GT_MetaTileEntity_FermentingBarrel(int aID) {
        super(aID, "gt.machine.primitive.fermenting_barrel", "Fermenting Barrel", 0, 0, new String[]{"GG"},
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_BOTTOM")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_TOP")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_SIDE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_SIDE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_BOTTOM_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_TOP_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_SIDE_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/fermenting_barrel/OVERLAY_SIDE_ACTIVE"))
        );
    }

    public GT_MetaTileEntity_FermentingBarrel(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    protected ITexture[] getBaseTextures(int color) {
        return Arrays.stream(Textures.BlockIcons.WOODEN_CASINGS).map( i -> ((GT_RenderedTexture) i)
                .copyWithModulation(Dyes.getModulation(color - 1, Dyes._NULL.getRGBA()))).toArray(ITexture[]::new);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    protected boolean checkRecipe() {
        FluidStack[] tFluids = new FluidStack[]{getFluid()};
        mLastRecipe = mRecipeMap.findRecipe(getBaseMetaTileEntity(), mLastRecipe, false,0, tFluids);
        if (mLastRecipe != null && mLastRecipe.isRecipeInputEqual(false, tFluids)) {
            mMaxProgressTime = mLastRecipe.mDuration;
            mNextFluidStack = mLastRecipe.mFluidOutputs == null || mLastRecipe.mFluidOutputs.length == 0 ? null : mLastRecipe.mFluidOutputs[0].copy();
            return true;
        }
        return false;
    }

    @Override
    protected boolean finishRecipe() {
        mFluid = mNextFluidStack;
        mNextFluidStack = null;
        return true;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_FermentingBarrel(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public boolean canTankBeFilled() {
        return mMaxProgressTime == 0;
    }

    @Override
    public boolean canTankBeEmptied() {
        return mMaxProgressTime == 0;
    }

    @Override
    public int getCapacity() {
        return 5000;
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
                getBaseMetaTileEntity().isActive() ? "Is working" : "Is waiting for inputs",
                "Work progress: " + mProgressTime + " / " + mMaxProgressTime
        };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        if (mNextFluidStack != null) {
            aNBT.setTag("mNextFluid", mNextFluidStack.writeToNBT(new NBTTagCompound()));
        }
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        mNextFluidStack = aNBT.hasKey("mNextFluid") ? FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mNextFluid")) : null;
        super.loadNBTData(aNBT);
    }
}
