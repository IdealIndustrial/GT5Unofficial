package gregtech.common.tileentities.machines.multi;

import static gregtech.api.enums.GT_Values.VN;

import java.util.*;

import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.gui.GT_GUIContainer_DrillerBase;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.ItemData;
import gregtech.api.util.*;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.objects.GT_ItemStack;

public abstract class GT_MetaTileEntity_OreDrillingPlantBase extends GT_MetaTileEntity_DrillerBase {

    private final ArrayList<ChunkPosition> oreBlockPositions = new ArrayList<>();

    public static int dFluidPerOperation; // an amount of drilling fluid that miner consumes each operation in normal mode

    // under-bedrock params:
    public static int dFluidUnderBredrock; // an amount of drilling fluid that miner consumes each operation in under-bedrock mode if found an ore
    public static int consumeMiningPipeAfterCycles; // after each count of cycles miner will consume 1 mining pipe
    public static int optimizationRate; // decrease in times value of working speed for under-bedrock mode
    public static int[] origOreFactors; // the bigger value will more reduce chance to get an ore
    public int oreFactor; // the bigger value will more reduce chance to get an ore

    int currentOreFactor;
    int totalMinedOresCount;
    int moreCyclesTimes = 1;

    boolean isAllowPutPipesToController = false;
    int underBrOperationsCount = 0; // it used to consume mining pipes in under-bedrock mode
    float operationsCountPerCalcPeriod = -1f;
    ArrayList<OreHash> oreHashes; // it used to keep ore blocks that miner can find under bedrock

    private static final String underBrOresFlowLabel1 = EnumChatFormatting.GRAY + GT_LanguageManager.addStringLocalization("underBrOresFlowLabel1",
            "Use Data Reader to get more info") + EnumChatFormatting.RESET;
    private static final String underBrOresMainTitle = EnumChatFormatting.WHITE + GT_LanguageManager.addStringLocalization("underBrOresMainTitle",
            "     Data about under-bedrock ores\n     based on collected ores") + EnumChatFormatting.RESET;

    private static final String warningLabel = EnumChatFormatting.RED + GT_LanguageManager.addStringLocalization("warningLabel",
            "     Warning! ") + EnumChatFormatting.RESET;

    private static final String underBrWarningLabel = EnumChatFormatting.GOLD + GT_LanguageManager.addStringLocalization("underBrWarningLabel",
            "Do not use this data orb in other miners, or at least, the position of controller should be same "
                    +"by x, y, z as in this data orb sub title. Otherwise you will overwrite collected data, that will "
                    +"block an ability to mine under-bedrock ores in previous place because of miner can not mine "
                    +"under-bedrock without data orb or with empty data orb. ") + EnumChatFormatting.RESET;
    private static final String underBrOresFlowLabel2 = EnumChatFormatting.DARK_GRAY + GT_LanguageManager.addStringLocalization("underBrOresFlowLabel2",
            "   Middle under-bedrock flow for 1 tier\n   miner and MV energy hatch.\n   Ore blocks count each hour:") + EnumChatFormatting.RESET;

    private static final String minedOresCountLabel = GT_LanguageManager.addStringLocalization("minedOresCount", "Mined Ores Count");
    private static final String minedOreTypesCountLabel = GT_LanguageManager.addStringLocalization("minedOreTypesCount", "Mined Ore Types Count");
    private static final String underBrModeEnabledLabel = EnumChatFormatting.YELLOW + GT_LanguageManager.addStringLocalization(
            "underBrModeEnabledLabel", "Mining under bedrock") + EnumChatFormatting.RESET;

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

    class OreCollection implements Comparator<OreCollection>{
        OreCollection(){}
        OreCollection(String oreName, int oreCount){
            this.oreName = oreName;
            this.oreCount = oreCount;
        }
        String oreName;
        int oreCount;
        public int compare(OreCollection a, OreCollection b)
        {
            return b.oreCount - a.oreCount;
        }
        public NBTTagList getTooltipLines(ArrayList<OreCollection> aList, int totalBlocksCount, int oreTypesCount){
            String bookTitle = underBrOresMainTitle + "\n\n";

            bookTitle += warningLabel;
            bookTitle += underBrWarningLabel + "\n\n";

            bookTitle += "     " + EnumChatFormatting.DARK_GREEN + minedOresCountLabel + " " + EnumChatFormatting.AQUA + totalBlocksCount + "\n\n     "
                + EnumChatFormatting.DARK_GREEN + minedOreTypesCountLabel + " " + EnumChatFormatting.AQUA + oreTypesCount + EnumChatFormatting.RESET;

            NBTTagList tTagList = new NBTTagList();
            Collections.sort(aList, this);
            String line = "";
            ArrayList<String> pages = new ArrayList<>();
            pages.add(bookTitle);
            for (int i = 0; i < aList.size(); i++){
                String tempLine = EnumChatFormatting.GRAY + aList.get(i).oreName + " " + EnumChatFormatting.AQUA + calcOreFlow(aList.get(i).oreCount);
                if(line == "") {
                    line = underBrOresFlowLabel2 + "\n\n";
                    line += "  "+tempLine;
                }
                else {
                    if(i % 7 == 6) {
                        line += "\n\n  " + tempLine;
                        pages.add(line);
                        line = "";
                    } else {
                        line += "\n\n  " + tempLine;
                        if(i == aList.size()-1) {
                            pages.add(line);
                        }
                    }
                }
            }
            int idx = 0;
            for (String str : pages){
                str = EnumChatFormatting.GRAY + "                         " + (idx+1) + " / " + pages.size()
                        +EnumChatFormatting.RESET + "\n\n" + str;
                tTagList.appendTag(new NBTTagString(str));
                idx++;
            }
            return tTagList;
        }
    }

