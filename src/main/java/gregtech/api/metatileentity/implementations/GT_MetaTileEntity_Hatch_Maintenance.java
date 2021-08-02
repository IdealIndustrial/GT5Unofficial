package gregtech.api.metatileentity.implementations;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_2by2;
import gregtech.api.gui.GT_Container_MaintenanceHatch;
import gregtech.api.gui.GT_GUIContainer_2by2;
import gregtech.api.gui.GT_GUIContainer_MaintenanceHatch;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.List;

public class GT_MetaTileEntity_Hatch_Maintenance extends GT_MetaTileEntity_Hatch {
    public boolean mWrench = false, mScrewdriver = false, mSoftHammer = false, mHardHammer = false, mSolderingTool = false, mCrowbar = false, mAuto;

    public GT_MetaTileEntity_Hatch_Maintenance(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "For maintaining Multiblocks");
        mAuto = false;
    }

    public GT_MetaTileEntity_Hatch_Maintenance(int aID, String aName, String aNameRegional, int aTier, boolean aAuto) {
        super(aID, aName, aNameRegional, aTier, 4, "For automatically maintaining Multiblocks");
        mAuto = aAuto;
    }

    public GT_MetaTileEntity_Hatch_Maintenance(String aName, int aTier, String aDescription, ITexture[][][] aTextures, boolean aAuto) {
        super(aName, aTier, aAuto ? 4 : 1, aDescription, aTextures);
        mAuto = aAuto;
    }

    public GT_MetaTileEntity_Hatch_Maintenance(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, boolean aAuto) {
        super(aName, aTier, aAuto ? 4 : 1, aDescription, aTextures);
        mAuto = aAuto;
    }

    @Override
    public String[] getDescription() {
        if (mAuto) {
            String[] desc = new String[mDescriptionArray.length + 3];
            System.arraycopy(mDescriptionArray, 0, desc, 0, mDescriptionArray.length);
            desc[mDescriptionArray.length] = "4 Ducttape, 2 Lubricant Cells";
            desc[mDescriptionArray.length + 1] = "4 Steel Screws, 2 Adv Circuits";
            desc[mDescriptionArray.length + 2] = "For each autorepair";
            return desc;
        } else {
            String[] desc = new String[mDescriptionArray.length + 1];
            System.arraycopy(mDescriptionArray, 0, desc, 0, mDescriptionArray.length);
            desc[mDescriptionArray.length] = "Cannot be shared between Multiblocks!";
            return desc;
        }
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_MAINTENANCE)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_MAINTENANCE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_DUCTTAPE)};
    }

    @Override
    public void initDefaultModes(NBTTagCompound aNBT) {
        getBaseMetaTileEntity().setActive(true);
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return mAuto && GT_Mod.gregtechproxy.mAMHInteraction;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        if (aTileEntity.getMetaTileID() == 111)
            return new GT_MetaTileEntity_Hatch_Maintenance(mName, mTier, mDescriptionArray, mTextures, true);
        return new GT_MetaTileEntity_Hatch_Maintenance(mName, mTier, mDescriptionArray, mTextures, false);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        if (aSide == aBaseMetaTileEntity.getFrontFacing()) aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if (mAuto) return new GT_Container_2by2(aPlayerInventory, aBaseMetaTileEntity);
        return new GT_Container_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if (mAuto) return new GT_GUIContainer_2by2(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
        return new GT_GUIContainer_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    public void updateSlots() {
        for (int i = 0; i < mInventory.length; i++)
            if (mInventory[i] != null && mInventory[i].stackSize <= 0) mInventory[i] = null;
    }
	
	private boolean areStacksSame(ItemStack a, ItemStack b) {
		return (GT_Utility.areUnificationsEqual(a, b, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, a), b, true));
	}
	
	private int countStack(ItemStack[] aList, ItemStack aStack) {
		if (aStack == null) return 0;
		
		int count = 0;
		
		for (ItemStack tStack : aList) {
			if (areStacksSame(tStack, aStack)) {
				count += tStack.stackSize;
			}
		}
		
		return count;
	}
	
	private ItemStack autoRepairKit = ItemList.AutoRepairKit.get(1);
	
	private ItemStack[] getRepairStack() {
		return new ItemStack[]{
			ItemList.Duct_Tape.get(4),
			GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Lubricant, 2),
			GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 4),
			GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2)
		};
	}
	
	private void fullRepair() {
		this.mCrowbar = true;
		this.mHardHammer = true;
		this.mScrewdriver = true;
		this.mSoftHammer = true;
		this.mSolderingTool = true;
		this.mWrench = true;
	}
	
	private void payCost (ItemStack[] repairCost) {
		for (ItemStack row : repairCost) {
			if (row == null) continue;
			
			int rowCost = row.stackSize;
			
			for (ItemStack aStack : mInventory) {
				if (areStacksSame(aStack, row)) {
					if (aStack.stackSize < rowCost) {
						rowCost -= aStack.stackSize;
						aStack.stackSize = 0;
					} else {
						aStack.stackSize -= rowCost;
						rowCost = 0;
						break;
					}
				}
			}
		}
		updateSlots();
	}

    public boolean autoMaintainance() {
        ItemStack[] repairCost = getRepairStack();
		
        if (repairCost.length > 0 && mInventory == null) {
			return false;
		}
		
		int kitsCount = countStack(mInventory, autoRepairKit);
		
		if (kitsCount >= autoRepairKit.stackSize) {
			payCost(new ItemStack[]{ autoRepairKit });
		} else {
			for (ItemStack row : repairCost) {
				int inventoryCount = countStack(mInventory, row);
				
				if (row.stackSize > inventoryCount) return false;
			}
			payCost(repairCost);
		}
		
		fullRepair();
		
		return true;
    }

    public void onToolClick(ItemStack aStack, EntityLivingBase aPlayer) {
        if (aStack == null || aPlayer == null) return;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sWrenchList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mWrench = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sScrewdriverList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mScrewdriver = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sSoftHammerList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mSoftHammer = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sHardHammerList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mHardHammer = true;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sCrowbarList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer))
            mCrowbar = true;
        if (GT_ModHandler.useSolderingIron(aStack, aPlayer)) mSolderingTool = true;
        if (GT_OreDictUnificator.isItemStackInstanceOf(aStack, "craftingDuctTape")) {
            mWrench = mScrewdriver = mSoftHammer = mHardHammer = mCrowbar = mSolderingTool = true;
            getBaseMetaTileEntity().setActive(false);
            aStack.stackSize--;
        }
        if (mSolderingTool && aPlayer instanceof EntityPlayerMP) {
            EntityPlayerMP tPlayer = (EntityPlayerMP) aPlayer;
            try {
                GT_Mod.achievements.issueAchievement(tPlayer, "maintainance");
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if(!(mAuto && GT_Mod.gregtechproxy.mAMHInteraction))
            return false;
		
		if (!isRepairItem(aStack))
			return false;
			
		return isItemNotFull(aStack);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if(!(mAuto && GT_Mod.gregtechproxy.mAMHInteraction))
            return false;
		
		if (!isRepairItem(aStack))
			return false;
			
		return isItemNotFull(aStack);
    }
	
	private boolean isRepairItem(ItemStack aStack) {
		if (areStacksSame(aStack, autoRepairKit)) {
			return true;
		}
		
        ItemStack[] mInputs = getRepairStack();
		
		for(ItemStack nStack :mInputs) {
            if (areStacksSame(aStack, nStack)) {
                return true;
            }
        }
        return false;
	}
	
	private boolean isItemNotFull (ItemStack aStack) {
		return countStack(mInventory, aStack) < 64;
	}
}
