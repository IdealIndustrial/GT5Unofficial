package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * The GUI-Container I use for all my Basic Machines
 * <p/>
 * As the NEI-RecipeTransferRect Handler can't handle one GUI-Class for all GUIs I needed to produce some dummy-classes which extend this class
 */
public class GT_GUIContainer_PrimitiveMiner extends GT_GUIContainerMetaTile_Machine {

    int[] screenCoordinates = new int[]{35,69,35,67};
    ResourceLocation mProgressIcon = new ResourceLocation(RES_PATH_GUI + "multimachines/" + "ProgressPrimitiveMiner.png");

    public GT_GUIContainer_PrimitiveMiner(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile, int powerPerClick, int hungryDurationPerOperation, int foodPerOperation) {
        super(new GT_Container_PrimitiveMiner(aInventoryPlayer, aTileEntity, powerPerClick, hungryDurationPerOperation, foodPerOperation), RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        int x = par1 - xStart;
        int y = par2 - yStart;
        if (inventorySlots.getSlot(28) != null && isTheMouseOverSlot(inventorySlots.getSlot(28), x, y)) {
            drawHoveringText(Arrays.asList(trans("tooltip")), par1, par2, fontRendererObj);
            RenderHelper.enableGUIStandardItemLighting();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        if (mContainer != null) {

        }
    }

    public String trans(String aKey) {
        return GT_LanguageManager.getTranslation("primitive_miner.gui." + aKey);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        mc.renderEngine.bindTexture(mProgressIcon);

        if (this.mContainer != null && mContainer.mTileEntity != null) {
            double tBarLength = ((double) mContainer.mProgressTime) / mContainer.mMaxProgressTime;
            if (mContainer.mMaxProgressTime != 0) {
                drawTexturedModalRect(x + 35, y + 42, 0, 0, Math.min(16, (int) (tBarLength * 16)), 2);
            }
            if (mContainer.mSteam > 0) {
                int tBarLengthOne = mContainer.mSteam / 8;
                int tBarLengthTwo = mContainer.mSteam % 8;
                drawTexturedModalRect(x + screenCoordinates[0], y + screenCoordinates[1] - tBarLengthOne*2, 0, 0, 16, tBarLengthOne*2);
                if(tBarLengthTwo > 0) {
                    drawTexturedModalRect(x + screenCoordinates[2] + (16 - tBarLengthTwo * 2), y + screenCoordinates[3] - tBarLengthOne * 2, 0, 0, tBarLengthTwo * 2, 2);
                }
                //fontRendererObj.drawString(String.valueOf(mContainer.mSteam), x + 9, y - 12, 16448255);
            }
        }
    }

}
