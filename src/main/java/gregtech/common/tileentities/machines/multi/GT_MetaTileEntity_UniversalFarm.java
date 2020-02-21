package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import net.minecraft.util.EnumChatFormatting;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import static gregtech.api.enums.GT_Values.VN;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;

public class GT_MetaTileEntity_UniversalFarm extends GT_MetaTileEntity_MultiBlockBase {

	//private int coilMetaID;
	public static GT_CopiedBlockTexture mTextureULV = new GT_CopiedBlockTexture(Block.getBlockFromItem(ItemList.Casing_ULV.get(1).getItem()), 6, 0,Dyes.MACHINE_METAL.mRGBa);
	
	private final int CASING_INDEX = 22;
	
    public GT_MetaTileEntity_UniversalFarm(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_UniversalFarm(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Universal Farm",
                "Produces woods and saplings and etc.",
                "Size(WxHxD): 5x5x4 (Hollow), Controller (Bottom center)",
				"3x1x3 of Steel Pipe Casing (At the center of the bottom layer)",
				"3x1x3 of Reinforced Glass (At the center of the top layer)",
                "1x Input Hatch/Bus (Any casing)",
                "1x Output Hatch/Bus (Any casing)",
                "1x Maintenance Hatch (Any casing)",
                "1x Energy Hatch (Any casing)",
				"Put circuit in to Input Bus",
                "ULV Machine Casings for the rest (58 at least!)"
				};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{mTextureULV, new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN)};
        }
        return new ITexture[]{mTextureULV};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "UniversalFarm.png");
    }

	@Override
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<ItemStack> tInputList = getStoredInputs();
		int tInputList_sS = tInputList.size();
		for (int i = 0; i < tInputList_sS - 1; i++) {
			for (int j = i + 1; j < tInputList_sS; j++) {
				if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
					if (tInputList.get(i).stackSize >= tInputList.get(j).stackSize) {
						tInputList.remove(j--);
						tInputList_sS = tInputList.size();
					} else {
						tInputList.remove(i--);
						tInputList_sS = tInputList.size();
						break;
					}
				}
			}
		}
		ItemStack[] tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);

		ArrayList<FluidStack> tFluidList = getStoredFluids();
		int tFluidList_sS = tFluidList.size();
		for (int i = 0; i < tFluidList_sS - 1; i++) {
			for (int j = i + 1; j < tFluidList_sS; j++) {
				if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
					if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
						tFluidList.remove(j--);
						tFluidList_sS = tFluidList.size();
					} else {
						tFluidList.remove(i--);
						tFluidList_sS = tFluidList.size();
						break;
					}
				}
			}
		}
		FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
		if (tInputList.size() > 0) {
			long tVoltage = getMaxInputVoltage();
			byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
			GT_Recipe recipe = GT_Recipe.GT_Recipe_Map.sFarmRecipes.findRecipe(getBaseMetaTileEntity(), false,
					false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs);
			if ((recipe != null) && (recipe.isRecipeInputEqual(true, tFluids, tInputs))) {
				this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
				this.mEfficiencyIncrease = 10000;

				int EUt = recipe.mEUt;
				int maxProgresstime = recipe.mDuration;

				while (EUt <= gregtech.api.enums.GT_Values.V[tTier - 1] && maxProgresstime > 2) {
					EUt *= 4;
					maxProgresstime /= 4;
				}
				if (maxProgresstime < 2) {
					maxProgresstime = 2;
					EUt = recipe.mEUt * recipe.mDuration / 2;
				}

				this.mEUt = -EUt;
				this.mMaxProgresstime = maxProgresstime;
				mOutputItems = new ItemStack[recipe.mOutputs.length];
				for (int i = 0; i < recipe.mOutputs.length; i++) {
					if (getBaseMetaTileEntity().getRandomNumber(10000) < recipe.getOutputChance(i)) {
						this.mOutputItems[i] = recipe.getOutput(i);
					}
				}
				this.mOutputFluids = recipe.mFluidOutputs;
				this.updateSlots();
				return true;
			}
		}
		return false;
	}	
	
		public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
			int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX*2;
				int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ*2;
					for (int i = -2; i < 3; i++) {
						for (int j = -2; j < 3; j++) {
							for (int h = 0; h < 4; h++) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                    if ((i != -2 && i != 2) && (j != -2 && j != 2)) {
						if(h == 0){		
							if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings2) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 13) {
                                return false;
                            }
                        } else if (h == 3) {
                            if (!aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j).getUnlocalizedName().equals("blockAlloyGlass")) {
								return false;
    	                    }
							//Если стекло будет кейсингом, то раскоментировать нижние строки и убрать 161-163
							/*if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings9) {	
                                return false;
                            }
							if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 0) {
                                return false;
                            }*/
                        }  else {// innen air
                            if (!aBaseMetaTileEntity.getAirOffset(xDir + i, h, zDir + j)) {
                                return false;
                            }
                        }    
                    }  else {
						if (h == 0) {
                            if ((!addMaintenanceToMachineList(tTileEntity, CASING_INDEX)) && (!addInputToMachineList(tTileEntity, CASING_INDEX)) && (!addOutputToMachineList(tTileEntity, CASING_INDEX)) && (!addEnergyInputToMachineList(tTileEntity, CASING_INDEX))) {
                                if ((xDir + i != 0) || (zDir + j != 0)) {
                                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings1) {
                                        return false;
                                    }
                                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 0) {
                                        return false;
                                    }
                                }
                            }
                        } else {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings1) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
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

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_UniversalFarm(this.mName);
    }

	@Override
	public String[] getInfoData() {


    long storedEnergy=0;
    long maxEnergy=0;
    for(GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
        if (isValidMetaTileEntity(tHatch)) {
            storedEnergy+=tHatch.getBaseMetaTileEntity().getStoredEU();
            maxEnergy+=tHatch.getBaseMetaTileEntity().getEUCapacity();
        }
    }

    return new String[]{
    		StatCollector.translateToLocal("GT5U.multiblock.Progress")+": " +EnumChatFormatting.GREEN + Integer.toString(mProgresstime/20) + EnumChatFormatting.RESET +" s / "+
                    EnumChatFormatting.YELLOW + Integer.toString(mMaxProgresstime/20) + EnumChatFormatting.RESET +" s",
            StatCollector.translateToLocal("GT5U.multiblock.energy")+": " +EnumChatFormatting.GREEN + Long.toString(storedEnergy) + EnumChatFormatting.RESET +" EU / "+
                    EnumChatFormatting.YELLOW + Long.toString(maxEnergy) + EnumChatFormatting.RESET +" EU",
            StatCollector.translateToLocal("GT5U.multiblock.usage")+": "+EnumChatFormatting.RED + Integer.toString(-mEUt) + EnumChatFormatting.RESET + " EU/t"+" "+EnumChatFormatting.YELLOW+VN[GT_Utility.getTier(getMaxInputVoltage())]+ EnumChatFormatting.RESET,
            //StatCollector.translateToLocal("GT5U.multiblock.mei")+": "+EnumChatFormatting.YELLOW+Long.toString(getMaxInputVoltage())+EnumChatFormatting.RESET+" EU/t(*2A) "+StatCollector.translateToLocal("GT5U.machines.tier")+": "+
                    //EnumChatFormatting.YELLOW+VN[GT_Utility.getTier(getMaxInputVoltage())]+ EnumChatFormatting.RESET,
            StatCollector.translateToLocal("GT5U.multiblock.problems")+": "+
                    EnumChatFormatting.RED+ (getIdealStatus() - getRepairStatus())+EnumChatFormatting.RESET+
                    " "+StatCollector.translateToLocal("GT5U.multiblock.efficiency")+": "+
                    EnumChatFormatting.YELLOW+Float.toString(mEfficiency / 100.0F)+EnumChatFormatting.RESET + " %",


    };
}
	
}
