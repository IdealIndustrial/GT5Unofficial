package idealindustrial.commands;

import codechicken.nei.recipe.ShapedRecipeHandler;
import gloomyfolken.hooklib.minecraft.HookLibPlugin;
import gregtech.api.enums.Materials;
import gregtech.api.events.TeleporterUsingEvent;
import gregtech.api.items.GT_MetaGenerated_Item;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import idealindustrial.hooks.HookLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class DimTPCommand extends CommandBase {


    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public String getCommandName() {
        return "dtp";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        if (!(ics instanceof EntityPlayer)) {
            return;
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        int d = Integer.parseInt(args[3]);
        TeleporterUsingEvent tEvent = new TeleporterUsingEvent((Entity) ics, x, y, z, d, true);
        MinecraftForge.EVENT_BUS.post(tEvent);
        if (!tEvent.isCanceled()) {
            GT_Utility.moveEntityToDimensionAtCoords((Entity) ics, d, x + 0.5D, y + 0.5D, z + 0.5D);
        }
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "dimension teleport /dtp <X> <Y> <Z> <D>";
    }

}
