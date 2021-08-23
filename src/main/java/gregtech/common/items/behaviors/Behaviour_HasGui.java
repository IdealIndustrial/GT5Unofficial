package gregtech.common.items.behaviors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.GT_Values;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.IBehaviorWithGui;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public abstract class Behaviour_HasGui
        extends Behaviour_None implements IBehaviorWithGui {

    @Override
    public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if (!aWorld.isRemote) {
            aPlayer.openGui(GT_Values.MOD_ID, 1, aWorld, 0,0,0);
        }
        return aStack;
    }

}
