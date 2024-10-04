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
import ic2.core.IHasGui;
import ic2.core.item.ItemToolbox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_Hatch_Maintenance extends GT_MetaTileEntity_Hatch {

    public enum RepairMethod {
        Manual, AutoKit, FullAuto
    }
    private static final ItemStack autoRepairKit = ItemList.AutoRepairKit.get(1);
    private static final ItemStack[] oldRepairItems = new ItemStack[]{
        ItemList.Duct_Tape.get(4),
        GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Lubricant, 2),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 4),
        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2)
    };

    public boolean mWrench = false, mScrewdriver = false, mSoftHammer = false, mHardHammer = false, mSolderingTool = false, mCrowbar = false;

    private final RepairMethod mMethod;

    public static GT_MetaTileEntity_Hatch_Maintenance Manual(int aID, String aName, String aNameRegional, int aTier) {
        return new GT_MetaTileEntity_Hatch_Maintenance(aID, aName, aNameRegional, aTier, RepairMethod.Manual);
    }

    public static GT_MetaTileEntity_Hatch_Maintenance AutoKit(int aID, String aName, String aNameRegional, int aTier) {
        return new GT_MetaTileEntity_Hatch_Maintenance(aID, aName, aNameRegional, aTier, RepairMethod.AutoKit);
    }

    public static GT_MetaTileEntity_Hatch_Maintenance FullAuto(int aID, String aName, String aNameRegional, int aTier) {
        return new GT_MetaTileEntity_Hatch_Maintenance(aID, aName, aNameRegional, aTier, RepairMethod.FullAuto);
    }

    private GT_MetaTileEntity_Hatch_Maintenance(int aID, String aName, String aNameRegional, int aTier, RepairMethod method) {
        super(aID, aName, aNameRegional, aTier, getBufferSize(method), getShortDescription(method));
        mMethod = method;
    }

    private GT_MetaTileEntity_Hatch_Maintenance(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, RepairMethod method) {
        super(aName, aTier, getBufferSize(method), aDescription, aTextures);
        mMethod = method;
    }

    public boolean isAuto() {
        return mMethod != RepairMethod.Manual;
    }

    private static int getBufferSize(RepairMethod method) {
        return method == RepairMethod.AutoKit ? 4 : 1;
    }

    private static String getShortDescription(RepairMethod method) {
        switch (method) {
            case Manual:
                return "For maintaining Multiblocks";
            case AutoKit:
                return "For automatically maintaining Multiblocks via repair kits";
            case FullAuto:
                return "For automatically maintaining Multiblocks without repair kits";
            default:
                return "";
        }
    }

    private String getCostDescription() {
        switch (mMethod) {
            case Manual:
                return "Cannot be shared between Multiblocks!";
            case AutoKit:
                return "1 Auto Repair Kit for each autorepair";
            case FullAuto:
                return "No need for maintaining!";
            default:
                return "";
        }
    }

    @Override
    public String[] getDescription() {
        return GT_Utility.concat(
                mDescriptionArray,
                getCostDescription()
                        .split("\n")
        );
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
        return isAuto() && GT_Mod.gregtechproxy.mAMHInteraction;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Hatch_Maintenance(mName, mTier, mDescriptionArray, mTextures, mMethod);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        }
        if (mMethod != RepairMethod.FullAuto && aSide == aBaseMetaTileEntity.getFrontFacing()) {
            aBaseMetaTileEntity.openGUI(aPlayer);
        }
        return true;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if (isAuto()) {
            return new GT_Container_2by2(aPlayerInventory, aBaseMetaTileEntity);
        }
        return new GT_Container_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if (isAuto()) {
            return new GT_GUIContainer_2by2(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
        }
        return new GT_GUIContainer_MaintenanceHatch(aPlayerInventory, aBaseMetaTileEntity);
    }

    public void updateSlots() {
        for (int i = 0; i < mInventory.length; i++) {
            if (mInventory[i] != null && mInventory[i].stackSize <= 0) {
                mInventory[i] = null;
            }
        }
    }

    private boolean areStacksSame(ItemStack a, ItemStack b) {
        return (GT_Utility.areUnificationsEqual(a, b, true) || GT_Utility.areUnificationsEqual(GT_OreDictUnificator.get(false, a), b, true));
    }

    private int countStack(ItemStack[] aList, ItemStack aStack) {
        if (aStack == null) {
            return 0;
        }

        int count = 0;

        for (ItemStack tStack : aList) {
            if (areStacksSame(tStack, aStack)) {
                count += tStack.stackSize;
            }
        }

        return count;
    }

    private void fullRepair() {
        this.mCrowbar = true;
        this.mHardHammer = true;
        this.mScrewdriver = true;
        this.mSoftHammer = true;
        this.mSolderingTool = true;
        this.mWrench = true;
    }

    private void payCost(ItemStack[] repairCost) {
        for (ItemStack row : repairCost) {
            if (row == null) {
                continue;
            }

            int rowCost = row.stackSize;

            for (ItemStack aStack : mInventory) {
                if (areStacksSame(aStack, row)) {
                    if (aStack.stackSize < rowCost) {
                        rowCost -= aStack.stackSize;
                        aStack.stackSize = 0;
                    } else {
                        aStack.stackSize -= rowCost;
                        break;
                    }
                }
            }
        }
        updateSlots();
    }

    public boolean autoMaintainance() {
        if (!isAuto()) {
            return false;
        }

        if (mMethod == RepairMethod.FullAuto) {
            fullRepair();
            return true;
        }

        if (mInventory == null) {
            return false;
        }

        int kitsCount = countStack(mInventory, autoRepairKit);

        if (kitsCount >= autoRepairKit.stackSize) {
            payCost(new ItemStack[]{autoRepairKit});
        } else {
            for (ItemStack row : oldRepairItems) {
                int inventoryCount = countStack(mInventory, row);

                if (row.stackSize > inventoryCount) {
                    return false;
                }
            }
            payCost(oldRepairItems);
        }

        fullRepair();

        return true;
    }

    public void onToolClick(ItemStack aStack, EntityLivingBase aPlayer) {
        if (aStack == null || aPlayer == null) {
            return;
        }

        if (GT_OreDictUnificator.isItemStackInstanceOf(aStack, "craftingDuctTape")) {
            fullRepair();
            getBaseMetaTileEntity().setActive(false);
            aStack.stackSize--;
            return;
        }

        if (GT_Utility.isStackInList(aStack, GregTech_API.sWrenchList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer)) {
            mWrench = true;
        }
        if (GT_Utility.isStackInList(aStack, GregTech_API.sScrewdriverList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer)) {
            mScrewdriver = true;
        }
        if (GT_Utility.isStackInList(aStack, GregTech_API.sSoftHammerList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer)) {
            mSoftHammer = true;
        }
        if (GT_Utility.isStackInList(aStack, GregTech_API.sHardHammerList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer)) {
            mHardHammer = true;
        }
        if (GT_Utility.isStackInList(aStack, GregTech_API.sCrowbarList) && GT_ModHandler.damageOrDechargeItem(aStack, 1, 1000, aPlayer)) {
            mCrowbar = true;
        }
        if (GT_Utility.isStackInList(aStack, GregTech_API.sSolderingToolList) && GT_ModHandler.useSolderingIron(aStack, aPlayer)) {
            mSolderingTool = true;
        }
        if (mSolderingTool && aPlayer instanceof EntityPlayerMP) {
            EntityPlayerMP tPlayer = (EntityPlayerMP) aPlayer;
            try {
                GT_Mod.achievements.issueAchievement(tPlayer, "maintainance");
            } catch (Exception ignored) {
            }
        }

        //click with toolbox
        if (aStack.getItem() instanceof ItemToolbox) {
            ItemToolbox tb = (ItemToolbox) aStack.getItem();
            IHasGui tti = tb.getInventory((EntityPlayer) aPlayer, aStack);
            for (int i = 0; i < tti.getSizeInventory(); i++) {
                onToolClick(tti.getStackInSlot(i), aPlayer);
                ItemStack is = tti.getStackInSlot(i);
                if (is != null && is.stackSize <= 0) {
                    tti.setInventorySlotContents(i, null);
                }
            }
        }

    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (!(isAuto() && GT_Mod.gregtechproxy.mAMHInteraction)) {
            return false;
        }

        if (isNotRepairItem(aStack)) {
            return false;
        }

        return isItemNotFull(aStack);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        if (!(isAuto() && GT_Mod.gregtechproxy.mAMHInteraction)) {
            return false;
        }

        if (isNotRepairItem(aStack)) {
            return false;
        }

        return isItemNotFull(aStack);
    }

    private boolean isNotRepairItem(ItemStack aStack) {
        if (areStacksSame(aStack, autoRepairKit)) {
            return false;
        }

        for (ItemStack nStack : oldRepairItems) {
            if (areStacksSame(aStack, nStack)) {
                return false;
            }
        }
        return true;
    }

    private boolean isItemNotFull(ItemStack aStack) {
        return countStack(mInventory, aStack) < 64;
    }
}
