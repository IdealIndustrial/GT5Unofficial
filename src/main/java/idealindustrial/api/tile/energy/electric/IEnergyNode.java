package idealindustrial.api.tile.energy.electric;

import idealindustrial.impl.tile.energy.electric.system.EnergyConnection;
import idealindustrial.impl.tile.energy.electric.system.EnergyNodeType;

import java.util.List;

//may be INode should not extend from IEnergyPassThrough
public interface IEnergyNode extends IEnergyPassThrough {
    EnergyNodeType type();

    List<EnergyConnection> connections();
}
