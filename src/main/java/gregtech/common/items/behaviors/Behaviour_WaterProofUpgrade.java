package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Behaviour_WaterProofUpgrade
        extends Behaviour_None {
    public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_WaterProofUpgrade();

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (((aPlayer instanceof EntityPlayerMP))) {
            TileEntity tile = aWorld.getTileEntity(aX, aY, aZ);
            if(tile instanceof BaseMetaTileEntity){
                BaseMetaTileEntity t = (BaseMetaTileEntity)tile;
                if (!t.mWaterProof){
                        t.mWaterProof = true;
                        t.sendBlockEvent((byte)8,(byte)1);
                        if (!aPlayer.capabilities.isCreativeMode) {
                           aStack.stackSize--;
                        }
                }
                return true;
            }
        }
        return aPlayer instanceof EntityPlayerMP;
    }

}
