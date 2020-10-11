package gregtech.common.tileentities.machines.basic;

import cpw.mods.fml.common.registry.GameRegistry;

import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_BasicTank;
import gregtech.api.gui.GT_GUIContainer_BasicTank;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import static gregtech.api.enums.GT_Values.D1;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.util.GT_Utility.getFakePlayer;

public class GT_MetaTileEntity_Pump extends GT_MetaTileEntity_BasicDrillerBase {

    static {
        RADIUS = new int[]{8, 10, 20, 60,180,540,48,56,64,72,80};
        SPEED = new int[]{80, 80, 40, 20, 10, 5,5,2,2,2,2};
        ENERGY = new int[]{8, 4, 16, 64, 256, 1024, 2048 ,32768,131072,524288};
    }

    public GT_MetaTileEntity_Pump(int aID, String aName, String aNameRegional, int aTier) {
        super(aID,aName,aNameRegional,aTier,new String[]{"The best way to empty Oceans!",
                        "Pumping Area: " + (RADIUS[aTier]* 2 + 1) + "x" + (RADIUS[aTier] * 2 + 1),
                "Uses: "+ENERGY[aTier]+" EU per tick",
                "Pumps one fluid block each "+SPEED[aTier]+" ticks"}
                                ,2,2,"Pump.png",
                new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),
                new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/miner/OVERLAY_FRONT_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/miner/OVERLAY_FRONT")),
                new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ADV_PUMP),
                new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT),new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)
                );
    }


    public GT_MetaTileEntity_Pump(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName,aTier,aDescription,aTextures,2,1,"Miner.png");
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Pump(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return false;
    }


    @Override
    public boolean moveOneDown(IGregTechTileEntity aBaseMetaTileEntity) {
        if (aBaseMetaTileEntity.getYCoord() + drillY - 1 < 0
                ||!canPump(aBaseMetaTileEntity,0,drillY-1,0)
                || !GT_Utility.setBlockByFakePlayer(getFakePlayer(aBaseMetaTileEntity), aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord() + drillY - 1, aBaseMetaTileEntity.getZCoord(), MINING_PIPE_TIP_BLOCK, 0, true)) {
            isPickingPipes = true;
            return false;
        }
        if (aBaseMetaTileEntity.getBlockOffset(0, drillY, 0) == MINING_PIPE_TIP_BLOCK) {
            aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord() + drillY, aBaseMetaTileEntity.getZCoord(), MINING_PIPE_BLOCK);
        }
        miningPipes:
        {
            for (int i = 0; i < mInputSlotCount; i++) {
                ItemStack s = getInputAt(i);
                if (s != null && s.getItem() == MINING_PIPE.getItem() && s.stackSize > 0) {
                    s.stackSize--;
                    if (s.stackSize == 0) {
                        mInventory[getInputSlot() + i] = null;
                    }
                    break miningPipes;
                }
            }
            waitMiningPipe = true;
            return false;
        }
        pumpBlock(aBaseMetaTileEntity,0,drillY-1,0);
        aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord() + drillY - 1, aBaseMetaTileEntity.getZCoord(), MINING_PIPE_TIP_BLOCK);
        drillY--;
        drillZ = -RADIUS[mTier];
        drillX = -RADIUS[mTier];
        return true;
    }

    @Override
    public boolean hasFreeSpace() {
        return getFluid()==null||getFluid().amount<=getCapacity()-1000;
    }

    @Override
    public boolean workBlock(IGregTechTileEntity aBaseMetaTileEntity) {
        return pumpBlock(aBaseMetaTileEntity,drillX,drillY,drillZ);
    }

    public boolean canPump(IGregTechTileEntity aBaseTileEntity, int aX, int aY, int aZ) {
        if(aBaseTileEntity.getAirOffset(aX,aY,aZ))
            return true;
        Block tBlock = aBaseTileEntity.getBlockOffset(aX,aY,aZ);
        return tBlock == Blocks.water||tBlock == Blocks.lava|| tBlock == Blocks.flowing_lava || tBlock == Blocks.flowing_water||tBlock instanceof IFluidBlock;
    }



    public boolean pumpBlock(IGregTechTileEntity aBaseTileEntity, int aX, int aY, int aZ){
        if(aBaseTileEntity.getAirOffset(aX,aY,aZ))
            return false;
        Block tBlock = aBaseTileEntity.getBlockOffset(aX,aY,aZ);
        if(tBlock == Blocks.water){
            if(aBaseTileEntity.getWorld().setBlock(aBaseTileEntity.getXCoord()+aX,aBaseTileEntity.getYCoord()+aY,aBaseTileEntity.getZCoord()+aZ,Blocks.air,0,2)) {
                if (mOutputFluid == null)
                    mOutputFluid = GT_ModHandler.getWater(1000L);
                else if (mOutputFluid.equals(GT_ModHandler.getWater(1000L)))
                    mOutputFluid.amount += 1000;
                return true;
            }

        }
        else if(tBlock == Blocks.lava){
            if(aBaseTileEntity.getWorld().setBlock(aBaseTileEntity.getXCoord()+aX,aBaseTileEntity.getYCoord()+aY,aBaseTileEntity.getZCoord()+aZ,Blocks.air,0,2)) {
                if (mOutputFluid == null)
                    mOutputFluid = GT_ModHandler.getLava(1000L);
                else if (mOutputFluid.equals(GT_ModHandler.getLava(1000L)))
                    mOutputFluid.amount += 1000;
                return true;
            }

        }
        else if(tBlock == Blocks.flowing_lava || tBlock == Blocks.flowing_water) {
            aBaseTileEntity.getWorld().setBlock(aBaseTileEntity.getXCoord() + aX, aBaseTileEntity.getYCoord() + aY, aBaseTileEntity.getZCoord() + aZ,Blocks.air,0,2);
            return true;
        }
        else if(tBlock instanceof IFluidBlock){
            FluidStack fStack = ((IFluidBlock)tBlock).drain(aBaseTileEntity.getWorld(),aBaseTileEntity.getXCoord() + aX, aBaseTileEntity.getYCoord() + aY, aBaseTileEntity.getZCoord() + aZ,false);
            aBaseTileEntity.getWorld().setBlock(aBaseTileEntity.getXCoord() + aX, aBaseTileEntity.getYCoord() + aY, aBaseTileEntity.getZCoord() + aZ,Blocks.air,0,2);
            if(fStack==null)
                return true;
            if(mOutputFluid== null)
                mOutputFluid = fStack;
            else if(fStack.equals(mOutputFluid)) {
                mOutputFluid.amount += fStack.amount;
                return true;
            }

        }
        return false;

    }

    @Override
    public int getCapacity() {
        return 16000 * this.mTier;
    }

}
