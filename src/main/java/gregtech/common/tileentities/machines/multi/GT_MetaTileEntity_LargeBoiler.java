package gregtech.common.tileentities.machines.multi;

import gregtech.GT_Mod;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public abstract class GT_MetaTileEntity_LargeBoiler
        extends GT_MetaTileEntity_MultiBlockBase {
    private boolean firstRun = true;
    private int integratedCircuitConfig = 0; //Steam output is reduced by 1000L per config
    private int excessFuel = 0; //Eliminate rounding errors for fuels that burn half items
    private int excessProjectedEU = 0; //Eliminate rounding errors from throttling the boiler
    private int progressTimeStash = 0;
    private int defaultProgressChunk = 20;
    private int lastFuelEfficiencyIncrease = 0;

    private int tGeneratedEU = 0;
    private boolean solidSuperFuel = false;
    private float water;
    protected boolean oxygenBoost = false;

    public GT_MetaTileEntity_LargeBoiler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_LargeBoiler(String aName) {
        super(aName);
    }

    public void onConfigLoad(GT_Config aConfig) {
        super.onConfigLoad(aConfig);
        defaultProgressChunk =  Math.min(80,Math.max(20, aConfig.get(ConfigCategories.machineconfig, "LargeBoiler.defaultProgressChunk", 20)));
    }

    public String[] getDescription() {
        return new String[]{
            "Controller Block for the Large Boiler",
            "Produces " + (getEUt() * 40) * (runtimeBoost(20) / 20f) + "L of Steam with 1 Coal at " + getEUt() * 40 + "L/s",
            "A programmed circuit in the main block throttles the boiler (-1000L/s per config)",
            "Size(WxHxD): 3x5x3, Controller (Front middle in Fireboxes)",
            "3x1x3 of " +getCasingMaterial()+ " Firebox Casings (Bottom layer, Min 3)",
            "3x4x3 of " +getCasingMaterial()+ " " +getCasingBlockType()+ " (Above Fireboxes, hollow, Min 24!)",
            "1x3x1 of " +getCasingMaterial()+ " Pipe Casings (Inside the Hollow Machine Casings/Plated Bricks)",
            "1x Fuel Input Hatch/Bus instead any Firebox",
            "1x Input Hatch instead any Firebox for Water / Distilled Water",
            "1x Steam Output Hatch instead any Casing",
            "1x Maintenance Hatch instead any Firebox",
            "1x Muffler Hatch instead any Firebox",
            "Firstly Consumes Distilled Water, Secondly Water",
            "Diesel fuels have 1/4 efficiency",
            (getOxygenPerOperation() > 0 ?
                getOxygenPerOperation()+"L/s of Oxygen and Solid Super Fuel will give a Superheated Steam"
                : "Only Large Titanium or Large TungstenSteel Boilers can be boosted"),
            String.format("Takes %.2f seconds to heat up", 500.0 / getEfficiencyIncrease()),
            "Causes up to " + 20 * getPollutionPerTick(null) + " Pollution per second"
        };
    }
    
    public abstract String getCasingMaterial();

    public abstract Block getCasingBlock();
    
    public abstract String getCasingBlockType();

    public abstract byte getCasingMeta();

    public abstract byte getCasingTextureIndex();

    public abstract Block getPipeBlock();

    public abstract byte getPipeMeta();

    public abstract Block getFireboxBlock();

    public abstract byte getFireboxMeta();

    public abstract byte getFireboxTextureIndex();
    public abstract long getOxygenPerOperation();

    public abstract int getEUt();

    public abstract int getEfficiencyIncrease();

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeBoiler.png");
    }
    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setFloat("water", water);
        aNBT.setInteger("excessProjectedEU", excessProjectedEU);
        aNBT.setInteger("progressTimeStash", progressTimeStash);
        aNBT.setInteger("excessFuel", excessFuel);
        aNBT.setBoolean("oxygenBoost", oxygenBoost);
        aNBT.setBoolean("solidSuperFuel", solidSuperFuel);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        water = aNBT.getFloat("water");
        excessProjectedEU = aNBT.getInteger("excessProjectedEU");
        progressTimeStash = aNBT.getInteger("progressTimeStash");
        excessFuel = aNBT.getInteger("excessFuel");
        oxygenBoost = aNBT.getBoolean("oxygenBoost");
        solidSuperFuel = aNBT.getBoolean("solidSuperFuel");
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    private void calcProgressVars(){
        if(getOxygenPerOperation() > 0 && this.solidSuperFuel) {
            this.oxygenBoost = depleteInput(Materials.Oxygen.getGas(getOxygenPerOperation() * this.mMaxProgresstime));
        } else {
            this.oxygenBoost = false;
        }
        this.mEfficiencyIncrease = lastFuelEfficiencyIncrease;

        this.mMaxProgresstime = adjustBurnTimeForConfig(runtimeBoost(this.mMaxProgresstime));
        this.mEUt = adjustEUtForConfig(getEUt());
    }

    public boolean checkRecipe(ItemStack aStack) {
        if (mInventory[1] != null && mInventory[1].getUnlocalizedName().startsWith("gt.integrated_circuit")) {
            int circuit_config = mInventory[1].getItemDamage();
            if (circuit_config >= 1 && circuit_config <= 25) {
                this.integratedCircuitConfig = circuit_config;
            }
        } else {
            this.integratedCircuitConfig = 0;
        }
        if(this.progressTimeStash >= defaultProgressChunk) {
            this.progressTimeStash -= defaultProgressChunk;
            this.mMaxProgresstime = defaultProgressChunk;
            calcProgressVars();
            return true;
        }
        this.solidSuperFuel = false; // reset fuel boost

        for (GT_Recipe tRecipe : GT_Recipe.GT_Recipe_Map.sDieselFuels.mRecipeList) {
            FluidStack tFluid = GT_Utility.getFluidForFilledItem(tRecipe.getRepresentativeInput(0), true);
            if (tFluid != null && tRecipe.mSpecialValue > 1) {
                tFluid.amount = 8000;
                if (depleteInput(tFluid)) {
                    this.mMaxProgresstime = (tRecipe.mSpecialValue * (tFluid.amount / 1000)) / 2;
                    if(this.mMaxProgresstime >= defaultProgressChunk) {
                        this.progressTimeStash = this.mMaxProgresstime - defaultProgressChunk;
                        this.mMaxProgresstime = defaultProgressChunk;
                    }
                    lastFuelEfficiencyIncrease = this.mMaxProgresstime * getEfficiencyIncrease() * 4;
                    calcProgressVars();
                    return true;
                }
            }
        }
        for (GT_Recipe tRecipe : GT_Recipe.GT_Recipe_Map.sDenseLiquidFuels.mRecipeList) {
            FluidStack tFluid = GT_Utility.getFluidForFilledItem(tRecipe.getRepresentativeInput(0), true);
            if (tFluid != null) {
                tFluid.amount = 8000;
                if (depleteInput(tFluid)) {
                    this.mMaxProgresstime = Math.max(1, tRecipe.mSpecialValue * (tFluid.amount / 1000) * 2);
                    if(this.mMaxProgresstime >= defaultProgressChunk) {
                        this.progressTimeStash = this.mMaxProgresstime - defaultProgressChunk;
                        this.mMaxProgresstime = defaultProgressChunk;
                    }
                    lastFuelEfficiencyIncrease = this.mMaxProgresstime * getEfficiencyIncrease();
                    calcProgressVars();
                    return true;
                }
            }
        }
        ArrayList<ItemStack> tInputList = getStoredInputs();
        if (!tInputList.isEmpty()) {
            for (ItemStack tInput : tInputList) {
				if (tInput != GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.Lava, 1)){
                    int fuelValue = GT_ModHandler.getFuelValue(tInput) / 80;
                    this.solidSuperFuel = fuelValue >= 1250;
					if ((GT_Utility.getFluidForFilledItem(tInput, true) == null && fuelValue > 0)) {
                        this.mMaxProgresstime = 0;
                        while (tInput.stackSize > 0 && this.mMaxProgresstime < defaultProgressChunk) {
                            this.mMaxProgresstime += fuelValue;
                            this.excessFuel += GT_ModHandler.getFuelValue(tInput) % 80;
                            this.mMaxProgresstime += this.excessFuel / 80;
                            this.excessFuel %= 80;
                            this.mOutputItems = new ItemStack[]{GT_Utility.getContainerItem(tInput, true)};
                            tInput.stackSize -= 1;
                        }
                        this.mMaxProgresstime += this.progressTimeStash;
                        this.progressTimeStash = 0;
                        if(this.mMaxProgresstime >= defaultProgressChunk) {
                            this.progressTimeStash = this.mMaxProgresstime - defaultProgressChunk;
                            this.mMaxProgresstime = defaultProgressChunk;
                        }
                        lastFuelEfficiencyIncrease = this.mMaxProgresstime * getEfficiencyIncrease();
                        calcProgressVars();
                    	updateSlots();
                    	return true;
                	}
				}
            }
        }
        if(this.progressTimeStash > 0) {
            this.mMaxProgresstime = this.progressTimeStash;
            this.progressTimeStash = 0;
            calcProgressVars();
            this.mEfficiencyIncrease = this.mMaxProgresstime * getEfficiencyIncrease();
            return true;
        }
        this.mMaxProgresstime = 0;
        this.mEUt = 0;
        return false;
    }

    abstract int runtimeBoost(int mTime);

    public boolean onRunningTick(ItemStack aStack) {
        if (this.mEUt > 0) {
            mEfficiency = Math.max(0, Math.min(mEfficiency, getMaxEfficiency(mInventory[1]) - ((getIdealStatus() - getRepairStatus()) * 1000)));
            tGeneratedEU = (int) (this.mEUt * 2L * this.mEfficiency / 10000L);
            if (tGeneratedEU > 0) {
                water = water + tGeneratedEU/160f;
                int amount = (int) water;
                water = water - amount;

                if (depleteInput(GT_ModHandler.getDistilledWater(amount))||depleteInput(Materials.Water.getFluid(amount))) {
                    addOutput(oxygenBoost ? FluidRegistry.getFluidStack("ic2superheatedsteam", tGeneratedEU) : GT_ModHandler.getSteam(tGeneratedEU));
                } else {
                    explodeMultiblock();
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (mProgresstime > 0 && firstRun) {
            firstRun = false;
            GT_Mod.achievements.issueAchievement(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "extremepressure");
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;

        int tCasingAmount = 0;
        int tFireboxAmount = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    for (int k = 1; k <= 4; k++) {
                        if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), getCasingTextureIndex())) {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getCasingBlock()) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getCasingMeta()) {
                                return false;
                            }
                            tCasingAmount++;
                        }
                    }
                } else {
                    for (int k = 1; k <= 3; k++) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getPipeBlock()) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getPipeMeta()) {
                            return false;
                        }
                    }
                    if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 4, zDir + j), getCasingTextureIndex())) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 4, zDir + j) != getCasingBlock()) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 4, zDir + j) != getCasingMeta()) {
                            return false;
                        }
                        tCasingAmount++;
                    }
                }
            }
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (xDir + i != 0 || zDir + j != 0) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
                    if (!addMaintenanceToMachineList(tTileEntity, getFireboxTextureIndex()) && !addInputToMachineList(tTileEntity, getFireboxTextureIndex()) && !addMufflerToMachineList(tTileEntity, getFireboxTextureIndex())) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != getFireboxBlock()) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != getFireboxMeta()) {
                            return false;
                        }
                        tFireboxAmount++;
                    }
                }
            }
        }
        return tCasingAmount >= 24 && tFireboxAmount >= 3;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        int adjustedEUOutput = Math.max(25, getEUt() - 25 * integratedCircuitConfig);
        adjustedEUOutput *= oxygenBoost ? 3 : 1;
        return Math.max(1, 12 * adjustedEUOutput / getEUt());
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    private boolean isHeatingUp(){
        return mEfficiency < getMaxEfficiency(mInventory[1]) - ((getIdealStatus() - getRepairStatus()) * 1000);
    }

    private int adjustEUtForConfig(int rawEUt) {
        if (isHeatingUp()) {
            return rawEUt;
        }
        int adjustedSteamOutput = rawEUt - 25 * integratedCircuitConfig;
        return Math.max(adjustedSteamOutput, 25);
    }

    private int adjustBurnTimeForConfig(int rawBurnTime) {
        if (isHeatingUp()) {
            return rawBurnTime;
        }
        int adjustedEUt = Math.max(25, getEUt() - 25 * integratedCircuitConfig);
        int adjustedBurnTime = rawBurnTime * getEUt() / adjustedEUt;
        this.excessProjectedEU += getEUt() * rawBurnTime - adjustedEUt * adjustedBurnTime;
        adjustedBurnTime += this.excessProjectedEU / adjustedEUt;
        this.excessProjectedEU %= adjustedEUt;
        return adjustedBurnTime;
    }

    @Override
    public String[] getInfoData() {
        String tRunning = mMaxProgresstime > 0 ? "Running" : "Stopped";
        return new String[] {
            getCasingMaterial() + " Boiler",
            tRunning,
            "Producing: ",
            (oxygenBoost ? "SH Steam" : "Steam "),
            "Amount: ",
            (mEUt > 0 ? tGeneratedEU : 0)+" L/t",
            "Efficiency: ",
            (mEfficiency / 100) + "%",
            StatCollector.translateToLocal("GT5U.multiblock.problems") + ": ",
            "" + (getIdealStatus() - getRepairStatus())
        };
    }

}
