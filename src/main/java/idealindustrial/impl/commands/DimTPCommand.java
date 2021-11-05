package idealindustrial.impl.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

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

    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "dimension teleport /dtp <X> <Y> <Z> <D>";
    }

}
