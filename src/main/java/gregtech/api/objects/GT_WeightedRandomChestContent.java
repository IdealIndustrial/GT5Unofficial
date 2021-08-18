package gregtech.api.objects;

import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Utility;
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
            for(int i = 0; i < newInventory.getSizeInventory(); i++){
                if(GT_Utility.areStacksEqual(ItemList.ProspectorsBook.get(1L),newInventory.getStackInSlot(i),true))
                    return new ItemStack[0];
            }
            TileEntity tileEntity = (TileEntity)newInventory;
            return new ItemStack[]{Behaviour_ProspectorsBook.getBook(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.getWorldObj().getChunkHeightMapMinimum(tileEntity.xCoord,tileEntity.zCoord), tileEntity.zCoord, random,false)};
        }
        return new ItemStack[0];
    }
}
