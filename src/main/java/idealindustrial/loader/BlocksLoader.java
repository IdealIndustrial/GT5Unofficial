package idealindustrial.loader;

import idealindustrial.autogen.blocks.II_Blocks;
import idealindustrial.teststuff.FluidTest;

public class BlocksLoader {

    public void preLoad() {
        II_Blocks.INSTANCE.init();
        new FluidTest();
    }
}
