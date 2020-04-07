package gregtech.common.items.behaviors;

import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.*;

public class Behaviour_ProspectorsBook_Creative
        extends Behaviour_None {

    public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_ProspectorsBook_Creative();
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.prospecting.creative", "Creates miner's diary by right click");


    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if(aWorld.isRemote)
            return true;
        ItemStack tBook = Behaviour_ProspectorsBook.getBook(aWorld,aX,aY,aZ,new Random());
        aPlayer.setCurrentItemOrArmor(0,tBook);
        return true;
    }


    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }

}
