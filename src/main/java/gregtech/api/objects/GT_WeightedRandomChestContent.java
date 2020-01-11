package gregtech.api.objects;

import gregtech.common.items.behaviors.Behaviour_ProspectorsBook;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;

import java.util.Random;

public class GT_WeightedRandomChestContent extends WeightedRandomChestContent {

    public GT_WeightedRandomChestContent(ItemStack aStack, int aMinCount, int aMaxCount, int p_i1558_4_)
    {
        super(aStack, aMinCount, aMaxCount, p_i1558_4_);
    }


    @Override
    protected ItemStack[] generateChestContent(Random random, IInventory newInventory) {
        if(newInventory instanceof TileEntity){
            TileEntity tileEntity = (TileEntity)newInventory;
            return new ItemStack[]{Behaviour_ProspectorsBook.getBook(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, random)};
        }
        return super.generateChestContent(random, newInventory);
    }
}
