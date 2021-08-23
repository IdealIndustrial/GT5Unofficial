package gregtech.api.interfaces;

import gregtech.api.gui.widgets.GT_GuiTooltip;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;


public interface IGuiScreen {

    interface IGuiElement {
        void onInit();
        void draw(int mouseX, int mouseY, float parTicks);
    }

    void addToolTip(GT_GuiTooltip toolTip);

    boolean removeToolTip(GT_GuiTooltip toolTip);

    GuiButton getSelectedButton();
    void clearSelectedButton();
    void buttonClicked(GuiButton button);

    int getGuiLeft();
    int getGuiTop();

    int getXSize();
    int getYSize();

    void addElement(IGuiElement element);
    boolean removeElement(IGuiElement element);

    RenderItem getItemRenderer();
    FontRenderer getFontRenderer();
}