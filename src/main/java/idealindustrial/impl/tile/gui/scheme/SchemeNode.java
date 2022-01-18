package idealindustrial.impl.tile.gui.scheme;

import java.util.List;

public interface SchemeNode {

    List<SchemeNode> getConnections();

    void draw(DrawPhase phase);
}