    @Override
    public void onConfigLoad(GT_Config aConfig) {
        super.onConfigLoad(aConfig);
        optimizationRate = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.optimizationRate", 1);
        optimizationRate = optimizationRate < 1 ? 1 : Math.min(optimizationRate, 4);
        dFluidPerOperation = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.dFluidPerOperation", 2000);
        dFluidUnderBredrock = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.dFluidUnderBredrock", 1000) * optimizationRate;
        consumeMiningPipeAfterCycles = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.consumeMiningPipeAfterCycles", 1000) / optimizationRate;
        boolean isFactorsConfigInvalid;
        try {
            String factorsStr = aConfig.get(ConfigCategories.machineconfig, "OreDrillingPlant.oreFactors", "70000,120000,263000,575000").replaceAll("[^\\d,]", "");
            String[] factors = factorsStr.split(",");
            isFactorsConfigInvalid = factors.length != 4;
            if(!isFactorsConfigInvalid) {
                origOreFactors = new int[4];
                for(int i = 0; i < 4; i++){
                    origOreFactors[i] = Integer.parseInt(factors[i]);
                    if(origOreFactors[i] < 10000) origOreFactors[i] = 10000;
                }
            }
        } catch (Throwable e) {
            isFactorsConfigInvalid = true;
            System.out.println("ERROR - OreDrillingPlant.oreFactors config is invalid, it should be 4 numbers separated by comma without spaces");
            e.printStackTrace(GT_Log.err);
        }
        if(isFactorsConfigInvalid) origOreFactors = new int[]{70000,120000,263000,575000};
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
        aNBT.setInteger("underBrOperationsCount", underBrOperationsCount);
    }
    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        underBrOperationsCount = aNBT.getInteger("underBrOperationsCount");
    }

    protected boolean allowPutPipesToController() {
        return isAllowPutPipesToController;
    }

    private String prepareDataOrbTitle(IGregTechTileEntity te){
        return EnumChatFormatting.DARK_GREEN + te.getWorld().provider.getDimensionName() + EnumChatFormatting.RESET
                + EnumChatFormatting.GOLD + " (x " + te.getXCoord() + ", y " + te.getYCoord() + ", z " + te.getZCoord()+ ")";
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
        int oreTypesCount = 0;
        ArrayList<OreCollection> oreCollection = new ArrayList<>();
        HashMap<String, Integer> oresMap = new HashMap<>();
        for(GT_ItemStack gts : gtStackMap.keySet()){
            ItemStack itst = gts.toStack();
            blocksCounts[currentIdx] = gtStackMap.get(gts);
            oresMap.putIfAbsent(itst.getDisplayName(), 0);
            oresMap.put(itst.getDisplayName(), oresMap.get(itst.getDisplayName()) + blocksCounts[currentIdx]);
            totalBlocksCount += blocksCounts[currentIdx];
            NBTTagCompound sNbt = new NBTTagCompound();
            itst.writeToNBT(sNbt);
            orbNbt.setTag("minerOre"+currentIdx, sNbt);
            currentIdx++;
        }
        for (String oreName : oresMap.keySet()) {
            oreCollection.add(new OreCollection(oreName, oresMap.get(oreName)));
            oreTypesCount++;
        }
        orbNbt.setIntArray("blocksCounts", blocksCounts);
        orbNbt.setString("mDataTitle", prepareDataOrbTitle(te));
        orbNbt.setString("mDataName", underBrOresFlowLabel1);

        NBTTagList tTagList = new OreCollection().getTooltipLines(oreCollection, totalBlocksCount, oreTypesCount);
        orbNbt.setTag("pages", tTagList);
        orb.setTagCompound(orbNbt);
    }

    private String calcOreFlow(int blocksCount){
        double chanceToGet = (double)blocksCount / (double)getOreFactor();
        double oreFlow = chanceToGet * getUnderBrOperationsCountPerPeriod();
        double roundValue = oreFlow < 0.001d ? 0 : (oreFlow < 0.01d ? 1000d : (oreFlow < 0.1d ? 100d : (oreFlow > 10d ? 10 : 100d))) ;
        if(roundValue == 0) {
            return EnumChatFormatting.RED + "<" + EnumChatFormatting.RESET + " 0.001";
        }
        oreFlow = Math.round(oreFlow * roundValue) / roundValue; // to round more if number is not so tiny
        return ""+oreFlow;
    }

    float getUnderBrOperationsCountPerPeriod(){
        if(operationsCountPerCalcPeriod > 0) return operationsCountPerCalcPeriod;
        float ticksIn1hour = 20*60*60;
        operationsCountPerCalcPeriod = ticksIn1hour / 960f; // 960f is progress time for 1 tier miner
        return operationsCountPerCalcPeriod;
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
                    result = getRandomUnderBedrockOre();
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
        if(currentOreFactor > getOreFactor()) {
            moreCyclesTimes = (int)Math.ceil((float)currentOreFactor / (float)getOreFactor());
            currentOreFactor = getOreFactor() * moreCyclesTimes;
        } else {
            currentOreFactor = getOreFactor();
        }
    }

    private int getOreFactor(){
        if(oreFactor > 0) return oreFactor;
        oreFactor = origOreFactors[getMinTier() - 2];
        return oreFactor;
    }

    private ItemStack getRandomUnderBedrockOre(){
        ItemStack output = null;
        int randVal = getBaseMetaTileEntity().getRandomNumber(currentOreFactor);
        for(OreHash hash : oreHashes){
            if(output == null) output = hash.tryGetItem(randVal);
        }
        if(output == null) {
            output = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L);
        }
        if(moreCyclesTimes > 1) {
            output.stackSize *= moreCyclesTimes;
        }
        if(optimizationRate > 1) {
            output.stackSize *= optimizationRate;
        }
        return output;
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_DrillerBase(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "OreDrillingPlant.png");
    }

    @Override
    protected boolean workingDownward(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
        getBaseMetaTileEntity().setExtraInfo(yHead);
    	if (yHead != oldYHead) oreBlockPositions.clear();
        fillMineListIfEmpty(xDrill, yDrill, zDrill, xPipe, zPipe, yHead);
        if (oreBlockPositions.isEmpty()) {
            int LowerPipeResult = (int)tryLowerPipe();
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
        }

        if (oreBlock != null && oreBlock != Blocks.air) {
            ArrayList<ItemStack> oreBlockDrops = getBlockDrops(oreBlock, oreBlockPos.chunkPosX, oreBlockPos.chunkPosY, oreBlockPos.chunkPosZ);
            saveMinedOreBlocks((ArrayList<ItemStack>)oreBlockDrops.clone());
            getBaseMetaTileEntity().getWorld().setBlockToAir(oreBlockPos.chunkPosX, oreBlockPos.chunkPosY, oreBlockPos.chunkPosZ);
            mOutputItems = getOutputByDrops(oreBlockDrops);
        }
        return true;
    }

    @Override
    protected boolean workingUpward(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
        IGregTechTileEntity te = getBaseMetaTileEntity();
        te.setExtraInfo(-2);
        ItemStack pipesInController = getStackInSlot(1);
        boolean foundPipesStack = false;
        for(ItemStack itst : getStoredInputs()) {
            if(!foundPipesStack && itst.isItemEqual(miningPipe)) {
                foundPipesStack = true;
                addOutput(itst.copy());
                depleteInput(itst);
            }
        }
        if(foundPipesStack) return true; // waiting for next cycle
        if(pipesInController != null && pipesInController.stackSize > 0){
            addOutput(pipesInController.copy());
            setInventorySlotContents(1, null);
            return true; // waiting for next cycle
        }
        return super.workingUpward(aStack, xDrill, yDrill, zDrill, xPipe, zPipe, yHead, oldYHead);
    }

    @Override
    protected boolean workingAtBottom(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe, int zPipe, int yHead, int oldYHead) {
        if (reachingVoidOrBedrock()) {
            ItemStack output = tryMineUnderBedrock();
            if(output == null) {
                workState = STATE_UPWARD;
                return true;
            } else {
                getBaseMetaTileEntity().setExtraInfo(-1);
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
            isSuccess = tryConsumeDrillingFluid(dFluidUnderBredrock);
        } else {
            isSuccess = false;
        }
        if(isSuccess) underBrOperationsCount++;
        return isSuccess;
    }

    private void saveMinedOreBlocks(ArrayList<ItemStack> oreBlockDrops) {
        for(ItemStack itst : oreBlockDrops){
            if(itst.getItem() instanceof ItemBlock){
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
        int mProgTime;
        if(workState == STATE_DOWNWARD) {
            mProgTime = getBaseProgressTime();
        } else if (workState == STATE_AT_BOTTOM){
            mProgTime = (getBaseProgressTime() * optimizationRate);
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