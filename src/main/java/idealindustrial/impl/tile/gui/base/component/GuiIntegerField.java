package idealindustrial.impl.tile.gui.base.component;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiIntegerField extends GuiTextField {

    public GuiIntegerField(FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(fontRenderer, x, y, width, height);
    }


    @Override
    public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
        String prevText = getText();
        boolean out = super.textboxKeyTyped(p_146201_1_, p_146201_2_);
        String text = getText();
        try {
            if (!text.equals("")) {
                Integer.parseInt(text);
            }
        } catch (Exception e) {
            setText(prevText);
        }
        return out;
    }

    public int getValue() {
        String text = getText();
        if (text.equals("")) {
            return 0;
        }
        return Integer.parseInt(text);
    }

}
