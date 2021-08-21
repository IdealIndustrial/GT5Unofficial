package idealindustrial.net.fields;

import idealindustrial.net.II_NetworkedField;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

import java.util.List;

public class II_NetworkedShort  {

    protected short value;
    protected int id;
    protected List<ICrafting> crafters;
    protected Container container;

    public II_NetworkedShort(int id, Container container, List<ICrafting> crafters) {
        this.id = id;
        this.container = container;
        this.crafters = crafters;
    }

    public void set(short value) {
        if (this.value != value) {
            crafters.forEach(c -> c.sendProgressBarUpdate(container, id, value));
        }
        this.value = value;
    }

    public void accept(int id, int value) {
        if (this.id == id) {
            this.value = (short) value;
        }
    }

    public short get() {
        return value;
    }
}
