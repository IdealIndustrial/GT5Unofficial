package idealindustrial.tile.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import idealindustrial.II_Core;
import idealindustrial.tile.base.II_BaseTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class II_GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof II_BaseTile) {
            return ((II_BaseTile) tile).getServerGUI(player, ID);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof II_BaseTile) {
            return ((II_BaseTile) tile).getClientGUI(player, ID);
        }
        return null;
    }

    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(II_Core.INSTANCE, new II_GuiHandler());
    }
}
