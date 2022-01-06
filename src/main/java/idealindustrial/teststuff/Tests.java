package idealindustrial.teststuff;

import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.chem.Element;

public class Tests {


    public static void main(String[] args) {
        Element e = II_Materials.tin.getChemicalInfo().getAsElement();
        int am = II_Materials.cassiterite.getChemicalInfo().getAtoms();
        int el = II_Materials.cassiterite.getChemicalInfo().countElement(e);
        int a = 0;
    }
}
