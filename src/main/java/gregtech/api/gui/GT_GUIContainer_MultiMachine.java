package gregtech.api.gui;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_DrillerBase;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeTurbine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * The GUI-Container I use for all my Basic Machines
 * <p/>
 * As the NEI-RecipeTransferRect Handler can't handle one GUI-Class for all GUIs I needed to produce some dummy-classes which extend this class
 */
public class GT_GUIContainer_MultiMachine extends GT_GUIContainerMetaTile_Machine {

    String mName = "";
    ResourceLocation mProgressIcon  = new ResourceLocation(RES_PATH_GUI + "multimachines/"+"Progress.png");

    public GT_GUIContainer_MultiMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
        super(new GT_Container_MultiMachine(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile));
        mName = aName;
    }



    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(mName, 10, 8, 16448255);

        if (mContainer != null) {
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 1) != 0)
                fontRendererObj.drawString(trans("132", "Pipe is loose."), 10, 16, 16448255);
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 2) != 0)
                fontRendererObj.drawString(trans("133", "Screws are missing."), 10, 24, 16448255);
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 4) != 0)
                fontRendererObj.drawString(trans("134", "Something is stuck."), 10, 32, 16448255);
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 8) != 0)
                fontRendererObj.drawString(trans("135", "Platings are dented."), 10, 40, 16448255);
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 16) != 0)
                fontRendererObj.drawString(trans("136", "Circuitry burned out."), 10, 48, 16448255);
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 32) != 0)
                fontRendererObj.drawString(trans("137", "That doesn't belong there."), 10, 56, 16448255);
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 64) != 0)
                fontRendererObj.drawString(trans("138", "Incomplete Structure."), 10, 64, 16448255);

            if (((GT_Container_MultiMachine) mContainer).mDisplayErrorCode == 0) {
                if (((GT_Container_MultiMachine) mContainer).mActive == 0) {
                    fontRendererObj.drawString(trans("139", "Hit with Soft Hammer"), 10, 16, 16448255);
                    fontRendererObj.drawString(trans("140", "to (re-)start the Machine"), 10, 24, 16448255);
                    fontRendererObj.drawString(trans("141", "if it doesn't start."), 10, 32, 16448255);
                } else {
                    fontRendererObj.drawString(trans("142", "Running perfectly."), 10, 17, 16448255);
                    fontRendererObj.drawString(trans("145","Progress") + " " + EnumChatFormatting.GREEN + mContainer.mProgressTime/20 + EnumChatFormatting.RESET + " / " + EnumChatFormatting.YELLOW + mContainer.mMaxProgressTime/20 + EnumChatFormatting.RESET + " s",10,26,16448255);
                }
                if (mContainer.mTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_DrillerBase) {
                    ItemStack tItem = mContainer.mTileEntity.getMetaTileEntity().getStackInSlot(1);
                    if (tItem == null || !GT_Utility.areStacksEqual(tItem, GT_ModHandler.getIC2Item("miningPipe", 1L))) {
                        fontRendererObj.drawString(trans("143", "Missing Mining Pipe"), 10, ((GT_Container_MultiMachine) mContainer).mActive == 0 ? 40 : 24, 16448255);
                    }
                } else if (mContainer.mTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_LargeTurbine) {
                    ItemStack tItem = mContainer.mTileEntity.getMetaTileEntity().getStackInSlot(1);
                    if (tItem == null || !(tItem.getItem() == GT_MetaGenerated_Tool_01.INSTANCE && tItem.getItemDamage() >= 170 && tItem.getItemDamage() <= 177)) {
                        fontRendererObj.drawString(trans("144", "Missing Turbine Rotor"), 10, ((GT_Container_MultiMachine) mContainer).mActive == 0 ? 40 : 24, 16448255);
                    }
                }
            }
        }
    }

    public String trans(String aKey, String aEnglish) {
        return GT_LanguageManager.addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        mc.renderEngine.bindTexture(mProgressIcon);
        drawTexturedModalRect(x+130,y+59,220,0,18,18);
        drawTexturedModalRect(x+135,y+8,137,6,11,11);

        if (this.mContainer != null) {
            if (mContainer.mDisplayErrorCode == 0) {
                double tBarLength = ((double) mContainer.mProgressTime) / mContainer.mMaxProgressTime;
                if(mContainer.mMaxProgressTime != 0) {
                    drawTexturedModalRect(x + 12, y + 61, 0, 226, Math.min(113, (int) (tBarLength * 113)), 13);
                    drawTexturedModalRect(x + 10, y + 59, 0, 239, 119, 17);
                }
                if (mContainer.mActive == 0) {
                    drawTexturedModalRect(x + 136, y + 9, 238, 0, 9, 9);

                } else
                    drawTexturedModalRect(x + 136, y + 9, 247, 0, 9, 9);
            }
            if(mContainer.mTileEntity != null){
                if(mContainer.mAllowedToWork == 1){
                    drawTexturedModalRect(x+133,y+61,238,41,14,14);
                }
                else{
                    if(mContainer.mProgressTime>0){
                        drawTexturedModalRect(x+133,y+61,238,26,14,14);
                    }
                    else {
                        drawTexturedModalRect(x+133,y+61,238,11,14,14);
                    }
                }
            }
        }


    }

}
