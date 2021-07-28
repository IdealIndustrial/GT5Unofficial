package idealindustrial.util.energy.system;

import java.util.List;

public interface IEnergyNode extends IEnergyPassThrough {
    EnergyNodeType type();

    List<EnergyConnection> connections();
}
