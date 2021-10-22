package idealindustrial.util.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


/**
 * class for use in HashMap, ignores NBT and NBT when comparing
 */
public class HashedStack implements ItemHelper.Rehashable {
    protected int hash;
    protected int itemID;
    protected final int damage;
    protected final Item item;

    public HashedStack(ItemStack item) {
        this(item.getItem(), Items.feather.getDamage(item));
    }

    public HashedStack(Item item, int id, int damage) {
        assert item != null;
        this.item = item;
        this.damage = damage;
        this.itemID = id;
        this.hash = id | (item.isDamageable() ? 0 : (damage << 16));
    }

    public HashedStack(Item item, int damage) {
        this(item, Item.getIdFromItem(item), damage);
    }

    public HashedStack(int hash) {
        damage =  (hash & 0xFFFF0000) >>> 16;
        itemID = hash & 0x0000FFFF;
        this.hash = hash;
        item = Item.getItemById(itemID);
    }

    /**
     * makes II_Stack equivalent of this
     *
     * @param amount - stackSize
     * @return II_Stack
     */
    public II_ItemStack toIIStack(int amount) {
        return new II_ItemStack(item, getDamage(), amount);
    }

    /**
     * to use as method reference
     *
     * @return II_Stack equivalent of this
     */
    public II_ItemStack toIIStack() {
        return toIIStack(1);
    }

    public ItemStack toItemStack(int amount) {
        return new ItemStack(item, amount, damage);
    }

    public int getDamage() {//untested, so todo: check
        return item.isDamageable() ? 0 : (hash & 0xFFFF0000) >>> 16;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashedStack that = (HashedStack) o;
        return hash == that.hash;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    /**
     * minecraft int item IDs can change after server starting, so it's necessary to update hash value
     */
    public void fixHash() {
        itemID = Item.getIdFromItem(item);
        this.hash = itemID| (item.isDamageable() ? 0 : (damage << 16));
    }

    public HashedStack asWildcard() {
        return new HashedStack(item, itemID, OreDictionary.WILDCARD_VALUE);
    }
}
