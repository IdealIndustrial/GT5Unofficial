package idealindustrial.impl.autogen.material.submaterial.chem;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.MaterialStack;

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

    public int amount() {
        return amount * element.amount();
    }

    public int countElement(Element element) {
        return amount * element.countElement(element);
    }

    public ChemicalMatter getElement() {
        return element;
    }

    public int getAmount() {
        return amount;
    }

    public MaterialStack asMaterialStack() {
        II_Material material = ChemicalInfo.materialMap.get(element.toString());
        if (material == null) {
            return null;
        }
        return new MaterialStack(material, amount);
    }

}
