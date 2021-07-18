package idealindustrial.util.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/**
 * class for use in HashMap, ignores NBT and NBT when comparing
 */
public class II_HashedStack {
    protected final int hash, itemID,damage;
    protected final Item item;

    public II_HashedStack(ItemStack item) {
        this(item.getItem(), Items.feather.getDamage(item));
    }

    public II_HashedStack(Item item, int id, int damage) {
        assert item != null;
        this.item = item;
        this.damage = damage;
        this.itemID = id;
        this.hash = id | (item.isDamageable() ? 0 : (damage << 16));
    }

    public II_HashedStack(Item item, int damage) {
        this(item, Item.getIdFromItem(item), damage);
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

    public int getDamage() {//untested, so todo: check
        return item.isDamageable() ? 0 : (hash & 0xFFFF0000) >>> 16;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        II_HashedStack that = (II_HashedStack) o;
        return hash == that.hash;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
