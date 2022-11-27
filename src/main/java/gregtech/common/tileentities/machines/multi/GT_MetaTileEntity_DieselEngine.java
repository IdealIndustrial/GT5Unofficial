package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;

public class GT_MetaTileEntity_DieselEngine extends GT_MetaTileEntity_MultiBlockBase {
    protected int fuelConsumption = 0;
    protected int fuelValue = 0;
    protected int fuelRemaining = 0;
    protected boolean boostEu = false;

    private int oxygenPerTick = 50;

    public GT_MetaTileEntity_DieselEngine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_DieselEngine(String aName) {
        super(aName);
    }

    public void onConfigLoad(GT_Config aConfig) {
        super.onConfigLoad(aConfig);
        oxygenPerTick =  Math.min(1,Math.max(100, aConfig.get(ConfigCategories.machineconfig, "DieselEngine.oxygenPerTick", 50)));
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Large Combustion Engine",
                "Size(WxHxD): 3x3x4, Controller (front centered)",
                "3x3x4 of Stable Titanium Machine Casing (hollow, Min 16!)",
                "2x Titanium Gear Box Machine Casing inside the Hollow Casing",
                "8x Engine Intake Machine Casing (around controller)",
                "2x Input Hatch (Fuel/Lubricant) (one of the Casings next to a Gear Box)",
                "1x Maintenance Hatch (one of the Casings next to a Gear Box)",
                "1x Muffler Hatch (top middle back, next to the rear Gear Box)",
                "1x Dynamo Hatch (back centered)",
                "Engine Intake Casings must not be obstructed in front (only air blocks)",
                "Supply Flammable Fuels and 1000L of Lubricant per hour to run.",
                "Supply "+(oxygenPerTick * 20)+"L of Oxygen per second to boost output (optional).",
                "Default: Produces 2048EU/t at 100% efficiency",
                "Boosted: Produces 6144EU/t at 150% efficiency",
                "Causes " + 20 * getPollutionPerTick(null) + " Pollution per second"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50]};
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return getMaxEfficiency(aStack) > 0;
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeDieselEngine.png");
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<FluidStack> tFluids = getStoredFluids();
        Collection<GT_Recipe> tRecipeList = GT_Recipe.GT_Recipe_Map.sDieselFuels.mRecipeList;

        if(tFluids.size() > 0 && tRecipeList != null) { //Does input hatch have a diesel fuel?
            for (FluidStack hatchFluid1 : tFluids) { //Loops through hatches
                for(GT_Recipe aFuel : tRecipeList) { //Loops through diesel fuel recipes
                    FluidStack tLiquid;
                    if ((tLiquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null) { //Create fluidstack from current recipe
                        if (hatchFluid1.isFluidEqual(tLiquid)) { //Has a diesel fluid
                            fuelConsumption = tLiquid.amount = boostEu ? (4096 / aFuel.mSpecialValue) : (2048 / aFuel.mSpecialValue); //Calc fuel consumption
                            if(depleteInput(tLiquid)) { //Deplete that amount
                                boostEu = depleteInput(Materials.Oxygen.getGas(oxygenPerTick));

                                if(tFluids.contains(Materials.Lubricant.getFluid(1L))) { //Has lubricant?
                                    //Deplete Lubricant. 1000L should = 1 hour of runtime (if baseEU = 2048)
                                    if(mRuntime % 72 == 0 || mRuntime == 0) depleteInput(Materials.Lubricant.getFluid(boostEu ? 2 : 1));
                                } else return false;

                                fuelValue = aFuel.mSpecialValue;
                                fuelRemaining = hatchFluid1.amount; //Record available fuel
                                this.mEUt = mEfficiency < 2000 ? 0 : 2048; //Output 0 if startup is less than 20%
                                this.mProgresstime = 1;
                                this.mMaxProgresstime = 1;
                                this.mEfficiencyIncrease = 15;
                                if(this.mDynamoHatches.size()>0){
                                    if(this.mDynamoHatches.get(0).getBaseMetaTileEntity().getOutputVoltage() < (int)((long)mEUt * (long)mEfficiency / 10000L)){
                                        explodeMultiblock();}
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        this.mEUt = 0;
        this.mEfficiency = 0;
        return false;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        byte tSide = getBaseMetaTileEntity().getBackFacing();
        int tX = getBaseMetaTileEntity().getXCoord();
        int tY = getBaseMetaTileEntity().getYCoord();
        int tZ = getBaseMetaTileEntity().getZCoord();

        if (checkNotBlockDistance(getGearboxBlock(), getGearboxMeta(), tSide, 2, false)) {
            return false;
        }
        for (byte i = -1; i < 2; i = (byte) (i + 1)) {
            for (byte j = -1; j < 2; j = (byte) (j + 1)) {
                if ((i != 0) || (j != 0)) {
                    for (byte k = 0; k < 4; k = (byte) (k + 1)) {
                        if (checkNotAirOffset(- (tSide == 5 ? 1 : tSide == 4 ? -1 : i), j, - (tSide == 2 ? -1 : tSide == 3 ? 1 : i))) {
                            return false;
                        }
                        int xOff = tSide == 5 ? k : tSide == 4 ? -k : i;
                        int zOff = tSide == 2 ? -k : tSide == 3 ? k : i;
                        if (((i == 0) || (j == 0)) && ((k == 1) || (k == 2))) {
                            if (getBaseMetaTileEntity().getBlock(tX + xOff, tY + j, tZ + zOff) != getCasingBlock() || getBaseMetaTileEntity().getMetaID(tX + xOff, tY + j, tZ + zOff) != getCasingMeta()) {
                                int mZ = tZ + (tSide == 3 ? 2 : tSide == 2 ? -2 : 0);
                                int mX = tX + (tSide == 5 ? 2 : tSide == 4 ? -2 : 0);
                                if (!addMufflerToMachineList(getBaseMetaTileEntity().getIGregTechTileEntity(mX, tY + 1, mZ), getCasingTextureIndex())) {
                                    sendErrorExpectedHatchOffset(mX, tY + 1, mZ);
                                    return false; //Fail if no muffler top middle back
                                } else if (!addToMachineList(getBaseMetaTileEntity().getIGregTechTileEntity(tX + xOff, tY + j, tZ + zOff))) {
                                    sendErrorExpectedHatchOffset(tX + xOff, tY + j, tZ + zOff);
                                    return false;
                                }
                            }
                        } else if (k == 0) {
                            if(checkNotBlockOffset(getIntakeBlock(), getIntakeMeta(), tX + xOff, tY + j, tZ + zOff, false)) {
                                return false;
                            }
                        } else {
                            if (checkNotBlockOffset(getCasingBlock(), getCasingMeta(), tX + xOff, tY + j, tZ + zOff, false)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        this.mDynamoHatches.clear();
        IGregTechTileEntity tTileEntity = getBaseMetaTileEntity().getIGregTechTileEntityAtSideAndDistance(getBaseMetaTileEntity().getBackFacing(), 3);
        if ((tTileEntity != null) && (tTileEntity.getMetaTileEntity() != null)) {
            if ((tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Dynamo)) {
                this.mDynamoHatches.add((GT_MetaTileEntity_Hatch_Dynamo) tTileEntity.getMetaTileEntity());
                ((GT_MetaTileEntity_Hatch) tTileEntity.getMetaTileEntity()).updateTexture(getCasingTextureIndex());
            } else {
                IGregTechTileEntity te = getBaseMetaTileEntity();
                byte side = te.getBackFacing();
                sendErrorExpectedHatchOffset(te.getOffsetX(side, 3), te.getOffsetY(side, 3), te.getOffsetZ(side, 3));
                return false;
            }
        }
        return true;
    }

    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings4;
    }

    public byte getCasingMeta() {
        return 2;
    }

    public Block getIntakeBlock() {
        return GregTech_API.sBlockCasings4;
    }

    public byte getIntakeMeta() {
        return 13;
    }

    public Block getGearboxBlock() {
        return GregTech_API.sBlockCasings2;
    }

    public byte getGearboxMeta() {
        return 4;
    }

    public byte getCasingTextureIndex() {
        return 50;
    }

    private boolean addToMachineList(IGregTechTileEntity tTileEntity) {
        return ((addMaintenanceToMachineList(tTileEntity, getCasingTextureIndex())) || (addInputToMachineList(tTileEntity, getCasingTextureIndex())) || (addOutputToMachineList(tTileEntity, getCasingTextureIndex())) || (addMufflerToMachineList(tTileEntity, getCasingTextureIndex())));
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_DieselEngine(this.mName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 1;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return boostEu ? 30000 : 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return boostEu ? 32 : 16;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return true;
    }

    @Override
    public String[] getInfoData() {
        String tRunning = mMaxProgresstime>0 ? "Running":"Stopped";
        return new String[]{
                "Diesel Engine",
                tRunning,
                "Mode: ",
                (boostEu ? "Boosted" : "Normal"),
                "Output: ",
                mEUt * mEfficiency / 10000 + " EU/t",
                "Fuel Consumption: ",
                fuelConsumption + "L/t",
                "Fuel Value: ",
                fuelValue + " EU/L",
                "Fuel Remaining: ",
                fuelRemaining + " Litres",
                "Efficiency: ",
                (mEfficiency / 100) + "%",
                StatCollector.translateToLocal("GT5U.multiblock.problems") + ": ",
                "" + (getIdealStatus() - getRepairStatus())};
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }
}
