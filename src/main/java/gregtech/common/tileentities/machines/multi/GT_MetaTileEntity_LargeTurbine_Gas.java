package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;

public class GT_MetaTileEntity_LargeTurbine_Gas extends GT_MetaTileEntity_LargeTurbine {

    private static float oxygenFactor = 28f;
    private int oxygenConsume = 0;
    private int prevOxygenConsume = 0;
    private boolean isBoosted = false;

    public GT_MetaTileEntity_LargeTurbine_Gas(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_LargeTurbine_Gas(String aName) {
        super(aName);
    }

    @Override
    public void onConfigLoad(GT_Config aConfig) {
        super.onConfigLoad(aConfig);
        oxygenFactor = (float)Math.max(1,Math.min(1000, aConfig.get(ConfigCategories.machineconfig, "LargeTurbineGas.oxygenFactor", 28f)));
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[1][aColorIndex + 1], aFacing == aSide ? aActive ? new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_SS_ACTIVE5) : new GT_RenderedTexture(Textures.BlockIcons.LARGETURBINE_SS5) : Textures.BlockIcons.CASING_BLOCKS[58]};
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("isBoosted", isBoosted);
        aNBT.setInteger("oxygenConsume", oxygenConsume);
        aNBT.setInteger("prevOxygenConsume", prevOxygenConsume);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        isBoosted = aNBT.getBoolean("isBoosted");
        oxygenConsume = aNBT.getInteger("oxygenConsume");
        prevOxygenConsume = aNBT.getInteger("prevOxygenConsume");
    }


    @Override
    public String[] getDescription() {
        return new String[]{
            "Controller Block for the Large Gas Turbine",
            "Size(WxHxD): 3x3x4 (Hollow), Controller (Front centered)",
            "1x Gas Input Hatch (Side centered)",
            "1x Maintenance Hatch (Side centered)",
            "1x Muffler Hatch (Side centered)",
            "1x Dynamo Hatch (Back centered)",
            "Stainless Steel Turbine Casings for the rest (24 at least!)",
            "Needs a Turbine Item (Inside controller GUI)",
            "Energy Output depending on Rotor: 102-6720EU/t",
            "Oxygen can boost output x3 but fuel consume x2",
            "Oxygen consuming depends on generating EU/t",
            "Causes " + 20 * getPollutionPerTick(null) + " Pollution per second"
        };
    }

    public int getFuelValue(FluidStack aLiquid) {
        if (aLiquid == null || GT_Recipe_Map.sTurbineFuels == null) return 0;
        FluidStack tLiquid;
        Collection<GT_Recipe> tRecipeList = GT_Recipe_Map.sTurbineFuels.mRecipeList;
        if (tRecipeList != null) for (GT_Recipe tFuel : tRecipeList)
            if ((tLiquid = GT_Utility.getFluidForFilledItem(tFuel.getRepresentativeInput(0), true)) != null)
                if (aLiquid.isFluidEqual(tLiquid)) return tFuel.mSpecialValue;
        return 0;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_LargeTurbine_Gas(mName);
    }

    @Override
    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings4;
    }

    @Override
    public byte getCasingMeta() {
        return 10;
    }

    @Override
    public byte getCasingTextureIndex() {
        return 58;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return isBoosted ? 16 : 8;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) {
            isBoosted = false;
            oxygenConsume = 0;
            return 0;
        }
        if (aStack.getItem() instanceof GT_MetaGenerated_Tool_01) {
            return Math.max(mEfficiency, 10000);
        }
        return 0;
    }

    private int calcBoostDelta(int maxOxygenConsume, int currentOxygenConsume){
        int res = 5;
        if(maxOxygenConsume > currentOxygenConsume){
            res = -6;
            if(mEfficiency > 12550) {
                res -= (mEfficiency - 12550) / 10; // extra penalty for high turbine efficient
            }
        }
        return res;
    }

    @Override
    int fluidIntoPower(ArrayList<FluidStack> aFluids, int aOptFlow, int aBaseEff) {
        if (aFluids.size() >= 1) {
            float fuelOverload = 0;
            if(mEfficiency >= 10000) {
                fuelOverload = (mEfficiency - 10000) / 5000f;
                oxygenConsume = Math.max(1, (int)(mEUt / (2 * oxygenFactor))); //base eu/t = mEUt/2
                if(mEfficiency < 12550) {
                    float overload = (float)Math.sqrt(fuelOverload);
                    oxygenConsume *= overload;
                }
                oxygenConsume = Math.max(1, oxygenConsume);
                if(prevOxygenConsume <= oxygenConsume) {
                    prevOxygenConsume = oxygenConsume;
                } else {
                    prevOxygenConsume--;
                    oxygenConsume = prevOxygenConsume;
                }
                int realOxygenConsumed = depleteInputUpTo(Materials.Oxygen.getGas(oxygenConsume));
                isBoosted = realOxygenConsumed > 0 || mEfficiency > 10000;
                if(!isBoosted) {
                    oxygenConsume = 0;
                } else {
                    int deltaEfficient = calcBoostDelta(oxygenConsume, realOxygenConsumed);
                    mEfficiency = Math.min(mEfficiency + deltaEfficient, 15000);
                    oxygenConsume = realOxygenConsumed;
                }
            } else {
                isBoosted = false;
                oxygenConsume = 0;
            }

            FluidStack firstFuelType = null;
            boolean foundFuel = false;
            for (FluidStack fs : aFluids) {
                if(!foundFuel) {
                    firstFuelType = new FluidStack(fs, 0);
                    foundFuel = getFuelValue(firstFuelType) != 0;
                }
            }
            if(!foundFuel) {
                return 0;
            }
            int fuelValue = getFuelValue(firstFuelType);
            int actualOptimalFlow = (int) (aOptFlow / fuelValue);
            if(fuelOverload > 0) {
                actualOptimalFlow += Math.round(actualOptimalFlow * fuelOverload);
            }
            this.realOptFlow = actualOptimalFlow;

            float remainingFlowFactor = 1.25f;
            int remainingFlow = (int) (actualOptimalFlow * remainingFlowFactor); // Allowed to use up to 125% of optimal flow.  Variable required outside of loop for multi-hatch scenarios.
            int totalFlow = 0;

            int aFluids_sS=aFluids.size();
            for (int i = 0; i < aFluids_sS; i++) {
                if (aFluids.get(i).isFluidEqual(firstFuelType)) {
                    int flow = aFluids.get(i).amount; // Get all (steam) in hatch
                    flow = Math.min(flow, Math.min(remainingFlow, (int) (actualOptimalFlow * 1.25f))); // try to use up to 125% of optimal flow w/o exceeding remainingFlow
                    depleteInput(new FluidStack(aFluids.get(i), flow)); // deplete that amount
                    this.storedFluid = aFluids.get(i).amount;
                    remainingFlow -= flow; // track amount we're allowed to continue depleting from hatches
                    totalFlow += flow; // track total input used
                }
            }

            int tEU = (int) (Math.min((float) actualOptimalFlow, totalFlow) * fuelValue);

            if (totalFlow != actualOptimalFlow) {
                float efficiency = 1.0f - Math.abs(((totalFlow - (float) actualOptimalFlow) / actualOptimalFlow));
                if(totalFlow>actualOptimalFlow){efficiency = 1.0f;}
                if (efficiency < 0)
                    efficiency = 0; // Can happen with really ludicrously poor inefficiency.
                tEU *= efficiency;
                tEU = Math.max(1, (int)((long)tEU * (long)aBaseEff / 10000L));
            } else {
                tEU = (int)((long)tEU * (long)aBaseEff / 10000L);
            }
            return tEU;
        }
        return 0;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if(!aBaseMetaTileEntity.isAllowedToWork()){
            isBoosted = false;
            oxygenConsume = 0;
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    public int getRealOutEu(){
        return (int)(mEUt * mEfficiency / 10000f);
    }

    @Override
    public String[] getInfoData() {
        String tRunning = mMaxProgresstime>0 ? "Running":"Stopped";
        int tDura = 0;

        if (mInventory[1] != null && mInventory[1].getItem() instanceof GT_MetaGenerated_Tool_01) {
            tDura = (int) ((100.0f / GT_MetaGenerated_Tool.getToolMaxDamage(mInventory[1]) * (GT_MetaGenerated_Tool.getToolDamage(mInventory[1]))+1));
        }

        return new String[]{
                "Gas Turbine",
                tRunning,
                "Mode: "+(isBoosted ? "Boosted" : "Normal"),
                getRealOutEu()+" EU/t",
                "Oxygen Consuming: ",
                oxygenConsume+" L/t",
                "Optimal Fuel Flow: ",
                (int)realOptFlow+" L/t",
                "Fuel: ",
                storedFluid+"L",
                "Speed: ",
                (mEfficiency/100)+"%",
                "Damage: ",
                tDura+"%",
                StatCollector.translateToLocal("GT5U.multiblock.problems") + ": ",
                "" + (getIdealStatus() - getRepairStatus())};
    }


}
