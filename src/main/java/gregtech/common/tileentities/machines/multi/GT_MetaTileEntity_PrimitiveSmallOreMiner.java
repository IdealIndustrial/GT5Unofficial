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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GT_MetaTileEntity_PrimitiveSmallOreMiner extends GT_MetaTileEntity_MultiBlockBase {

    private boolean hasHead = false;
    private int digHeight = 0;
    private int powerPerClick = 8;
    private int hungryDurationPerOperation = 480;
    private int damagePerOperation = 25;
    private int checkForOreInRadius = 3;
    private int progresstimePerOre = 480;
    private int drillIdx = -1;
    private int cobblestoneDirtIdx = -1;

    public GT_MetaTileEntity_PrimitiveSmallOreMiner(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 28);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("digHeight", digHeight);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        digHeight = aNBT.getInteger("digHeight");
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

    public GT_MetaTileEntity_PrimitiveSmallOreMiner(String aName) {
        super(aName, 28);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PrimitiveSmallOreMiner(this.mName);
    }

    public String[] getDescription() {
        return new String[]
        {
            "Search and dig only small ores in 7 by 7 radius",
            "Required items: Primitive Drill, Cobblestone or Dirt",
            "Cobblestone or Dirt will be placed instead of small ore",
            "Miner do not leave a hole under it while working",
            "Working speed depends on stored energy",
            "Required a lot of muscle power - you will be hungry"
        };
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if(aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10], new GT_RenderedTexture(aActive
                    ? Textures.BlockIcons.OVERLAY_PRIMITIVE_MINER_DRILL : Textures.BlockIcons.OVERLAY_PRIMITIVE_MINER)};
        } else  {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10]};
        }
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    public boolean validate(){
        drillIdx = -1;
        cobblestoneDirtIdx = -1;
        for(int i = 0; i < 5; i++) {
            ItemStack its = mInventory[i];
            if(its != null){
                if(drillIdx == -1 && its.getItemDamage() == 180) {
                    drillIdx = i;
                }
                if(cobblestoneDirtIdx == -1 && (its.getItem().equals(Item.getItemFromBlock(Blocks.cobblestone))
                        || its.getItem().equals(Item.getItemFromBlock(Blocks.dirt)))) {
                    cobblestoneDirtIdx = i;
                }
            }
        }
        return drillIdx != -1 && cobblestoneDirtIdx != -1;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        return super.onRightclick(aBaseMetaTileEntity, aPlayer);
    }

    private void pushToOutputSlots(ArrayList<ItemStack> drops){
        GT_Utility.pushToOutputSlots(mInventory, drops, 5);
    }

    private boolean digNextOre(IGregTechTileEntity aBaseMetaTileEntity){
        if (digHeight == 1) {
            return false;
        } else if(digHeight == 0) {
            digHeight = aBaseMetaTileEntity.getYCoord()-1;
        } else {
            digHeight--;
        }
        boolean foundOreInLayer = false;
        ArrayList<ItemStack> layerDrop = new ArrayList<>();
        World aWorld = aBaseMetaTileEntity.getWorld();
        ArrayList<ChunkPosition> blocksPos = GT_Utility.getBlocksAtLayer(aBaseMetaTileEntity, digHeight, checkForOreInRadius);
        for (ChunkPosition pos : blocksPos) {
            if(!foundOreInLayer) {
                Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                if(!Blocks.air.equals(aBlock)) {
                    if(GT_OreDictUnificator.isSmallOre(aWorld, aBlock, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta)) {
                        foundOreInLayer = true;
                        layerDrop.addAll(aBlock.getDrops(getBaseMetaTileEntity().getWorld(), pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta, 1));
                        aWorld.setBlockToAir(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                        Block blockToSet = GT_Utility.decreaseInvItemAndGetBlock(mInventory, cobblestoneDirtIdx);
                        if(blockToSet != null) {
                            aWorld.setBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, blockToSet);
                        }
                    }
                }
            }
        }
        if(!foundOreInLayer) {
            return digNextOre(aBaseMetaTileEntity);
        }
        if(layerDrop.size() > 0) {
            pushToOutputSlots(layerDrop);
        }
        if(GT_MetaGenerated_Tool.addDmgAndCheckIsDestroy(mInventory[drillIdx], damagePerOperation)) {
            mInventory[drillIdx] = null;
        }
        doWorkSound(aBaseMetaTileEntity);
        return foundOreInLayer;
    }

    public void doWorkSound(IGregTechTileEntity aBaseMetaTileEntity){
            GT_Utility.sendSoundToPlayers(aBaseMetaTileEntity.getWorld(),
                    (String) GregTech_API.sSoundList.get(101), 1.0f, -1.0F,
                    aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
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
        return progresstimePerOre - Math.round((te.getStoredSteam() * progresstimePerOre / 2f) / maxSteamStore());
    }

    public boolean checkRecipe(ItemStack aStack) {
        if(digHeight == 1) return false;
        BaseMetaTileEntity te = (BaseMetaTileEntity)getBaseMetaTileEntity();
        if(te.getStoredSteam() > 0 && validate()) {
            if(digNextOre(te)){
                te.decreaseStoredSteam(1, false);
            }
            mMaxProgresstime = calcProgTimeOnEnergyAmount(te);
            return true;
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