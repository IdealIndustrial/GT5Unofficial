package gregtech.common.gui;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;

import static gregtech.api.enums.GT_Values.*;

public class GT_GUIContainer_CreativeGenerator
        extends GT_GUIContainerMetaTile_Machine {
    public GT_GUIContainer_CreativeGenerator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_CreativeGenerator(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "Creative_Generator.png");
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString("Creative", 48, 8, 16448255);
        this.fontRendererObj.drawString("Generator", 48, 18, 16448255);
        if (this.mContainer != null) {
            int voltage = (int)V[((GT_Container_CreativeGenerator) this.mContainer).aTier];
            String tier = VN[((GT_Container_CreativeGenerator) this.mContainer).aTier];
            this.fontRendererObj.drawString("Tier: " , 48, 28, 16448255);
            this.fontRendererObj.drawString(tier, 72, 28, 0xFF00ff00);
            this.fontRendererObj.drawString("Voltage:", 48, 38, 16448255);
            this.fontRendererObj.drawString(GT_Utility.parseNumberToString(voltage) , 48, 48, 0xFF00ffff);
            this.fontRendererObj.drawString("Ampers: ", 48, 58, 16448255);
            this.fontRendererObj.drawString(GT_Utility.parseNumberToString(((GT_Container_CreativeGenerator) this.mContainer).ampers), 88, 58, 0xFFff9900);
        }
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
