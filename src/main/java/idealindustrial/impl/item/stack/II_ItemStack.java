package idealindustrial.impl.item.stack;


import appeng.util.item.AESharedNBT;
import appeng.util.item.II_Hackery;
import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.oredict.OreInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * class for all itemStack work in GT, has fast hashCode and equals for NBT
 */
public class II_ItemStack {
    ItemDef def;
    public int amount;
    HashedStack cachedStack;
    ItemStack cachedMCStack;

    public II_ItemStack(ItemStack is) {
        assert is != null && is.getItem() != null;
        this.def = new ItemDef(is.getItem());
        this.def.setDamageValue(Items.feather.getDamage(is));
        if (is.getTagCompound() != null) {
            this.def.setTagCompound(II_Hackery.getNBTHackery(is.getTagCompound(), is));
        }
        this.amount = is.stackSize;
    }

    public II_ItemStack(Item item, int damage, int amount) {
        this.def = new ItemDef(item);
        assert def.getItem() != null;
        def.setDamageValue(damage);
        this.amount = amount;
    }


    protected II_ItemStack() {

    }

    protected II_ItemStack(II_ItemStack stack) {
        this.def =  stack.def.copy();
        this.amount = stack.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof HashedStack) {
            HashedStack that = ((HashedStack) o);

        }
//        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof II_ItemStack)) {
            return false;
        }
        II_ItemStack that = (II_ItemStack) o;
        return def.equals(that.def);
    }

    public boolean equalsIgnoreNBT(II_ItemStack stack) {
        return def.getItemID() == stack.def.getItemID() && def.getDamageValue() == stack.def.getDamageValue();
    }

    public boolean equalsHashedStack(HashedStack stack) {
        return stack.itemID == def.getItemID() && stack.damage == def.getDamageValue();
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
        OreInfo oreInfo = OreDict.get(ore);
        if (oreInfo == null) {
            return false;
        }
        for (HashedStack stack : oreInfo.getSubItems()) {
            if (equalsHashedStack(stack)) {
                return true;
            }
        }
        return false;
    }


    public HashedStack toHashedStack() {
        if (cachedStack == null) {
            return cachedStack = new HashedStack(def.getItem(), def.getDamageValue());
        }
        return cachedStack;
    }

    public ItemStack toMCStack() {
        if (def == null) {
            System.out.println("bug, II_ItemStack with no item");
            return null;
        }
        ItemStack stack =  new ItemStack(def.getItem(), amount, def.getDamageValue());
        if (def.getTagCompound() != null) {
            stack.setTagCompound(def.getTagCompound().getNBTTagCompoundCopy());
        }
        cachedMCStack = stack;
        return stack;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        return toMCStack().writeToNBT(tagCompound);//todo: implement better
    }

    public static II_ItemStack loadFromNBT(NBTTagCompound tagCompound) {
        ItemStack is = ItemStack.loadItemStackFromNBT(tagCompound);
        return is == null ? null : new II_ItemStack(is);
    }

    public GameRegistry.UniqueIdentifier getUniqueID() {
        return def.getUniqueID();
    }

    public int getDamageValue() {
        return def.getDamageValue();
    }

    public AESharedNBT getTagCompound() {
        return def.getTagCompound();
    }

    public II_ItemStack copy() {
        return new II_ItemStack(this);
    }

    public boolean isValid() {
        return def != null && def.getItem() != null;
    }

    public void setTagCompound(NBTTagCompound nbt) {
        def.setTagCompound(II_Hackery.getNBTHackery(nbt, toMCStack()));
    }
}
