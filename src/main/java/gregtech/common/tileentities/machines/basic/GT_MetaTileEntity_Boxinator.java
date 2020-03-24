package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_Boxinator extends GT_MetaTileEntity_BasicMachine {
    public GT_MetaTileEntity_Boxinator(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Puts things into Boxes", 2, 1, "Packager.png", "", new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_BOXINATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_BOXINATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_BOXINATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_BOXINATOR)});
    }

    public GT_MetaTileEntity_Boxinator(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 2, 1, aGUIName, aNEIName);
    }

    public GT_MetaTileEntity_Boxinator(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 2, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Boxinator(this.mName, this.mTier, this.mDescriptionArray, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public GT_Recipe.GT_Recipe_Map getRecipeList() {
        return GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes;
    }

    public int checkRecipe() {
        int tCheck = super.checkRecipe();
        if (tCheck != 0) {
            return tCheck;
        } else {
            if (GT_Utility.isStackValid(this.getInputAt(0)) && GT_Utility.isStackValid(this.getInputAt(1)) && GT_Utility.getContainerItem(this.getInputAt(0), true) == null) {
                if (ItemList.Schematic_1by1.isStackEqual(this.getInputAt(1)) && this.getInputAt(0).stackSize >= 1) {
                    this.mOutputItems[0] = GT_ModHandler.getRecipeOutput(new ItemStack[]{this.getInputAt(0)});
                    if (this.mOutputItems[0] != null && this.canOutput(new ItemStack[]{this.mOutputItems[0]})) {
                        --this.getInputAt(0).stackSize;
                        this.mEUt = 32 * (1 << this.mTier - 1) * (1 << this.mTier - 1);
                        this.mMaxProgresstime = 16 / (1 << this.mTier - 1);
                        return 2;
                    }

                    return 0;
                }

                ItemStack var10000;
                if (ItemList.Schematic_2by2.isStackEqual(this.getInputAt(1)) && this.getInputAt(0).stackSize >= 4) {
                    this.mOutputItems[0] = GT_ModHandler.getRecipeOutput(new ItemStack[]{this.getInputAt(0), this.getInputAt(0), null, this.getInputAt(0), this.getInputAt(0)});
                    if (this.mOutputItems[0] != null && this.canOutput(new ItemStack[]{this.mOutputItems[0]})) {
                        var10000 = this.getInputAt(0);
                        var10000.stackSize -= 4;
                        this.mEUt = 32 * (1 << this.mTier - 1) * (1 << this.mTier - 1);
                        this.mMaxProgresstime = 32 / (1 << this.mTier - 1);
                        return 2;
                    }

                    return 0;
                }

                if (ItemList.Schematic_3by3.isStackEqual(this.getInputAt(1)) && this.getInputAt(0).stackSize >= 9) {
                    this.mOutputItems[0] = GT_ModHandler.getRecipeOutput(new ItemStack[]{this.getInputAt(0), this.getInputAt(0), this.getInputAt(0), this.getInputAt(0), this.getInputAt(0), this.getInputAt(0), this.getInputAt(0), this.getInputAt(0), this.getInputAt(0)});
                    if (this.mOutputItems[0] != null && this.canOutput(new ItemStack[]{this.mOutputItems[0]})) {
                        var10000 = this.getInputAt(0);
                        var10000.stackSize -= 9;
                        this.mEUt = 32 * (1 << this.mTier - 1) * (1 << this.mTier - 1);
                        this.mMaxProgresstime = 64 / (1 << this.mTier - 1);
                        return 2;
                    }

                    return 0;
                }
            }

            return 0;
        }
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) {
            if (!ItemList.Schematic_1by1.isStackEqual(this.getInputAt(1)) && !ItemList.Schematic_2by2.isStackEqual(this.getInputAt(1)) && !ItemList.Schematic_3by3.isStackEqual(this.getInputAt(1))) {
                return GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes.containsInput(aStack);
            }

            if (GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes.findRecipe(this.getBaseMetaTileEntity(), true, GT_Values.V[this.mTier], (FluidStack[])null, new ItemStack[]{GT_Utility.copyAmount(64L, new Object[]{aStack}), this.getInputAt(1)}) != null) {
                return true;
            }

            if (ItemList.Schematic_1by1.isStackEqual(this.getInputAt(1)) && GT_ModHandler.getRecipeOutput(new ItemStack[]{aStack}) != null) {
                return true;
            }

            if (ItemList.Schematic_2by2.isStackEqual(this.getInputAt(1)) && GT_ModHandler.getRecipeOutput(new ItemStack[]{aStack, aStack, null, aStack, aStack}) != null) {
                return true;
            }

            if (ItemList.Schematic_3by3.isStackEqual(this.getInputAt(1)) && GT_ModHandler.getRecipeOutput(new ItemStack[]{aStack, aStack, aStack, aStack, aStack, aStack, aStack, aStack, aStack}) != null) {
                return true;
            }
        }

        return false;
    }
}
