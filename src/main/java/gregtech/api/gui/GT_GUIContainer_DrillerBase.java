package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_DrillerBase extends GT_GUIContainer_MultiMachine {

    // labels:
    String labelPickingUpPipes = trans("labelPickingUpPipes", "Picking up pipes");
    String labelMiningUnderBedrock = trans("labelMiningUnderBedrock", "Mining under bedrock");
    String labelDrillHeadHeight = trans("labelDrillHeadHeight", "Drill head height ");

    public GT_GUIContainer_DrillerBase(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile){
        super(aInventoryPlayer, aTileEntity, aName, aTextureFile);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        if (mContainer != null && mContainer.mActive != 0) {
            switch (mContainer.mShowExtraGuiInfo) {
                case -2:
                    fontRendererObj.drawString(labelPickingUpPipes, 10, 40, 16448255);
                    break;
                case -1:
                    fontRendererObj.drawString(labelMiningUnderBedrock, 10, 40, 16448255);
                    break;
                default:
                    fontRendererObj.drawString(labelDrillHeadHeight + mContainer.mShowExtraGuiInfo, 10, 40, 16448255);
            }
        }
    }

    @Override
    public String trans(String aKey, String aText){
        return GT_LanguageManager.addStringLocalization("Gui_" + aKey, aText, true);
    }
}
