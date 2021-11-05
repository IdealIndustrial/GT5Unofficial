package idealindustrial.impl.tile.impl.multi.struct;

import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams.CheckMode;
import idealindustrial.util.misc.II_Util;
import idealindustrial.impl.world.util.BoundingBox;
import idealindustrial.api.world.util.ICoordManipulator;

import java.util.List;
import java.util.function.Consumer;

public class MultiMachineShape {

    List<MultiMachineStructureBox> boxes;
    List<Consumer<IStructuredMachine>> predicates;//named predicates cause it throws exception if structure is invalid

    public MultiMachineShape(List<MultiMachineStructureBox> boxes, List<Consumer<IStructuredMachine>> predicates) {
        this.boxes = boxes;
        this.predicates = predicates;
    }

    protected void run(IStructuredMachine tile, CheckMachineParams params) {
        for (MultiMachineStructureBox box : boxes) {
            ICoordManipulator<CheckMachineParams> manipulator = box.getManipulator();
            manipulator.rotateY(tile.getRotation());
            manipulator.move(tile.getPosition());
            manipulator.start(params);
        }
        if (params.mode == CheckMode.CHECK) {
            for (Consumer<IStructuredMachine> predicate : predicates) {
                predicate.accept(tile);
            }
        }
    }

    public boolean checkMachine(IStructuredMachine tile) {
        try {
            run(tile, new CheckMachineParams(CheckMode.CHECK, tile, null));
        } catch (MachineStructureException e) {
            System.out.println(e.getMessage());//todo properly send to player
            return false;
        }
        return true;
    }

    public void render(IStructuredMachine tile, IGuideRenderer guideRenderer) {
        run(tile, new CheckMachineParams(CheckMode.RENDER, tile, guideRenderer));
    }

    public void build(IStructuredMachine tile) {
        run(tile, new CheckMachineParams(CheckMode.BUILD, tile, null));
    }

    public TLongSet getAwaitingChunks(IStructuredMachine tile) {
        TLongSet set = new TLongHashSet();
        for (MultiMachineStructureBox box : boxes) {
            ICoordManipulator<?> manipulator = box.getManipulator();
            manipulator.rotateY(tile.getRotation());
            manipulator.move(tile.getPosition());
            BoundingBox bb = manipulator.getBox();
            int startChunkX = bb.getMinX() >> 4, endChunkX = bb.getMaxX() >> 4;
            int startChunkZ = bb.getMinZ() >> 4, endChunkZ = bb.getMaxZ() >> 4;
            for (int x = startChunkX; x <= endChunkX; x++) {
                for (int z = startChunkZ; z <= endChunkZ; z++) {
                    set.add(II_Util.intsToLong(x, z));
                }
            }
        }
        return set;
    }
}
