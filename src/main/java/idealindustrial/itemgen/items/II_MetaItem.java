package idealindustrial.itemgen.items;

import java.util.BitSet;
import java.util.Map;

public abstract class II_MetaItem extends II_BaseItem {


    public II_MetaItem(String unlocalized) {
        super(unlocalized);
        setHasSubtypes(true);
    }
}
