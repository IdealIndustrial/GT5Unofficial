package gregtech.common.tileentities.machines.basic;

import gregtech.api.GregTech_API;
import gregtech.api.enums.RelativeOffset;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GT_MetaTileEntity_PrimitiveMineDigger extends GT_MetaTileEntity_PrimitiveMuscleMachine {

    private boolean foundOre = false;
    private int digHeight = 0;
    private int digsCount = 0;
    private final int setTorchEachBlocks = 16;
    private int oreVienHeight = 7;
    private boolean finalStop = false;
    private boolean isPickupLadders = false;
    private int ladderIdx = -1;
    private int cobblestoneIdx = -1;
    private int torchIdx = -1;
    private boolean meetNotHarvestableLayer = false;

    public GT_MetaTileEntity_PrimitiveMineDigger(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("finalStop", finalStop);
        aNBT.setInteger("digHeight", digHeight);
        aNBT.setInteger("digsCount", digsCount);
        aNBT.setBoolean("isPickupLadders", isPickupLadders);
        aNBT.setBoolean("meetNotHarvestableLayer", meetNotHarvestableLayer);
        aNBT.setBoolean("foundOre", foundOre);
        aNBT.setInteger("oreVienHeight", oreVienHeight);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        finalStop = aNBT.getBoolean("finalStop");
        digHeight = aNBT.getInteger("digHeight");
        digsCount = aNBT.getInteger("digsCount");
        isPickupLadders = aNBT.getBoolean("isPickupLadders");
        meetNotHarvestableLayer = aNBT.getBoolean("meetNotHarvestableLayer");
        foundOre = aNBT.getBoolean("foundOre");
        oreVienHeight = aNBT.getInteger("oreVienHeight");
    }

    public GT_MetaTileEntity_PrimitiveMineDigger(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PrimitiveMineDigger(this.mName);
    }

    @Override
    public int getHungryDurationPerOperation() {
        return 320;
    }

    @Override
    public int getDamagePerOperation() {
        return 12;
    }

    @Override
    public int getProgresstimePerOre() {
        return isPickupLadders ? 5 : 20;
    }

    @Override
    public boolean isDrillRequiredToWork() {
        return !isPickupLadders;
    }

    @Override
    public int getDecreaseSteamPerOperation() {
        int steamPerOperation = 1;
        if (isPickupLadders) {
            digsCount++;
            int pickupCheeperInTimes = 8;
            steamPerOperation = (digsCount % pickupCheeperInTimes == 0) ? 1 : 0;
        }
        return steamPerOperation;
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "Mine a small hole down with your muscle power",
            "Required items: Primitive Drill, Ladder, Cobblestone",
            "Optional are torches (will be placed on dig finish)",
            "To start pickup torches and ladders you have to remove",
            "all items from inputs except cobblestone, to fill holes",
            "Digger will stop if found an ore vein in a 3x3 radius or bedrock block",
            "Working speed depends on stored energy",
            "Required a lot of muscle power - you will be hungry!"
        };
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10], new GT_RenderedTexture(aActive
                ? Textures.BlockIcons.OVERLAY_PRIMITIVE_MINE_DIGGER_DRILL : Textures.BlockIcons.OVERLAY_PRIMITIVE_MINE_DIGGER)};
        } else {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10]};
        }
    }

    public boolean validate() {
        boolean isValid = true;
        ladderIdx = -1;
        cobblestoneIdx = -1;
        torchIdx = -1;
        for (int i = 0; i < 5; i++) {
            ItemStack its = mInventory[i];
            if (its != null) {
                if (ladderIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.ladder))) {
                    ladderIdx = i;
                }
                if (cobblestoneIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.cobblestone))) {
                    cobblestoneIdx = i;
                }
                if (torchIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.torch))) {
                    torchIdx = i;
                }
            }
        }
        if (ladderIdx == -1) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForPickUp() {
        int drillIdx = -1;
        ladderIdx = -1;
        cobblestoneIdx = -1;
        torchIdx = -1;
        for (int i = 0; i < 5; i++) {
            ItemStack its = mInventory[i];
            if (its != null) {
                if (drillIdx == -1 && its.getItemDamage() == 180) {
                    drillIdx = i;
                }
                if (ladderIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.ladder))) {
                    ladderIdx = i;
                }
                if (cobblestoneIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.cobblestone))) {
                    cobblestoneIdx = i;
                }
                if (torchIdx == -1 && its.getItem().equals(Item.getItemFromBlock(Blocks.torch))) {
                    torchIdx = i;
                }
            }
        }
        return drillIdx == -1 && ladderIdx == -1 && torchIdx == -1 && cobblestoneIdx > -1;
    }

    @Override
    protected void endProcess(IGregTechTileEntity aBaseMetaTileEntity) {
        digNextLayer(aBaseMetaTileEntity);
    }

    private boolean digNextLayer(IGregTechTileEntity aBaseMetaTileEntity) {
        if (!validate()) {
            return false;
        }
        if (digHeight == 0) {
            digHeight = aBaseMetaTileEntity.getYCoord();
        }
        World aWorld = aBaseMetaTileEntity.getWorld();
        int checkForOreInRadius = 2;
        ArrayList<ChunkPosition> blocksPos = GT_Utility.getBlocksAtLayer(aBaseMetaTileEntity, digHeight - 1, checkForOreInRadius);
        for (ChunkPosition pos : blocksPos) {
            Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            if (aBlock != Blocks.air) {
                if (Blocks.bedrock.equals(aBlock) || oreVienHeight <= 0 || isNextLayerContainsOre(aBaseMetaTileEntity)) {
                    meetNotHarvestableLayer = true;
                    break;
                } else if (foundOre(aBlock, aWorld, pos, tMeta)) {
                    foundOre = true;
                }
            }
        }
        doWorkSound(aBaseMetaTileEntity, !meetNotHarvestableLayer);
        if (!meetNotHarvestableLayer) {
            digHeight--;
            //if found ore vein dig to it's bottom
            if (foundOre) {
                oreVienHeight--;
            }
            digHole(aBaseMetaTileEntity);
        } else {
            if (torchIdx > -1 && aBaseMetaTileEntity.isServerSide()) {
                setTorches(aBaseMetaTileEntity);
            }
        }
        return !meetNotHarvestableLayer;
    }

    private boolean foundOre(Block aBlock, World aWorld, ChunkPosition pos, int tMeta) {
        boolean isOre = GT_OreDictUnificator.isGtOre(aBlock);
        return isOre && !GT_OreDictUnificator.isSmallOre(aWorld, aBlock, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta);
    }

    private void setTorches(IGregTechTileEntity te) {
        World aWorld = te.getWorld();
        int aY = digHeight;
        boolean placeToSetTorch = true;
        if (isUnstableBlock(aWorld.getBlock(te.getXCoord(), aY - 1, te.getZCoord()))) { // checking a flor to be a block
            if (decreaseInventoryItem(cobblestoneIdx)) {
                aWorld.setBlock(te.getXCoord(), aY - 1, te.getZCoord(), Blocks.cobblestone);
            } else {
                placeToSetTorch = false;
            }
        }
        if (placeToSetTorch && decreaseInventoryItem(torchIdx)) {
            aWorld.setBlock(te.getXCoord(), aY, te.getZCoord(), Blocks.torch, 0, 3);
        }
        aY += setTorchEachBlocks;
        if (te.getYCoord() > aY && mInventory[torchIdx] != null) {
            setTorchOnWall(te, aY);
        }
    }

    private void setTorchOnWall(IGregTechTileEntity te, int aY) {
        World aWorld = te.getWorld();
        ChunkPosition chPos = GT_Utility.getFrontRelativeOffset(te, RelativeOffset.BACK, 1, aY);
        boolean placeToSetTorch = true;
        if (isUnstableBlock(aWorld.getBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ))) { // checking is a solid wall
            if (decreaseInventoryItem(cobblestoneIdx)) {
                aWorld.setBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ, Blocks.cobblestone);
            } else {
                placeToSetTorch = false;
            }
        }
        if (placeToSetTorch && decreaseInventoryItem(torchIdx)) {
            aWorld.setBlock(te.getXCoord(), aY, te.getZCoord(), Blocks.torch, getTorchLadderMetaByFrontSize(te, false), 3);
        }

        aY += setTorchEachBlocks;
        if (te.getYCoord() > aY && mInventory[torchIdx] != null) {
            setTorchOnWall(te, aY);
        }
    }

    public void doWorkSoundPickUp(IGregTechTileEntity aBaseMetaTileEntity) {
        GT_Utility.sendSoundToPlayers(aBaseMetaTileEntity.getWorld(),
                (String) GregTech_API.sSoundList.get(6), 0.1f, -1.0F,
                aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
    }

    @Override
    public void doWorkSound(IGregTechTileEntity aBaseMetaTileEntity, boolean isSuccess) {
        GT_Utility.sendSoundToPlayers(aBaseMetaTileEntity.getWorld(),
                (String) GregTech_API.sSoundList.get(isSuccess ? 101 : 6), isSuccess ? 1.0f : 0.8f, -1.0F,
                aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
    }

    private boolean checkIsPriorityGrass(IGregTechTileEntity te) {
        World aWorld = te.getWorld();
        int grassTypes = 0;
        int sandTypes = 0;
        for (int y = te.getYCoord() - 3; y <= te.getYCoord() + 3; y++) {
            for (int x = te.getXCoord() - 3; x <= te.getXCoord() + 3; x++) {
                for (int z = te.getZCoord() - 3; z <= te.getZCoord() + 3; z++) {
                    Block aBlock = aWorld.getBlock(x, y, z);
                    if (Blocks.grass.equals(aBlock) || Blocks.dirt.equals(aBlock)) {
                        grassTypes++;
                    }
                    if (Blocks.sand.equals(aBlock) || Blocks.sandstone.equals(aBlock)) {
                        sandTypes++;
                    }
                }
            }
        }
        return grassTypes >= sandTypes;
    }

    private Block getTrashBlock(IGregTechTileEntity te, boolean isNextToTop) {
        Block aBlock = null;
        int[] idxes = new int[]{-1, -1, -1, -1, -1};
        int maxPriorityIdx = -1;
        for (int i = 27; i >= 0; i--) {
            ItemStack its = mInventory[i];
            if (its != null) {
                aBlock = Block.getBlockFromItem(its.getItem());
                if (!Blocks.air.equals(aBlock) && !Blocks.ladder.equals(aBlock) && !Blocks.torch.equals(aBlock)) {
                    if (idxes[0] == -1 && (Blocks.dirt.equals(aBlock) || Blocks.grass.equals(aBlock))) {
                        idxes[0] = i;
                    } else if (idxes[1] == -1 && (Blocks.sandstone.equals(aBlock) || Blocks.sand.equals(aBlock))) {
                        idxes[1] = i;
                    } else if (idxes[2] == -1 && (Blocks.gravel.equals(aBlock) || Blocks.sand.equals(aBlock))) {
                        idxes[2] = i;
                    } else if (idxes[3] == -1) {
                        idxes[3] = i;
                    } else if (idxes[4] == -1) {
                        idxes[4] = i;
                    }
                }
            }
        }
        if (isNextToTop && (idxes[0] > -1 || idxes[1] > -1)) {
            boolean isPriorityGrass = checkIsPriorityGrass(te) && idxes[0] > -1;
            maxPriorityIdx = isPriorityGrass ? idxes[0] : idxes[1];
        } else if (idxes[2] > -1) {
            maxPriorityIdx = idxes[2];
        }
        if (maxPriorityIdx == -1) {
            maxPriorityIdx = Math.max(idxes[3], idxes[4]);
        }
        if (maxPriorityIdx == -1) {
            maxPriorityIdx = Math.max(idxes[0], idxes[1]);
        }
        if (maxPriorityIdx == -1) {
            return null;
        }
        if (mInventory[maxPriorityIdx] != null) {
            aBlock = Block.getBlockFromItem(mInventory[maxPriorityIdx].getItem());
            decreaseInventoryItem(maxPriorityIdx);
        }
        return aBlock;
    }

    public boolean pickUpLadder(IGregTechTileEntity te) {
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

            Block aBlockToPut = getTrashBlock(te, isNextToTop);
            if (aBlockToPut != null) {
                if (aBlock != Blocks.air) {
                    layerDrop.addAll(aBlock.getDrops(getBaseMetaTileEntity().getWorld(), pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta, 1));
                }
                aWorld.setBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, aBlockToPut);
            } else {
                success = false;
            }
        }
        if (!layerDrop.isEmpty()) {
            pushToOutputSlots(layerDrop);
        }
        if (success) {
            digHeight++;
            if (te.getYCoord() == digHeight) {
                finalStop = true;
            }
            doWorkSoundPickUp(te);
        }
        return success;
    }

    /**
     * Check 2 blocks for digging for beign Ore
     *
     * @param te
     * @return
     */
    private boolean isNextLayerContainsOre(IGregTechTileEntity te) {
        List<ChunkPosition> blocksToCheck = new ArrayList<>();
        blocksToCheck.add(GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 0, digHeight - 1));
        blocksToCheck.add(GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 1, digHeight - 1));
        for (ChunkPosition blockToCheck : blocksToCheck) {
            Block aBlock = te.getWorld().getBlock(blockToCheck.chunkPosX, blockToCheck.chunkPosY, blockToCheck.chunkPosZ);
            int tMeta = te.getWorld().getBlockMetadata(blockToCheck.chunkPosX, blockToCheck.chunkPosY, blockToCheck.chunkPosZ);
            if (foundOre(aBlock, te.getWorld(), blockToCheck, tMeta)) {
                return true;
            }
        }
        return false;
    }

    public void digHole(IGregTechTileEntity te) {
        World aWorld = te.getWorld();
        ArrayList<ItemStack> layerDrop = new ArrayList<>();
        ArrayList<ChunkPosition> blocksPosToCheck = new ArrayList<>();
        blocksPosToCheck.add(GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 1, digHeight));
        blocksPosToCheck.add(new ChunkPosition(te.getXCoord(), digHeight, te.getZCoord()));
        for (ChunkPosition pos : blocksPosToCheck) {
            Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            if (aBlock != Blocks.air && !foundOre(aBlock, aWorld, pos, tMeta)) {
                layerDrop.addAll(aBlock.getDrops(getBaseMetaTileEntity().getWorld(), pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta, 1));
                aWorld.setBlockToAir(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            }
        }
        if (!layerDrop.isEmpty()) {
            pushToOutputSlots(layerDrop);
        }
        //block for ladder
        ChunkPosition chPos = GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 2, digHeight);
        boolean stabilized = true;
        if (isUnstableBlock(aWorld.getBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ))) {
            stabilized = decreaseInventoryItem(cobblestoneIdx);
            if (stabilized) {
                aWorld.setBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ, Blocks.cobblestone);
            }
        }
        //ladder
        if (stabilized) {
            chPos = GT_Utility.getFrontRelativeOffset(te, RelativeOffset.FORWARD, 1, digHeight);
            if (decreaseInventoryItem(ladderIdx)) {
                aWorld.setBlock(chPos.chunkPosX, chPos.chunkPosY, chPos.chunkPosZ, Blocks.ladder, getTorchLadderMetaByFrontSize(te, true), 3);
            }
        }
    }

    private int getTorchLadderMetaByFrontSize(IGregTechTileEntity te, boolean isLadder) {
        if (te.getFrontFacing() == 2) {
            return isLadder ? 3 : 4;
        }
        if (te.getFrontFacing() == 3) {
            return isLadder ? 2 : 3;
        }
        if (te.getFrontFacing() == 4) {
            return isLadder ? 5 : 0;
        }
        return isLadder ? 4 : 1;
    }

    @Override
    public boolean isReadyToDig() {
        if (finalStop) {
            return false;
        }
        BaseMetaTileEntity te = (BaseMetaTileEntity) getBaseMetaTileEntity();
        if (!meetNotHarvestableLayer && validate()) {
            return true;
        } else if (meetNotHarvestableLayer) {
            if (!isPickupLadders) {
                isPickupLadders = validateForPickUp();
            }
            return isPickupLadders && pickUpLadder(te);
        }
        return false;
    }
}
