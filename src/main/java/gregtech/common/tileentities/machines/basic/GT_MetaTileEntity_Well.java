package gregtech.common.tileentities.machines.basic;

import cpw.mods.fml.common.eventhandler.Event;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.net.GT_Packet_Block_Event;
import gregtech.api.net.GT_Packet_ExtendedBlockEvent;
import gregtech.api.objects.GT_IconContainer;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.GT_SidedTexture;
import gregtech.api.objects.GT_UO_Fluid;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_UndergroundOil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;

import static gregtech.api.enums.GT_Values.NW;

public class GT_MetaTileEntity_Well extends GT_MetaTileEntity_BasicTank {

    private static ArrayList<Fluid> allowedFluids = new ArrayList<>();
    private FluidStack fluid = null;
    private int mProgresstime = 0;

    public GT_MetaTileEntity_Well(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, "Slowly pumps up underbedrock water",
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_SIDE_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_SIDE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_FRONT_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_FRONT")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_TOP_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_TOP")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_BOTTOM_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_BOTTOM")));
    }

    public GT_MetaTileEntity_Well(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    public GT_MetaTileEntity_Well(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Well(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if((fluid!=null&&fluid.amount>=1000)||aBaseMetaTileEntity.isClientSide() || !aBaseMetaTileEntity.isAllowedToWork())
            return;
        mProgresstime++;
        if(mProgresstime>=60){
            mProgresstime = 0;
            FluidStack fs = GT_UndergroundOil.undergroundOil(getBaseMetaTileEntity(),1f);
            if(isFluidAllowed(fs.getFluid()) && testForAir()) {
                if(fluid == null)
                    fluid = fs;
                else
                    fluid.amount += fs.amount;
                if(fluid.amount>=1000) {
                    aBaseMetaTileEntity.setActive(true);
                    fluid.amount = 1000;
                    NW.sendPacketToAllPlayersInRange(getBaseMetaTileEntity().getWorld(), new GT_Packet_ExtendedBlockEvent(getBaseMetaTileEntity().getXCoord(), (short) getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), 129, fluid.getFluidID()), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getZCoord());
                }
            }
        }

    }

    protected boolean isFluidAllowed(Fluid aFluid){
        for(Fluid tFluid : allowedFluids){
            if(aFluid != null && tFluid != null && aFluid == tFluid)
                return true;
        }
        return false;
    }

    @Override
    public void onConfigLoad(GT_Config aConfig) {
        super.onConfigLoad(aConfig);
        String s =  aConfig.get(ConfigCategories.machineconfig,"well.allowedFluids","water|oil");
        String[] fs = s.split("\\|");
        for(String str : fs){
            allowedFluids.add(FluidRegistry.getFluid(str));
        }

    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if(aSide == 0 && aActive) {
            if(fluid == null)
                return mTextures[5][aColorIndex + 1];
            mTextures[4][aColorIndex + 1][0] = new GT_RenderedTexture(new GT_IconContainer(fluid.getFluid().getStillIcon(), null, null));
            return mTextures[4][aColorIndex + 1];
        }
        return mTextures[aSide == 0 ? 7 : aSide == 1 ?  aActive ? 4 : 5 : aSide > 1 && aSide < 6 ?  3 : 4][aColorIndex + 1];

    }



    @Override
    public void receiveExtendedBlockEvent(int aID, int aValue) {
        if(aID == 129)
            fluid = new FluidStack(FluidRegistry.getFluid(aValue),10);
        else if(aID == 130)
            fluid = null;
    }

    /*for (int i = 0; i < aTextures.length - 1; i++)
            if (aTextures[i] != null) for (byte c = -1; c < 16; c++) {
                if (rTextures[i][c + 1] == null) {
                    if(i == 4) {
                        rTextures[i][c + 1] = new ITexture[]{ null, aTextures[i]}; //null for fluid texture
                    }
                    else
                        rTextures[i][c + 1] = new ITexture[]{aTextures[i]};
                }
            }*/
    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[14][17][];
        aTextures = Arrays.copyOf(aTextures, 14);

        for (int i = 0; i < aTextures.length; i++)
            if (aTextures[i] != null) for (byte c = -1; c < 16; c++) {
                if (rTextures[i][c + 1] == null) {
                    if(i == 4)
                        rTextures[i][c + 1] = new ITexture[]{ new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/DEFAULT_FLUID")), aTextures[i]}; //null for fluid texture
                    else
                        rTextures[i][c + 1] = new ITexture[]{aTextures[i]};
                }
            }
        return rTextures;
    }

    @Override
    public boolean onWrenchRightClick(byte aSide, byte aWrenchingSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if(getBaseMetaTileEntity().isClientSide()) {
            return true;
        }
        if(fluid == null)
            GT_Utility.sendChatToPlayer(aPlayer,"Currently is empty");
        else if (fluid.amount < 1000)
            GT_Utility.sendChatToPlayer(aPlayer,"Currently is "+ fluid.amount/10 +"% ready");
        if(fluid == null||fluid.amount<1000)
            return false;
        ItemStack tStack = aPlayer.getHeldItem();
        ItemStack aStack = GT_Utility.fillFluidContainer(fluid,tStack, false, true);
        if(aPlayer.getHeldItem().stackSize==1&&aStack!=null) {
            aPlayer.setCurrentItemOrArmor(0, aStack);
            fluid = null;
            getBaseMetaTileEntity().setActive(false);
            NW.sendPacketToAllPlayersInRange(getBaseMetaTileEntity().getWorld(), new GT_Packet_ExtendedBlockEvent(getBaseMetaTileEntity().getXCoord(), (short) getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), 130, 0), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getZCoord());

            return true;
        }
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setTag("fFluid",fluid.writeToNBT(new NBTTagCompound()));
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        fluid = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("fFluid"));
    }

    public boolean testForAir(){
        int i = -1;
        Block tBlock =getBaseMetaTileEntity().getBlockOffset(0,i,0);
        while(tBlock!= Blocks.bedrock){
            if(!tBlock.isAir(getBaseMetaTileEntity().getWorld(),getBaseMetaTileEntity().getXCoord(),getBaseMetaTileEntity().getYCoord()+i,getBaseMetaTileEntity().getZCoord()))
                return false;
            i--;
            tBlock = getBaseMetaTileEntity().getBlockOffset(0,i,0);

        }
        return true;
    }

    @Override
    public boolean doesFillContainers() {
        return false;
    }

    @Override
    public boolean doesEmptyContainers() {
        return false;
    }

    @Override
    public boolean canTankBeFilled() {
        return false;
    }

    @Override
    public boolean canTankBeEmptied() {
        return false;
    }

    @Override
    public boolean displaysItemStack() {
        return false;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }
}
