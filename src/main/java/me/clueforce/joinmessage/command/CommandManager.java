package me.clueforce.joinmessage.command;

import me.clueforce.joinmessage.BukkitPlugin;
import me.clueforce.joinmessage.command.subcommands.*;
import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

public class CommandManager implements CommandExecutor {

    private Set<CommandBase> commands;

    private LangUtils langUtils;

    public CommandManager(ConfigUtils configUtils, LangUtils langUtils) {
        this.langUtils = langUtils;

        commands = new HashSet<>();

        commands.add(new AddCommand(configUtils, langUtils));
        commands.add(new ReloadCommand(configUtils, langUtils));
        commands.add(new RemoveCommand(configUtils, langUtils));
        commands.add(new SetCommand(configUtils, langUtils));
        commands.add(new VersionCommand(configUtils, langUtils));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {

            if (sender.hasPermission("jm.admin")) {
                for (String s : langUtils.getList("help")) {
                    sender.sendMessage(langUtils.color(s));
                }
                return true;
            }
            sender.sendMessage(langUtils.getMessage("no-permission", true));
            return true;

        }

        CommandBase command = getCommand(args[0]);

        if (command == null) {
            sender.sendMessage(langUtils.getMessage("invalid-sub-cmd", true));
            return true;
        }

        command.onCommand(sender, args);

        return true;
    }

    private CommandBase getCommand(String command) {
        for (CommandBase commandBase : commands) {
            if (commandBase.getCommand().equalsIgnoreCase(command)) {
                return commandBase;
            }
        }
        return null;
    }

}
