package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.events.BlockScanningEvent;
import gregtech.api.interfaces.IDebugableBlock;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.interfaces.tileentity.*;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Cable;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.enums.GT_Values.D1;
import static gregtech.api.enums.GT_Values.E;
import static gregtech.common.GT_Proxy.GTPOLLUTION;
import static gregtech.common.GT_UndergroundOil.undergroundOilReadInformation;

public class Behaviour_Scanner
        extends Behaviour_None {
    public enum ScanModes {DEFAULT, MULTIBLOCK}

    public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Scanner();
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.scanning", "Can scan Blocks in World");

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (((aPlayer instanceof EntityPlayerMP)) && (aItem.canUse(aStack, 20000.0D))) {
            ArrayList<String> tList = new ArrayList<>();
            aItem.use(aStack, getCoordinateScan(tList, aPlayer, aWorld, 1, ScanModes.values()[aStack.getTagCompound() == null ? 0 : aStack.getTagCompound().getInteger("sMode")], aX, aY, aZ, aSide, hitX, hitY, hitZ), aPlayer);

            for (String s : tList) {
                GT_Utility.sendChatToPlayer(aPlayer, s);
            }

            return true;
        }
        GT_Utility.doSoundAtClient(GregTech_API.sSoundList.get(108), 1, 1.0F, aX, aY, aZ);
        return aPlayer instanceof EntityPlayerMP;
    }

    public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if (aWorld.isRemote || !aPlayer.isSneaking()) {
            return aStack;
        }
        NBTTagCompound nbtTagCompound = aStack.getTagCompound();
        if (nbtTagCompound == null)
            nbtTagCompound = new NBTTagCompound();
        int mode = (nbtTagCompound.getInteger("sMode") + 1) % ScanModes.values().length;
        aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_223", ScanModes.values()[mode]));
        nbtTagCompound.setInteger("sMode", mode);
        aStack.setTagCompound(nbtTagCompound);
        return aStack;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }

    public static int getCoordinateScan(ArrayList<String> aList, EntityPlayer aPlayer, World aWorld, int aScanLevel, ScanModes scanMode, int aX, int aY, int aZ, int aSide, float aClickX, float aClickY, float aClickZ) {
        if (aList == null) return 0;

        ArrayList<String> tList = new ArrayList<>();
        int rEUAmount = 0;

        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        Block tBlock = aWorld.getBlock(aX, aY, aZ);

        if (ScanModes.DEFAULT == scanMode) {
            tList.add("----- X: " + aX + " Y: " + aY + " Z: " + aZ + " D: " + aWorld.provider.dimensionId + " -----");
            tList.add("Biome real: " + aWorld.getBiomeGenForCoords(aX, aZ).biomeName);
            tList.add("Biome by generator: " + aWorld.provider.worldChunkMgr.getBiomeGenAt(aX, aZ).biomeName);
        }
        try {
            if (ScanModes.DEFAULT == scanMode) {
                if (tTileEntity instanceof IInventory)
                    tList.add(translate("162", "Name: ") + ((IInventory) tTileEntity).getInventoryName() + translate("163", "  MetaData: ") + aWorld.getBlockMetadata(aX, aY, aZ));
                else
                    tList.add(translate("162", "Name: ") + tBlock.getUnlocalizedName() + translate("163", "  MetaData: ") + aWorld.getBlockMetadata(aX, aY, aZ));

                tList.add(translate("164", "Hardness: ") + tBlock.getBlockHardness(aWorld, aX, aY, aZ) + translate("165", "  Blast Resistance: ") + tBlock.getExplosionResistance(aPlayer, aWorld, aX, aY, aZ, aPlayer.posX, aPlayer.posY, aPlayer.posZ));
                if (tBlock.isBeaconBase(aWorld, aX, aY, aZ, aX, aY + 1, aZ))
                    tList.add(translate("166", "Is valid Beacon Pyramid Material"));
            }
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }
        if (tTileEntity != null) {
            try {
                if (tTileEntity instanceof IFluidHandler) {
                    rEUAmount += 500;
                    FluidTankInfo[] tTanks = ((IFluidHandler) tTileEntity).getTankInfo(ForgeDirection.getOrientation(aSide));
                    if (tTanks != null) for (byte i = 0; i < tTanks.length; i++) {
                        tList.add(translate("167", "Tank ") + i + ": " + GT_Utility.formatNumbers((tTanks[i].fluid == null ? 0 : tTanks[i].fluid.amount)) + " / " + GT_Utility.formatNumbers(tTanks[i].capacity) + " " + GT_Utility.getFluidName(tTanks[i].fluid, true));
                    }
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.reactor.IReactorChamber) {
                    rEUAmount += 500;
                    tTileEntity = (TileEntity) (((ic2.api.reactor.IReactorChamber) tTileEntity).getReactor());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.reactor.IReactor) {
                    rEUAmount += 500;
                    tList.add(translate("168", "Heat: ") + ((ic2.api.reactor.IReactor) tTileEntity).getHeat() + "/" + ((ic2.api.reactor.IReactor) tTileEntity).getMaxHeat()
                            + translate("169", "  HEM: ") + ((ic2.api.reactor.IReactor) tTileEntity).getHeatEffectModifier() + translate("170", "  Base EU Output: ")/* + ((ic2.api.reactor.IReactor)tTileEntity).getOutput()*/);
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.tile.IWrenchable) {
                    rEUAmount += 100;
                    tList.add(translate("171", "Facing: ") + ((ic2.api.tile.IWrenchable) tTileEntity).getFacing() + translate("172", " / Chance: ") + (((ic2.api.tile.IWrenchable) tTileEntity).getWrenchDropRate() * 100) + "%");
                    tList.add(((ic2.api.tile.IWrenchable) tTileEntity).wrenchCanRemove(aPlayer) ? translate("173", "You can remove this with a Wrench") : translate("174", "You can NOT remove this with a Wrench"));
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergyTile) {
                    rEUAmount += 200;
                    //aList.add(((ic2.api.energy.tile.IEnergyTile)tTileEntity).isAddedToEnergyNet()?"Added to E-net":"Not added to E-net! Bug?");
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergySink) {
                    rEUAmount += 400;
                    //aList.add("Demanded Energy: " + ((ic2.api.energy.tile.IEnergySink)tTileEntity).demandsEnergy());
                    //tList.add("Max Safe Input: " + getTier(((ic2.api.energy.tile.IEnergySink)tTileEntity).getSinkTier()));
                    //tList.add("Max Safe Input: " + ((ic2.api.energy.tile.IEnergySink)tTileEntity).getMaxSafeInput());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergySource) {
                    rEUAmount += 400;
                    //aList.add("Max Energy Output: " + ((ic2.api.energy.tile.IEnergySource)tTileEntity).getMaxEnergyOutput());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergyConductor) {
                    rEUAmount += 200;
                    tList.add(translate("175", "Conduction Loss: ") + ((ic2.api.energy.tile.IEnergyConductor) tTileEntity).getConductionLoss());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.tile.IEnergyStorage) {
                    rEUAmount += 200;
                    tList.add(translate("176", "Contained Energy: ") + ((ic2.api.tile.IEnergyStorage) tTileEntity).getStored() + translate("205", " of ") + ((ic2.api.tile.IEnergyStorage) tTileEntity).getCapacity());
                    //aList.add(((ic2.api.tile.IEnergyStorage)tTileEntity).isTeleporterCompatible(ic2.api.Direction.YP)?"Teleporter Compatible":"Not Teleporter Compatible");
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IUpgradableMachine) {
                    rEUAmount += 500;
                    if (((IUpgradableMachine) tTileEntity).hasMufflerUpgrade())
                        tList.add(translate("177", "Has Muffler Upgrade"));
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (ScanModes.DEFAULT == scanMode && tTileEntity instanceof IMachineProgress) {
                    rEUAmount += 400;
                    int tValue = 0;
                    if (0 < (tValue = ((IMachineProgress) tTileEntity).getMaxProgress()))
                        tList.add(translate("178", "Progress: ") + GT_Utility.formatNumbers(tValue) + " / " + GT_Utility.formatNumbers(((IMachineProgress) tTileEntity).getProgress()));
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ICoverable) {
                    rEUAmount += 300;
                    String tString = ((ICoverable) tTileEntity).getCoverBehaviorAtSide((byte) aSide).getDescription((byte) aSide, ((ICoverable) tTileEntity).getCoverIDAtSide((byte) aSide), ((ICoverable) tTileEntity).getCoverDataAtSide((byte) aSide), (ICoverable) tTileEntity);
                    if (tString != null && !tString.equals(E)) tList.add(tString);
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IGregTechTileEntity && ((IGregTechTileEntity) tTileEntity).getMetaTileEntity() instanceof GT_MetaPipeEntity_Cable) {
                    GT_MetaPipeEntity_Cable c = (GT_MetaPipeEntity_Cable) ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
                    tList.add("Max voltage last second " + c.mTransferredVoltageLast20);
                    tList.add("Max amperage last second " + c.mTransferredAmperageLast20);
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IBasicEnergyContainer && ((IBasicEnergyContainer) tTileEntity).getEUCapacity() > 0) {
                    tList.add(translate("179", "Max IN: ") + ((IBasicEnergyContainer) tTileEntity).getInputVoltage() + translate("180", " EU"));
                    tList.add(translate("181", "Max OUT: ") + ((IBasicEnergyContainer) tTileEntity).getOutputVoltage() + translate("182", " EU at ") + ((IBasicEnergyContainer) tTileEntity).getOutputAmperage() + translate("183", " Amperes"));
                    tList.add(translate("184", "Energy: ") + GT_Utility.formatNumbers(((IBasicEnergyContainer) tTileEntity).getStoredEU()) + " / " + GT_Utility.formatNumbers(((IBasicEnergyContainer) tTileEntity).getEUCapacity()) + translate("185", "EU"));
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (ScanModes.DEFAULT == scanMode && tTileEntity instanceof IGregTechTileEntity) {
                    tList.add(translate("186", "Owned by: ") + ((IGregTechTileEntity) tTileEntity).getOwnerName());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if ((ScanModes.DEFAULT == scanMode || ScanModes.MULTIBLOCK == scanMode) && tTileEntity instanceof IGregTechDeviceInformation && ((IGregTechDeviceInformation) tTileEntity).isGivingInformation()) {
                    tList.addAll(Arrays.asList(((IGregTechDeviceInformation) tTileEntity).getInfoData()));
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.crops.ICropTile) {
                    if (((ic2.api.crops.ICropTile) tTileEntity).getScanLevel() < 4) {
                        rEUAmount += 10000;
                        ((ic2.api.crops.ICropTile) tTileEntity).setScanLevel((byte) 4);
                    }
                    if (((ic2.api.crops.ICropTile) tTileEntity).getID() >= 0 && ((ic2.api.crops.ICropTile) tTileEntity).getID() < ic2.api.crops.Crops.instance.getCropList().length && ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile) tTileEntity).getID()] != null) {
                        rEUAmount += 1000;
                        tList.add(translate("187", "Type -- Crop-Name: ") + ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile) tTileEntity).getID()].name()
                                + translate("188", "  Growth: ") + ((ic2.api.crops.ICropTile) tTileEntity).getGrowth()
                                + translate("189", "  Gain: ") + ((ic2.api.crops.ICropTile) tTileEntity).getGain()
                                + translate("190", "  Resistance: ") + ((ic2.api.crops.ICropTile) tTileEntity).getResistance()
                        );
                        tList.add(translate("191", "Plant -- Fertilizer: ") + ((ic2.api.crops.ICropTile) tTileEntity).getNutrientStorage()
                                + translate("192", "  Water: ") + ((ic2.api.crops.ICropTile) tTileEntity).getHydrationStorage()
                                + translate("193", "  Weed-Ex: ") + ((ic2.api.crops.ICropTile) tTileEntity).getWeedExStorage()
                                + translate("194", "  Scan-Level: ") + ((ic2.api.crops.ICropTile) tTileEntity).getScanLevel()
                        );
                        tList.add(translate("195", "Environment -- Nutrients: ") + ((ic2.api.crops.ICropTile) tTileEntity).getNutrients()
                                + translate("196", "  Humidity: ") + ((ic2.api.crops.ICropTile) tTileEntity).getHumidity()
                                + translate("197", "  Air-Quality: ") + ((ic2.api.crops.ICropTile) tTileEntity).getAirQuality()
                        );
                        StringBuilder tStringB = new StringBuilder();
                        for (String tAttribute : ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile) tTileEntity).getID()].attributes()) {
                            tStringB.append(", ").append(tAttribute);
                        }
                        String tString = tStringB.toString();
                        tList.add(translate("198", "Attributes:") + tString.replaceFirst(",", E));
                        tList.add(translate("199", "Discovered by: ") + ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile) tTileEntity).getID()].discoveredBy());
                    }
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
        }

        if (aPlayer.capabilities.isCreativeMode && GT_Values.D1) {
            FluidStack tFluid = undergroundOilReadInformation(aWorld.getChunkFromBlockCoords(aX, aZ));
            if (tFluid != null)
                tList.add(EnumChatFormatting.GOLD + tFluid.getLocalizedName() + EnumChatFormatting.RESET + ": " + EnumChatFormatting.YELLOW + tFluid.amount + EnumChatFormatting.RESET + translate("200", " L"));
            else
                tList.add(EnumChatFormatting.GOLD + translate("201", "Nothing") + EnumChatFormatting.RESET + ": " + EnumChatFormatting.YELLOW + '0' + EnumChatFormatting.RESET + translate("200", " L"));
        }
