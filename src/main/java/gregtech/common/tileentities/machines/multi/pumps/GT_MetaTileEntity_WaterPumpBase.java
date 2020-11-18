package gregtech.common.tileentities.machines.multi.pumps;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Fluid;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class GT_MetaTileEntity_WaterPumpBase extends GT_MetaTileEntity_MultiBlockBase {
    public static HashMap<Integer,HashMap<Long, GT_MetaTileEntity_WaterPumpBase>> mPumps = new HashMap<>();
    public ArrayList<GT_MetaTileEntity_WaterPumpBase> mConnectedPumps = new ArrayList<>(2);
    protected ArrayList<GT_MetaPipeEntity_Fluid> mPipes = new ArrayList<>();
    protected GT_MetaPipeEntity_Fluid mPipe = null;
    public double mEfficiency = 0, mEfficiencyRate = 1d;
    protected int mWorkCycles = 0;
    public int mWaterSurface = 0;
    protected int mHeadX = 0, mHeadY = -1, mHeadZ = 0;
    protected int mFilledPipes = 0;
    public int mMainFacing = 2;
    protected boolean mRiver = false;

    public GT_MetaTileEntity_WaterPumpBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_WaterPumpBase(String aName) {
        super(aName);
    }

   /* public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_WaterPumpBase(this.mName);
    }*/

    public abstract int getRadius();

    public abstract int getSurfaceBlocksCount();

    public abstract int getPipeLength();

    public abstract double getOutputRate();

    public abstract int getFilterMeta();

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        mMainFacing = aNBT.getInteger("mMainF");
        mEfficiencyRate = aNBT.getDouble("mEffRate");
        if (mEfficiencyRate < 0.0000001d)
            mEfficiencyRate = 1d;
        mWorkCycles = aNBT.getInteger("mCycles");
        super.loadNBTData(aNBT);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("mMainF", mMainFacing);
        aNBT.setDouble("mEffRate", mEfficiencyRate);
        aNBT.setInteger("mCycles", mWorkCycles);
        super.saveNBTData(aNBT);
    }

    @Override
    public void onProcessEnd() {
        if (++mWorkCycles == 16800) {
            mWorkCycles = 0;
            mEfficiencyRate *= 0.99d;
        }
    }

    double waterToOutput = 0f;
    @Override
    public boolean onRunningTick(ItemStack aStack) {
        boolean run = super.onRunningTick(aStack);
        if (!run)
            return false;
        double tOut = getOutputRate()*(mEfficiency/10000) + waterToOutput;
        tOut *= mEfficiencyRate;
        int rOut = (int)tOut;
        waterToOutput = tOut - rOut;
        addOutput(mRiver ? GT_ModHandler.getWater(rOut) : Materials.SaltWater.getFluid(rOut));
        return true;
    }

    public boolean checkBiome(BiomeGenBase aBiome) {
        if (aBiome instanceof BiomeGenRiver && mRiver)
            return true;
        return aBiome instanceof BiomeGenOcean && !mRiver;
    }

    @Override
    public boolean addOutput(FluidStack aLiquid) {
        while (mFilledPipes < mPipes.size() && aLiquid.amount > 0) {
            GT_MetaPipeEntity_Fluid tPipe = mPipes.get(mFilledPipes);
           int aConsume = tPipe.fill_default(ForgeDirection.DOWN, aLiquid, true);
           if(aConsume <= 0)
               mFilledPipes++;
           aLiquid.amount -= aConsume;
        }
        if (mFilledPipes == mPipes.size()) {
            int aConsume = mPipe.fill_default(ForgeDirection.DOWN, aLiquid, true);
            if(aConsume <= 0)
                mFilledPipes++;
        }
        return super.addOutput(aLiquid);
    }

    public boolean constructStructure(byte aDir){
        IGregTechTileEntity tBase = getBaseMetaTileEntity();
        TileEntity[] tTiles = new TileEntity[3];
        tTiles[0] = tBase.getTileEntityAtSide(aDir);
        tTiles[1] = tBase.getTileEntityAtSide((byte)1);
        tTiles[2] = tBase.getTileEntityOffset(ForgeDirection.getOrientation(aDir).offsetX, 1, ForgeDirection.getOrientation(aDir).offsetZ);
        if(!addToStructure(tTiles[0], tTiles[1], tTiles[2], false))
            return false;
        return addToStructure(tTiles[0], tTiles[1], tTiles[2], true);
    }

    public abstract boolean addToStructure(TileEntity aTileEntityInput, TileEntity aTileEntityPipe, TileEntity aTileEntityOutput,  boolean aDoAdd);

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mScrewdriver = mWrench = mCrowbar = mHardHammer = mSoftHammer = mSolderingTool = true;

      /*  TileEntity tileEntity1 = aBaseMetaTileEntity.getTileEntityOffset(0,1,0);
        TileEntity tileEntity2 = aBaseMetaTileEntity.getTileEntityOffset(0,2,0);
        if(!(tileEntity1 instanceof IGregTechTileEntity && tileEntity2 instanceof IGregTechTileEntity && addInputToMachineList((IGregTechTileEntity)tileEntity1, 48) && addOutputToMachineList((IGregTechTileEntity)tileEntity2,48)))
            return false;*/ // true checkMachine

        byte tDir = -1;
        for (byte i = 2; i < 6; i++) {
            if (i == aBaseMetaTileEntity.getFrontFacing())
                continue;
            if (constructStructure(i)) {
                tDir = i;
                break;
            }
        }
        if (tDir == -1 || tDir == mMainFacing)
            return false;

        TileEntity aPipe = aBaseMetaTileEntity.getTileEntityAtSide((byte)1);
        if (aPipe instanceof IGregTechTileEntity && ((IGregTechTileEntity)aPipe).getMetaTileEntity() instanceof GT_MetaPipeEntity_Fluid) {
            GT_MetaPipeEntity_Fluid qPipe = (GT_MetaPipeEntity_Fluid)((IGregTechTileEntity)aPipe).getMetaTileEntity();
            qPipe.mStructurePart = true;
            for (byte i = 0; i < 6; i++)
                qPipe.disconnect(i);
            qPipe.connect((byte)0);
            qPipe.connect(tDir);
            mPipe = qPipe;
        }
        else
            return false;





        mPipes.clear();
        TileEntity tile = aBaseMetaTileEntity.getTileEntityAtSide((byte)mMainFacing);
        GT_MetaPipeEntity_Fluid tPipe = null;
        if(tile instanceof IGregTechTileEntity && ((IGregTechTileEntity)tile).getMetaTileEntity() instanceof GT_MetaPipeEntity_Fluid)
            tPipe = (GT_MetaPipeEntity_Fluid)((IGregTechTileEntity)tile).getMetaTileEntity();
        if(tPipe == null)
            return false;

        if (!checkHead(mPipes, 0, tPipe, mMainFacing))
            return false;
        for(GT_MetaPipeEntity_Fluid pipe : mPipes)
            pipe.mStructurePart = true;


        int aX = getHeadX(), aZ = getHeadZ();
        BiomeGenBase tBiome = aBaseMetaTileEntity.getWorld().getBiomeGenForCoords(aX,aZ);
        if (tBiome instanceof BiomeGenRiver) {
            mRiver = true;
        }
        else if(tBiome instanceof BiomeGenOcean) {
            mRiver = false;
        }
        else {
            return false;
        }
       /* if (mPumps.get(aBaseMetaTileEntity.getWorld().provider.dimensionId) != null &&
                mPumps.get(aBaseMetaTileEntity.getWorld().provider.dimensionId).get(((long)aX) << 32 + (aZ)) != null)
            return false;*/

        mConnectedPumps.clear();
        ArrayList<GT_MetaTileEntity_WaterPumpBase> tAffectedPumps = new ArrayList<>();
        mWaterSurface = 0;
        mPumps.computeIfAbsent(aBaseMetaTileEntity.getWorld().provider.dimensionId, k -> new HashMap<>());
        fillList(tAffectedPumps, new HashSet<>(), getHeadX(), getHeadZ(), 0);
        for (GT_MetaTileEntity_WaterPumpBase p : tAffectedPumps) {
            if((Math.abs(getHeadX() - p.getHeadX()) <= 4) || Math.abs(getHeadZ() - p.getHeadZ()) <= 4)
                return false;
        }
        for(GT_MetaTileEntity_WaterPumpBase d : tAffectedPumps) {
            d.onAdded(this);
        }
        mPumps.get(aBaseMetaTileEntity.getWorld().provider.dimensionId).put((((long)aX) << 32) + aZ, this);

        mConnectedPumps.addAll(tAffectedPumps);
        recalculateEfficiency();
        return true;
    }

    public boolean checkHead(ArrayList<GT_MetaPipeEntity_Fluid> aPipes, int aDepth, GT_MetaPipeEntity_Fluid aCurrentNode, int aSide){
        if (aDepth > getPipeLength())
            return false;
        if (aCurrentNode.mCapacity < getOutputRate())
            return false;
        int tSide = GT_Utility.getOppositeSide(aSide);
        int nextSide = -1;
        int tConnections = 0;
        for (int i = 0; i < 6; i++) {
           if (aCurrentNode.isConnectedAtSide(i)) {
               if (i != tSide)
                   nextSide = i;
               tConnections++;
           }
        }
        if (tConnections > 2 ) {
            return false;
        }

        TileEntity tTile = aCurrentNode.getBaseMetaTileEntity().getTileEntityAtSide((byte)nextSide);
        if (tTile instanceof IGregTechTileEntity && ((IGregTechTileEntity)tTile).getMetaTileEntity() instanceof GT_MetaPipeEntity_Fluid){
            if (checkHead(aPipes, ++aDepth, (GT_MetaPipeEntity_Fluid)((IGregTechTileEntity)tTile).getMetaTileEntity(), nextSide)) {
                aPipes.add(aCurrentNode);
                return true;
            }
            aCurrentNode.mStructurePart = false;
        }

        byte tOccurrences = 0;
        byte sSide = -1;
        for (byte i = 0; i < 6; i++) {
            if (aCurrentNode.getBaseMetaTileEntity().getBlockAtSide(i) == GregTech_API.sBlockCasings8 && aCurrentNode.getBaseMetaTileEntity().getMetaIDAtSide(i) == getFilterMeta()) {
                tOccurrences++;
                sSide = i;
            }
        }
        if (tOccurrences == 1) {
            mHeadX = aCurrentNode.getBaseMetaTileEntity().getOffsetX(sSide, 1);
            mHeadY = aCurrentNode.getBaseMetaTileEntity().getOffsetY(sSide, 1);
            mHeadZ = aCurrentNode.getBaseMetaTileEntity().getOffsetZ(sSide, 1);
            aPipes.add(aCurrentNode);
            aCurrentNode.mStructurePart = true;
            aCurrentNode.connect(sSide);
            return true;
        }

        return false;
    }

    @Override
    public int getCapacity() {
        return 1;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection aSide) {
        if (aSide.ordinal() != getBaseMetaTileEntity().getFrontFacing() && aSide.ordinal() == mMainFacing || aSide == ForgeDirection.UP)
            return super.getTankInfo(aSide);
        return new FluidTankInfo[0];
    }

    public int getMaxPipeLength(){
        return 10;
    }

    public void fillList(ArrayList<GT_MetaTileEntity_WaterPumpBase> aAffectedPumps, HashSet<Long> aPassedPositions, int aX, int aZ, int aDepth) {
        if (Math.abs(getHeadX() - aX) > getRadius() || Math.abs(getHeadZ() - aZ) > getRadius() || aDepth > 128)
            return;
        long tPosition = (((long)aX) << 32) + aZ;
        World tWorld = getBaseMetaTileEntity().getWorld();
        if (aDepth != 0 && (aPassedPositions.contains(tPosition) || !checkBiome(tWorld.getBiomeGenForCoords(aX, aZ)) ||
                (tWorld.getBlock(aX, getHeadY(), aZ) != Blocks.water)&&!(tWorld.getBlock(aX, getHeadY(), aZ) == GregTech_API.sBlockCasings8 && tWorld.getBlockMetadata(aX,getHeadY(),aZ)>=10)))
            return;
        mWaterSurface++;

       // tWorld.setBlock(aX, getBaseMetaTileEntity().getYCoord() + 1, aZ, Blocks.wool, 10, 2);
        aPassedPositions.add(tPosition);
        aDepth++;
        GT_MetaTileEntity_WaterPumpBase tPumpInChunk = mPumps.get(tWorld.provider.dimensionId).get(tPosition);
        if (tPumpInChunk != null && tPumpInChunk != this)
            aAffectedPumps.add(tPumpInChunk);
        //tWorld.setBlock(aX, getBaseMetaTileEntity().getYCoord()+10, aZ, Blocks.wool, 10, 2);
        fillList(aAffectedPumps, aPassedPositions, aX + 1, aZ, aDepth);
        fillList(aAffectedPumps, aPassedPositions, aX, aZ + 1, aDepth);
        fillList(aAffectedPumps, aPassedPositions, aX - 1, aZ, aDepth);
        fillList(aAffectedPumps, aPassedPositions, aX, aZ - 1, aDepth);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        for(GT_MetaTileEntity_WaterPumpBase d : mConnectedPumps)
            d.onRemoved(this);
        for(GT_MetaPipeEntity_Fluid tPipe : mPipes)
            tPipe.mStructurePart = false;
        if (mPipe != null)
            mPipe.mStructurePart = false;
        if(mPumps.get(getBaseMetaTileEntity().getWorld().provider.dimensionId) != null)
            mPumps.get(getBaseMetaTileEntity().getWorld().provider.dimensionId).remove((((long)getHeadX()) << 32) + getHeadZ());
    }

    @Override
    public boolean onWrenchRightClick(byte aSide, byte aWrenchingSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (!aPlayer.isSneaking()) {
            if (aWrenchingSide > 1 && getBaseMetaTileEntity().isServerSide()) {
                mMainFacing = aWrenchingSide;
                getBaseMetaTileEntity().sendBlockEvent((byte)1, (byte)mMainFacing);
                return true;
            }
        }
        else {
            if (getBaseMetaTileEntity().isValidFacing(aWrenchingSide)) {
                getBaseMetaTileEntity().setFrontFacing(aWrenchingSide);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onValueUpdate(byte aValue) {
        if (aValue != mMainFacing) {
            mMainFacing = aValue;
            getBaseMetaTileEntity().rebakeMap();
        }

    }

    public abstract ITexture getBaseTexture();

    public abstract IIconContainer getInputFacing();

    public abstract IIconContainer[] getFacings();



    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{getBaseTexture(), new GT_RenderedTexture(getFacings()[aActive ? 0 : 1])};
        }
        if (aSide == mMainFacing) {
            return new ITexture[]{getBaseTexture(), new GT_RenderedTexture(getInputFacing())};
        }
        return new ITexture[]{getBaseTexture()};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiblockDisplay.png");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
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
    public long maxEUStore() {
        return 0;
    }

    @Override
    public void onServerStart() {
        mPumps.clear();
    }

    public int getHeadX() {
        return mHeadX;
    }

    public int getHeadY() {
        return mHeadY;
    }

    public int getHeadZ() {
        return mHeadZ;
    }

    public boolean onAdded(GT_MetaTileEntity_WaterPumpBase aNewPump) {
        if (mConnectedPumps.contains(aNewPump) && aNewPump.getBaseMetaTileEntity() != null && !aNewPump.getBaseMetaTileEntity().isDead())
            return false;
        mConnectedPumps.add(aNewPump);
        recalculateEfficiency();
        return true;
    }

    public void onRemoved(GT_MetaTileEntity_WaterPumpBase aPump) {
        mConnectedPumps.remove(aPump);
        recalculateEfficiency();
    }

    public void recalculateEfficiency( ){
        mEfficiency = 10000f;
        if (mConnectedPumps.size() > 0)
            mEfficiency *= 1f / ((float)mConnectedPumps.size());
        if (mWaterSurface < getSurfaceBlocksCount())
            mEfficiency *= ((float)mWaterSurface)/getSurfaceBlocksCount();
    }

    @Override
    public String[] getInfoData() {
        return new String[]{"Progress:", (mProgresstime / 20) + "secs", (mMaxProgresstime / 20) + "secs", "Efficiency:", (mEfficiency / 100.0F) + "%", "Intake efficiency: " + (int)(mEfficiencyRate * 100) + "." + (int)(mEfficiencyRate*1000)%100 + "%", "Water surface covered: "+(Math.min(getSurfaceBlocksCount(),mWaterSurface)) + "/" + getSurfaceBlocksCount()+ " blocks", "Problems:", String.valueOf((getIdealStatus() - getRepairStatus()))};
    }

}
