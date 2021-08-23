package gregtech.common.tileentities.machines.multi;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.isValidForLowGravity;

public class GT_MetaTileEntity_AdvancedProcessingArray extends GT_MetaTileEntity_MultiBlockBase {

    GT_Recipe mLastRecipe;

    boolean separateBusesMode = false, processFluidCells = false;

    public GT_MetaTileEntity_AdvancedProcessingArray(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_AdvancedProcessingArray(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_AdvancedProcessingArray(this.mName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Advanced Processing Array",
                "Is an improved version of basic processing Array",
                "Runs supplied machines as if placed in the world",
                "Size(WxHxD): 5x5x5 (Hollow), Controller (Front  Second Layer centered)",
                "1x Input Hatch/Bus (Any casing)",
                "1x Output Hatch/Bus (Any casing)",
                "1x Maintenance Hatch (Any casing)",
                "1x Energy Hatch (Any casing)",
                "Robust HSS-G Machine Casings for the rest (70 at least!)",
                "Place up to 64 Single Block GT Machines into the Controller Inventory",
                "Screwdriver rightclick to process all buses separately",
                "Screwdriver rightclick while sneaking enables fluid autocanning",
                "Right click with wire cutter to toggle recipe conflicts resolving"};
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] {
                    Textures.BlockIcons.casingTexturePages[1][50],
                    new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_PROCESSING_ARRAY_ACTIVE
                            : Textures.BlockIcons.OVERLAY_FRONT_PROCESSING_ARRAY) };
        }
        return new ITexture[] { Textures.BlockIcons.casingTexturePages[1][50] };
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, trans("gt.blockmachines." + mName + ".guiname", "Advanced P. A."), "AdvancedProcessingArray.png");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        if (mInventory[1] == null) return null;
        String tmp = mInventory[1].getUnlocalizedName().replaceAll("gt\\.blockmachines\\.basicmachine\\.", "");
        if (tmp.startsWith("centrifuge")) {
            return GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes;
        } else if (tmp.startsWith("electrolyzer")) {
            return GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes;
        } else if (tmp.startsWith("alloysmelter")) {
            return GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes;
        } else if (tmp.startsWith("assembler")) {
            return GT_Recipe.GT_Recipe_Map.sAssemblerRecipes;
        } else if (tmp.startsWith("compressor")) {
            return GT_Recipe.GT_Recipe_Map.sCompressorRecipes;
        } else if (tmp.startsWith("extractor")) {
            return GT_Recipe.GT_Recipe_Map.sExtractorRecipes;
        } else if (tmp.startsWith("macerator")) {
            return GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;
        } else if (tmp.startsWith("recycler")) {
            return GT_Recipe.GT_Recipe_Map.sRecyclerRecipes;
        } else if (tmp.startsWith("thermalcentrifuge")) {
            return GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes;
        } else if (tmp.startsWith("orewasher")) {
            return GT_Recipe.GT_Recipe_Map.sOreWasherRecipes;
        } else if (tmp.startsWith("chemicalreactor")) {
            return GT_Recipe.GT_Recipe_Map.sChemicalRecipes;
        } else if (tmp.startsWith("chemicalbath")) {
            return GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes;
        } else if (tmp.startsWith("electromagneticseparator")) {
            return GT_Recipe.GT_Recipe_Map.sElectroMagneticSeparatorRecipes;
        } else if (tmp.startsWith("autoclave")) {
            return GT_Recipe.GT_Recipe_Map.sAutoclaveRecipes;
        } else if (tmp.startsWith("mixer")) {
            return GT_Recipe.GT_Recipe_Map.sMixerRecipes;
        } else if (tmp.startsWith("hammer")) {
            return GT_Recipe.GT_Recipe_Map.sHammerRecipes;
        } else if (tmp.startsWith("sifter")) {
            return GT_Recipe.GT_Recipe_Map.sSifterRecipes;
        } else if (tmp.startsWith("extruder")) {
            return GT_Recipe.GT_Recipe_Map.sExtruderRecipes;
        } else if (tmp.startsWith("laserengraver")) {
            return GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes;
        } else if (tmp.startsWith("bender")) {
            return GT_Recipe.GT_Recipe_Map.sBenderRecipes;
        } else if (tmp.startsWith("wiremill")) {
            return GT_Recipe.GT_Recipe_Map.sWiremillRecipes;
        } else if (tmp.startsWith("arcfurnace")) {
            return GT_Recipe.GT_Recipe_Map.sArcFurnaceRecipes;
        } else if (tmp.startsWith("brewery")) {
            return GT_Recipe.GT_Recipe_Map.sBrewingRecipes;
        } else if (tmp.startsWith("canner")) {
            return GT_Recipe.GT_Recipe_Map.sCannerRecipes;
        } else if (tmp.startsWith("cutter")) {
            return GT_Recipe.GT_Recipe_Map.sCutterRecipes;
        } else if (tmp.startsWith("fermenter")) {
            return GT_Recipe.GT_Recipe_Map.sFermentingRecipes;
        } else if (tmp.startsWith("fluidextractor")) {
            return GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes;
        } else if (tmp.startsWith("fluidsolidifier")) {
            return GT_Recipe.GT_Recipe_Map.sFluidSolidficationRecipes;
        } else if (tmp.startsWith("lathe")) {
            return GT_Recipe.GT_Recipe_Map.sLatheRecipes;
        } else if (tmp.startsWith("boxinator")) {
            return GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes;
        } else if (tmp.startsWith("unboxinator")) {
            return GT_Recipe.GT_Recipe_Map.sUnboxinatorRecipes;
        } else if (tmp.startsWith("polarizer")) {
            return GT_Recipe.GT_Recipe_Map.sPolarizerRecipes;
        } else if(tmp.startsWith("press")){
            return GT_Recipe.GT_Recipe_Map.sPressRecipes;
        } else if (tmp.startsWith("plasmaarcfurnace")) {
            return GT_Recipe.GT_Recipe_Map.sPlasmaArcFurnaceRecipes;
        } else if (tmp.startsWith("printer")) {
            return GT_Recipe.GT_Recipe_Map.sPrinterRecipes;
        } else if (tmp.startsWith("press")) {
            return GT_Recipe.GT_Recipe_Map.sPressRecipes;
        } else if (tmp.startsWith("fluidcanner")) {
            return GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes;
        } else if (tmp.startsWith("fluidheater")) {
            return GT_Recipe.GT_Recipe_Map.sFluidHeaterRecipes;
        } else if (tmp.startsWith("distillery")) {
            return GT_Recipe.GT_Recipe_Map.sDistilleryRecipes;
        } else if (tmp.startsWith("slicer")) {
            return GT_Recipe.GT_Recipe_Map.sSlicerRecipes;
        } else if (tmp.startsWith("amplifier")) {
            return GT_Recipe.GT_Recipe_Map.sAmplifiers;
        } else if (tmp.startsWith("circuitassembler")) {
            return GT_Recipe.GT_Recipe_Map.sCircuitAssemblerRecipes;
        } else if (tmp.startsWith("filter")) {
            return GT_Recipe.GT_Recipe_Map.sFilterRecipes;            
        }
        return null;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        if (aStack != null && aStack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.")) {
            return true;
        }
        return false;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public String mMachine = "";
    public boolean checkRecipe(ItemStack aStack) {
        if (!isCorrectMachinePart(mInventory[1])) {
            return false;
        }
        GT_Recipe.GT_Recipe_Map map = getRecipeMap();
        if (map == null) return false;
        ArrayList<ItemStack> tInputList = getStoredInputs();
        int tTier = 0;
        if (mInventory[1].getUnlocalizedName().endsWith("1")) {
            tTier = 1;
        }else if (mInventory[1].getUnlocalizedName().endsWith("2")) {
            tTier = 2;
        }else if (mInventory[1].getUnlocalizedName().endsWith("3")) {
            tTier = 3;
        }else if (mInventory[1].getUnlocalizedName().endsWith("4")) {
            tTier = 4;
        }else if (mInventory[1].getUnlocalizedName().endsWith("5")) {
            tTier = 5;
        }else if (mInventory[1].getUnlocalizedName().endsWith("6")) {
            tTier = 6;
        }else if (mInventory[1].getUnlocalizedName().endsWith("7")) {
            tTier = 7;
        }else if (mInventory[1].getUnlocalizedName().endsWith("8")) {
            tTier = 8;
        }

        if(processFluidCells&&!(map.mUnlocalizedName.equals("gt.recipe.centrifuge")||map.mUnlocalizedName.equals("gt.recipe.electrolyzer")||map.mUnlocalizedName.equals("gt.recipe.mixer")))
            return false;

        if(!mMachine.equals(mInventory[1].getUnlocalizedName()))mLastRecipe=null;
        mMachine = mInventory[1].getUnlocalizedName();
        ItemStack[] tInputs = (ItemStack[]) tInputList.toArray(new ItemStack[tInputList.size()]);

        ArrayList<FluidStack> tFluidList = getStoredFluids();

        FluidStack[] tFluids = (FluidStack[]) tFluidList.toArray(new FluidStack[tFluidList.size()]);

        if (tInputList.size() > 0 || tFluids.length > 0) {
            GT_Recipe tRecipe = null;
            this.mEUt = 0;
            this.mOutputItems = null;
            this.mOutputFluids = null;
            int machines = Math.min(64, mInventory[1].stackSize);
            int i = 0;
            if(separateBusesMode){
                boolean tSucceed = false;
                a:for(GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses){
                    i = 0;
                    tInputs = tBus.mInventory;
                    tInputList = new ArrayList<>(Arrays.asList(tInputs));
                    tRecipe = findRecipe(map, mLastRecipe, tInputs, tFluids, gregtech.api.enums.GT_Values.V[tTier]);
                    if(tRecipe == null && processFluidCells){
                        for(FluidStack tFluid : tFluids){
                            if(tFluid.amount%1000!=0)
                                continue;
                            tInputList.add(GT_Utility.fillFluidContainer(tFluid, GT_ModHandler.getIC2Item("cell",tFluid.amount/1000),false,true));
                        }
                        int s = tInputList.size();
                        for(int q = 0; q < s; q++)
                            tInputList.add(GT_ModHandler.getIC2Item("cell", 64));
                        tInputs = (ItemStack[]) tInputList.toArray(new ItemStack[tInputList.size()]);
                        tRecipe = findRecipe(map, mLastRecipe, tInputs, tFluids, GT_Values.V[tTier]);
                        if(tRecipe==null||tRecipe.mOutputs.length>0&&GT_Utility.areStacksEqual(tRecipe.mOutputs[0],GT_ModHandler.getIC2Item("electrolyzedWaterCell", 1L),true))
                            continue a;
                    }
                    if (tRecipe!=null) {
                        if (GT_Mod.gregtechproxy.mLowGravProcessing && tRecipe.mSpecialValue == -100 && !isValidForLowGravity(tRecipe,getBaseMetaTileEntity().getWorld().provider.dimensionId))
                            continue a;
                        for (; i < machines; i++) {
                            if (processFluidCells ? !isRecipeInputEqualFluids(tRecipe,tInputs,tFluids,true) : !tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
                                if (i == 0) {
                                    continue a;
                                }
                                tSucceed = true;
                                break a;
                            }
                        }
                        tSucceed = true;
                        break a;
                    }
                }
                if(!tSucceed)
                    return false;
            }else {
                tRecipe = findRecipe(map, mLastRecipe, tInputs, tFluids, GT_Values.V[tTier]);
                if(tRecipe == null && processFluidCells){
                    for(FluidStack tFluid : tFluids){
                        if(tFluid.amount%1000!=0)
                            continue;
                        tInputList.add(GT_Utility.fillFluidContainer(tFluid, GT_ModHandler.getIC2Item("cell",tFluid.amount/1000),false,true));
                    }
                    int s = tInputList.size();
                    for(int q = 0; q < s; q++)
                        tInputList.add(GT_ModHandler.getIC2Item("cell", 64));
                    tInputs = (ItemStack[]) tInputList.toArray(new ItemStack[tInputList.size()]);
                    tRecipe = findRecipe(map, mLastRecipe, tInputs, tFluids, GT_Values.V[tTier]);
                    if(tRecipe==null||tRecipe.mOutputs.length>0&&GT_Utility.areStacksEqual(tRecipe.mOutputs[0],GT_ModHandler.getIC2Item("electrolyzedWaterCell", 1L),true))
                        return false;
                }
                if(tRecipe == null)
                    return false;
                if (GT_Mod.gregtechproxy.mLowGravProcessing && tRecipe.mSpecialValue == -100 && !isValidForLowGravity(tRecipe,getBaseMetaTileEntity().getWorld().provider.dimensionId))
                    return false;
                i = 0;
                for (; i < machines; i++) {
                    if (processFluidCells ? !isRecipeInputEqualFluids(tRecipe,tInputs,tFluids,true) : !tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {    if (i == 0) {
                        return false;
                    }
                        break;
                    }
                }
            }
            if(tRecipe == null)
                return false;

            mLastRecipe = tRecipe;

            this.mMaxProgresstime = tRecipe.mDuration;
            this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            if (tRecipe.mEUt <= 16) {
                this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
                this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
            } else {
                this.mEUt = tRecipe.mEUt;
                this.mMaxProgresstime = tRecipe.mDuration;
                while (this.mEUt <= V[tTier - 1] * map.mAmperage) {
                    this.mEUt *= 4;
                    this.mMaxProgresstime /= 2;
                }
            }
            this.mEUt *= i;
            if (this.mEUt > 0) {
                this.mEUt = (-this.mEUt);
            }
            ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
            for (int h = 0; h < tRecipe.mOutputs.length; h++) {
                if(tRecipe.getOutput(h)!=null){
                    tOut[h] = tRecipe.getOutput(h).copy();
                    tOut[h].stackSize = 0;
                }
            }
            FluidStack tFOut = null;
            if (tRecipe.getFluidOutput(0) != null)
                tFOut = tRecipe.getFluidOutput(0).copy();
            for (int f = 0; f < tOut.length; f++) {
                if (tRecipe.mOutputs[f] != null && tOut[f] != null) {
                    for (int g = 0; g < i; g++) {
                        if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(f))
                            tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                    }
                }
            }
            if (tFOut != null) {
                int tSize = tFOut.amount;
                tFOut.amount = tSize * i;
            }
            tOut = clean(tOut);
            this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
            List<ItemStack> overStacks = new ArrayList<ItemStack>();
            for (int f = 0; f < tOut.length; f++) {
                while (tOut[f].getMaxStackSize() < tOut[f].stackSize) {
                    if(tOut[f]!=null){
                        ItemStack tmp = tOut[f].copy();
                        tmp.stackSize = tmp.getMaxStackSize();
                        tOut[f].stackSize = tOut[f].stackSize - tOut[f].getMaxStackSize();
                        overStacks.add(tmp);}
                }
            }
            if (overStacks.size() > 0) {
                ItemStack[] tmp = new ItemStack[overStacks.size()];
                tmp = overStacks.toArray(tmp);
                tOut = ArrayUtils.addAll(tOut, tmp);
            }
            List<ItemStack> tSList = new ArrayList<ItemStack>();
            ArrayList<FluidStack> tFOuts = new ArrayList<>();
            tFOuts.add(tFOut);
            for (ItemStack tS : tOut) {//make convertation
                if(processFluidCells){
                    FluidStack tFluid = GT_Utility.getFluidForFilledItem(tS, true);
                    if(tFluid!=null) {
                        tFluid.amount = 1000*tS.stackSize;
                        tFOuts.add(tFluid);
                        continue;
                    }
                    if (GT_Utility.areStacksEqual(tS, GT_ModHandler.getIC2Item("cell", 1), true))
                        continue;
                }
                if (tS.stackSize > 0)
                    tSList.add(tS);
            }
            tOut = tSList.toArray(new ItemStack[tSList.size()]);
            this.mOutputItems = tOut;
            this.mOutputFluids = tFOuts.toArray(new FluidStack[tFOuts.size()]);
            updateSlots();
            return true;

        }
        return false;
    }

    public static ItemStack[] clean(final ItemStack[] v) {
        List<ItemStack> list = new ArrayList<ItemStack>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new ItemStack[list.size()]);
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX*2;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ*2;
        int tAmount = 0;
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                for (int h = -1; h < 4; h++) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                    if (i != 2 && i != -2 && j != 2 && j != -2) {
                        if(h == -1||h==3){
                            if ((!addMaintenanceToMachineList(tTileEntity, 178)) && (!addInputToMachineList(tTileEntity, 178)) && (!addOutputToMachineList(tTileEntity, 178)) && (!addEnergyInputToMachineList(tTileEntity, 178))) {
                                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings8) {
                                    return false;
                                }
                                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 2) {
                                    return false;
                                }
                                tAmount++;
                            }
                        }
                        else{
                            if (!aBaseMetaTileEntity.getAirOffset(xDir + i, h, zDir + j)) {
                                return false;
                            }
                        }
                    }
                    else {
                        if(!(i+xDir==0&&j+zDir==0&&h==0)){
                            if ((!addMaintenanceToMachineList(tTileEntity, 178)) && (!addInputToMachineList(tTileEntity, 178)) && (!addOutputToMachineList(tTileEntity, 178)) && (!addEnergyInputToMachineList(tTileEntity, 178))) {
                                if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings8) {
                                    return false;
                                }
                                if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 2) {
                                    return false;
                                }
                                tAmount++;
                            }
                        }
                    }

                }
            }
        }
        return tAmount >=70;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        if (aPlayer.isSneaking()){
            processFluidCells = !processFluidCells;
            if (processFluidCells)
                GT_Utility.sendChatToPlayer(aPlayer, "Fluid Autocanning Enabled");
            else
                GT_Utility.sendChatToPlayer(aPlayer, "Fluid Autocanning Disabled");
        }else {
            separateBusesMode = !separateBusesMode;
            if (separateBusesMode)

                GT_Utility.sendChatToPlayer(aPlayer, "Processing all buses separately");
            else
                GT_Utility.sendChatToPlayer(aPlayer, "Processing all buses together");
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setBoolean("processingMode",separateBusesMode);
        aNBT.setBoolean("autocanning",processFluidCells);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        separateBusesMode = aNBT.getBoolean("processingMode");
        processFluidCells = aNBT.getBoolean("autocanning");
    }

    public static boolean isRecipeInputEqualFluids(GT_Recipe aRecipe, ItemStack[] aInputs, FluidStack[] aFluidInputs, boolean aDecreaseStacksizeBySuccess){
        if (aFluidInputs.length > 0 && aInputs == null) return false;
        int amt;
        ArrayList<ItemStack> mInputs = new ArrayList<>(Arrays.asList(aRecipe.mInputs));
        Iterator<ItemStack> iterator = mInputs.iterator();
        while (iterator.hasNext()){
            ItemStack is = iterator.next();
            if(GT_Utility.areStacksEqual(GT_ModHandler.getIC2Item("cell",1),is, true)){
                iterator.remove();
            }
        }
        ArrayList<FluidStack> mFluids = new ArrayList<>(Arrays.asList(aRecipe.mFluidInputs));
        Iterator<ItemStack> itr = mInputs.iterator();
        while(itr.hasNext()){
            ItemStack is = itr.next();
            FluidStack tFluid = GT_Utility.getFluidForFilledItem(is,true);
            if(tFluid == null)
                continue;
            tFluid.amount = 1000*is.stackSize;
            mFluids.add(tFluid);
            itr.remove();


        }
        FluidStack[] mFluidInputs = mFluids.toArray(new FluidStack[mFluids.size()]);
        for (FluidStack tFluid : mFluidInputs)
            if (tFluid != null) {
                boolean temp = true;
                amt = tFluid.amount;
                for (FluidStack aFluid : aFluidInputs)
                    if (aFluid != null && aFluid.isFluidEqual(tFluid)) {
                        amt -= aFluid.amount;
                        if (amt < 1) {
                            temp = false;
                            break;
                        }
                    }
                if (temp) return false;
            }

        if (mInputs.size() > 0 && aInputs == null) return false;

        for (ItemStack tStack : mInputs) {
            if (tStack != null) {
                amt = tStack.stackSize;
                boolean temp = true;
                for (ItemStack aStack : aInputs) {
                    if ((GT_Utility.areUnificationsEqual(aStack, tStack, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, aStack), tStack, true))) {
                        amt -= aStack.stackSize;
                        if (amt < 1) {
                            temp = false;
                            break;
                        }
                    }
                }
                if (temp) return false;
            }
        }
        if (aDecreaseStacksizeBySuccess) {
            if (aFluidInputs != null) {
                for (FluidStack tFluid : mFluidInputs) {
                    if (tFluid != null) {
                        amt = tFluid.amount;
                        for (FluidStack aFluid : aFluidInputs) {
                            if (aFluid != null && aFluid.isFluidEqual(tFluid)) {
                                if (aFluid.amount < amt) {
                                    amt -= aFluid.amount;
                                    aFluid.amount = 0;
                                } else {
                                    aFluid.amount -= amt;
                                    amt = 0;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (aInputs != null) {
                for (ItemStack tStack : mInputs) {
                    if (tStack != null) {
                        amt = tStack.stackSize;
                        for (ItemStack aStack : aInputs) {
                            if ((GT_Utility.areUnificationsEqual(aStack, tStack, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, aStack), tStack, true))) {
                                if (aStack.stackSize < amt) {
                                    amt -= aStack.stackSize;
                                    aStack.stackSize = 0;
                                } else {
                                    aStack.stackSize -= amt;
                                    amt = 0;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }


    @Override
    protected boolean canHaveRecipeConflicts() {
        return true;
    }
}
