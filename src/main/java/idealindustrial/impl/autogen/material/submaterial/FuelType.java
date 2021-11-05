package idealindustrial.impl.autogen.material.submaterial;

public enum FuelType {
    Burner(MatterState.Solid), Turbine(MatterState.Gas), Combustion(MatterState.Liquid)// et c
    ;
    MatterState fuelUnit;


    FuelType(MatterState fuelUnit) {
        this.fuelUnit = fuelUnit;
    }

    public MatterState getFuelUnit() {
        return fuelUnit;
    }


}
