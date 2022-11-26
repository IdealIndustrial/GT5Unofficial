package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.RelativeOffset;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_PrimitiveMiner;
import gregtech.api.gui.GT_GUIContainer_PrimitiveMiner;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class GT_MetaTileEntity_PrimitiveMineDigger extends GT_MetaTileEntity_MultiBlockBase {

    private boolean hasHead = false;
    private int digHeight = 0;
    private int digsCount = 0;
    private int powerPerClick = 8;
    private int hungryDurationPerOperation = 320;
    private int decreaseFoodLevelAfterOperations = 8;
    private int damagePerOperation = 10;
    private int setTorchEachBlocks = 16;
    private boolean finalStop = false;
    private boolean isPickupLadders = false;

    private int checkForOreInRadius = 2;
    private int timePerOperation = 10;
    private int pickupCheeperInTimes = 8;
    private int pickupSpeed = 8;
    private int drillIdx = -1;
    private int ladderIdx = -1;
    private int cobblestoneIdx = -1;
    private int torchIdx = -1;

    private boolean meetNotHarvestableLayer = false;

    public GT_MetaTileEntity_PrimitiveMineDigger(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 28);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("finalStop", finalStop);
        aNBT.setInteger("digHeight", digHeight);
        aNBT.setInteger("digsCount", digsCount);
        aNBT.setBoolean("isPickupLadders", isPickupLadders);
        aNBT.setBoolean("meetNotHarvestableLayer", meetNotHarvestableLayer);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        finalStop = aNBT.getBoolean("finalStop");
        digHeight = aNBT.getInteger("digHeight");
        digsCount = aNBT.getInteger("digsCount");
        isPickupLadders = aNBT.getBoolean("isPickupLadders");
        meetNotHarvestableLayer = aNBT.getBoolean("meetNotHarvestableLayer");
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_PrimitiveMiner(aPlayerInventory, aBaseMetaTileEntity, powerPerClick, hungryDurationPerOperation);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_PrimitiveMiner(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "PrimitiveMiner.png", powerPerClick, hungryDurationPerOperation);
    }

    public GT_MetaTileEntity_PrimitiveMineDigger(String aName) {
        super(aName, 28);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PrimitiveMineDigger(this.mName);
    }

    public String[] getDescription() {
        return new String[]
        {
            "Mine a small hole down with your muscle power",
            "Required items: Primitive Drill, Ladder, Cobblestone",
            "Optional are torches (will be placed on dig finish)",
            "To start pickup torches and ladders you have to remove",
            "all items from inputs except cobblestone, to fill hole",
            "Digger will stop if found an ore vein or bedrock block",
            "Working speed depends on stored energy",
            "Required a lot of muscle power - you will be hungry"
        };
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if(aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10], new GT_RenderedTexture(aActive
                    ? Textures.BlockIcons.OVERLAY_PRIMITIVE_MINE_DIGGER_DRILL : Textures.BlockIcons.OVERLAY_PRIMITIVE_MINE_DIGGER)};
        } else  {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10]};
        }
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    public boolean validate(IGregTechTileEntity aBaseMetaTileEntity){
        boolean isValid = true;
        drillIdx = -1;
        ladderIdx = -1;
        cobblestoneIdx = -1;
        torchIdx = -1;
        for(int i = 0; i < 5; i++) {
            ItemStack its = mInventory[i];
            if(its != null){
                if(drillIdx == -1 && its.getItemDamage() == 180) {
                    drillIdx = i;
                }
                if(ladderIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.ladder))) {
                    ladderIdx = i;
                }
                if(cobblestoneIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.cobblestone))) {
                    cobblestoneIdx = i;
                }
                if(torchIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.torch))) {
                    torchIdx = i;
                }
            }
        }
        if(drillIdx == -1 || ladderIdx == -1 || cobblestoneIdx == -1) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForPickUp(IGregTechTileEntity aBaseMetaTileEntity){
        boolean isValid = true;
        drillIdx = -1;
        ladderIdx = -1;
        cobblestoneIdx = -1;
        torchIdx = -1;
        for(int i = 0; i < 5; i++) {
            ItemStack its = mInventory[i];
            if(its != null){
                if(drillIdx == -1 && its.getItemDamage() == 180) {
                    drillIdx = i;
                }
                if(ladderIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.ladder))) {
                    ladderIdx = i;
                }
                if(cobblestoneIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.cobblestone))) {
                    cobblestoneIdx = i;
                }
                if(torchIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.torch))) {
                    torchIdx = i;
                }
            }
        }
        return drillIdx == -1 && ladderIdx == -1 && torchIdx == -1 && cobblestoneIdx > -1;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        return super.onRightclick(aBaseMetaTileEntity, aPlayer);
    }

    private void pushToOutputSlots(ArrayList<ItemStack> drops){
        GT_Utility.pushToOutputSlots(mInventory, drops, 5);
    }

    private boolean digNextLayer(IGregTechTileEntity aBaseMetaTileEntity){
        if(digHeight == 0) {
            digHeight = aBaseMetaTileEntity.getYCoord();
        }
        World aWorld = aBaseMetaTileEntity.getWorld();
        ArrayList<ChunkPosition> blocksPos = GT_Utility.getBlocksAtLayer(aBaseMetaTileEntity, digHeight-1, checkForOreInRadius);
        for (ChunkPosition pos : blocksPos) {
            Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            if(aBlock != Blocks.air) {
                boolean isOre = GT_OreDictUnificator.isGtOre(aBlock);
                if((isOre && !GT_OreDictUnificator.isSmallOre(aWorld, aBlock, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta))
                        || Blocks.bedrock.equals(aBlock)) {
                    meetNotHarvestableLayer = true;
                }
            }
        }
        doWorkSound(aBaseMetaTileEntity, !meetNotHarvestableLayer);
        if(!meetNotHarvestableLayer) {
            digHeight--;
            digHole(aBaseMetaTileEntity);
        } else {
            if (torchIdx > -1 && aBaseMetaTileEntity.isServerSide()) {
                setTorches(aBaseMetaTileEntity);
            }
        }
        return !meetNotHarvestableLayer;
    }

    private boolean decreaseInventoryItem(int idx) {
        return decreaseInventoryItem(idx, 1);
    }

    private boolean decreaseInventoryItem(int idx, int count) {
        boolean success = false;
        if(idx > -1 && mInventory[idx] != null) {
            success = true;
            if (mInventory[idx].stackSize > count){
                mInventory[idx].stackSize -= count;
            } else {
                mInventory[idx] = null;
            }
        }
        return success;
    }

    private void setTorches(IGregTechTileEntity te){
        World aWorld = te.getWorld();
        int aY = digHeight;
        boolean placeToSetTorch = true;
        if(isAirWaterLava(aWorld.getBlock(te.getXCoord(), aY-1, te.getZCoord()))) { // checking a flor to be a block
            if(decreaseInventoryItem(cobblestoneIdx)) {
                aWorld.setBlock(te.getXCoord(), aY-1, te.getZCoord(), Blocks.cobblestone);
            } else {
                placeToSetTorch = false;
            }
        }
        if(placeToSetTorch && decreaseInventoryItem(torchIdx)) {
            aWorld.setBlock(te.getXCoord(), aY, te.getZCoord(), Blocks.torch, 0, 3);
        }
        aY += setTorchEachBlocks;
        if(te.getYCoord() > aY && mInventory[torchIdx] != null) {
            setTorchOnWall(te, aY);
        }
    }

    private void setTorchOnWall(IGregTechTileEntity te, int aY) {
        World aWorld = te.getWorld();
        ChunkPosition chPos = GT_Utility.getFrontRelativeOffset(te, RelativeOffset.BACK, 1, aY);
        boolean placeToSetTorch = true;
        if(isAirWaterLava(aWorld.getBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ))) { // checking is a solid wall
            if(decreaseInventoryItem(cobblestoneIdx)) {
                aWorld.setBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ, Blocks.cobblestone);
            } else {
                placeToSetTorch = false;
            }
        }
        if(placeToSetTorch && decreaseInventoryItem(torchIdx)) {
            aWorld.setBlock(te.getXCoord(), aY, te.getZCoord(), Blocks.torch, getTorchLadderMetaByFrontSize(te, false), 3);
        }

        aY += setTorchEachBlocks;
        if(te.getYCoord() > aY && mInventory[torchIdx] != null) {
            setTorchOnWall(te, aY);
        }
    }

    public void doWorkSoundPickUp(IGregTechTileEntity aBaseMetaTileEntity){
        GT_Utility.sendSoundToPlayers(aBaseMetaTileEntity.getWorld(),
                (String) GregTech_API.sSoundList.get(6), 0.1f, -1.0F,
                aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
    }

    public void doWorkSound(IGregTechTileEntity aBaseMetaTileEntity, boolean isSuccess){
            GT_Utility.sendSoundToPlayers(aBaseMetaTileEntity.getWorld(),
                    (String) GregTech_API.sSoundList.get(Integer.valueOf(isSuccess ? 101 : 6)), isSuccess? 1.0f : 0.8f, -1.0F,
                    aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
    }

    public boolean isAirWaterLava(Block aBlock){
        return Blocks.air.equals(aBlock) || Blocks.water.equals(aBlock) || Blocks.flowing_water.equals(aBlock)
                || Blocks.lava.equals(aBlock) || Blocks.flowing_lava.equals(aBlock);
    }

    private boolean checkIsPriorityGrass(IGregTechTileEntity te){
        World aWorld = te.getWorld();
        int grassTypes = 0;
        int sandTypes = 0;
        for(int y = te.getYCoord()-3; y <= te.getYCoord()+3; y++) {
            for(int x = te.getXCoord()-3; x <= te.getXCoord()+3; x++) {
                for(int z = te.getZCoord()-3; z <= te.getZCoord()+3; z++) {
                    Block aBlock = aWorld.getBlock(x,y,z);
                    if(Blocks.grass.equals(aBlock) || Blocks.dirt.equals(aBlock)) grassTypes++;
                    if(Blocks.sand.equals(aBlock) || Blocks.sandstone.equals(aBlock)) sandTypes++;
                }
            }
        }
        return grassTypes >= sandTypes;
    }

    private Block getTrashBlock(IGregTechTileEntity te, boolean isNextToTop){
        Block aBlock = null;
        int[] idxes = new int[]{-1,-1,-1,-1,-1};
        boolean isPriorityGrass  = true;
        int maxPriorityIdx = -1;
        for(int i = 27; i >= 0; i--) {
            ItemStack its = mInventory[i];
            if(its != null){
                aBlock = Block.getBlockFromItem(its.getItem());
                if(!Blocks.air.equals(aBlock) && !Blocks.ladder.equals(aBlock) && !Blocks.torch.equals(aBlock)) {
                    if(idxes[0] == -1 && (Blocks.dirt.equals(aBlock) || Blocks.grass.equals(aBlock))){
                        idxes[0] = i;
                    } else if (idxes[1] == -1 && (Blocks.sandstone.equals(aBlock) || Blocks.sand.equals(aBlock))) {
                        idxes[1] = i;
                    } else if (idxes[2] == -1 && (Blocks.gravel.equals(aBlock) || Blocks.sand.equals(aBlock))) {
                        idxes[2] = i;
                    } else if(idxes[3] == -1){
                        idxes[3] = i;
                    } else if(idxes[4] == -1){
                        idxes[4] = i;
                    }
                }
            }
        }
        if(isNextToTop && (idxes[0] > -1 || idxes[1] > -1)) {
            isPriorityGrass = checkIsPriorityGrass(te) && idxes[0] > -1;
            maxPriorityIdx = isPriorityGrass ? idxes[0] : idxes[1];
        } else if (idxes[2] > -1){
            maxPriorityIdx = idxes[2];
        }
        if(maxPriorityIdx == -1) {
            maxPriorityIdx = Math.max(idxes[3], idxes[4]);
        }
        if(maxPriorityIdx == -1) {
            maxPriorityIdx = Math.max(idxes[0], idxes[1]);
        }
        if(maxPriorityIdx == -1) return null;
        if(mInventory[maxPriorityIdx] != null) {
            aBlock = Block.getBlockFromItem(mInventory[maxPriorityIdx].getItem());
            decreaseInventoryItem(maxPriorityIdx);
        } else {
            int debug = maxPriorityIdx;
        }
        return aBlock;
    }

    public boolean pickUpLadder(IGregTechTileEntity te){
        boolean success = true;
        World aWorld = te.getWorld();
        ArrayList<ItemStack> layerDrop = new ArrayList<>();
        ArrayList<ChunkPosition> blocksPosToCheck = new ArrayList<>();
        blocksPosToCheck.add(GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 1, digHeight));
        blocksPosToCheck.add(new ChunkPosition(te.getXCoord(), digHeight, te.getZCoord()));
        boolean isNextToTop = (te.getYCoord() - 3) <= digHeight;
        for (ChunkPosition pos : blocksPosToCheck) {
            Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            if(aBlock != Blocks.air) {
                layerDrop.addAll(aBlock.getDrops(getBaseMetaTileEntity().getWorld(), pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta, 1));
            }
            Block aBlockToPut = getTrashBlock(te, isNextToTop);
            if(aBlockToPut != null) {
                aWorld.setBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, aBlockToPut);
            } else {
                success = false;
            }
        }
        if(layerDrop.size() > 0) {
            pushToOutputSlots(layerDrop);
        }
        if(success){
            digHeight++;
            if(te.getYCoord() == digHeight) {
                finalStop = true;
            }
        }
        return success;
    }

    public void digHole(IGregTechTileEntity te){
        World aWorld = te.getWorld();
        ArrayList<ItemStack> layerDrop = new ArrayList<>();
        ArrayList<ChunkPosition> blocksPosToCheck = new ArrayList<>();
        blocksPosToCheck.add(GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 1, digHeight));
        blocksPosToCheck.add(new ChunkPosition(te.getXCoord(), digHeight, te.getZCoord()));
        for (ChunkPosition pos : blocksPosToCheck) {
            Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            if(aBlock != Blocks.air) {
                layerDrop.addAll(aBlock.getDrops(getBaseMetaTileEntity().getWorld(), pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta, 1));
                aWorld.setBlockToAir(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            }
        }
        if(layerDrop.size() > 0) {
            pushToOutputSlots(layerDrop);
        }
        ChunkPosition chPos = GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 2, digHeight);
        if(isAirWaterLava(aWorld.getBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ))){
            if(decreaseInventoryItem(cobblestoneIdx)) {
                aWorld.setBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ, Blocks.cobblestone);
            }
        }
        chPos = GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 1, digHeight);
        if(decreaseInventoryItem(ladderIdx)){
            aWorld.setBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ, Blocks.ladder, getTorchLadderMetaByFrontSize(te, true),3);
        }
        if(GT_MetaGenerated_Tool.addDmgAndCheckIsDestroy(mInventory[drillIdx], damagePerOperation)) {
            mInventory[drillIdx] = null;
        }
    }

    private int getTorchLadderMetaByFrontSize(IGregTechTileEntity te, boolean isLadder){
        if(te.getFrontFacing() == 2) return isLadder ? 3 : 4;
        if(te.getFrontFacing() == 3) return isLadder ? 2 : 3;
        if(te.getFrontFacing() == 4) return isLadder ? 5 : 0;
        return isLadder ? 4 : 1;
    }

    @Override
    public long maxEUStore() {
        return 0;
    }

    @Override
    public long maxSteamStore() {
        return 64;
    }

    @Override
    public boolean isElectric() {
        return false;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    private int calcProgTimeOnEnergyAmount(BaseMetaTileEntity te){
        return timePerOperation - Math.round((te.getStoredSteam() * timePerOperation / 2f) / maxSteamStore());
    }

    public boolean checkRecipe(ItemStack aStack) {
        if(finalStop) return false;
        BaseMetaTileEntity te = (BaseMetaTileEntity)getBaseMetaTileEntity();
        if(!meetNotHarvestableLayer && te.getStoredSteam() > 0 && validate(te)) {
            if(digNextLayer(te)){
                te.decreaseStoredSteam(1, false);
            }
            mMaxProgresstime = calcProgTimeOnEnergyAmount(te);
            return true;
        } else if(meetNotHarvestableLayer && te.getStoredSteam() > 0) {
            if(!isPickupLadders){
                isPickupLadders = validateForPickUp(te);
            }
            if(isPickupLadders) {
                digsCount++;
                if(!pickUpLadder(te)) return false;
                if(digsCount % pickupCheeperInTimes == 0) {
                    te.decreaseStoredSteam(1, false);
                }
                doWorkSoundPickUp(te);
                mMaxProgresstime = pickupSpeed;
                return true;
            }
        }
        return false;
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
        return true;
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
}