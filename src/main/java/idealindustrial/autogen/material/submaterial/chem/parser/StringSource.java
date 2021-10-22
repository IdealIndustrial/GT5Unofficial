package idealindustrial.autogen.material.submaterial.chem.parser;

public class StringSource implements CharSource {

    String str;
    int i;

    public StringSource(String str) {
        this.str = str;
        this.i = 0;
    }

    @Override
    public char getNext() {
        return str.charAt(i);
    }

    @Override
    public boolean hasNext() {
        return i < str.length();
    }

    @Override
    public char next() {
        return str.charAt(i++);
    }
}
