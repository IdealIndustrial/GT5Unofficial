package gregtech.common.tileentities.machines.multi.pumps;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Frame;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class GT_MetaTileEntity_WaterPumpElectric extends GT_MetaTileEntity_WaterPumpBase{

    public int mTier;

    public GT_MetaTileEntity_WaterPumpElectric(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional);
        mTier = aTier;
    }

    public GT_MetaTileEntity_WaterPumpElectric(String aName, int aTier) {
        super(aName);
        mTier = aTier;
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Primitive Water Pump",
                "2x2x1 or 1x2x2",
                "Controller (any down corner)",
                "Energy hatch (any casing)",
                "Output hatch for water (any casing)",
                getFrameMaterial().mLocalizedName + " frame box (any casing)",
                "Input side of controller connects to pipe",
                "Pipe (up to " + getPipeLength() + " blocks length) connects to Filter",
                "Filter placed in top water block of river (or Ocean)",
                "In case pump is situated in ocean it will output salt water",
                "Must cover " + getSurfaceBlocksCount() + " blocks of water surface in radius of " + getRadius(),
                "For each other pump in work radius will decrease efficiency"
        };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_WaterPumpElectric(mName, mTier);
    }

    public boolean checkRecipe(ItemStack aStack) {
        mEUt = (int)-Math.pow(4,getTier());
        this.mEfficiencyIncrease = 10000;
        this.mMaxProgresstime = 10;
        return true;
    }

    public int getTier() {
        return mTier;
    }

    public static Materials[] mTierMaterials = new Materials[]{Materials.Steel, Materials.Aluminium, Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel};

    public Materials getFrameMaterial() {
        return mTierMaterials[getTier()-1];
    }

    @Override
    public int getRadius() {
        return 16+4*getTier();
    }

    @Override
    public int getSurfaceBlocksCount() {
        return 128+32*getTier();
    }

    @Override
    public int getPipeLength() {
        return 10+2*getTier();
    }

    @Override
    public double getOutputRate() {
        return (12.5d*Math.pow(2,getTier()));
    }

    @Override
    public int getFilterMeta() {
        return 10+getTier();
    }

    @Override
    public boolean addToStructure(TileEntity aTileEntity, boolean aDoAdd) {
        if (!(aTileEntity instanceof IGregTechTileEntity))
            return false;
        IMetaTileEntity tMetaTile = ((IGregTechTileEntity)aTileEntity).getMetaTileEntity();
        if (tMetaTile instanceof GT_MetaTileEntity_Hatch_Energy || tMetaTile instanceof GT_MetaTileEntity_Hatch_Output) {
            if (aDoAdd) {
                addToMachineList((IGregTechTileEntity)aTileEntity, 128+51+getTier());
            }
            return true;
        }
        return tMetaTile instanceof GT_MetaPipeEntity_Frame && ((GT_MetaPipeEntity_Frame) tMetaTile).mMaterial == getFrameMaterial();
    }

    @Override
    public ITexture getBaseTexture() {
        return Textures.BlockIcons.MACHINE_CASINGS[1][51+getTier()];
    }

    @Override
    public IIconContainer getInputFacing() {
        return Textures.BlockIcons.OVERLAY_PIPE_IN;
    }

    private static IIconContainer[] mFaces = new IIconContainer[]{Textures.BlockIcons.OVERLAY_ELECTRIC_PUMP_ACTIVE, Textures.BlockIcons.OVERLAY_ELECTRIC_PUMP_INACTIVE};

    @Override
    public IIconContainer[] getFacings() {
        return mFaces;
    }

}
