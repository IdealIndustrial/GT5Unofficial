package idealindustrial.util.worldgen.util;

import java.util.Random;

public interface IRandomStructure<M> {

    ICoordManipulator<M> provideManipulator(Random random);
}
