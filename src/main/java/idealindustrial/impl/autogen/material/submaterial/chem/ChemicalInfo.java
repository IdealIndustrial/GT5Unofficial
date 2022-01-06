package idealindustrial.impl.autogen.material.submaterial.chem;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.MaterialStack;

import java.util.*;

public class ChemicalInfo {

    static Map<String, II_Material> materialMap = new HashMap<>();
    static Map<Element, II_Material> elementMap = new HashMap<>();

    ChemicalStack stack;
    List<MaterialStack> subMaterials;

    public ChemicalInfo(ChemicalStack stack) {
        this.stack = stack;
    }

    public String getFormula() {
        return stack.asArgumentString();
    }

    public int getAtoms() {
        return stack.amount();
    }

    public Element getAsElement() {
        assert stack.element.isPrimitive();
        return (Element) stack.element;
    }

    public boolean isPrimitive() {
        return stack.element.isPrimitive();
    }

    public int countElement(Element element) {
        return stack.countElement(element);
    }

    public ChemicalStack getStack() {
        return stack;
    }

    public List<MaterialStack> getSubMaterials() {
        return subMaterials;
    }


    public void putToMap(II_Material material) {
        subMaterials = new ArrayList<>();

        if (stack.element instanceof Element) {
            if (stack.element == Element.NULL) {
                return;
            }
            ((Element) stack.element).material = material;
            elementMap.put((Element) stack.element, material);
        }

        materialMap.put(getFormula(), material);

        if (stack.element instanceof Molecule) {
            Molecule molecule = (Molecule) stack.element;
            molecule.elements.stream().map(ChemicalStack::asMaterialStack).filter(Objects::nonNull).forEach(ms -> subMaterials.add(ms));
        }

    }
}
