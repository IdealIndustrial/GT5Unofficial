package gregtech.common.tileentities.machines.multi.pumps;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
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
        mTierMaterials = new Materials[]{Materials.Bronze, Materials.Steel,  Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel};
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Electric Water Pump",
                "Structure 2x2x1",
                "Controller (any down corner)",
                "Energy hatch (down layer near controller)",
                getFrameMaterial().mLocalizedName + " fluid pipe (on top of the controller)",
                "Output hatch for water (next to " + getFrameMaterial().mLocalizedName + " pipe)",
                "Input side of controller connects to pipe",
                "Pipe (up to " + getPipeLength() + " blocks length) connects to Intake",
                "Uses " + Math.pow(4,getTier()) + " EU/t",
                "Controller input must be connected to input this pipe",
                "Pipe fluid capacity must be enough to transfer "+ getOutputRate()*20+ " l per second",
                "Intake placed in top water block of river (or Ocean, then outputs salt water)",
                "In case pump is situated in ocean it will output salt water",
                "Must cover " + getSurfaceBlocksCount() + " blocks of water surface in radius of " + getRadius(),
                "For each other pump in work radius will decrease efficiency",
                "Some pipes may connect only after all structure is assembled",
                "River and Ocean are Minecraft Biomes"
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

    @Override
    public boolean addToStructure(TileEntity aTileEntityInput, TileEntity aTileEntityPipe, TileEntity aTileEntityOutput, boolean aDoAdd) {
        if (aTileEntityInput instanceof IGregTechTileEntity && ((IGregTechTileEntity)aTileEntityInput).getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Energy &&
                aTileEntityOutput instanceof IGregTechTileEntity && ((IGregTechTileEntity)aTileEntityOutput).getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Output &&
                aTileEntityPipe instanceof IGregTechTileEntity && ((IGregTechTileEntity)aTileEntityPipe).getMetaTileEntity() instanceof GT_MetaPipeEntity_Fluid &&
                ((GT_MetaPipeEntity_Fluid)((IGregTechTileEntity)aTileEntityPipe).getMetaTileEntity()).mMaterial == getFrameMaterial()) {
            if (aDoAdd) {
                addToMachineList((IGregTechTileEntity) aTileEntityInput, 128 + 51 + getTier());
                addToMachineList((IGregTechTileEntity) aTileEntityOutput, 128 + 51 + getTier());
            }
            return true;
        }
        return false;
    }

    public int getTier() {
        return mTier;
    }

    public static Materials[] mTierMaterials = new Materials[]{Materials.Bronze, Materials.Steel,  Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel};

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
        return 10+4*getTier();
    }

    @Override
    public double getOutputRate() {
        return (12.5d*Math.pow(2,getTier()));
    }

    @Override
    public int getFilterMeta() {
        return 10+getTier();
    }

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
        return Textures.BlockIcons.casingTexturePages[1][51+getTier()];
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
