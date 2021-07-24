package idealindustrial.tile.gui.base.component;

import idealindustrial.util.misc.II_Paths;
import net.minecraft.util.ResourceLocation;

public class II_Slots {

    public static final ResourceLocation LOCATION = new ResourceLocation(II_Paths.SLOTS);
    public static final int rowCount = 14;

    public static int idToX(int id) {
        return (id % rowCount) * rowCount;
    }

    public static int idToY(int id) {
        return (id / rowCount) * rowCount;
    }
}
