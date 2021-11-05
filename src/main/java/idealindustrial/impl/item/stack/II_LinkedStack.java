package idealindustrial.impl.item.stack;

import net.minecraft.item.Item;

import java.util.Iterator;

public class II_LinkedStack extends II_ItemStack implements Iterable<II_LinkedStack> {

    public II_LinkedStack next;

    public II_LinkedStack(Item item, int damage, int amount) {
        super(item, damage, amount);
    }

    public II_LinkedStack(II_ItemStack stack) {
        super(stack);
    }

    @Override
    public Iterator<II_LinkedStack> iterator() {
        return new Iterator<II_LinkedStack>() {
            II_LinkedStack current = next, prev = null;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public II_LinkedStack next() {
                prev = current;
                current = current.next;
                return prev;
            }

            @Override
            public void remove() {
                prev.next = current.next;
            }
        };
    }

    public void append(II_ItemStack stack) {
        II_LinkedStack curr = this;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = new II_LinkedStack(stack);
    }
}
