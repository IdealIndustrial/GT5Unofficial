package gregtech.common.covers;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_NeedMaintainance extends GT_CoverBehavior {

	public boolean isRotor(ItemStack aRotor) {
		if (aRotor == null || !(aRotor.getItem() instanceof GT_MetaGenerated_Tool) || aRotor.getItemDamage() < 170 || aRotor.getItemDamage() > 176)
		{return true;}
		else 
		{return false;} 
	}

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        boolean needsRepair = false;
		if (aTileEntity instanceof IGregTechTileEntity) {
			IGregTechTileEntity tTileEntity = (IGregTechTileEntity) aTileEntity;
			IMetaTileEntity mTileEntity = tTileEntity.getMetaTileEntity();
			if (mTileEntity instanceof GT_MetaTileEntity_MultiBlockBase) {
				GT_MetaTileEntity_MultiBlockBase multi = (GT_MetaTileEntity_MultiBlockBase) mTileEntity;
				int ideal = multi.getIdealStatus();
				int real = multi.getRepairStatus();
				ItemStack tRotor = multi.getRealInventory()[1];
				if ((aCoverVariable == 0 || aCoverVariable == 1) && (ideal - real > 0)) {
				    needsRepair = true;
				}
				if ((aCoverVariable == 2 || aCoverVariable == 3) && (ideal - real > 1)) {
				    needsRepair = true;
				}
				if ((aCoverVariable == 4 || aCoverVariable == 5) && (ideal - real > 2)) {
				    needsRepair = true;
				}
				if ((aCoverVariable == 6 || aCoverVariable == 7) && (ideal - real > 3)) {
				    needsRepair = true;
				}
				if ((aCoverVariable == 8 || aCoverVariable == 9) && (ideal - real > 4)) {
				    needsRepair = true;
				}
				if (!(isRotor(tRotor))) {
					long tMax = GT_MetaGenerated_Tool.getToolMaxDamage(tRotor);
					long tCur = GT_MetaGenerated_Tool.getToolDamage(tRotor);
					int mEUt = multi.mEUt;
					long mExpectedDamage = Math.round(Math.min(mEUt / multi.damageFactorLow, Math.pow(mEUt, multi.damageFactorHigh)));
					if (aCoverVariable == 10 || aCoverVariable == 11) {
						needsRepair = isRotor(tRotor) ? true : (tCur >= tMax * 8 / 10) ? true : false;
					}
                	if (aCoverVariable == 12 || aCoverVariable == 13) {
						needsRepair = isRotor(tRotor) ? true : ((tCur + mExpectedDamage * 2) >= tMax) ? true : false;
                	}
				}
            }
        }
        if (aCoverVariable % 2 == 0) {
            needsRepair = !needsRepair;
        }

        aTileEntity.setOutputRedstoneSignal(aSide, (byte) (needsRepair ? 0 : 15));
        aTileEntity.setOutputRedstoneSignal(GT_Utility.getOppositeSide(aSide), (byte) (needsRepair ? 0 : 15));
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking()? -1 : 1)) % 14;
        if(aCoverVariable < 0){aCoverVariable = 13;}
        switch(aCoverVariable) {
            case 0: GT_Utility.sendChatToPlayer(aPlayer, trans("056", "Emit if 1 Maintenance Needed")); break;
            case 1: GT_Utility.sendChatToPlayer(aPlayer, trans("057", "Emit if 1 Maintenance Needed(inverted)")); break;
            case 2: GT_Utility.sendChatToPlayer(aPlayer, trans("058", "Emit if 2 Maintenance Needed")); break;
            case 3: GT_Utility.sendChatToPlayer(aPlayer, trans("059", "Emit if 2 Maintenance Needed(inverted)")); break;
            case 4: GT_Utility.sendChatToPlayer(aPlayer, trans("060", "Emit if 3 Maintenance Needed")); break;
            case 5: GT_Utility.sendChatToPlayer(aPlayer, trans("061", "Emit if 3 Maintenance Needed(inverted)")); break;
            case 6: GT_Utility.sendChatToPlayer(aPlayer, trans("062", "Emit if 4 Maintenance Needed")); break;
            case 7: GT_Utility.sendChatToPlayer(aPlayer, trans("063", "Emit if 4 Maintenance Needed(inverted)")); break;
            case 8: GT_Utility.sendChatToPlayer(aPlayer, trans("064", "Emit if 5 Maintenance Needed")); break;
            case 9: GT_Utility.sendChatToPlayer(aPlayer, trans("065", "Emit if 5 Maintenance Needed(inverted)")); break;
            case 10: GT_Utility.sendChatToPlayer(aPlayer, trans("066", "Emit if rotor needs maintenance low accuracy mod")); break;
            case 11: GT_Utility.sendChatToPlayer(aPlayer, trans("067", "Emit if rotor needs maintenance low accuracy mod(inverted)")); break;
            case 12: GT_Utility.sendChatToPlayer(aPlayer, trans("068", "Emit if rotor needs maintenance high accuracy mod")); break;
            case 13: GT_Utility.sendChatToPlayer(aPlayer, trans("069", "Emit if rotor needs maintenance high accuracy mod(inverted)")); break;
        }
        return aCoverVariable;
    }

    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 60;
    }

}
