package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.tools.GT_Tool_Pickaxe;
import gregtech.common.tools.GT_Tool_Shovel;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GT_MetaTileEntity_PrimitiveHandleMiner extends GT_MetaTileEntity_MultiBlockBase {

    private boolean isInitDone = false;
    private boolean hasHead = false;
    private int digHeight = 0;
    private int hungryDurationPerOperation = 40;
    private int decreaseFoodLevelAfterOperations = 8;
    private int currentOperationCounter  = 0;
    private ItemStack[] consumableItems = new ItemStack[2];
    private int[] chestSlots = new int[2];
    private TileEntity aChest = null;
    private boolean meetNotHarvestableLayer = false;
    private int[] stairsPosAndMetaMap = {
            -1,-1,3,/*1*/0,-1,0,/*2*/
            1,-1,0,/*3*/1,0,2,/*4*/
            1,1,2,/*5*/0,1,1,/*6*/
            -1,1,1,/*7*/-1,0,3/*8*/}; // x, z, meta and again... (by 3)

    public GT_MetaTileEntity_PrimitiveHandleMiner(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("hasHead", hasHead);
        aNBT.setInteger("digHeight", digHeight);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        hasHead = aNBT.getBoolean("hasHead");
        digHeight = aNBT.getInteger("digHeight");
    }

    public GT_MetaTileEntity_PrimitiveHandleMiner(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PrimitiveHandleMiner(this.mName);
    }

    public String[] getDescription() {
        return new String[]
        {
            /*"Primitive Mechanic Miner",*/
            "Mine a 3 by 3 hole down with your muscle power",
            "Each new start require a Flint Drill Head",
            "to start digging place a chest on the top of miner",
            "(chest should contain stairs and cobblestone wall)",
            "and hold Right Mouse Button to keep mining",
            "stairs will be placed instead of mined blocks",
            "Mined blocks (or ores) will be placed to a chest",
            "can dig only blocks same as 1 lvl pickaxe",
            "Required a lot of muscle power - you will be hungry"
        };
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == 1) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
        }
        if(aSide == aFacing) {
            NBTTagCompound nbt = new NBTTagCompound();
            aBaseMetaTileEntity.writeToNBT(nbt);
            boolean hasDrillHead = nbt.getBoolean("hasHead");
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10], new GT_RenderedTexture(hasDrillHead ? Textures.BlockIcons.OVERLAY_PRIMITIVE_MINER_DRILL : Textures.BlockIcons.OVERLAY_PRIMITIVE_MINER)};
        } else  {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10]};
        }
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    public boolean validate(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer){
        int aX = aBaseMetaTileEntity.getXCoord();
        int aY = aBaseMetaTileEntity.getYCoord();
        int aZ = aBaseMetaTileEntity.getZCoord();
        aChest = aBaseMetaTileEntity.getWorld().getTileEntity(aX, aY+1, aZ);
        if(aChest == null || !(aChest instanceof TileEntityChest)) {
            GT_Utility.sendChatToPlayer(aPlayer, "A chest is required on the top of miner.");
            return false;
        }
        if(!hasHead) {
            InventoryPlayer pInv = aPlayer.inventory;
            ItemStack stakInHand = pInv.getCurrentItem();
            if(stakInHand != null) {
                if(stakInHand.getItem().equals(ItemList.Flint_Drill_Head.get(1L).getItem())) {
                    if(stakInHand.stackSize > 1) {
                        stakInHand.stackSize--;
                    } else {
                        for (int i = 0; i < pInv.getSizeInventory(); i++) {
                            ItemStack its = pInv.getStackInSlot(i);
                            if(stakInHand.equals(its)) {
                                pInv.setInventorySlotContents(i, null);
                            }
                        }
                    }
                    hasHead = true;
                    NBTTagCompound nbt = new NBTTagCompound();
                    aBaseMetaTileEntity.writeToNBT(nbt);
                    nbt.setBoolean("hasHead", hasHead);
                    aBaseMetaTileEntity.readFromNBT(nbt);
                    if(!aBaseMetaTileEntity.getWorld().isRemote) {
                        aBaseMetaTileEntity.issueClientUpdate();
                    }
                    return false;
                }
            }
            if(!hasHead) {
                GT_Utility.sendChatToPlayer(aPlayer, "Require a Flint Drill Head");
                return false;
            }
        }
        TileEntityChest bc = (TileEntityChest)aChest;
        boolean foundStairs = false;
        boolean foundCobWall = false;
        for (int i = 0; i < bc.getSizeInventory(); i++) {
            ItemStack its = bc.getStackInSlot(i);
            if(its != null){
                if(its.getItem().equals(Item.getItemFromBlock(Blocks.cobblestone_wall))) {
                    foundCobWall = true;
                    consumableItems[0] = its;
                    chestSlots[0] = i;
                } else if(its.getItem().equals(Item.getItemFromBlock(Blocks.stone_stairs))) {
                    foundStairs = true;
                    consumableItems[1] = its;
                    chestSlots[1] = i;
                }
            }
        }
        if(!foundCobWall || !foundStairs) {
            if(!foundCobWall) GT_Utility.sendChatToPlayer(aPlayer, "Cobblestone wall not found in a chest");
            if(!foundStairs) GT_Utility.sendChatToPlayer(aPlayer, "Stone Stairs not found in a chest");
            return false;
        }
        if(aPlayer.inventory.getCurrentItem() != null) {
            GT_Utility.sendChatToPlayer(aPlayer, "To start working use your empty hand");
            return false;
        }
        return true;
    }

    private void drainMusclePlayerPower(EntityPlayer aPlayer){
        int hangryDuration = hungryDurationPerOperation;
        PotionEffect hunger = aPlayer.getActivePotionEffect(Potion.hunger);
        if(hunger != null) {
            hangryDuration += hunger.getDuration();
        }
        aPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, hangryDuration));
        currentOperationCounter++;
        if(decreaseFoodLevelAfterOperations <= currentOperationCounter){
            currentOperationCounter = 0;
            FoodStats fs = aPlayer.getFoodStats();
            int newFoodLevel = Math.max(0, fs.getFoodLevel()-1);
            fs.setFoodLevel(newFoodLevel);
            if(newFoodLevel == 0) {
                aPlayer.attackEntityFrom(DamageSource.starve,0.5f);
            }
        }
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if(!validate(aBaseMetaTileEntity, aPlayer)) {
            return true;
        } else {
            digNextLayer(aBaseMetaTileEntity, aPlayer);
            drainMusclePlayerPower(aPlayer);
        }

        if (aBaseMetaTileEntity.isClientSide()) return true;

        return true;
    }

    private void pushToChest(ArrayList<ItemStack> drops){
        GT_Utility.pushToChest((TileEntityChest)aChest, drops);
    }

    private void digNextLayer(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer){
        if(digHeight == 0) {
            digHeight = aBaseMetaTileEntity.getYCoord() - 1;
        } else {
            digHeight--;
        }
        meetNotHarvestableLayer = false;
        World aWorld = aBaseMetaTileEntity.getWorld();
        ArrayList<ChunkPosition> blocksPos = getBlocksAtLayer(aBaseMetaTileEntity, digHeight);
        ArrayList<ItemStack> layerDrop = new ArrayList<>();
        for (ChunkPosition pos : blocksPos) {
            Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
            if(aBlock != Blocks.air) {
                float bHardness = aBlock.getBlockHardness(aWorld, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                if(aBlock.getMaterial().isToolNotRequired() || (bHardness >= 0 && bHardness <= 2.5f &&
                        (GT_Tool_Pickaxe.canMineBlock(aBlock, (byte)tMeta) || GT_Tool_Shovel.canMineBlock(aBlock, (byte)tMeta))))
                {
                    layerDrop.addAll(aBlock.getDrops(getBaseMetaTileEntity().getWorld(), pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta, 1));
                    aWorld.setBlockToAir(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                } else {
                    meetNotHarvestableLayer = true;
                }
            }
        }
        if(layerDrop.size() > 0){
            pushToChest(layerDrop);
        }
        if(meetNotHarvestableLayer) {
            GT_Utility.sendChatToPlayer(aPlayer, "Miner meet strong rock or ore (height: "+digHeight+") that cannot to dig");
            doWorkSound(aBaseMetaTileEntity, false);
            digHeight++;
        } else {
            setPipeAndStairs(aBaseMetaTileEntity);
            doWorkSound(aBaseMetaTileEntity, true);
        }
    }

    public void doWorkSound(IGregTechTileEntity aBaseMetaTileEntity, boolean isSuccess){
            GT_Utility.sendSoundToPlayers(aBaseMetaTileEntity.getWorld(),
                    (String) GregTech_API.sSoundList.get(Integer.valueOf(isSuccess ? 101 : 6)), isSuccess? 1.0f : 0.5f, -1.0F,
                    aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
    }

    public void setPipeAndStairs(IGregTechTileEntity aBaseMetaTileEntity){
        TileEntityChest tec = (TileEntityChest)aChest;
        for(int i = 0; i < 2; i++){
            ItemStack iStack = consumableItems[i];
            if(iStack.stackSize > 1) {
                iStack.stackSize--;
                tec.setInventorySlotContents(chestSlots[i], iStack);
            } else {
                tec.setInventorySlotContents(chestSlots[i],null);
            }
        }
        aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getXCoord(), digHeight, aBaseMetaTileEntity.getZCoord(), Blocks.cobblestone_wall);
        int mapIdx = digHeight % 8;
        aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getXCoord() + stairsPosAndMetaMap[mapIdx*3], digHeight,
                aBaseMetaTileEntity.getZCoord() + stairsPosAndMetaMap[mapIdx*3+1],
                Blocks.stone_stairs, stairsPosAndMetaMap[mapIdx*3+2], 2);
    }

    public static ArrayList<ChunkPosition> getBlocksAtLayer(IGregTechTileEntity aBaseMetaTileEntity, int aY){
        ArrayList<ChunkPosition> blockPositions = new ArrayList<ChunkPosition>();
        int radius = 1;
        int baseX = aBaseMetaTileEntity.getXCoord() - radius;
        int baseZ = aBaseMetaTileEntity.getZCoord() - radius;
        int finalX = aBaseMetaTileEntity.getXCoord() + radius;
        int finalZ = aBaseMetaTileEntity.getZCoord() + radius;
        for(int x = baseX; x <= finalX; x ++){
            for(int z = baseZ; z <= finalZ; z ++){
                ChunkPosition chPos = new ChunkPosition(x,aY,z);
                blockPositions.add(chPos);
            }
        }
        return blockPositions;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
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