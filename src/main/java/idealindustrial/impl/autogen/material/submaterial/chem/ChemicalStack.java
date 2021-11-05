package idealindustrial.impl.autogen.material.submaterial.chem;

public class ChemicalStack {

    ChemicalMatter element;
    int amount;

    public ChemicalStack(ChemicalMatter element, int amount) {
        this.element = element;
        this.amount = amount;
    }

    @Override
    public String toString() {
        if (amount == 1) {
            return element.toString();
        }
        if (element.isPrimitive()) {
            return element.toString() + amount;
        }
        return "(" + element.toString() + ")" + amount;
    }

    public String asArgumentString() {
        if (element == null) {
            return "";
        }
        if (amount == 1) {
            return element.toString();
        }
        return amount + element.toString();
    }
}
