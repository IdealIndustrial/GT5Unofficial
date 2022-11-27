package gregtech.common.tileentities.machines.multi;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DataAccess;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import ic2.core.IC2;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_AssemblyLine
        extends GT_MetaTileEntity_MultiBlockBase {

    public ArrayList<GT_MetaTileEntity_Hatch_DataAccess> mDataAccessHatches = new ArrayList<GT_MetaTileEntity_Hatch_DataAccess>();

    public GT_MetaTileEntity_AssemblyLine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_AssemblyLine(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_AssemblyLine(this.mName);
    }

    public String[] getDescription() {
        return new String[]{"Assembly Line",
                "Size: 3x(5-16)x4, variable length",
                "Bottom: Steel Machine Casing(or Maintenance or Input Hatch),",
                "Input Bus (Last Output Bus), Steel Machine Casing",
                "Middle: Reinforced Glass, Assembling Line Casing, Reinforced Glass",
                "UpMiddle: Grate Machine Casing,",
                "    Assembler Machine Casing,",
                "    Grate Machine Casing (or Controller or Data Access Hatch)",
                "Top: Steel Casing(or Energy Hatch)",
                "Up to 16 repeating slices, last is Output Bus",
                "Optional 1x Data Access Hatch next to the Controller"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "AssemblyLine.png");
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

    public boolean checkRecipe(ItemStack aStack) {
    	if(GT_Values.D1)System.out.println("Start ALine recipe check");
        ArrayList<ItemStack> tDataStickList = getDataItems(2);
    	if (tDataStickList.size() == 0) return false;
    	if(GT_Values.D1)System.out.println("Stick accepted, " + tDataStickList.size() + " Data Sticks found");

        ItemStack tStack[] = new ItemStack[15];
    	FluidStack[] tFluids = new FluidStack[4];
    	boolean findRecipe = false;
    	nextDS:for (ItemStack tDataStick : tDataStickList){
    		NBTTagCompound tTag = tDataStick.getTagCompound();
    		if (tTag == null) continue;
    		for (int i = 0; i < 15; i++) {
    			int count = tTag.getInteger("a"+i);
                if (!tTag.hasKey("" + i) && count <= 0) continue;
                if (mInputBusses.get(i) == null) {
                	continue nextDS;
                }
                
                ItemStack stackInSlot = mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0);
                boolean flag = true;
                if (count > 0) {
            		for (int j = 0; j < count; j++) {
            			tStack[i] = GT_Utility.loadItem(tTag, "a" + i + ":" + j);
            			if (tStack[i] == null) continue;
            			if(GT_Values.D1)System.out.println("Item "+i+" : "+tStack[i].getUnlocalizedName());
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
            		if(GT_Values.D1)System.out.println("Item "+i+" : "+tStack[i].getUnlocalizedName());
        			if (GT_Utility.areStacksEqual(tStack[i], stackInSlot, true) && tStack[i].stackSize <= stackInSlot.stackSize) {
        				flag = false;
        			}
            	}
                if(GT_Values.D1) System.out.println(i + (flag ? " not accepted" : " accepted"));
                if (flag) continue nextDS;
            }
    		
    		if(GT_Values.D1)System.out.println("All Items done, start fluid check");
            for (int i = 0; i < 4; i++) {
                if (!tTag.hasKey("f" + i)) continue;
                tFluids[i] = GT_Utility.loadFluid(tTag, "f" + i);
                if (tFluids[i] == null) continue;
            	if(GT_Values.D1)System.out.println("Fluid "+i+" "+tFluids[i].getUnlocalizedName());
                if (mInputHatches.get(i) == null) {
                	continue nextDS;
                }
                FluidStack fluidInHatch = mInputHatches.get(i).mFluid;
                if (fluidInHatch == null || !GT_Utility.areFluidsEqual(fluidInHatch, tFluids[i], true) || fluidInHatch.amount < tFluids[i].amount) {
                	if(GT_Values.D1)System.out.println(i+" not accepted");
                	continue nextDS;
                }
            	if(GT_Values.D1)System.out.println(i+" accepted");
            }
            
            if(GT_Values.D1)System.out.println("Input accepted, check other values");
            if (!tTag.hasKey("output")) continue;
            mOutputItems = new ItemStack[]{GT_Utility.loadItem(tTag, "output")};
            if (mOutputItems[0] == null || !GT_Utility.isStackValid(mOutputItems[0]))
                continue;
            
            if (!tTag.hasKey("time")) continue;
            mMaxProgresstime = tTag.getInteger("time");
            if (mMaxProgresstime <= 0) continue;
            
            if (!tTag.hasKey("eu")) continue;
            mEUt = tTag.getInteger("eu");
            
            if(GT_Values.D1)System.out.println("Find avaiable recipe");
            findRecipe = true;
            break;
    	}
    	if (!findRecipe) return false;

    	if(GT_Values.D1)System.out.println("All checked start consuming inputs");
        for (int i = 0; i < 15; i++) {
            if (tStack[i] == null) continue;
            ItemStack stackInSlot = mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0);
            stackInSlot.stackSize -= tStack[i].stackSize;
        }

        for (int i = 0; i < 4; i++) {
            if (tFluids[i] == null) continue;
            mInputHatches.get(i).mFluid.amount -= tFluids[i].amount;
            if (mInputHatches.get(i).mFluid.amount <= 0) {
                mInputHatches.get(i).mFluid = null;
            }
        }
    	if(GT_Values.D1)System.out.println("Check overclock");

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
    	if(GT_Values.D1)System.out.println("Recipe sucessfull");
        return true;
    }

    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 20) {
            GT_Utility.doSoundAtClient(GregTech_API.sSoundList.get(212), 10, 1.0F, aX, aY, aZ);
        }
    }



    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        Block blockAlloyGlass = GameRegistry.findBlock("IC2", "blockAlloyGlass");
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        if (xDir != 0) {
            for (int r = 0; r <= 16; r++) {
                int i = r * xDir;

                IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(0, 0, i);
                if (i != 0 && checkNotBlockOffset(GregTech_API.sBlockCasings3, 10, 0, 0, i, true)) {
                    if(r == 1 && !addDataAccessToMachineList(tTileEntity, 16)){
                        return false;
                    }
                    if (r != 1) {
                        return false;
                    }
                }

                if (checkNotBlockOffset(blockAlloyGlass, 0, 0, -1, i, false)) {
                    return false;
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(0, -2, i);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))) {
                    if (checkNotBlockOffset(GregTech_API.sBlockCasings2,  0, 0, -2, i, true)) {
                        return false;
                    }
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir, 1, i);
                if (!addEnergyInputToMachineList(tTileEntity, 16)) {
                    if (checkNotBlockOffset(GregTech_API.sBlockCasings2,  0, xDir, 1, i, true)) {
                        return false;
                    }
                }
                if (i != 0 && checkNotBlockOffset(GregTech_API.sBlockCasings2, 9, xDir, 0, i, false)) {
                    return false;
                }
                if (i != 0 && checkNotBlockOffset(GregTech_API.sBlockCasings2, 5, xDir, -1, i, false)) {
                    return false;
                }

                if (checkNotBlockOffset(GregTech_API.sBlockCasings3, 10, xDir * 2, 0, i, false)) {
                    return false;
                }

                if (checkNotBlockOffset(blockAlloyGlass, 0, xDir * 2, -1, i, false)) {
                    return false;
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir * 2, -2, i);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))) {
                    if (i != 0 && checkNotBlockOffset(GregTech_API.sBlockCasings2, 0, xDir * 2, -2, i, true)) {
                        return false;
                    }
                }
                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir, -2, i);
                if (!addInputToMachineList(tTileEntity, 16) && addOutputToMachineList(tTileEntity, 16)) {
                    if (r == 0) {
                        sendErrorLocalString("multiblock.error.al.tooshort");
                        return false;
                    }
                    if (mEnergyHatches.size() == 0) {
                        sendErrorLocalString("multiblock.error.noenergyh");
                        return false;
                    }
                    return true;
                }
            }
        } else {
            for (int r = 0; r <= 16; r++) {
                int i = r * -zDir;

                IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, 0, 0);
                if (i != 0 && checkNotBlockOffset(GregTech_API.sBlockCasings3, 10, i, 0, 0, true)) {
                    if(r == 1 && !addDataAccessToMachineList(tTileEntity, 16)){
                        return false;
                    }
                }
                if (checkNotBlockOffset(blockAlloyGlass, 0, i, -1, 0, false)) {
                    return false;
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, -2, 0);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))) {
                    if (checkNotBlockOffset(GregTech_API.sBlockCasings2, 0, i, -2, 0, true)) {
                        return false;
                    }
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, 1, zDir);
                if (!addEnergyInputToMachineList(tTileEntity, 16)) {
                    if (checkNotBlockOffset(GregTech_API.sBlockCasings2, 0, i, 1, zDir, true)) {
                        return false;
                    }
                }

                if (i != 0 && checkNotBlockOffset(GregTech_API.sBlockCasings2, 9, i, 0, zDir, false)) {
                    return false;
                }
                if (i != 0 && checkNotBlockOffset(GregTech_API.sBlockCasings2, 5, i, -1, zDir, false)) {
                    return false;
                }


                if (checkNotBlockOffset(GregTech_API.sBlockCasings3, 10, i, 0, zDir * 2, false)) {
                    return false;
                }


                if (checkNotBlockOffset(blockAlloyGlass, 0, i, -1, zDir * 2, false)) {
                    return false;
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, -2, zDir * 2);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))) {

                    if (checkNotBlockOffset(GregTech_API.sBlockCasings2, 0, i, -2, zDir * 2, true)) {
                        return false;
                    }
                }
                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, -2, zDir);
                if (!addInputToMachineList(tTileEntity, 16) && addOutputToMachineList(tTileEntity, 16)) {
                    if (r == 0) {
                        sendErrorLocalString("multiblock.error.al.tooshort");
                        return false;
                    }
                    if (mEnergyHatches.size() == 0) {
                        sendErrorLocalString("multiblock.error.noenergyh");
                        return false;
                    }
                    return true;
                }
            }
        }
        sendErrorLocalString("multiblock.error.al.unknown");
        return false;
    }
        
    /**
     * @param state using bitmask, 1 for IntegratedCircuit, 2 for DataStick, 4 for DataOrb
     */
    private boolean isCorrectDataItem(ItemStack aStack, int state){
    	if ((state & 1) != 0 && ItemList.Circuit_Integrated.isStackEqual(aStack, true, true)) return true;
    	if ((state & 2) != 0 && ItemList.Tool_DataStick.isStackEqual(aStack, false, true)) return true;
    	if ((state & 4) != 0 && ItemList.Tool_DataOrb.isStackEqual(aStack, false, true)) return true;
    	return false;
    }
    
    /**
     * @param state using bitmask, 1 for IntegratedCircuit, 2 for DataStick, 4 for DataOrb
     */
    public ArrayList<ItemStack> getDataItems(int state) {
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
        if (GT_Utility.isStackValid(mInventory[1]) && isCorrectDataItem(mInventory[1], state)) {
        	rList.add(mInventory[1]);
        }
        for (GT_MetaTileEntity_Hatch_DataAccess tHatch : mDataAccessHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = 0; i < tHatch.getBaseMetaTileEntity().getSizeInventory(); i++) {
                    if (tHatch.getBaseMetaTileEntity().getStackInSlot(i) != null
                    		&& isCorrectDataItem(tHatch.getBaseMetaTileEntity().getStackInSlot(i), state))
                        rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
        }
        return rList;
    }

    public boolean addDataAccessToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_DataAccess) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mDataAccessHatches.add((GT_MetaTileEntity_Hatch_DataAccess) aMetaTileEntity);
        }
        return false;
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
