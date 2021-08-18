package gregtech.common.tileentities.machines.multi;

import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.lang.Math;

import static gregtech.api.enums.GT_Values.VN;
import static gregtech.common.GT_UndergroundOil.undergroundOil;
import static gregtech.common.GT_UndergroundOil.undergroundOilReadInformation;

import net.minecraft.util.EnumChatFormatting;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import static gregtech.api.enums.GT_Values.VN;
import net.minecraft.util.StatCollector;

public abstract class GT_MetaTileEntity_OilDrillBase extends GT_MetaTileEntity_DrillerBase {

    private boolean completedCycle = false;

    private ArrayList<Chunk> mOilFieldChunks = new ArrayList<Chunk>();
    private int mOilId = 0;

    public GT_MetaTileEntity_OilDrillBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_OilDrillBase(String aName) {
        super(aName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mOilId", mOilId);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mOilId = aNBT.getInteger("mOilId");
    }

    protected String[] getDescriptionInternal(String tierSuffix) {
        String casings = getCasingBlockItem().get(0).getDisplayName();
        return new String[]{
                "Controller Block for the Oil Drilling Rig " + (tierSuffix != null ? tierSuffix : ""),
                "Size(WxHxD): 3x7x3", "Controller (Front middle at bottom)",
                "3x1x3 Base of " + casings,
                "1x3x1 " + casings + " pillar (Center of base)",
                "1x3x1 " + getFrameMaterial().mName + " Frame Boxes (Each pillar side and on top)",
                "1x Output Hatch (One of base casings)",
                "1x Maintenance Hatch (One of base casings)",
                "1x " + VN[getMinTier()] + "+ Energy Hatch (Any bottom layer casing)",
                "Pumps "+50*Math.pow(3,getMinTier()-2)+"% of nominal volume per cycle ("+80/Math.pow(2,(getMinTier()-2))+" ticks at a base voltage).",
                "Working on " + getRangeInChunks() + " * " + getRangeInChunks() + " chunks",
                "Use Programmed Circuits to ignore near exhausted oil field"};
    }


    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "DrillingRig.png");
    }

    protected int getRangeInChunks(){
        return 0;
    }

    @Override
    protected boolean checkHatches() {
        return !mMaintenanceHatches.isEmpty() && !mOutputHatches.isEmpty() && !mEnergyHatches.isEmpty();
    }

    @Override
    protected void setElectricityStats() {
        this.mEfficiency = getCurrentEfficiency(null);
        this.mEfficiencyIncrease = 10000;
        int tier = Math.max(1, GT_Utility.getTier(getMaxInputVoltage()));
        this.mEUt = -3 * (1 << (tier << 1));
        this.mMaxProgresstime = (workState == STATE_AT_BOTTOM ? 320 : 80) / (1 << tier);
        this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
    }

    @Override
    protected boolean workingAtBottom(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
        switch (tryLowerPipe(true)) {
            case 0: workState = STATE_DOWNWARD; setElectricityStats(); return true;
            case 3: workState = STATE_UPWARD; return true;
        }
        
        if (reachingVoidOrBedrock() && tryFillChunkList()) {
            float speed = 1f/getMinTier()/(getMinTier()>2?2:1);
            FluidStack tFluid = pumpOil(speed);
            if (tFluid != null && tFluid.amount > getTotalConfigValue()){
                this.mOutputFluids = new FluidStack[]{tFluid};
                return true;
            }
        }
        workState = STATE_UPWARD;
        return true;
    }

    private boolean tryFillChunkList(){
        FluidStack tFluid, tOil;
        if (mOilId <= 0) {
            tFluid = undergroundOilReadInformation(getBaseMetaTileEntity());
            if (tFluid == null) return false;
            mOilId = tFluid.getFluidID();
        }
        
        tOil = new FluidStack(FluidRegistry.getFluid(mOilId), 0);

        if (mOilFieldChunks.isEmpty()) {
            Chunk tChunk = getBaseMetaTileEntity().getWorld().getChunkFromBlockCoords(getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getZCoord());
            int range = getRangeInChunks();
            int xChunk = Math.floorDiv(tChunk.xPosition,range) * range;
            int zChunk = Math.floorDiv(tChunk.zPosition,range) * range;
            
            for (int i = 0; i < range; i++) {
                for (int j = 0; j < range; j++) {
                    tChunk = getBaseMetaTileEntity().getWorld().getChunkFromChunkCoords(xChunk + i, zChunk + j);
                    tFluid = undergroundOilReadInformation(tChunk);
                    if (tOil.isFluidEqual(tFluid) && tFluid.amount > 0) {
                        mOilFieldChunks.add(tChunk);
                    }
                }
            }
		}
        return !mOilFieldChunks.isEmpty();		
    }

    private FluidStack pumpOil(float speed) {
        if (mOilId <= 0) return null;
        
        FluidStack tFluid, tOil;
        tOil = new FluidStack(FluidRegistry.getFluid(mOilId), 0);
        ArrayList<Chunk> emptyChunks = new ArrayList<Chunk>();
        
        for (Chunk tChunk : mOilFieldChunks) {
            tFluid = undergroundOil(tChunk, speed);
            if (tFluid == null || tFluid.amount<1) emptyChunks.add(tChunk);
            if (tOil.isFluidEqual(tFluid)) tOil.amount += tFluid.amount;
        }
        
        for(Chunk tChunk : emptyChunks) {
            mOilFieldChunks.remove(tChunk);
        }
        
        return tOil.amount == 0 ? null : tOil;
    }
    @Override
    public String[] getInfoData() {

    long storedEnergy=0;
    long maxEnergy=0;
    for(GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
        if (isValidMetaTileEntity(tHatch)) {
            storedEnergy+=tHatch.getBaseMetaTileEntity().getStoredEU();
            maxEnergy+=tHatch.getBaseMetaTileEntity().getEUCapacity();
        }
    }

    return new String[]{
    		StatCollector.translateToLocal("GT5U.multiblock.Progress")+": " +EnumChatFormatting.GREEN + Integer.toString(mProgresstime/20) + EnumChatFormatting.RESET +" s / "+
		EnumChatFormatting.YELLOW + Integer.toString(mMaxProgresstime/20) + EnumChatFormatting.RESET +" s",
		StatCollector.translateToLocal("GT5U.multiblock.energy")+": " +EnumChatFormatting.GREEN + Long.toString(storedEnergy) + EnumChatFormatting.RESET +" EU / "+
		EnumChatFormatting.YELLOW + Long.toString(maxEnergy) + EnumChatFormatting.RESET +" EU",
		StatCollector.translateToLocal("GT5U.multiblock.usage")+": "+EnumChatFormatting.RED + Integer.toString(-mEUt) + EnumChatFormatting.RESET + " EU/t"+" "+EnumChatFormatting.YELLOW+VN[GT_Utility.getTier(getMaxInputVoltage())]+ EnumChatFormatting.RESET,
		StatCollector.translateToLocal("GT5U.multiblock.problems")+": "+
		EnumChatFormatting.RED+ (getIdealStatus() - getRepairStatus())+EnumChatFormatting.RESET+
		" "+StatCollector.translateToLocal("GT5U.multiblock.efficiency")+": "+
		EnumChatFormatting.YELLOW+Float.toString(mEfficiency / 100.0F)+EnumChatFormatting.RESET + " %"
		};
	}
}
