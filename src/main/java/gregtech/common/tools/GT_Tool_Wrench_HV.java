package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_Tool_Wrench_HV
        extends GT_Tool_Wrench_LV {
    public int getToolDamagePerBlockBreak() {
        return 800;
    }

    public int getToolDamagePerDropConversion() {
        return 1600;
    }

    public int getToolDamagePerContainerCraft() {
        return 12800;
    }

    public int getToolDamagePerEntityAttack() {
        return 3200;
    }

    public int getBaseQuality() {
        return 1;
    }

    public float getBaseDamage() {
        return 2.0F;
    }

    public float getSpeedMultiplier() {
        return 4.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 4.0F;
    }

    public boolean canBlock() {
        return false;
    }

    public static boolean canBreakBlock(Block aBlock, int aMetaData) {
        String tTool = aBlock.getHarvestTool(aMetaData);
        return ((tTool != null) && (tTool.equals("wrench"))) || (aBlock.getMaterial() == Material.piston) || (aBlock == Blocks.hopper) || (aBlock == Blocks.dispenser) || (aBlock == Blocks.dropper);
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadWrench.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }
}
