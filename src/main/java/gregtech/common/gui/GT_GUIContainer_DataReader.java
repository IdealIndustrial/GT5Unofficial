package gregtech.common.gui;

import gregtech.api.enums.ItemList;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_OreDrillingPlantBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_GUIContainer_DataReader extends GuiContainer {
    public ResourceLocation mBackground, mProgress;
    ItemStack mTool;
    EntityPlayer mPlayer;

    public GT_GUIContainer_DataReader(ItemStack aTool, EntityPlayer aPlayer) {
        super(new GT_Container_DataReader(aPlayer.inventory, aTool));
        mTool = aTool;
        mPlayer = aPlayer;
        ySize = 256;
        mBackground = new ResourceLocation(RES_PATH_GUI +"DataReader.png");
        mProgress = new ResourceLocation(RES_PATH_GUI + "multimachines/" + "Progress.png");;
    }

    NBTTagCompound localNbtWithPagesData;
    public NBTTagCompound getTranslatedNbt(NBTTagCompound origOrbNbt){
        if(localNbtWithPagesData != null && isDataOrbsNbtEqual(localNbtWithPagesData, origOrbNbt)) {
            return localNbtWithPagesData;
        } else {
            localNbtWithPagesData = GT_MetaTileEntity_OreDrillingPlantBase.getTranslatedDataOrbNbtPages(origOrbNbt);
            return localNbtWithPagesData;
        }
    }

    public boolean isDataOrbsNbtEqual(NBTTagCompound nbt1, NBTTagCompound nbt2){
        return nbt1.getInteger("coordX") == nbt2.getInteger("coordX")
            && nbt1.getInteger("coordY") == nbt2.getInteger("coordY")
            && nbt1.getInteger("coordZ") == nbt2.getInteger("coordZ")
            && nbt1.getInteger("dimensionId") == nbt2.getInteger("dimensionId");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        Minecraft.getMinecraft().renderEngine.bindTexture(mBackground);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        mTool = mPlayer.getHeldItem();
        if (!(ItemList.Tool_DataReader_MV.isStackEqual(mTool, false, true) || ItemList.Tool_DataReader_EV.isStackEqual(mTool, false, true)))
            return;
        ItemStack aStick = ((GT_Container_DataReader)inventorySlots).mInventory.getStackInSlot(0);
        if(aStick == null) {
            localNbtWithPagesData = null;
            return;
        }
        NBTTagCompound tNBT = aStick.getTagCompound();
        if (tNBT == null) {
            localNbtWithPagesData = null;
            return;
        }
        if(ItemList.Tool_DataOrb.isStackEqual(aStick, false, true)) {
            drawPages(getTranslatedNbt(tNBT));
            return;
        }
        int tProgress = mTool.getTagCompound().getInteger("prog");
        if (!tNBT.hasKey("pages") && tProgress == 0)
            return;
        if (!ItemList.Tool_DataStick.isStackEqual(aStick, false, true)
                && !ItemList.Tool_CD.isStackEqual(aStick, false, true))
            return;
        if (mTool == null || mTool.getTagCompound() == null)
            return;
        if (tProgress > 0) {
            int tier = ((GT_MetaBase_Item)mTool.getItem()).getTier(mTool);
            double tBarLength = ((double) tProgress) / 1000* (1 << (tier-2));
            int xOff = 28, yOff = 75;
            fontRendererObj.drawSplitString("Scanning...",  x + xOff, y + yOff - 20, 200, 255 + (255 << 8) + (255 << 16) + (255 << 24));
            fontRendererObj.drawSplitString("Progress: "+tProgress/20+ "/" +50 / (1 << (tier-2)),  x + xOff, y + yOff - 10, 200, 255 + (255 << 8) + (255 << 16) + (255 << 24));
            Minecraft.getMinecraft().renderEngine.bindTexture(mProgress);
            drawTexturedModalRect(x + xOff +2, y + yOff + 1, 0, 226, Math.min(113, (int) (tBarLength * 113)), 13);
            drawTexturedModalRect(x + xOff, y + yOff - 1, 0, 239, 119, 17);
            return;
        }
        drawPages(tNBT);
    }
    protected void drawPages(NBTTagCompound tNBT){
        int tPage = mTool.getTagCompound().getInteger("page");
        NBTTagList pages = tNBT.getTagList("pages", 8);
        String s = pages.getStringTagAt(tPage);
        if (s == null)
            s = "";
        GL11.glPushMatrix();
        GL11.glScaled(.7d, .7d, .7d);
        fontRendererObj.drawSplitString(s,  (int)((guiLeft+10)*10d/7d), (int)((guiTop+10)*10d/7d), 200, 255 + (255 << 8) + (255 << 16) + (255 << 24));
        GL11.glPopMatrix();
    }
}
