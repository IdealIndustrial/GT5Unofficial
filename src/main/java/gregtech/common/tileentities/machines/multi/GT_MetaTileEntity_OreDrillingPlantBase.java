package gregtech.common.tileentities.machines.multi;

import static gregtech.api.enums.GT_Values.VN;

import java.util.*;

import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.ItemData;
import gregtech.api.util.*;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_Item_Ores;
import gregtech.common.blocks.GT_TileEntity_Ores;
import gregtech.common.items.behaviors.Behaviour_DataOrb;
import ibxm.Player;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.objects.GT_ItemStack;

public abstract class GT_MetaTileEntity_OreDrillingPlantBase extends GT_MetaTileEntity_DrillerBase {

    private final ArrayList<ChunkPosition> oreBlockPositions = new ArrayList<>();

    public static int dFluidPerOperation; // an amount of drilling fluid that miner consumes each operation in normal mode

    // under-bedrock params:
    public static int dFluidUnderBrOnGetOre; // an amount of drilling fluid that miner consumes each operation in under-bedrock mode if found an ore
    public static int dFluidUnderBrOnGetStoneDust; // an amount of drilling fluid that miner consumes each operation in under-bedrock mode if not found an ore
    public static int closeRangeRadius;
    public static int middleRangeRadius;
    public static int closeRangeMoreInTimes; // a value to multiply count of blocks in close range to keep for under-bedrock mode
    public static int middleRangeMoreInTimes; // a value to multiply count of blocks in middle range to keep for under-bedrock mode
    public static int eachTierPercentChanceDoubleDrop; // a chance between 0 and 100 to make an output double, depends on miner tier
    public static int consumeMiningPipeAfterCycles; // after each count of cycles miner will consume 1 mining pipe
    public static int underBrWorkTimeMoreInTimes; // decrease in times value of working speed for under-bedrock mode
    public static int oreFactor; // the bigger value will more reduce chance to get an ore
    boolean isCloseRangeCycle = false; // multiply ore blocks if ore is near in under-bedrock mode
    boolean isMiddleRangeCycle = false; // multiply ore blocks if ore not far near in under-bedrock mode
    DebugStatistic dbg = new DebugStatistic(); // TODO: just to ballance new under-bedrock logic - that it should be deleted ! ! !

    // next 3 variables will be init after call "prepareUnderBedrockOres"
    int currentOreFactor;
    int totalMinedOresCount;
    byte moreCyclesTimes = 1;
    boolean isFoundOreUnderBr = false; // it used to control drilling fluid consuming
    short underBrOperationsCount = 0; // it used to consume mining pipes in under-bedrock mode
    float operationsCountPerFiveMin = -1f;
    ArrayList<OreHash> oreHashes; // it used to keep ore blocks that miner can find under bedrock

    private static String underBrOresFlowLabel1 = EnumChatFormatting.DARK_GREEN + GT_LanguageManager.addStringLocalization("underBrOresFlowLabel1",
            "Info about middle flow based on mined ores count") + EnumChatFormatting.RESET;
    private static String underBrOresFlowLabel2 = EnumChatFormatting.DARK_GREEN + GT_LanguageManager.addStringLocalization("underBrOresFlowLabel2",
            "It is actual only for under-bedrock mining mode") + EnumChatFormatting.RESET;
    private static String underBrOresFlowLabel3 = EnumChatFormatting.DARK_GREEN + GT_LanguageManager.addStringLocalization("underBrOresFlowLabel3",
            "Data for first tier miner with MV energy hatch") + EnumChatFormatting.RESET;
    private static String underBrOresFlowLabel4 = EnumChatFormatting.DARK_GREEN + GT_LanguageManager.addStringLocalization("underBrOresFlowLabel4",
            "Middle amount of ore blocks mining each hour:") + EnumChatFormatting.RESET;

