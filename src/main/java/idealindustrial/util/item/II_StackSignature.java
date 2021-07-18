package idealindustrial.util.item;

import idealindustrial.itemgen.oredict.II_OreInfo;
import net.minecraft.item.Item;

import java.util.List;

public class II_StackSignature extends II_ItemStack {
    CheckType type;
    II_OreInfo oreInfo;
    public II_StackSignature(Item item, int damage, int amount, CheckType checkType) {
        super(item, damage, amount);
        assert checkType != CheckType.OREDICT;
        this.type = checkType;
    }

    public II_StackSignature(II_OreInfo oreInfo) {
        this.type = CheckType.OREDICT;
        this.oreInfo = oreInfo;
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
     * @return all possible stacks with this oreName
     */
    public List<II_HashedStack> correspondingStacks() {
        return oreInfo.getSubItems();
    }

    /**
     * for brute force solution in checkRecipe
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

}
