package idealindustrial.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.textures.TextureManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GT_FluidDisplayItem
        extends BaseItem {
    public static GT_FluidDisplayItem INSTANCE;
    public GT_FluidDisplayItem() {
        super("GregTech_FluidDisplay");
        INSTANCE = this;
        setHasSubtypes(true);
    }

    protected void addAdditionalToolTips(List aList, ItemStack aStack, EntityPlayer aPlayer) {
        NBTTagCompound aNBT = aStack.getTagCompound();
        if (aNBT != null) {
            long tToolTipAmount = aNBT.getLong("mFluidDisplayAmount");
            if (tToolTipAmount > 0L) {
                aList.add(EnumChatFormatting.BLUE + "Amount:" + tToolTipAmount + EnumChatFormatting.GRAY);
            }
            aList.add(EnumChatFormatting.RED + "Temperature:" + aNBT.getLong("mFluidDisplayHeat") + EnumChatFormatting.GRAY);
            aList.add(EnumChatFormatting.GREEN + "State:" + (aNBT.getBoolean("mFluidState") ? "Gas" : "Liquid") + EnumChatFormatting.GRAY);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aIconRegister) {

    }

    public IIcon getIconFromDamage(int aMeta) {
        return Stream.of(FluidRegistry.getFluid(aMeta), FluidRegistry.WATER)
                    .filter(Objects::nonNull)
                    .map(Fluid::getStillIcon)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack aStack, int aRenderPass) {
        Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
        return tFluid == null ? 16777215 : tFluid.getColor();
    }

    public int getSpriteNumber() {
        return 0;
    }

    public String getUnlocalizedName(ItemStack aStack) {
        if (aStack != null) {
            Fluid fs = FluidRegistry.getFluid(aStack.getItemDamage());
            if (fs != null) {
                return fs.getUnlocalizedName();
            }
        }
        return "";
    }

    public String getItemStackDisplayName(ItemStack aStack) {
        if (aStack != null) {
            Fluid fs = FluidRegistry.getFluid(aStack.getItemDamage());
            if (fs != null) {
                return fs.getUnlocalizedName();
            }
        }
        return "";
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item aItem, CreativeTabs aTab, List aList) {

    }
}
