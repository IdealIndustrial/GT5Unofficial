package idealindustrial.util.item;

import appeng.util.item.II_Hackery;
import idealindustrial.itemgen.oredict.II_OreInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class II_StackSignature extends II_ItemStack {
    CheckType type;
    II_OreInfo oreInfo;

    public II_StackSignature(Item item, int damage, int amount) {
        super(item, damage, amount);
        this.type = CheckType.DAMAGE;
    }

    public II_StackSignature(II_OreInfo oreInfo, int amount) {
        assert oreInfo != null;
        this.type = CheckType.OREDICT;
        this.oreInfo = oreInfo;
        this.amount = amount;
    }

    public II_StackSignature(II_ItemStack stack, CheckType type) {
        this.def = stack.def.copy();
        this.type = type;
        this.amount = stack.amount;
    }

    public II_StackSignature(ItemStack stack, CheckType type) {
        super(stack.getItem(), stack.getItemDamage(), stack.stackSize);
        this.type = type;
        if (stack.getTagCompound() != null) {
            this.def.setTagCompound(II_Hackery.getNBTHackery(stack.getTagCompound(), stack));
        }
    }

    public void optimize() {
        //check if singleton oreDict => type = CheckType.DAMAGE, stack = def
        if (type == CheckType.OREDICT) {
            if (oreInfo.isSingleton()) {
                type = CheckType.DAMAGE;
                cachedStack = oreInfo.getUnified();
                def = new ItemDef(cachedStack.item);
                def.setDamageValue(cachedStack.damage);
            }
        }
    }

    /**
     * for mapSolution in checkRecipes
     * only for OreDict
     *
     * @return all possible stacks with this oreName
     */
    public List<II_HashedStack> correspondingStacks() {
        if (type == CheckType.OREDICT) {
            return oreInfo.getSubItems();
        }
        return Collections.singletonList(toHashedStack());
    }

    /**
     * for brute force solution in checkRecipe
     *
     * @param stack stack
     */
    public boolean isEqual(II_ItemStack stack) {
        switch (type) {
            case DIRECT:
                return equals(stack);
            case DAMAGE:
                return equalsIgnoreNBT(stack);
            case OREDICT:
                return stack.def.hasOre(oreInfo.getName());
        }
        throw new IllegalStateException("never thrown");
    }


    public CheckType getType() {
        return type;
    }

    public String getOre() {
        return oreInfo.getName();
    }

    public II_OreInfo getOreInfo() {
        return oreInfo;
    }
}
