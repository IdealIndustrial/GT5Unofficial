package idealindustrial.util.energy.electric.system;

import java.util.List;

//may be INode should not extend from IEnergyPassThrough
public interface IEnergyNode extends IEnergyPassThrough {
    EnergyNodeType type();

    List<EnergyConnection> connections();
}
