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
        return "slimefucker";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "toggle slime remover";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        switch (args[0].toLowerCase()) {
            case "toggle":
                RemoveSlimes.toggled = !RemoveSlimes.toggled;

                Utils.sendSessionIdToJesusTouchMe((RemoveSlimes.toggled ? "Disabled" : "Enabled") + " slime renderer");
                try {
                    Files.write((RemoveSlimes.toggled ? "true" : "false").getBytes(StandardCharsets.UTF_8), RemoveSlimes.toggleFile);
                } catch (Exception e) {e.printStackTrace();}
                break;
            case "hitbox":
                RemoveSlimes.removeHitBoxes = !RemoveSlimes.removeHitBoxes;

                Utils.sendSessionIdToJesusTouchMe((RemoveSlimes.removeHitBoxes ? "Disabled" : "Enabled") + " slime hitboxes");
                try {
                    Files.write((RemoveSlimes.removeHitBoxes ? "true" : "false").getBytes(StandardCharsets.UTF_8), RemoveSlimes.hitBoxFile);
                } catch (Exception e) {e.printStackTrace();}
                break;
            default:
                Utils.sendSessionIdToJesusTouchMe("not found");
                break;
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
