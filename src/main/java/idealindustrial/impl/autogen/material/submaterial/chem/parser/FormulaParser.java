package idealindustrial.impl.autogen.material.submaterial.chem.parser;

import idealindustrial.impl.autogen.material.submaterial.chem.ChemicalStack;
import idealindustrial.impl.autogen.material.submaterial.chem.Element;
import idealindustrial.impl.autogen.material.submaterial.chem.Molecule;

import java.util.ArrayList;
import java.util.List;

public class FormulaParser {
    public static FormulaParser INSTANCE = new FormulaParser();

    public static ChemicalStack parse(String str) {
        return INSTANCE.parse(new StringSource(str), true);
    }

    public ChemicalStack parse(CharSource source, boolean prefixed) {
        source.skipWs();
        int amount = 1;
        if (prefixed && source.test(Character::isDigit)) {
            amount = parseAmount(source);
        }
        List<ChemicalStack> subElements = new ArrayList<>();
        do {
            if (source.test(Character::isLetter) && source.test(Character::isUpperCase)) {
                subElements.add(parseElement(source));
            } else if (source.skip('(')) {
                subElements.add(parse(source, false));
            } else {
                throw new IllegalArgumentException("Expected element");
            }
        } while (source.test(Character::isLetter) && source.test(Character::isUpperCase) || source.test('('));
        source.skip(')');
        if (!prefixed && source.test(Character::isDigit)) {
            amount = parseAmount(source);
        }
        return new ChemicalStack(new Molecule(subElements), amount);
    }

    private int parseAmount(CharSource source) {
        StringBuilder builder = new StringBuilder();
        while (source.test(Character::isDigit)) {
            builder.append(source.next());
        }
        return Integer.parseInt(builder.toString());
    }

    private ChemicalStack parseElement(CharSource source) {
        StringBuilder builder = new StringBuilder();
        builder.append(source.next());
        while (source.test(Character::isLetter) && source.test(Character::isLowerCase)) {
            builder.append(source.next());
        }
        int amount = 1;
        if (source.test(Character::isDigit)) {
            amount = parseAmount(source);
        }
        return new ChemicalStack(Element.valueOf(builder.toString()), amount);
    }
}
