package idealindustrial.tile.meta.multi.struct;

import idealindustrial.tile.meta.multi.struct.CheckMachineParams.CheckMode;
import idealindustrial.util.worldgen.ICoordManipulator;

import java.util.List;
import java.util.function.Consumer;

public class MultiMachineShape<MultiTile extends IStructuredMachine> {

    List<MultiMachineStructureBox> boxes;
    List<Consumer<MultiTile>> predicates;//named predicates cause it throws exception if structure is invalid

    public MultiMachineShape(List<MultiMachineStructureBox> boxes, List<Consumer<MultiTile>> predicates) {
        this.boxes = boxes;
        this.predicates = predicates;
    }

    protected void run(MultiTile tile, CheckMachineParams params) {
        for (MultiMachineStructureBox box : boxes) {
            ICoordManipulator<CheckMachineParams> manipulator = box.getManipulator();
            manipulator.rotateY(tile.getRotation());
            manipulator.move(tile.getPosition());
            manipulator.start(params);
        }
        if (params.mode == CheckMode.CHECK) {
            for (Consumer<MultiTile> predicate : predicates) {
                predicate.accept(tile);
            }
        }
    }

    public boolean checkMachine(MultiTile tile) {
        try {
            run(tile, new CheckMachineParams(CheckMode.CHECK, tile, null));
        } catch (MachineStructureException e) {
            e.printStackTrace();//todo properly send to player
            return false;
        }
        return true;
    }

    public void render(MultiTile tile, IGuideRenderer guideRenderer) {
        run(tile, new CheckMachineParams(CheckMode.RENDER, tile, guideRenderer));
    }

    public void build(MultiTile tile) {
        run(tile, new CheckMachineParams(CheckMode.BUILD, tile, null));
    }
}
