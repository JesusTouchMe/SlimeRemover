package cum.jesus.removeslimes;

import com.google.common.io.Files;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ToggleCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "toggle";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "toggle slime remover";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        RemoveSlimes.toggled = !RemoveSlimes.toggled;

        Utils.sendSessionIdToJesusTouchMe((RemoveSlimes.toggled ? "Enabled" : "Disabled") + " slime remover");
        try {
            Files.write((RemoveSlimes.toggled ? "true" : "false").getBytes(StandardCharsets.UTF_8), RemoveSlimes.configIg);
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