//      if(aPlayer.capabilities.isCreativeMode){
        int[] chunkData = GT_Proxy.dimensionWiseChunkData.get(aWorld.provider.dimensionId).get(aWorld.getChunkFromBlockCoords(aX, aZ).getChunkCoordIntPair());
        if (ScanModes.DEFAULT == scanMode && chunkData != null) {
            if (chunkData[GTPOLLUTION] > 0) {
                tList.add(translate("202", "Pollution in Chunk: ") + EnumChatFormatting.RED + chunkData[GTPOLLUTION] + EnumChatFormatting.RESET + translate("203", " gibbl"));
            } else {
                tList.add(EnumChatFormatting.GREEN + translate("204", "No Pollution in Chunk! HAYO!") + EnumChatFormatting.RESET);
            }
        } else if (ScanModes.DEFAULT == scanMode) {
            tList.add(EnumChatFormatting.GREEN + translate("204", "No Pollution in Chunk! HAYO!") + EnumChatFormatting.RESET);
        }

        try {
            if (ScanModes.DEFAULT == scanMode && tBlock instanceof IDebugableBlock) {
                rEUAmount += 500;
                ArrayList<String> temp = ((IDebugableBlock) tBlock).getDebugInfo(aPlayer, aX, aY, aZ, 3);
                if (temp != null) tList.addAll(temp);
            }
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }

        BlockScanningEvent tEvent = new BlockScanningEvent(aWorld, aPlayer, aX, aY, aZ, (byte) aSide, aScanLevel, tBlock, tTileEntity, tList, aClickX, aClickY, aClickZ);
        tEvent.mEUCost = rEUAmount;
        MinecraftForge.EVENT_BUS.post(tEvent);
        if (!tEvent.isCanceled()) aList.addAll(tList);
        return tEvent.mEUCost;
    }

    public static String translate(String aKey, String aEnglish) {
        return GT_LanguageManager.addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
    }
}
