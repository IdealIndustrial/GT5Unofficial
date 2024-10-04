package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.RelativeOffset;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DataAccess;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_MetaBlock;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Block_Casings2;
import gregtech.common.blocks.GT_Block_Casings3;
import ic2.core.block.BlockTexGlass;
import java.util.ArrayList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_AssemblyLine extends GT_MetaTileEntity_MultiBlockBase {

    private static final int TEXTURE_INDEX = 16;
    private static final int GRATE_META_ID = 10;
    private static final int STEEL_CASING_META_ID = 0;
    private static final int ASSEMBLER_CASING_META_ID = 9;
    private static final int LINE_CASING_META_ID = 5;
    private int checkRow;

    public ArrayList<GT_MetaTileEntity_Hatch_DataAccess> mDataAccessHatches = new ArrayList<>();

    public GT_MetaTileEntity_AssemblyLine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_AssemblyLine(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_AssemblyLine(this.mName);
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Assembly Line",
            "Size(WxHxD): (5-16)x4x3, various width",
            "Bottom: Solid Steel Machine Casing (or Maintenance, Input Hatches,",
            "Input Buses from left to right,  1x Output Bus any place)",
            "Middle: Reinforced Glass, Assembling Line Casing, Reinforced Glass",
            "UpMiddle:  Grate Machine Casing, Assembler Machine Casing,",
            "           Grate Machine Casing         ",
            "           (or Controller or optional Data Access Hatch)",
            "Top: Solid Steel Machine Casing, 1-2x Energy Hatch",
            "Up to 16 repeating slices, last is Output Bus"};
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16]};
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "AssemblyLine.png");
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        if (GT_Values.D1) {
            System.out.println("Start ALine recipe check");
        }
        ArrayList<ItemStack> tDataStickList = getDataItems(2);
        if (tDataStickList.isEmpty()) {
            return false;
        }
        if (GT_Values.D1) {
            System.out.println("Stick accepted, " + tDataStickList.size() + " Data Sticks found");
        }

        ItemStack tStack[] = new ItemStack[15];
        FluidStack[] tFluids = new FluidStack[4];
        boolean findRecipe = false;
        nextDS:
        for (ItemStack tDataStick : tDataStickList) {
            NBTTagCompound tTag = tDataStick.getTagCompound();
            if (tTag == null) {
                continue;
            }
            for (int i = 0; i < 15; i++) {
                int count = tTag.getInteger("a" + i);
                if (!tTag.hasKey("" + i) && count <= 0) {
                    continue;
                }
                if (mInputBusses.get(i) == null) {
                    continue nextDS;
                }

                ItemStack stackInSlot = mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0);
                boolean flag = true;
                if (count > 0) {
                    for (int j = 0; j < count; j++) {
                        tStack[i] = GT_Utility.loadItem(tTag, "a" + i + ":" + j);
                        if (tStack[i] == null) {
                            continue;
                        }
                        if (GT_Values.D1) {
                            System.out.println("Item " + i + " : " + tStack[i].getUnlocalizedName());
                        }
                        if (GT_Utility.areStacksEqual(tStack[i], stackInSlot, true) && tStack[i].stackSize <= stackInSlot.stackSize) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    tStack[i] = GT_Utility.loadItem(tTag, "" + i);
                    if (tStack[i] == null) {
                        flag = false;
                        continue;
                    }
                    if (GT_Values.D1) {
                        System.out.println("Item " + i + " : " + tStack[i].getUnlocalizedName());
                    }
                    if (GT_Utility.areStacksEqual(tStack[i], stackInSlot, true) && tStack[i].stackSize <= stackInSlot.stackSize) {
                        flag = false;
                    }
                }
                if (GT_Values.D1) {
                    System.out.println(i + (flag ? " not accepted" : " accepted"));
                }
                if (flag) {
                    continue nextDS;
                }
            }

            if (GT_Values.D1) {
                System.out.println("All Items done, start fluid check");
            }
            for (int i = 0; i < 4; i++) {
                if (!tTag.hasKey("f" + i)) {
                    continue;
                }
                tFluids[i] = GT_Utility.loadFluid(tTag, "f" + i);
                if (tFluids[i] == null) {
                    continue;
                }
                if (GT_Values.D1) {
                    System.out.println("Fluid " + i + " " + tFluids[i].getUnlocalizedName());
                }
                if (mInputHatches.get(i) == null) {
                    continue nextDS;
                }
                FluidStack fluidInHatch = mInputHatches.get(i).mFluid;
                if (fluidInHatch == null || !GT_Utility.areFluidsEqual(fluidInHatch, tFluids[i], true) || fluidInHatch.amount < tFluids[i].amount) {
                    if (GT_Values.D1) {
                        System.out.println(i + " not accepted");
                    }
                    continue nextDS;
                }
                if (GT_Values.D1) {
                    System.out.println(i + " accepted");
                }
            }

            if (GT_Values.D1) {
                System.out.println("Input accepted, check other values");
            }
            if (!tTag.hasKey("output")) {
                continue;
            }
            mOutputItems = new ItemStack[]{GT_Utility.loadItem(tTag, "output")};
            if (mOutputItems[0] == null || !GT_Utility.isStackValid(mOutputItems[0])) {
                continue;
            }

            if (!tTag.hasKey("time")) {
                continue;
            }
            mMaxProgresstime = tTag.getInteger("time");
            if (mMaxProgresstime <= 0) {
                continue;
            }

            if (!tTag.hasKey("eu")) {
                continue;
            }
            mEUt = tTag.getInteger("eu");

            if (GT_Values.D1) {
                System.out.println("Find avaiable recipe");
            }
            findRecipe = true;
            break;
        }
        if (!findRecipe) {
            return false;
        }

        if (GT_Values.D1) {
            System.out.println("All checked start consuming inputs");
        }
        for (int i = 0; i < 15; i++) {
            if (tStack[i] == null) {
                continue;
            }
            ItemStack stackInSlot = mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0);
            stackInSlot.stackSize -= tStack[i].stackSize;
        }

        for (int i = 0; i < 4; i++) {
            if (tFluids[i] == null) {
                continue;
            }
            mInputHatches.get(i).mFluid.amount -= tFluids[i].amount;
            if (mInputHatches.get(i).mFluid.amount <= 0) {
                mInputHatches.get(i).mFluid = null;
            }
        }
        if (GT_Values.D1) {
            System.out.println("Check overclock");
        }

        byte tTier = (byte) Math.max(1, GT_Utility.getTier(getMaxInputVoltage()));
        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        if (mEUt <= 16) {
            this.mEUt = (mEUt * (1 << tTier - 1) * (1 << tTier - 1));
            this.mMaxProgresstime = (mMaxProgresstime / (1 << tTier - 1));
        } else {
            while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
                this.mEUt *= 4;
                this.mMaxProgresstime /= 2;
            }
        }
        if (this.mEUt > 0) {
            this.mEUt = -this.mEUt;
        }
        this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
        updateSlots();
        if (GT_Values.D1) {
            System.out.println("Recipe sucessfull");
        }
        return true;
    }

    @Override
    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 20) {
            GT_Utility.doSoundAtClient(GregTech_API.sSoundList.get(212), 10, 1.0F, aX, aY, aZ);
        }
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity cBase, ItemStack aStack) {
        //controller's row
        int maxrowlength = 16;
        checkRow = 0;

        //checking blocks to the left
        for (int i = 1; i < maxrowlength; i++) {
            if (!validateControllerLayerBlocks(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.LEFT, i, 0, 0))) {
                break;  //found wrong block
            }
        }
        int topLeftOffset = checkRow;
        //checking blocks to the right
        for (int i = 1; i < maxrowlength - topLeftOffset; i++) {
            if (!validateControllerLayerBlocks(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 0, 0))) {
                break;  //found wrong block
            }
        }
        int fixedLength = checkRow + 1; //total length of array, should be not less than 5
        if (fixedLength < 5) {
            return false;
        }
        //check the rest blocks in controller layer
        for (int i = 0 - topLeftOffset; i < 0 - topLeftOffset + fixedLength; i++) {
            //middle row
            GT_MetaBlock mb = GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 1, 0);
            if (!validateCasing2Block(mb, ASSEMBLER_CASING_META_ID)) {
                return false;
            }
            //back row
            if (!validateControllerLayerBlocks(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 2, 0))) {
                return false;
            }
            //top layer
            mb = GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 1, 1);
            if (!validateCasing2Block(mb, STEEL_CASING_META_ID)) {
                //found energy hatch?
                if (!addEnergyInputToMachineList(mb.getTile(), TEXTURE_INDEX)) {
                    return false;
                }
            }
            //middle layer
            if (!(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 0, -1).getBlock() instanceof BlockTexGlass
                    && GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 2, -1).getBlock() instanceof BlockTexGlass)) {
                return false; //check glass
            }
            if (!validateCasing2Block(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 1, -1), LINE_CASING_META_ID)) {
                return false;
            }
            //bottomLayer
            if (!validateBottomBlock(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 0, -2))
                    || !validateBottomBlock(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 1, -2))
                    || !validateBottomBlock(GT_Utility.getFrontRelativeOffsetBackBlock(cBase, RelativeOffset.RIGHT, i, 2, -2))) {
                return false;
            }
        }
        return !(mEnergyHatches.isEmpty() || mOutputBusses.size() != 1 || mMaintenanceHatches.isEmpty());
    }

    private boolean validateBottomBlock(GT_MetaBlock mb) {
        if (!validateCasing2Block(mb, STEEL_CASING_META_ID)) {
            //try to add the hatches
            if (!addInputToMachineList(mb.getTile(), TEXTURE_INDEX)) {
                if (!addMaintenanceToMachineList(mb.getTile(), TEXTURE_INDEX)) {
                    if (!addOutputToMachineList(mb.getTile(), TEXTURE_INDEX)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean validateCasing2Block(GT_MetaBlock mb, int metaData) {
        return (mb.getBlock() instanceof GT_Block_Casings2 && mb.getMetaId() == metaData);
    }

    private boolean validateControllerLayerBlocks(GT_MetaBlock block) {
        if (block.getBlock() instanceof GT_Block_Casings3 && block.getMetaId() == GRATE_META_ID) {
            checkRow++;
            return true;
        } else if (block.getTile() instanceof BaseMetaTileEntity) {
            if (addDataAccessToMachineList(block.getTile(), TEXTURE_INDEX)) {
                checkRow++;
                return true;
            }
        }
        return false;
    }

    /**
     * @param state using bitmask, 1 for IntegratedCircuit, 2 for DataStick, 4 for DataOrb
     */
    private boolean isCorrectDataItem(ItemStack aStack, int state) {
        if ((state & 1) != 0 && ItemList.Circuit_Integrated.isStackEqual(aStack, true, true)) {
            return true;
        }
        if ((state & 2) != 0 && ItemList.Tool_DataStick.isStackEqual(aStack, false, true)) {
            return true;
        }
        if ((state & 4) != 0 && ItemList.Tool_DataOrb.isStackEqual(aStack, false, true)) {
            return true;
        }
        return false;
    }

    /**
     * @param state using bitmask, 1 for IntegratedCircuit, 2 for DataStick, 4 for DataOrb
     */
    public ArrayList<ItemStack> getDataItems(int state) {
        ArrayList<ItemStack> rList = new ArrayList<>();
        if (GT_Utility.isStackValid(mInventory[1]) && isCorrectDataItem(mInventory[1], state)) {
            rList.add(mInventory[1]);
        }
        for (GT_MetaTileEntity_Hatch_DataAccess tHatch : mDataAccessHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = 0; i < tHatch.getBaseMetaTileEntity().getSizeInventory(); i++) {
                    if (tHatch.getBaseMetaTileEntity().getStackInSlot(i) != null
                            && isCorrectDataItem(tHatch.getBaseMetaTileEntity().getStackInSlot(i), state)) {
                        rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                    }
                }
            }
        }
        return rList;
    }

    public boolean addDataAccessToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_DataAccess) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mDataAccessHatches.add((GT_MetaTileEntity_Hatch_DataAccess) aMetaTileEntity);
        }
        return false;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }
}
