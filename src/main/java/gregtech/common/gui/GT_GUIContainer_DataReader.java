package gregtech.common.gui;

import gregtech.api.enums.ItemList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_DataReader extends GuiContainer {
    ItemStack mTool;
    public GT_GUIContainer_DataReader(ItemStack aTool, EntityPlayer aPlayer) {
        super(new GT_Container_DataReader(aPlayer.inventory, aTool));
        mTool = aTool;
        ySize = 256;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        mTool = ((GT_Container_DataReader)inventorySlots).mTool;
        ItemStack aStick = ((GT_Container_DataReader)inventorySlots).mInventory.getStackInSlot(0);
        if (!ItemList.Tool_DataStick.isStackEqual(aStick, false, true) && !ItemList.Tool_CD.isStackEqual(aStick, false, true))
            return;
        if (mTool == null || mTool.getTagCompound() == null)
            return;
        NBTTagCompound tNBT = aStick.getTagCompound();
        if (tNBT == null || !tNBT.hasKey("pages"))
            return;
        int tPage = mTool.getTagCompound().getInteger("page");
        NBTTagList pages = tNBT.getTagList("pages", 8);
        String s = pages.getStringTagAt(tPage);
        if (s == null)
            s = "";
        GL11.glPushMatrix();
        GL11.glScaled(.7d, .7d, .7d);
        fontRendererObj.drawSplitString(s,  (int)((guiLeft+10)*10d/7d), (int)((guiTop+30)*10d/7d), 200, 0);
        GL11.glPopMatrix();

    }
}
