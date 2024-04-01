package gregtech.common.tileentities.machines.multi.pumps;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Fluid;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Frame;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class GT_MetaTileEntity_WaterPumpElectric extends GT_MetaTileEntity_WaterPumpBase {
    public static int[] SPEED = new int[]{80, 80, 40, 20, 10, 5, 5, 2, 2, 2, 2},
            ENERGY = new int[]{8, 4, 16, 64, 256, 1024, 2048, 32768, 131072, 524288};
    public int mTier;

    public GT_MetaTileEntity_WaterPumpElectric(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional);
        mTier = aTier;
    }

    public GT_MetaTileEntity_WaterPumpElectric(String aName, int aTier) {
        super(aName);
        mTier = aTier;
        mTierMaterials = new Materials[]{Materials.Bronze, Materials.Steel, Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel};
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Electric Water Pump",
                "Structure 2x2x1",
                "Controller (any down corner)",
                "Energy " + GT_Values.VN[getTier()] + " hatch (down layer near controller)",
                getFrameMaterial().mLocalizedName + " fluid pipe (on top of the controller)",
                "Output " + GT_Values.VN[getTier()] + " hatch for water (next to " + getFrameMaterial().mLocalizedName + " pipe)",
                "Input side of controller connects to pipe",
                "Pipe (up to " + getPipeLength() + " blocks length) connects to Intake",
                "Uses " + Math.pow(4, getTier()) + " EU/t",
                "Controller input must be connected to input this pipe",
                "Pipe fluid capacity must be enough to transfer " + getOutputRate() * 20 + " l per second",
                "Intake placed in top water block of river (or Ocean, then outputs salt water)",
                "In case pump is situated in ocean it will output salt water",
                "In case pump is situated in swamp it will output dirty water",            
                "Must cover " + getSurfaceBlocksCount() + " blocks of water surface in radius of " + getRadius(),
                "For each other pump in work radius will decrease efficiency",
                "Also efficiency slowly decrease as intake gets clogged",
                "Plunger sneaky right click on the controller clears it",
                "Some pipes may connect only after all structure is assembled",
                "River and Ocean and Swampland are Minecraft Biomes"
        };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_WaterPumpElectric(mName, mTier);
    }

    public boolean checkRecipe(ItemStack aStack) {
        super.checkRecipe(aStack);
        recalculateEfficiency();
        long neededEnergy =  ENERGY[getTier()] * 10;
        if (mEnergyHatches.get(0).getEUVar() >= neededEnergy) {
            mEUt = -ENERGY[getTier()];
            this.mMaxProgresstime = 10;
            return true;
        }
        return false;
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        String guiLocalName = trans("gt.blockmachines." + mName + ".guiname", (getTier() == 1 ? getLocalName() : ("Advanced Water Pump ") + (getTier() == 2 ? "" : getTier() == 3 ? "II" : getTier() == 4 ? "III" : "IV")));
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, guiLocalName, "MultiblockDisplay.png");
    }

    @Override
    public boolean addToStructure(TileEntity aTileEntityInput, TileEntity aTileEntityPipe, TileEntity aTileEntityOutput, boolean aDoAdd) {
        if (aTileEntityInput instanceof IGregTechTileEntity && ((IGregTechTileEntity) aTileEntityInput).getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Energy &&
                aTileEntityOutput instanceof IGregTechTileEntity && ((IGregTechTileEntity) aTileEntityOutput).getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Output &&
                aTileEntityPipe instanceof IGregTechTileEntity && ((IGregTechTileEntity) aTileEntityPipe).getMetaTileEntity() instanceof GT_MetaPipeEntity_Fluid &&
                ((GT_MetaPipeEntity_Fluid) ((IGregTechTileEntity) aTileEntityPipe).getMetaTileEntity()).mMaterial == getFrameMaterial()) {
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

    public static Materials[] mTierMaterials = new Materials[]{Materials.Bronze, Materials.Steel, Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel};

    public Materials getFrameMaterial() {
        return mTierMaterials[getTier() - 1];
    }

    @Override
    public int getRadius() {
        return 16 + 4 * getTier();
    }

    @Override
    public int getSurfaceBlocksCount() {
        return 128 + 32 * getTier();
    }

    @Override
    public int getPipeLength() {
        return 10 + 4 * getTier();
    }

    @Override
    public double getOutputRate() {
        return 1000d / SPEED[getTier()];
    }

    @Override
    public int getFilterMeta() {
        return 10 + getTier();
    }

    public boolean addToStructure(TileEntity aTileEntity, boolean aDoAdd) {
        if (!(aTileEntity instanceof IGregTechTileEntity)) {
            return false;
        }
        IMetaTileEntity tMetaTile = ((IGregTechTileEntity) aTileEntity).getMetaTileEntity();
        if (tMetaTile instanceof GT_MetaTileEntity_Hatch_Energy || tMetaTile instanceof GT_MetaTileEntity_Hatch_Output) {
            if (aDoAdd) {
                addToMachineList((IGregTechTileEntity) aTileEntity, 128 + 51 + getTier());
            }
            return true;
        }
        return tMetaTile instanceof GT_MetaPipeEntity_Frame && ((GT_MetaPipeEntity_Frame) tMetaTile).mMaterial == getFrameMaterial();
    }

    @Override
    public ITexture getBaseTexture() {
        return Textures.BlockIcons.casingTexturePages[1][51 + getTier()];
    }

    @Override
    public IIconContainer getInputFacing() {
        return Textures.BlockIcons.OVERLAY_PIPE_IN;
    }

    private static final IIconContainer[] mFaces = new IIconContainer[]{Textures.BlockIcons.OVERLAY_ELECTRIC_PUMP_ACTIVE, Textures.BlockIcons.OVERLAY_ELECTRIC_PUMP_INACTIVE};

    @Override
    public IIconContainer[] getFacings() {
        return mFaces;
    }

    @Override
    protected String getConsumptionDescription() {
        return (getBaseMetaTileEntity().isActive() ? ENERGY[getTier()] : 0) + " EU/t";
    }
}
