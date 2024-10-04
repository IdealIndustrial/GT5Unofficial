package gregtech.common.tileentities.machines.multi.pumps;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Fluid;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class GT_MetaTileEntity_WaterPumpPrimitive extends GT_MetaTileEntity_WaterPumpBase {

    public GT_MetaTileEntity_WaterPumpPrimitive(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_WaterPumpPrimitive(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_WaterPumpPrimitive(this.mName);
    }

    public int getRadius() {
        return 16;
    }

    public int getSurfaceBlocksCount() {
        return 128;
    }

    public int getPipeLength() {
        return 10;
    }

    @Override
    public double getOutputRate() {
        return 6.25d;
    }

    @Override
    public int getFilterMeta() {
        return 10;
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Primitive Water Pump",
                "Structure 2x2x1",
                "Controller (any down corner)",
                "Input hatch for steam (down layer near controller)",
                "Bronze fluid pipe (on top of the controller)",
                "Output hatch for water (next to bronze pipe)",
                "Input side of controller connects to pipe",
                "Pipe (up to 10 blocks length) connects to Intake",
                "Controller input must be connected to input this pipe",
                "Uses 200l of steam per second",
                "Pipe fluid capacity must be enough to transfer " + getOutputRate() * 20 + " l per second",
                "Intake is situated in top layer of water source",
                "River for water or ocean for salt water or swamp for dirty water",            
                "Must cover 128 blocks of water surface in radius of 16",
                "For each other pump in work radius will decrease efficiency",
                "Also efficiency slowly decrease as intake gets clogged",
                "Plunger sneaky right click on the controller clears it",
                "Some pipes may connect only after all structure is assembled",
                "River and Ocean and Swampland are Minecraft Biomes"
        };
    }

    public boolean checkRecipe(ItemStack aStack) {
        super.checkRecipe(aStack);
        if (!depleteInput(GT_ModHandler.getSteam(40))) {
            return false;
        }
        this.mMaxProgresstime = 10;
        this.mEUt = 0;
        return true;
    }


    @Override
    public boolean onRunningTick(ItemStack aStack) {
        double tOut = getOutputRate() * (mPumpingEfficiency / 10000) + waterToOutput;
        tOut *= mEfficiencyRate;
        int rOut = (int) tOut;
        waterToOutput = tOut - rOut;
        if (mRiver) {
            addOutput(GT_ModHandler.getWater(rOut));
        } else if (mOcean) {
            addOutput(Materials.SaltWater.getFluid(rOut));
        } else if (mSwamp) {
            addOutput(Materials.DirtyWater.getFluid(rOut));
        }
        return true;
    }

    @Override
    public boolean addToStructure(TileEntity aTileEntityInput, TileEntity aTileEntityPipe, TileEntity aTileEntityOutput, boolean aDoAdd) {
        if (aTileEntityInput instanceof IGregTechTileEntity && ((IGregTechTileEntity) aTileEntityInput).getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Input &&
                aTileEntityOutput instanceof IGregTechTileEntity && ((IGregTechTileEntity) aTileEntityOutput).getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Output &&
                aTileEntityPipe instanceof IGregTechTileEntity && ((IGregTechTileEntity) aTileEntityPipe).getMetaTileEntity() instanceof GT_MetaPipeEntity_Fluid &&
                ((GT_MetaPipeEntity_Fluid) ((IGregTechTileEntity) aTileEntityPipe).getMetaTileEntity()).mMaterial == Materials.Bronze) {
            if (aDoAdd) {
                addToMachineList((IGregTechTileEntity) aTileEntityInput, 128 + 51);
                addToMachineList((IGregTechTileEntity) aTileEntityOutput, 128 + 51);
            }
            return true;
        }
        return false;
    }


    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return super.checkMachine(aBaseMetaTileEntity, aStack);
    }

    @Override
    public ITexture getBaseTexture() {
        return new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_PUMP_ULV);
    }

    @Override
    public IIconContainer getInputFacing() {
        return Textures.BlockIcons.OVERLAY_PIPE_IN;
    }

    private static final IIconContainer[] mFaces = new IIconContainer[]{Textures.BlockIcons.OVERLAY_PRIMITIVE_PUMP_ACTIVE, Textures.BlockIcons.OVERLAY_PRIMITIVE_PUMP_INACTIVE};

    @Override
    public IIconContainer[] getFacings() {
        return mFaces;
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return super.getTexture(aBaseMetaTileEntity, aSide, aFacing, aColorIndex, aActive, aRedstone);
    }

    @Override
    protected String getConsumptionDescription() {
        return (getBaseMetaTileEntity().isActive() ? 40 : 0) + " L/t";
    }


}
