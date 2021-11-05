package idealindustrial.items.tools;

import idealindustrial.items.MetaToolItem;
import idealindustrial.items.ToolBehavior;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_EventUtil;
import idealindustrial.util.misc.II_Util;
import idealindustrial.util.nbt.NBTFieldInt;
import idealindustrial.util.worldgen.util.ICoordManipulator;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ToolDrill implements ToolBehavior {
    public static final int TOOL_DRILL_ID = 0;
    private static final int MaxRadius = 5;

    public static final NBTFieldInt radius = new NBTFieldInt("radius", 0), speed = new NBTFieldInt("speed", 1);

    @Override
    public float getBreakSpeed(PlayerEvent.BreakSpeed event) {
        return speed.get(event.entityPlayer.getHeldItem()) * 10;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        int sp = speed.get(stack);
        if (player.isSneaking()) {
            sp--;
        } else {
            sp++;
        }
        if (sp < 1) {
            sp = 1;
        }
        if (sp > 100) {
            sp = 100;
        }
        II_Util.sendChatToPlayer(player, "Drill Speed: " + sp);
        speed.set(stack, sp);
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        }
        int rad = radius.get(itemStack);
        if (player.isSneaking()) {
            rad--;
        } else {
            rad++;
        }
        if (rad < 0) {
            rad = 0;
        }
        if (rad > MaxRadius) {
            rad = MaxRadius;
        }
        II_Util.sendChatToPlayer(player, "Drill radius: " + rad);
        radius.set(itemStack, rad);
        return itemStack;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase player) {
        //todo use energy
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        //        int radius = getRadius(itemStack);
        int radius = ToolDrill.radius.get(itemstack);
        if (radius == 0) {
            return false;
        }
        getManipulator(player, radius, x, y, z)
                .start(pos -> {
                    breakAdjacentBlock(player, itemstack, pos.x, pos.y, pos.z, player.worldObj);
                });
        return false;
    }

    private void breakAdjacentBlock(EntityPlayer player, ItemStack is, int x, int y, int z, World world) {
        if (world.isRemote || !II_EventUtil.canBreak(player, x, y, z)) {
            return;
        }
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (block.getBlockHardness(world, x, y, z) > 0F)
            is.getItem().onBlockDestroyed(is, world, block, x, y, z, player);

        block.dropXpOnBlockBreak(world, x, y, z, block.getExpDrop(world, meta, 0));
        block.onBlockHarvested(world, x, y, z, meta, player);
        if (block.removedByPlayer(world, player, x, y, z, true)) {
            block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            if (!player.capabilities.isCreativeMode) {
                block.harvestBlock(world, player, x, y, z, meta);
            }
        }
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        if ("pickaxe".equals(toolClass) || "shovel".equals(toolClass)) {
            return 3;
        }
        return 0;
    }

    public static void onServerTick() {

    }


    private static int localID = 0;

    public static void updateBlockBreaking(ItemInWorldManager manager, int id, int x, int y, int z, int state) {
        ItemStack is = manager.thisPlayerMP.getHeldItem();
        if (is == null || is.getItem() != MetaToolItem.INSTANCE || is.getItemDamage() != TOOL_DRILL_ID || radius.get(is) == 0) {
            return;
        }
        localID = id * 1000;
        getManipulator(manager.thisPlayerMP, radius.get(is), x, y, z)
                .start(pos -> {
                    if (pos.x == x && pos.y == y && pos.z == z) {
                        return;
                    }
                    manager.theWorld.destroyBlockInWorldPartially(localID++, pos.x, pos.y, pos.z, state);
                });
    }


    public static void cancelBlockBreaking(ItemInWorldManager manager, int id, int x, int y, int z) {
        ItemStack is = manager.thisPlayerMP.getHeldItem();
        if (is == null || is.getItem() != MetaToolItem.INSTANCE || is.getItemDamage() != TOOL_DRILL_ID || radius.get(is) == 0) {
            return;
        }
        localID = id * 1000;
        getManipulator(manager.thisPlayerMP, radius.get(is), x, y, z)
                .start(pos -> {
                    if (pos.x == x && pos.y == y && pos.z == z) {
                        return;
                    }
                    manager.theWorld.destroyBlockInWorldPartially(localID++, pos.x, pos.y, pos.z, -1);
                });
    }


    private static final DrillMaskMatrix[][] matricesCache = new DrillMaskMatrix[2][MaxRadius];

    static {
        for (int b = 0; b < 2; b++) {
            boolean val = b == 1;
            for (int r = 0; r < matricesCache[b].length; r++) {
                matricesCache[b][r] = new DrillMaskMatrix(r + 1, val);
            }
        }
    }

    private static ICoordManipulator<DrillMaskMatrix.Action> getManipulator(EntityPlayer player, int radius, int x, int y, int z) {
        assert radius >= 1;
        int side = II_DirUtil.getPlacingFace(player.rotationYaw, player.rotationPitch, true);
        side = II_DirUtil.getOppositeSide(side);
        int rotation = II_DirUtil.getRotationForDirectionFromNormal(side);
        ICoordManipulator<DrillMaskMatrix.Action> out =
                radius - 1 < MaxRadius ?
                        matricesCache[side >= 2 ? 1 : 0][radius - 1].getManipulator() :
                        new DrillMaskMatrix(radius, side >= 2).getManipulator();
        out.move(x, y, z);
        out.rotateY(rotation);
        return out;
    }


}
