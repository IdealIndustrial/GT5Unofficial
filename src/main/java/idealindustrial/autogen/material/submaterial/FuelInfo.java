package idealindustrial.autogen.material.submaterial;

public class FuelInfo {

    long energyCapacityPerUnit;
    FuelType type;

    public FuelInfo(FuelType type, long energyCapacityPerUnit) {
        this.energyCapacityPerUnit = energyCapacityPerUnit;
        this.type = type;
    }
}
