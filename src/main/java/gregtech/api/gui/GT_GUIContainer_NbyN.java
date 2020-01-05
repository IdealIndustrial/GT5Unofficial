package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_GUIContainer_NbyN extends GT_GUIContainerMetaTile_Machine {

    private final String mName;

    public GT_GUIContainer_NbyN(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, int N) {
        super(new GT_Container_NbyN(aInventoryPlayer, aTileEntity, N), RES_PATH_GUI + "by" + N + ".png");
        mName = aName;
        xSize = 182 + (N - 5) * 18;
        ySize = 182 + (N - 5) * 18;
    }
    public GT_GUIContainer_NbyN(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aBackground, int N) {
        super(new GT_Container_NbyN(aInventoryPlayer, aTileEntity, N), RES_PATH_GUI + aBackground + N +"by" + N + ".png");
        mName = aName;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(mName, 8, 4, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
