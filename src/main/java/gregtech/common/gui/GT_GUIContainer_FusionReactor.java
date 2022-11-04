package gregtech.common.gui;

import gregtech.api.gui.GT_Container_Fusion;
import gregtech.api.gui.GT_Container_MultiMachine;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_GUIContainer_FusionReactor extends GT_GUIContainerMetaTile_Machine {

    public String mNEI;
    String mName = "";
    ResourceLocation mProgressIcon = new ResourceLocation(RES_PATH_GUI + "multimachines/" + "Progress.png");

    public GT_GUIContainer_FusionReactor(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile, String aNEI) {
        super(new GT_Container_Fusion(aInventoryPlayer, aTileEntity, false), RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile));
        mName = aName;
        mNEI = aNEI;
    }


    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        int x = par1 - xStart;
        int y = par2 - yStart;
        if (inventorySlots.getSlot(1) != null && isTheMouseOverSlot(inventorySlots.getSlot(1), x, y, 17, 1)) {
            drawHoveringText(Arrays.asList(getButtonToolTip()), par1, par2, fontRendererObj);
            RenderHelper.enableGUIStandardItemLighting();
        }
    }

    public String trans(String aKey, String aEnglish) {
        return GT_LanguageManager.addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
    }

    protected String[] getButtonToolTip() {
        switch (getMode()) {
            case 3:
                return new String[]{trans("200", "Running perfectly")};
            case 2:
                return new String[]{trans("201", "Enabled, waiting for inputs")};
            case 1:
                return new String[]{trans("202", "Disabled, working last cycle and turning off")};
            case 0:
                return new String[]{trans("203", "Disabled")};
        }
        return new String[0];
    }

    protected int getMode() {
        if (mContainer.mTileEntity != null) {
            if (mContainer.mAllowedToWork == 1) {
                if (mContainer.mProgressTime > 0)
                    return 3;
                else
                    return 2;
            } else {
                if (mContainer.mProgressTime > 0)
                    return 1;
                else
                    return 0;
            }
        }
        return -1;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(mName, 8, -10, 16448255);

        if (mContainer != null) {
            if ((((GT_Container_MultiMachine) mContainer).mDisplayErrorCode & 64) != 0)
                fontRendererObj.drawString("Incomplete Structure.", 10, 8, 16448255);

            if (((GT_Container_MultiMachine) mContainer).mDisplayErrorCode == 0) {
                if (((GT_Container_MultiMachine) mContainer).mActive == 0) {
                    fontRendererObj.drawString("Hit with Soft Hammer to (re-)start the Machine if it doesn't start.", -70, 170, 16448255);
                } else {
                    fontRendererObj.drawString("Running perfectly.", 10, 170, 16448255);
                }
            }
            if(this.mContainer.mEnergy > 160000000 && this.mContainer.mEnergy < 160010000)
                fontRendererObj.drawString("160,000,000 EU", 50, 155, 0x00ff0000);
            else if(this.mContainer.mEnergy > 320000000 && this.mContainer.mEnergy < 320010000)
                fontRendererObj.drawString("320,000,000 EU", 50, 155, 0x00ff0000);
            else if(this.mContainer.mEnergy > 640000000 && this.mContainer.mEnergy < 640010000)
                fontRendererObj.drawString("640,000,000 EU", 50, 155, 0x00ff0000);
            else
            fontRendererObj.drawString(GT_Utility.formatNumbers(this.mContainer.mEnergy) + " EU", 50, 155, 0x00ff0000);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        if (this.mContainer != null) {
            double tScale = (double) this.mContainer.mEnergy / (double) this.mContainer.mStorage;
            drawTexturedModalRect(x + 5, y + 156, 0, 251, Math.min(147, (int) (tScale * 148)), 5);
            mc.renderEngine.bindTexture(mProgressIcon);
            if (mContainer.mActive == 0) {
                drawTexturedModalRect(x + 138, y + 9, 238, 0, 9, 9);
            } else {
                drawTexturedModalRect(x + 138, y + 9, 247, 0, 9, 9);
            }
            switch (getMode()) {
                case 3:
                    drawTexturedModalRect(x + 157, y + 24, 238, 41, 14, 14);
                    break;
                case 1:
                case 2:
                    drawTexturedModalRect(x + 157, y + 24, 238, 26, 14, 14);
                    break;
                case 0:
                    drawTexturedModalRect(x + 157, y + 24, 238, 11, 14, 14);
                    break;
            }
        }
    }
}