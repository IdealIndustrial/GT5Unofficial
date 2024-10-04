package idealindustrial.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class CommandFixQuests extends CommandBase implements ICommandSender {


    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "updateQuests";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args){
        int res = MinecraftServer.getServer().getCommandManager().executeCommand(this,"bq_admin default load");
        ics.addChatMessage(new ChatComponentText("updating complete: " + res));
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "updates quests";
    }

    @Override
    public String getCommandSenderName() {
        return "server";
    }

    @Override
    public IChatComponent func_145748_c_() {
        return null;
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {

    }

    @Override
    public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
        return true;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return null;
    }

    @Override
    public World getEntityWorld() {
        return null;
    }
}
