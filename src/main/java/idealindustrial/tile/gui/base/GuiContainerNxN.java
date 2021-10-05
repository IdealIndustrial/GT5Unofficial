package idealindustrial.tile.gui.base;

import idealindustrial.util.misc.II_Paths;
import net.minecraft.util.ResourceLocation;

import java.util.stream.Stream;

public class GuiContainerNxN extends GenericGuiContainer<ContainerNxN> {
    static String[] textures = Stream.of("1by1")
            .map(i -> II_Paths.PATH_GUI + i + ".png")
            .toArray(String[]::new);


    public GuiContainerNxN(ContainerNxN container) {
        super(container, textures[Math.max(0, container.n - 4)]);
    }
}
