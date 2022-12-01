package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_DrillerBase extends GT_GUIContainer_MultiMachine {

    // labels:
    String labelPickingUpPipes = transGui("146");
    String labelMiningUnderBedrock = transGui("147");
    String labelDrillHeadHeight = transGui("148");

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
                    fontRendererObj.drawString(labelDrillHeadHeight +' '+ mContainer.mShowExtraGuiInfo, 10, 40, 16448255);
            }
        }
    }

}
