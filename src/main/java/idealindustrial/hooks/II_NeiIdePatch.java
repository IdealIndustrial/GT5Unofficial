package idealindustrial.hooks;

import codechicken.nei.NEIController;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.lang.reflect.Field;
import java.util.Set;

public class II_NeiIdePatch {

    private static Field field1, field2;
    static {
        try {
            field1 = GuiContainer.class.getDeclaredField("field_147007_t");
            field1.setAccessible(true);
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        try {
            field2 = GuiContainer.class.getDeclaredField("field_147008_s");
            field2.setAccessible(true);
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ALWAYS)
    public static boolean isSpreading(NEIController controller, GuiContainer gui) {
        try {
            return ((Boolean) field1.get(gui)) && ((Set) field2.get(gui)).size() > 1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
