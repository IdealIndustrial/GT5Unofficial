package idealindustrial.util.recipe;

//todo: think about interface. May be class with public fields will be ~~ok
public interface MachineParams {

    int tier();

    int voltage();

    int amperage();

    boolean areValid(MachineParams directMachineParams);

    //computation, quantum energy et.c

}
