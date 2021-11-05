package idealindustrial.impl.autogen.material.submaterial.chem.parser;

public interface CharSource {

    char getNext();

    boolean hasNext();

    char next();

    default boolean skip(char ch) {
        if (hasNext() && getNext() == ch) {
            next();
            return true;
        }
        return false;
    }

    default boolean skip(CharPredicate ch) {
        if (hasNext() && ch.apply(getNext())) {
            next();
            return true;
        }
        return false;
    }

    default boolean skipWs() {
        boolean skipped = false;
        while (skip(Character::isWhitespace)) {
            skipped = true;
        }
        return skipped;
    }

    default boolean test(char ch) {
        return hasNext() && getNext() == ch;
    }

    default boolean test(CharPredicate predicate) {
        return hasNext() && predicate.apply(getNext());
    }


    interface CharPredicate {
        boolean apply(char ch);
    }
}