    class OreHash{
        OreHash(ItemStack itst, int i, int a){
            this.itemStack = itst;
            this.min = i;
            this.max = a;
        }
        int min, max;
        ItemStack itemStack;
        ItemStack tryGetItem(int val) {
            return this.min < val && this.max > val ? this.itemStack : null;
        }
    }

    class DebugStatistic{ // TODO: this class is only to debug, and after ballance finish, it should be removed
        int smallOresCount = 0;
        int oresFromVeinCount = 0;
        int underBrOresCount = 0;
        long startTime = 0;
        long workingTime = 0;
        long prevLogTime = 0;
        long finishNormalModeTime = 0;
        float veinOresEachFiveMinutes = 0;
        float smallOresEachFiveMinutes = 0;
        float underBrOresEachFiveMinutes = 0;
        boolean wasSmallOre = false;

        void init(){
            if(startTime != 0) return;
            startTime = System.currentTimeMillis();
            this.prevLogTime = startTime;
            log("Start to write statistic.");
        }
        void addOre(){
            this.workingTime = (System.currentTimeMillis() - this.startTime) / 1000;
            if(finishNormalModeTime > 0) {
                underBrOresCount++;
            } else {
                if(wasSmallOre) this.smallOresCount++;
                else oresFromVeinCount++;
            }
            if(this.prevLogTime < this.workingTime - 300) {
                this.prevLogTime = this.workingTime;
                log(calcStatisticData());
            }
        }
        void enableUnderBrMode(){
            if(finishNormalModeTime > 0) return;
            this.workingTime = (System.currentTimeMillis() - this.startTime) / 1000;
            finishNormalModeTime = this.workingTime;
            log("Seconds left from start to finish mining in normal mode " + finishNormalModeTime);
            log("Under-bedrock mode Enabled.");
        }
        String[] calcStatisticData(){
            smallOresEachFiveMinutes = roundToTwoDecimals((float)smallOresCount / (float)workingTime);
            veinOresEachFiveMinutes = roundToTwoDecimals((float)oresFromVeinCount / (float)workingTime);
            underBrOresEachFiveMinutes = roundToTwoDecimals((float)underBrOresCount / (float)workingTime);
            return new String[]{
                    "Working Time: "+workingTime,
                    "Small Ores Count "+smallOresCount,
                    "Ores From Vein Count "+oresFromVeinCount,
                    "Under Bedrock Ores Count"+underBrOresCount,
                    "Small Ores Each Five Minutes"+smallOresEachFiveMinutes,
                    "veinOresEachFiveMinutes"+veinOresEachFiveMinutes,
                    "underBrOresEachFiveMinutes"+underBrOresEachFiveMinutes,
            };
        }
        float roundToTwoDecimals(float n) {
            return Math.round(n*100)/100f;
        }
        void log(String msg){
            GT_Utility.logToChat(msg);
        }
        void log(String[] msg){
            GT_Utility.logToChat(msg);
        }
    }

    @Override
    public void onConfigLoad(GT_Config aConfig) {
        super.onConfigLoad(aConfig);
        dFluidPerOperation = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.dFluidPerOperation", 2000);
        dFluidUnderBrOnGetOre = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.dFluidUnderBrOnGetOre", 4000);
        dFluidUnderBrOnGetStoneDust = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.dFluidUnderBrOnGetStoneDust", 200);
        consumeMiningPipeAfterCycles = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.consumeMiningPipeAfterCycles", 1000);
        closeRangeRadius = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.closeRangeRadius", 16);
        middleRangeRadius = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.middleRangeRadius", 32);
        eachTierPercentChanceDoubleDrop = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.eachTierPercentChanceDoubleDrop", 10);
        oreFactor = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.oreFactor", 5000);
        closeRangeMoreInTimes = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.closeRangeMoreInTimes", 4);
        middleRangeMoreInTimes = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.middleRangeMoreInTimes", 2);
        underBrWorkTimeMoreInTimes = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.underBrWorkTimeMoreInTimes", 8);
    }

