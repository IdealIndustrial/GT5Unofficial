package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_PrimitiveMiner extends GT_MetaTileEntity_PrimitiveMuscleMachine {

    private int digHeight = 0;
    private int cobblestoneDirtIdx = -1;

    public GT_MetaTileEntity_PrimitiveMiner(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    public GT_MetaTileEntity_PrimitiveMiner(String aName) {
        super(aName);
    }
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PrimitiveMiner(this.mName);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
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
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if(aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10], new GT_RenderedTexture(aActive
                    ? Textures.BlockIcons.OVERLAY_PRIMITIVE_MINER_DRILL : Textures.BlockIcons.OVERLAY_PRIMITIVE_MINER)};
        } else  {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10]};
        }
    }

    public boolean haveConsumableItems(){
        cobblestoneDirtIdx = -1;
        for(int i = 0; i < 5; i++) {
            ItemStack its = mInventory[i];
            if(its != null){
                if(cobblestoneDirtIdx == -1 && (its.getItem().equals(Item.getItemFromBlock(Blocks.cobblestone))
                        || its.getItem().equals(Item.getItemFromBlock(Blocks.dirt)))) {
                    cobblestoneDirtIdx = i;
                }
            }
        }
        return cobblestoneDirtIdx != -1;
    }

    private boolean digNextOre(IGregTechTileEntity aBaseMetaTileEntity){
        if (digHeight == 1 || !haveConsumableItems()) {
            return false;
        } else if(digHeight == 0) {
            digHeight = aBaseMetaTileEntity.getYCoord()-1;
        } else {
            digHeight--;
        }
        boolean foundOreInLayer = false;
        ArrayList<ItemStack> layerDrop = new ArrayList<>();
        World aWorld = aBaseMetaTileEntity.getWorld();
        int checkForOreInRadius = 3;
        ArrayList<ChunkPosition> blocksPos = GT_Utility.getBlocksAtLayer(aBaseMetaTileEntity, digHeight, checkForOreInRadius);
        for (ChunkPosition pos : blocksPos) {
            if(!foundOreInLayer) {
                Block aBlock = aWorld.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                int tMeta = aWorld.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                if(!Blocks.air.equals(aBlock)) {
                    if(GT_OreDictUnificator.isSmallOre(aWorld, aBlock, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta)) {
                        foundOreInLayer = true;
                        layerDrop.addAll(aBlock.getDrops(getBaseMetaTileEntity().getWorld(), pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, tMeta, 1));
                        Block blockToSet = GT_Utility.decreaseInvItemAndGetBlock(mInventory, cobblestoneDirtIdx);
                        if(blockToSet != null) {
                            aWorld.setBlockToAir(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
                            aWorld.setBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, blockToSet);
                        } else {
                            digHeight++;
                            return false;
                        }
                    }
                }
            }
        }
        if(!foundOreInLayer) {
            return digNextOre(aBaseMetaTileEntity);
        }
        if(!layerDrop.isEmpty()) {
            pushToOutputSlots(layerDrop);
        }
        doWorkSound(aBaseMetaTileEntity);
        return foundOreInLayer;
    }
    @Override
    public int getDecreaseFoodPerOperation(){
        return 2;
    }
    @Override
    public int getHungryDurationPerOperation(){
        return 480;
    }
    @Override
    public int getDamagePerOperation(){
        return 25;
    }
    @Override
    public int getProgresstimePerOre(){
        return 480;
    }

    @Override
    public boolean isReadyToDig() {
        return digHeight != 1 && haveConsumableItems();
    }

    @Override
    protected void endProcess(IGregTechTileEntity aBaseMetaTileEntity){
        digNextOre(aBaseMetaTileEntity);
    }

}