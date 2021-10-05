package idealindustrial.tile.impl.multi.struct;

import idealindustrial.util.worldgen.Vector3;

public class MachineStructureException extends RuntimeException {

    public MachineStructureException(String message) {
        super(message);
    }

    public static MachineStructureException invalidBlockAt(Vector3 position) throws MachineStructureException {
        throw new MachineStructureException("invalid block at: " + position);
    }

    public static MachineStructureException invalidBlockAt(Vector3 position, String expectedName, String actual) throws MachineStructureException {
        throw new MachineStructureException("invalid block at: " + position + ", expected: " + expectedName + ", find: " + actual);
    }

    public static MachineStructureException notEnoughInfo() {
        throw new NotEnoughInfoException("no info");
    }

    public static class NotEnoughInfoException extends MachineStructureException {

        public NotEnoughInfoException(String message) {
            super(message);
        }
    }
}
