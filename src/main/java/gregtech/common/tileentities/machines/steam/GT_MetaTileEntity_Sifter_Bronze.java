package gregtech.common.tileentities.machines.steam;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_BasicMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_Bronze;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_MetaTileEntity_Sifter_Bronze
        extends GT_MetaTileEntity_BasicMachine_Bronze {
    public GT_MetaTileEntity_Sifter_Bronze(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, "Stay calm and keep sifting", 1, 9, true);
    }

    public GT_MetaTileEntity_Sifter_Bronze(String aName, String aDescription, ITexture[][][] aTextures) {
        super(aName, aDescription, aTextures, 1, 9, true);
    }

    public GT_MetaTileEntity_Sifter_Bronze(String aName, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aDescription, aTextures, 1, 9, true);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Sifter_Bronze(this.mName, this.mDescriptionArray, this.mTextures);
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "BronzeSifter.png", GT_Recipe.GT_Recipe_Map.sSifterRecipes.mUnlocalizedName);
    }

    public int checkRecipe() {
        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sSifterRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[1], null, getAllInputs());
        if ((tRecipe != null) && (canOutput(tRecipe.mOutputs)) && (tRecipe.isRecipeInputEqual(true, null, getAllInputs()))) {
            for (int i = 0; i < mOutputItems.length; i++) {
                if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(i)) {
                    this.mOutputItems[i] = tRecipe.getOutput(i);
                }
            }
            this.mEUt = tRecipe.mEUt;
            this.mMaxProgresstime = (tRecipe.mDuration * 2);
            return 2;
        }
        return 0;
    }

    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 1) {
            GT_Utility.doSoundAtClient((String) GregTech_API.sSoundList.get(Integer.valueOf(200)), 10, 1.0F, aX, aY, aZ);
        }
    }

    public void startProcess() {
        sendLoopStart((byte) 1);
    }

    public ITexture[] getSideFacingActive(byte aColor) {
        return new ITexture[]{super.getSideFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_COMPRESSOR_ACTIVE)};
    }

    public ITexture[] getSideFacingInactive(byte aColor) {
        return new ITexture[]{super.getSideFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_COMPRESSOR)};
    }

    public ITexture[] getFrontFacingActive(byte aColor) {
        return new ITexture[]{super.getFrontFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_SIFTER_ACTIVE)};
    }

    public ITexture[] getFrontFacingInactive(byte aColor) {
        return new ITexture[]{super.getFrontFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_SIFTER)};
    }

    public ITexture[] getTopFacingActive(byte aColor) {
        return new ITexture[]{super.getTopFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_COMPRESSOR_ACTIVE)};
    }

    public ITexture[] getTopFacingInactive(byte aColor) {
        return new ITexture[]{super.getTopFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_COMPRESSOR)};
    }

    public ITexture[] getBottomFacingActive(byte aColor) {
        return new ITexture[]{super.getBottomFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_COMPRESSOR_ACTIVE)};
    }

    public ITexture[] getBottomFacingInactive(byte aColor) {
        return new ITexture[]{super.getBottomFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_COMPRESSOR)};
    }
}
