package idealindustrial.impl.tile.impl.multi.struct;

import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import idealindustrial.api.world.util.ICoordManipulator;
import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams.CheckMode;
import idealindustrial.impl.world.util.BoundingBox;
import idealindustrial.util.misc.II_Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MultiMachineShape {

    public static final MultiMachineShape EmptyShape = new MultiMachineShape(new ArrayList<>(), new ArrayList<>()) {
        @Override
        public boolean checkMachine(IStructuredMachine tile) {
            return false;
        }
    };

    List<MultiMachineStructureBox> boxes;
    List<Consumer<IStructuredMachine>> predicates; //named predicates cause it throws exception if structure is invalid

    public MultiMachineShape(List<MultiMachineStructureBox> boxes, List<Consumer<IStructuredMachine>> predicates) {
        this.boxes = boxes;
        this.predicates = predicates;
    }

    protected void checkBox(MultiMachineStructureBox box, IStructuredMachine tile, CheckMachineParams params) {
        ICoordManipulator<CheckMachineParams> manipulator = getManipulator(box, tile, params);
        MachineStructureException exception;
        try {
            manipulator.start(params);
            return;
        } catch (MachineStructureException e) {
            exception = e;
        }
        if (box.canBeXInverted()) {
            manipulator = getManipulator(box, tile, params);
            manipulator.invert(true, false);
            try {
                manipulator.start(params);
                return;
            } catch (MachineStructureException e) {
                exception = e;
            }
        }
        if (box.canBeZInverted()) {
            manipulator = getManipulator(box, tile, params);
            manipulator.invert(false, true);
            try {
                manipulator.start(params);
                return;
            } catch (MachineStructureException e) {
                exception = e;
            }
        }

        if (box.canBeXInverted() && box.canBeZInverted()) {
            manipulator = getManipulator(box, tile, params);
            manipulator.invert(true, true);
            try {
                manipulator.start(params);
                return;
            } catch (MachineStructureException e) {
                exception = e;
            }
        }
        throw exception;
    }

    protected ICoordManipulator<CheckMachineParams> getManipulator(MultiMachineStructureBox box, IStructuredMachine tile, CheckMachineParams params) {
        ICoordManipulator<CheckMachineParams> manipulator = box.getManipulator();
        manipulator.rotateY(tile.getRotation());
        manipulator.move(tile.getPosition());
        if (params.renderer != null) {
            manipulator.invert(params.renderer.isXInverted(), params.renderer.isZInverted());
        }
        return manipulator;
    }

    protected void run(IStructuredMachine tile, CheckMachineParams params) {
        for (MultiMachineStructureBox box : boxes) {
            if (params.mode == CheckMode.CHECK) {
                checkBox(box, tile, params);
                continue;
            }
            getManipulator(box, tile, params).start(params);
        }
        if (params.mode == CheckMode.CHECK) {
            for (Consumer<IStructuredMachine> predicate : predicates) {
                predicate.accept(tile);
            }
        }
    }

    public boolean checkMachine(IStructuredMachine tile) {
        try {
            run(tile, new CheckMachineParams(CheckMode.CHECK, tile));
        } catch (MachineStructureException e) {
            System.out.println(e.getMessage());//todo properly send to player
            return false;
        }
        return true;
    }

    public void render(IStructuredMachine tile, IGuideRenderer guideRenderer) {
        run(tile, new CheckMachineParams(CheckMode.RENDER, tile).setRenderer(guideRenderer));
    }

    public void build(IStructuredMachine tile, IGuideRenderer guideRenderer) {
        run(tile, new CheckMachineParams(CheckMode.BUILD, tile));
    }

    public void execute(IStructuredMachine tile, CheckMachineParams.CoordVisitor visitor) {
        run(tile, new CheckMachineParams(CheckMode.EVAL, tile).setVisitor(visitor));
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