    public GT_MetaTileEntity_OreDrillingPlantBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_OreDrillingPlantBase(String aName) {
        super(aName);
    }


    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setShort("underBrOperationsCount", underBrOperationsCount);
    }
    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        underBrOperationsCount = aNBT.getShort("underBrOperationsCount");
    }

    private String prepareDataOrbTitle(IGregTechTileEntity te){
        return EnumChatFormatting.DARK_GREEN + te.getWorld().provider.getDimensionName() + EnumChatFormatting.RESET
                + EnumChatFormatting.GOLD + " (x " + te.getXCoord() + ", y " + te.getYCoord() + ", z " + te.getZCoord()
                + EnumChatFormatting.RESET + ")";
    }
    private String prepareMainOresInfo(int totalBlocksCount, short oreTypesCount){
        String result = EnumChatFormatting.DARK_PURPLE + "Ore blocks count " + totalBlocksCount + ". Ore types count "
                + oreTypesCount+ EnumChatFormatting.RESET;
        return result;
    }

    private void updateDataOrbNbt(GT_ItemStack gtStack){
        ArrayList<ItemStack> orbs = getDataItems(4);
        boolean foundOneOrb = false;
        for(ItemStack orb : orbs){
            if(!foundOneOrb  && orbs.get(0).stackSize == 1) {
                Map<GT_ItemStack, Integer> gtStackMap = new HashMap<>();
                foundOneOrb = true;
                NBTTagCompound orbNbt = orb.getTagCompound();
                if(orbNbt == null) {
                    orbNbt = new NBTTagCompound();
                }
                IGregTechTileEntity te = getBaseMetaTileEntity();
                if (te.getXCoord() == orbNbt.getInteger("coordX")
                        && te.getYCoord() == orbNbt.getInteger("coordY")
                        && te.getZCoord() == orbNbt.getInteger("coordZ")
                        && te.getWorld().provider.dimensionId == orbNbt.getInteger("dimensionId")) {
                    int[] blocksCounts = orbNbt.getIntArray("blocksCounts");
                    if(blocksCounts != null){
                        for (int i = 0; i < blocksCounts.length; i++) {
                            GT_ItemStack savedGtStack = new GT_ItemStack(ItemStack.loadItemStackFromNBT(orbNbt.getCompoundTag("minerOre"+i)));
                            gtStackMap.put(savedGtStack, blocksCounts[i]);
                        }
                    }
                } else {
                    orbNbt = new NBTTagCompound(); // if you place a controller block in other position it will reset all data about ores.
                }
                gtStackMap.putIfAbsent(gtStack, 0);
                int blocksCount = gtStack.mStackSize;
                if(isCloseRangeCycle) blocksCount *= closeRangeMoreInTimes;
                else if (isMiddleRangeCycle) blocksCount *= middleRangeMoreInTimes;
                gtStackMap.put(gtStack, gtStackMap.get(gtStack) + blocksCount);
                saveDataToOrb(orb, orbNbt, gtStackMap);
            }
        }
    }

    private void saveDataToOrb(ItemStack orb, NBTTagCompound orbNbt, Map<GT_ItemStack, Integer> gtStackMap){
        IGregTechTileEntity te = getBaseMetaTileEntity();
        orbNbt.setInteger("coordX", te.getXCoord());
        orbNbt.setInteger("coordY", te.getYCoord());
        orbNbt.setInteger("coordZ", te.getZCoord());
        orbNbt.setInteger("dimensionId", te.getWorld().provider.dimensionId);

        int[] blocksCounts = new int[gtStackMap.keySet().size()];
        int currentIdx = 0;
        int totalBlocksCount = 0;
        short oreTypesCount = (short)gtStackMap.keySet().size();
        byte oresPerLine = 3;
        ArrayList<String> foundOresNames = new ArrayList<>();
        String[] lines = new String[4+(byte)Math.ceil((float)oreTypesCount / (float)oresPerLine)];
        lines[0] = underBrOresFlowLabel1;
        lines[1] = underBrOresFlowLabel2;
        lines[2] = underBrOresFlowLabel3;
        lines[3] = underBrOresFlowLabel4;
        for(GT_ItemStack gts : gtStackMap.keySet()){
            ItemStack itst = gts.toStack();
            foundOresNames.add(itst.getDisplayName());
            blocksCounts[currentIdx] = gtStackMap.get(gts);
            totalBlocksCount += blocksCounts[currentIdx];
            NBTTagCompound sNbt = new NBTTagCompound();
            itst.writeToNBT(sNbt);
            orbNbt.setTag("minerOre"+currentIdx, sNbt);
            byte subTitleIdx = (byte)(4+((byte)(currentIdx / oresPerLine)));
            lines[subTitleIdx] = (lines[subTitleIdx] != null ? lines[subTitleIdx] + " " : "") +(itst.getDisplayName()
                    + ": " + EnumChatFormatting.AQUA + calcOreFlow(blocksCounts[currentIdx]) + EnumChatFormatting.RESET);
            currentIdx++;
        }
        orbNbt.setIntArray("blocksCounts", blocksCounts);
        orbNbt.setString("mDataTitle", prepareDataOrbTitle(te));
        orbNbt.setString("mDataName", prepareMainOresInfo(totalBlocksCount, oreTypesCount)); // gtStackMap .getDisplayName()
        orb.setTagCompound(orbNbt);
        Behaviour_DataOrb.setSubTitleLines(orb, lines);
        //GT_Utility.logToChat();
    }

    private String calcOreFlow(int blocksCount){
        float chanceToGet = (float)blocksCount / (float)oreFactor;
        return ""+ (Math.round(1000f * (chanceToGet / getUnderBrOperationsCountPerFiveMin())) / 1000f);
    }

    float getUnderBrOperationsCountPerFiveMin(){
        if(operationsCountPerFiveMin > 0) return operationsCountPerFiveMin;
        float hourInTicks = 20*60*60;
        operationsCountPerFiveMin = hourInTicks / (960f * underBrWorkTimeMoreInTimes);
        return operationsCountPerFiveMin;
    }

    private ItemStack tryMineUnderBedrock() {
        ArrayList<ItemStack> orbs = getDataItems(4);
        boolean foundOneOrb = false;
        ItemStack result = null;
        for (ItemStack orb : orbs) {
            if (!foundOneOrb && orbs.get(0).stackSize == 1) {
                foundOneOrb = true;
                IGregTechTileEntity te = getBaseMetaTileEntity();
                NBTTagCompound orbNbt = orb.getTagCompound();
                if (te.getXCoord() == orbNbt.getInteger("coordX")
                        && te.getYCoord() == orbNbt.getInteger("coordY")
                        && te.getZCoord() == orbNbt.getInteger("coordZ")
                        && te.getWorld().provider.dimensionId == orbNbt.getInteger("dimensionId")) {
                    if(oreHashes == null) {
                        prepareUnderBedrockOres(orbNbt);
                    }
                    result = getRandomUnderBedrockOre(orbNbt);
                }
            }
        }
        return result;
    }

    private void prepareUnderBedrockOres(NBTTagCompound orbNbt){
        int[] blocksCounts = orbNbt.getIntArray("blocksCounts");
        int currentVal = 1;
        oreHashes = new ArrayList<>();
        if(blocksCounts != null){
            for (int i = 0; i < blocksCounts.length; i++) {
                ItemStack aStack = ItemStack.loadItemStackFromNBT(orbNbt.getCompoundTag("minerOre"+i));
                aStack.stackSize = 1; // each stack here is single item ore block;
                oreHashes.add(new OreHash(aStack, currentVal, blocksCounts[i] + currentVal - 1));
                currentVal += blocksCounts[i];
                totalMinedOresCount = currentVal-1;
            }
        }
        currentOreFactor = totalMinedOresCount;
        if(currentOreFactor > oreFactor) {
            moreCyclesTimes = (byte)Math.ceil((float)currentOreFactor / (float)oreFactor);
            currentOreFactor = oreFactor * moreCyclesTimes;
        } else {
            currentOreFactor = oreFactor;
        }
    }

    private ItemStack getRandomUnderBedrockOre(NBTTagCompound orbNbt){
        ItemStack output = null;
        isFoundOreUnderBr = true; // but it will set to false if not found
        int randVal = getBaseMetaTileEntity().getRandomNumber(currentOreFactor);
        for(OreHash hash : oreHashes){
            if(output == null) output = hash.tryGetItem(randVal);
        }
        if(output == null) {
            isFoundOreUnderBr = false;
            output = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L);
        }
        if(moreCyclesTimes > 1) {
            output.stackSize *= moreCyclesTimes;
        }
        if(getMinTier() > 2 && eachTierPercentChanceDoubleDrop > 0) {
            // if standard chance value is 10, for 1 tier miner - 0% chance, and for 4 tier - 30%
            if((eachTierPercentChanceDoubleDrop * (getMinTier()-2)) >= getBaseMetaTileEntity().getRandomNumber(100)){
                output.stackSize *= 2; // if random is success, it will set a double amount of ore blocks;
            }
        }
        if(isFoundOreUnderBr) dbg.addOre();
        return output;
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "OreDrillingPlant.png");
    }

    @Override
    protected boolean workingDownward(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
        dbg.init();
    	if (yHead != oldYHead) oreBlockPositions.clear();
        fillMineListIfEmpty(xDrill, yDrill, zDrill, xPipe, zPipe, yHead);
        if (oreBlockPositions.isEmpty()) {
            byte LowerPipeResult = (byte)tryLowerPipe();
        	switch (LowerPipeResult) {
                case 2: mMaxProgresstime = 0; return false;
                case 3: workState = STATE_UPWARD; return true;
                case 1: workState = STATE_AT_BOTTOM; return true;
        	}
            //new layer - fill again
            if(workState == STATE_DOWNWARD) {
                fillMineListIfEmpty(xDrill, yDrill, zDrill, xPipe, zPipe, yHead);
            }
        }

        ChunkPosition oreBlockPos = null;
        Block oreBlock = null;
        IGregTechTileEntity te = getBaseMetaTileEntity();

        if (!tryConsumeDrillingFluid(dFluidPerOperation)) {
            return false;
        }
        while ((oreBlock == null || oreBlock == Blocks.air) && !oreBlockPositions.isEmpty()) {
            oreBlockPos = oreBlockPositions.remove(0);
            if (GT_Utility.eraseBlockByFakePlayer(getFakePlayer(te), oreBlockPos.chunkPosX, oreBlockPos.chunkPosY, oreBlockPos.chunkPosZ, true))
            	oreBlock = te.getBlock(oreBlockPos.chunkPosX, oreBlockPos.chunkPosY, oreBlockPos.chunkPosZ);
            if(oreBlock != null && (closeRangeRadius > 0 || middleRangeRadius > 0)) {
                isCloseRangeCycle = Math.abs(oreBlockPos.chunkPosX - xPipe) <= closeRangeRadius && Math.abs(oreBlockPos.chunkPosZ - zPipe) <= closeRangeRadius;
                if(!isCloseRangeCycle) {
                    isMiddleRangeCycle = Math.abs(oreBlockPos.chunkPosX - xPipe) <= middleRangeRadius && Math.abs(oreBlockPos.chunkPosZ - zPipe) <= middleRangeRadius;
                }
            }
        }

        if (oreBlock != null && oreBlock != Blocks.air) {
            dbg.wasSmallOre = true;
            ArrayList<ItemStack> oreBlockDrops = getBlockDrops(oreBlock, oreBlockPos.chunkPosX, oreBlockPos.chunkPosY, oreBlockPos.chunkPosZ);
            saveMinedOreBlocks((ArrayList<ItemStack>)oreBlockDrops.clone());
            dbg.addOre();
            getBaseMetaTileEntity().getWorld().setBlockToAir(oreBlockPos.chunkPosX, oreBlockPos.chunkPosY, oreBlockPos.chunkPosZ);
            mOutputItems = getOutputByDrops(oreBlockDrops);
        }
        return true;
    }

    @Override
    protected boolean workingAtBottom(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
        if (reachingVoidOrBedrock()) {
            dbg.enableUnderBrMode();
            ItemStack output = tryMineUnderBedrock();
            if(output == null) {
                workState = STATE_UPWARD;
                return true;
            }
            if(!consumeExpendableMaterials()){
                mMaxProgresstime = 0; return false;
            }
            ArrayList<ItemStack> outStacks = new ArrayList<>();
            outStacks.add(output);
            mOutputItems = getOutputByDrops(outStacks);
        }
        return true;
    }

    private boolean consumeExpendableMaterials(){
        boolean isSuccess;
        if(isHasMiningPipes()) {
            if(consumeMiningPipeAfterCycles <= underBrOperationsCount) {
                getBaseMetaTileEntity().decrStackSize(1, 1);
                underBrOperationsCount = 0;
            }
            isSuccess = tryConsumeDrillingFluid(isFoundOreUnderBr ? dFluidUnderBrOnGetOre : dFluidUnderBrOnGetStoneDust);
        } else {
            isSuccess = false;
        }
        if(isSuccess) underBrOperationsCount++;
        return isSuccess;
    }

    private void saveMinedOreBlocks(ArrayList<ItemStack> oreBlockDrops) {
        for(ItemStack itst : oreBlockDrops){
            if(itst.getItem() instanceof ItemBlock){
                dbg.wasSmallOre = false;
                updateDataOrbNbt(new GT_ItemStack(itst));
            }
        }
    }

    @Override
    protected boolean checkHatches(){
    	return !mMaintenanceHatches.isEmpty() && !mInputHatches.isEmpty() && !mOutputBusses.isEmpty() && !mEnergyHatches.isEmpty();
    }
    
    @Override
    protected void setElectricityStats() {
        mEfficiency = getCurrentEfficiency(null);
        mEfficiencyIncrease = 10000;
        int tier = Math.max(1, GT_Utility.getTier(getMaxInputVoltage()));
        mEUt = -3 * (1 << (tier << 1));
        short mProgTime;
        if(workState == STATE_DOWNWARD) {
            mProgTime = (short)getBaseProgressTime();
        } else if (workState == STATE_AT_BOTTOM){
            mProgTime = (short)(getBaseProgressTime() * underBrWorkTimeMoreInTimes);
        } else {
            mProgTime = 80;
        }
        mMaxProgresstime = mProgTime / (1 <<tier);
        mMaxProgresstime = Math.max(1, mMaxProgresstime);
    }

    private ItemStack[] getOutputByDrops(ArrayList<ItemStack> oreBlockDrops) {
        long voltage = getMaxInputVoltage();
        ArrayList<ItemStack> outputItems = new ArrayList<>();
        while (!oreBlockDrops.isEmpty()) {
            ItemStack currentItem = oreBlockDrops.remove(0).copy();
            if (!doUseMaceratorRecipe(currentItem)) {
                multiplyStackSize(currentItem);
                outputItems.add(currentItem);
                continue;
            }

            GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.findRecipe(getBaseMetaTileEntity(), false, voltage, null, currentItem);
            if (tRecipe == null) {
                outputItems.add(currentItem);
                continue;
            }

            for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                ItemStack recipeOutput = tRecipe.mOutputs[i].copy();
                if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(i))
                    multiplyStackSize(recipeOutput);
                outputItems.add(recipeOutput);
            }
        }
        return outputItems.toArray(new ItemStack[0]);
    }

    private boolean doUseMaceratorRecipe(ItemStack currentItem) {
        ItemData itemData = GT_OreDictUnificator.getItemData(currentItem);
        return itemData == null
                || itemData.mPrefix != OrePrefixes.crushed
                && itemData.mPrefix != OrePrefixes.dustImpure
                && itemData.mPrefix != OrePrefixes.dust
                && itemData.mMaterial.mMaterial != Materials.Oilsands;
    }

    private void multiplyStackSize(ItemStack itemStack) {
        itemStack.stackSize *= getBaseMetaTileEntity().getRandomNumber(4) + 1;
    }

    private ArrayList<ItemStack> getBlockDrops(final Block oreBlock, int posX, int posY, int posZ) {
        final int blockMeta = getBaseMetaTileEntity().getMetaID(posX, posY, posZ);
        if (oreBlock.canSilkHarvest(getBaseMetaTileEntity().getWorld(), null, posX, posY, posZ, blockMeta)) {
            return new ArrayList<ItemStack>() {{
                add(new ItemStack(oreBlock, 1, blockMeta));
            }};
        } else return oreBlock.getDrops(getBaseMetaTileEntity().getWorld(), posX, posY, posZ, blockMeta, 1);
    }

    private boolean tryConsumeDrillingFluid(int fAmount) {
        if (!depleteInput(new FluidStack(ItemList.sDrillingFluid, fAmount))) {
        	mMaxProgresstime = 0;
        	return false;
        }
        return true;
    }

    private void fillMineListIfEmpty(int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead) {
        if (!oreBlockPositions.isEmpty()) return;

        tryAddOreBlockToMineList(xPipe, yHead - 1, zPipe);
        if (yHead == yDrill) return; //skip controller block layer

        int radius = getRadiusInChunks() << 4;
        for (int xOff = -radius; xOff <= radius; xOff++)
            for (int zOff = -radius; zOff <= radius; zOff++)
                tryAddOreBlockToMineList(xDrill + xOff, yHead, zDrill + zOff);
    }

    private void tryAddOreBlockToMineList(int x, int y, int z) {
        Block block = getBaseMetaTileEntity().getBlock(x, y, z);
        int blockMeta = getBaseMetaTileEntity().getMetaID(x, y, z);
        ChunkPosition blockPos = new ChunkPosition(x, y, z);
        if (oreBlockPositions.contains(blockPos)) return;
        if (block instanceof GT_Block_Ores_Abstract) {
            TileEntity tTileEntity = getBaseMetaTileEntity().getTileEntity(x, y, z);
            if (tTileEntity != null && tTileEntity instanceof GT_TileEntity_Ores && ((GT_TileEntity_Ores) tTileEntity).mNatural)
                oreBlockPositions.add(blockPos);
        } else {
            ItemData association = GT_OreDictUnificator.getAssociation(new ItemStack(block, 1, blockMeta));
            if (association != null && association.mPrefix.toString().startsWith("ore"))
                oreBlockPositions.add(blockPos);
        }
    }

    protected abstract int getRadiusInChunks();

    protected abstract int getBaseProgressTime();

    protected String[] getDescriptionInternal(String tierSuffix) {
        String casings = getCasingBlockItem().get(0).getDisplayName();
        return new String[]{
                "Controller Block for the Ore Drilling Plant " + (tierSuffix != null ? tierSuffix : ""),
                "Size(WxHxD): 3x7x3, Controller (Front middle bottom)",
                "3x1x3 Base of " + casings,
                "1x3x1 " + casings + " pillar (Center of base)",
                "1x3x1 " + getFrameMaterial().mName + " Frame Boxes (Each pillar side and on top)",
                "1x Input Hatch for drilling fluid (Any bottom layer casing)",
                "1x Input Bus for mining pipes (Any bottom layer casing; not necessary)",
                "1x Output Bus (Any bottom layer casing)",
                "1x Maintenance Hatch (Any bottom layer casing)",
                "1x " + VN[getMinTier()] + "+ Energy Hatch (Any bottom layer casing)",
                "Radius is " + (getRadiusInChunks() << 4) + " blocks"};
    }
}