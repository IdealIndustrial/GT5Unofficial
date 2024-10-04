package gregtech.common.tileentities.machines.basic;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_UndergroundOil;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_MetaTileEntity_SeismicProspector extends GT_MetaTileEntity_BasicMachine {

    public GT_MetaTileEntity_SeismicProspector(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "", 1, 0, "SeismicProspector.png", "", new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER)});
    }

    public String[] getDescription() {
        return new String[]{
                "Activate with explosives:",
                "8 Dynamite or Powderbarrel, 4 TNT, 2 Industrial TNT, 1 Glyceryl",
                "Use Data Stick for save data"};
    }

    public GT_MetaTileEntity_SeismicProspector(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 0, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_SeismicProspector(this.mName, this.mTier, this.mDescriptionArray, this.mTextures, this.mGUIName, this.mNEIName);
    }


    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_BasicMachine(aPlayerInventory, aBaseMetaTileEntity) {
            @Override
            public Slot provideSpecialSlot(IInventory iInventory, int id, int x, int y) {
                return new GT_Slot_ClosedInteraction(iInventory, id, x, y, p -> !(mProgressTime > 0 || mMaxProgressTime > 0));
            }
        };
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_BasicMachine((GT_Container_BasicMachine) getServerGUI(0, aPlayerInventory, aBaseMetaTileEntity), getLocalName(), mGUIName, mNEIName);
    }

    @Override
    public int checkRecipe() {
        ItemStack aStack = getInputAt(0);
        ItemStack tStack = getSpecialSlot();
        if(tStack != null && tStack.stackSize == 1 && tStack.getItem().equals(ItemList.Paper_Punch_Card_Empty.getItem())
                && aStack != null && ItemList.Small_Gunpowder_Bundle.getItem().equals(aStack.getItem())) {
            aStack.stackSize -= 1;
            if (aStack.stackSize == 0) {
                mInventory[getInputSlot()] = null;
            }
            mMaxProgresstime = 50;
            return 2;
        }
        if ((aStack != null) && (
                (aStack.getItem() == Item.getItemFromBlock(Blocks.tnt) && aStack.stackSize > 3) ||
                        (aStack.getItem() == ItemList.Block_Powderbarrel.getItem() && aStack.stackSize > 7) ||
                        (aStack.getItem() == Ic2Items.industrialTnt.getItem() && aStack.stackSize > 1) ||
                        (aStack.getItem() == Ic2Items.dynamite.getItem() && aStack.stackSize > 7) ||
                        (GT_OreDictUnificator.getItemData(aStack) != null && GT_OreDictUnificator.getItemData(aStack).mMaterial.mMaterial == Materials.Glyceryl && aStack.stackSize > 0)) &&
                           tStack != null && tStack.stackSize == 1 && (ItemList.Tool_DataStick.isStackEqual(tStack, false, true) || ItemList.Tool_CD.isStackEqual(tStack, false, false))
        ) {
            if (aStack.getItem() == Item.getItemFromBlock(Blocks.tnt)) {
                aStack.stackSize -= 4;
            } else if (aStack.getItem() == ItemList.Block_Powderbarrel.getItem()) {
                aStack.stackSize -= 8;
            } else if (aStack.getItem() == Ic2Items.industrialTnt.getItem()) {
                aStack.stackSize -= 2;
            } else if (aStack.getItem() == Ic2Items.dynamite.getItem()) {
                aStack.stackSize -= 8;
            } else {
                aStack.stackSize -= 1;
            }
            if (aStack.stackSize == 0) {
                mInventory[getInputSlot()] = null;
            }
            mMaxProgresstime = 200;			
            return 2;
        }
        return 0;
    }

    private void makePunchedCard(ItemStack aStack){
        String stringCode = "";
        String enName = "";
        for (int i = this.getBaseMetaTileEntity().getYCoord(); i > 0 && stringCode.length() == 0; i--) {
            for (int f = -2; f < 3 && stringCode.length() == 0; f++) {
                for (int g = -2; g < 3 && stringCode.length() == 0; g++) {
                    Block tBlock = this.getBaseMetaTileEntity().getBlockOffset(f, -i, g);
                    if ((tBlock instanceof GT_Block_Ores_Abstract)) {
                        TileEntity tTileEntity = getBaseMetaTileEntity().getWorld().getTileEntity(getBaseMetaTileEntity().getXCoord() + f, getBaseMetaTileEntity().getYCoord() + (-i), getBaseMetaTileEntity().getZCoord() + g);
                        if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                            int aMeta = ((GT_TileEntity_Ores) tTileEntity).mMetaData;
                            if (aMeta < 16000) {
                                Materials tMaterial = GregTech_API.sGeneratedMaterials[(aMeta % 1000)];
                                if ((tMaterial != null) && (tMaterial != Materials._NULL)) {
                                    stringCode = String.valueOf(tMaterial.mMetaItemSubID);
                                    enName = tMaterial.mDefaultLocalName;
                                }
                            }
                        }
                    }
                }
            }
        }
        ItemStack pStack = ItemList.Paper_Punch_Card_Encoded.get(1);
        setSpecialSlot(pStack);
        NBTTagCompound tNBT = new NBTTagCompound();
        pStack.writeToNBT(tNBT);
        if (!stringCode.equals("")) {
            tNBT.setString("oreName", "gt.blockores."+stringCode+".name" );
            tNBT.setString("enOreName", enName );
        } else {
            tNBT.setBoolean("oresNotFound", true );
        }
        pStack.setTagCompound(tNBT);
    }

    @Override
    public void endProcess() {
        ItemStack aStack = getSpecialSlot();
        if (aStack != null && aStack.stackSize == 1 && ItemList.Paper_Punch_Card_Empty.isStackEqual(aStack, false, true)){
            makePunchedCard(aStack);
            return;
        }
        if (aStack == null || aStack.stackSize != 1 || (!ItemList.Tool_DataStick.isStackEqual(aStack, false, true) && !ItemList.Tool_CD.isStackEqual(aStack, false, false))) {
            return;
        }
        GT_Utility.ItemNBT.setBookTitle(aStack, "Raw Prospection Data");
        List<String> tStringList = new ArrayList<String>();
        for (int i = this.getBaseMetaTileEntity().getYCoord(); i > 0; i--) {
            for (int f = -2; f < 3; f++) {
                for (int g = -2; g < 3; g++) {
                    Block tBlock = this.getBaseMetaTileEntity().getBlockOffset(f, -i, g);
                    if ((tBlock instanceof GT_Block_Ores_Abstract)) {
                        TileEntity tTileEntity = getBaseMetaTileEntity().getWorld().getTileEntity(getBaseMetaTileEntity().getXCoord() + f, getBaseMetaTileEntity().getYCoord() + (-i), getBaseMetaTileEntity().getZCoord() + g);
                        if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                            if (((GT_TileEntity_Ores) tTileEntity).mMetaData < 16000) {
                                Materials tMaterial = GregTech_API.sGeneratedMaterials[(((GT_TileEntity_Ores) tTileEntity).mMetaData % 1000)];
                                if ((tMaterial != null) && (tMaterial != Materials._NULL)) {
                                    if (!tStringList.contains(tMaterial.mDefaultLocalName)) {
                                        tStringList.add(tMaterial.mDefaultLocalName);
                                    }
                                }
                            }
                        }
                    } else {
                        int tMetaID = getBaseMetaTileEntity().getWorld().getBlockMetadata(getBaseMetaTileEntity().getXCoord() + f, getBaseMetaTileEntity().getYCoord() + (-i), getBaseMetaTileEntity().getZCoord() + g);
                        ItemData tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(tBlock, 1, tMetaID));
                        if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith("ore"))) {
                            if (!tStringList.contains(tAssotiation.mMaterial.mMaterial.mDefaultLocalName)) {
                                tStringList.add(tAssotiation.mMaterial.mMaterial.mDefaultLocalName);
                            }
                        }
                    }
                }
            }
        }
        if (tStringList.size() < 1) {
            tStringList.add("No Ores found.");
        }
        FluidStack tFluid = GT_UndergroundOil.undergroundOilReadInformation(getBaseMetaTileEntity());
        String[] tStringArray = new String[tStringList.size()];
        {
            for (int i = 0; i < tStringArray.length; i++) {
                tStringArray[i] = tStringList.get(i);
            }
        }
        GT_Utility.ItemNBT.setProspectionData(aStack, this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord(), this.getBaseMetaTileEntity().getWorld().provider.dimensionId, tFluid, tStringArray);
        getBaseMetaTileEntity().disableWorking();
    }
	
    @Override
    public long getMinimumStoredEU() {
        return 0;
    }

    @Override
    public long maxEUStore() {
        return 0;
    }
}
