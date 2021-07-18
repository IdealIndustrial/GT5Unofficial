package idealindustrial.util.item;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * class for all itemStack work in GT, has fast hashCode and equals for NBT
 */
public class II_ItemStack {
    ItemDef def;
    public int amount;
    II_HashedStack cachedStack;
    ItemStack cachedMCStack;

    public II_ItemStack(ItemStack is) {
        assert is != null && is.getItem() != null;
        this.def = new ItemDef(is.getItem());
    }

    public II_ItemStack(Item item, int damage, int amount) {
        this.def = new ItemDef(item);
        def.setDamageValue(damage);
        this.amount = amount;
    }

    protected II_ItemStack() {

    }

    protected II_ItemStack(II_ItemStack stack) {
        this.def =  stack.def;
        this.amount = stack.amount;
        this.cachedStack = stack.cachedStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof II_HashedStack) {
            II_HashedStack that = ((II_HashedStack) o);
            return that.itemID == def.getItemID() && that.damage == def.getDamageValue();
        }
        if (o == null || getClass() != o.getClass()) return false;
        II_ItemStack that = (II_ItemStack) o;
        return def.equals(that.def);
    }

    public boolean equalsIgnoreNBT(II_ItemStack stack) {
        return def.getItemID() == stack.def.getItemID() && def.getDamageValue() == stack.def.getDamageValue();
    }

    @Override
    public int hashCode() {
        return def.getMyHash();
    }

    public int amount() {
        return amount;
    }

    public void reHash() {
        def.reHash();
        cachedStack = toHashedStack();
    }

    public boolean hasOre(String ore) {
        return def.hasOre(ore);
    }


    public II_HashedStack toHashedStack() {
        if (cachedStack == null) {
            return cachedStack = new II_HashedStack(def.getItem(), def.getDamageValue());
        }
        return cachedStack;
    }

    public ItemStack toMCStack() {
        ItemStack stack =  new ItemStack(def.getItem(), amount, def.getDamageValue());
        stack.setTagCompound(def.getTagCompound().getNBTTagCompoundCopy());
        return stack;
    }
}
