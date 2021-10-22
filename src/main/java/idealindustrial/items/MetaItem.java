package idealindustrial.items;

public abstract class MetaItem extends BaseItem {


    public MetaItem(String unlocalized) {
        super(unlocalized);
        setHasSubtypes(true);
    }
}
