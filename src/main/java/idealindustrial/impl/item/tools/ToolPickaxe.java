package idealindustrial.impl.item.tools;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.submaterial.MaterialAutogenInfo;
import idealindustrial.util.misc.II_Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ToolPickaxe extends ToolWithBreakingMatrix {

    public ToolPickaxe(int itemID) {
        super(itemID);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote || !player.isSneaking()) {
            return false;
        }
        int rad = radius.get(stack);
        rad = (rad + 1) % 2;
        int len = rad * 2 + 1;
        if (rad == 0) {
           II_Util.sendChatToPlayer(player, "normal mode");
        }
        else {
            II_Util.sendChatToPlayer(player, "tunnel mode");
            II_Util.sendChatToPlayer(player, "now mines " + len + "x" + len);
        }
        radius.set(stack, rad);
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }


    @Override
    public ItemStack initForMaterial(ItemStack itemStack, II_Material material) {
        MaterialAutogenInfo info = material.getAutogenInfo();
        assert info.toolLevel != 0;
        speed.set(itemStack,info.toolSpeed);
        harvestLevel.set(itemStack, info.toolLevel);
        maxDamage.set(itemStack, info.toolHardness);
        harvestTools.set(itemStack, "p");
        return itemStack;
    }
}
