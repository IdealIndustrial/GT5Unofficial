package idealindustrial.impl.autogen.material.submaterial.chem;

public class ChemicalInfo {

    ChemicalStack stack;

    public ChemicalInfo(ChemicalStack stack) {
        this.stack = stack;
    }

    public String getFormula() {
        return stack.asArgumentString();
    }
}
