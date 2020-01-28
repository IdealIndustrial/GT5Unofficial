package gregtech.common.tileentities.machines.basic;

import cpw.mods.fml.common.eventhandler.Event;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.GT_UO_Fluid;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_Well extends GT_MetaTileEntity_BasicMachine {

    FluidStack fluid = null;
    public GT_MetaTileEntity_Well(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Slowly pumps underbedrock water", 0, 0, "Default.png", "",  new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_SIDE_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_SIDE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_FRONT_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_FRONT")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_TOP_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_TOP")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_BOTTOM_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/well/OVERLAY_BOTTOM")));
    }

    public GT_MetaTileEntity_Well(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 9, aGUIName, aNEIName);
    }

    public GT_MetaTileEntity_Well(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 9, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Well(this.mName, this.mTier, this.mDescriptionArray, this.mTextures, this.mGUIName, this.mNEIName);
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
            FluidStack fs = GT_UndergroundOil.undergroundOilReadInformation(getBaseMetaTileEntity());
            if(GT_Utility.areFluidsEqual(fs,new FluidStack(FluidRegistry.WATER,1000),true) && testForAir()) {
                fluid.amount += fs.amount;
                if(fluid.amount>=1000) {
                    aBaseMetaTileEntity.setActive(true);
                    fluid.amount = 1000;
                }
            }
        }

    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if(getBaseMetaTileEntity().isClientSide())
            return true;
        if(fluid == null||fluid.amount<1000)
            return false;
        ItemStack tStack = aPlayer.getHeldItem();
        ItemStack aStack = GT_Utility.fillFluidContainer(fluid,tStack, false, true);
        if(aPlayer.getHeldItem().stackSize==1&&aStack!=null) {
            aPlayer.setCurrentItemOrArmor(0, aStack);
            fluid = null;
            getBaseMetaTileEntity().setActive(false);
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

    protected MovingObjectPosition getMovingObjectPositionFromPlayer(World p_77621_1_, EntityPlayer p_77621_2_, boolean p_77621_3_)
    {
        float f = 1.0F;
        float f1 = p_77621_2_.prevRotationPitch + (p_77621_2_.rotationPitch - p_77621_2_.prevRotationPitch) * f;
        float f2 = p_77621_2_.prevRotationYaw + (p_77621_2_.rotationYaw - p_77621_2_.prevRotationYaw) * f;
        double d0 = p_77621_2_.prevPosX + (p_77621_2_.posX - p_77621_2_.prevPosX) * (double)f;
        double d1 = p_77621_2_.prevPosY + (p_77621_2_.posY - p_77621_2_.prevPosY) * (double)f + (double)(p_77621_1_.isRemote ? p_77621_2_.getEyeHeight() - p_77621_2_.getDefaultEyeHeight() : p_77621_2_.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = p_77621_2_.prevPosZ + (p_77621_2_.posZ - p_77621_2_.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (p_77621_2_ instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)p_77621_2_).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return p_77621_1_.func_147447_a(vec3, vec31, p_77621_3_, !p_77621_3_, false);
    }
}
