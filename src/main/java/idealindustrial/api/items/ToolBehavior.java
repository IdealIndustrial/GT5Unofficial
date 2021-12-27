package idealindustrial.api.items;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.util.nbt.NBTFieldInt;
import idealindustrial.util.nbt.NBTFieldString;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ToolBehavior {
    NBTFieldInt maxDamage = new NBTFieldInt("maxDmg", 10);
    NBTFieldInt currentDamage = new NBTFieldInt("dmg", 0);
    NBTFieldInt harvestLevel = new NBTFieldInt("hlevel", -1);
    Set<String> supportedTools = Stream.of("pickaxe", "shovel", "axe").collect(Collectors.toSet());
    NBTFieldString harvestTools = new NBTFieldString("htools", "");
    default float getBreakSpeed(PlayerEvent.BreakSpeed event) {
        return 1f;
    }

    default boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    default boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase player) {
        return false;
    }

    default ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        return itemStack;
    }

    default boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        return false;
    }


    default int getHarvestLevel(ItemStack stack, String toolClass) {
        if (toolClass == null) {
            return -1;
        }
        toolClass = toolClass.toLowerCase();
        if (!supportedTools.contains(toolClass)) {
            return -1;
        }
        String tools = harvestTools.get(stack);
        if (!tools.contains(toolClass.substring(0, 1))) {
            return -1;
        }
        return harvestLevel.get(stack);
    }

    default ItemStack initForMaterial(ItemStack itemStack, II_Material material) {
        return itemStack;
    }
}
