package idealindustrial.impl.autogen.material.submaterial.chem;

import java.util.List;

public class Molecule implements ChemicalMatter{

    List<ChemicalStack> elements;


    public Molecule(List<ChemicalStack> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ChemicalStack stack : elements) {
            builder.append(stack);
        }
        return builder.toString();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }
}
