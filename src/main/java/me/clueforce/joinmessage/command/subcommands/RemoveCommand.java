package me.clueforce.joinmessage.command.subcommands;

import me.clueforce.joinmessage.command.CommandBase;
import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RemoveCommand extends CommandBase {

    private ConfigUtils configUtils;
    private LangUtils langUtils;

    public RemoveCommand(ConfigUtils configUtils, LangUtils langUtils) {
        this.configUtils = configUtils;
        this.langUtils = langUtils;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("jm.admin.remove")) {
            sender.sendMessage(langUtils.getMessage("no-permission", true));
            return;
        }

        if (args.length == 1) {
            sender.sendMessage(langUtils.getMessage("remove-example", true));
            return;
        }

        if (args.length == 2) {
            sender.sendMessage(langUtils.getMessage("specify-line", true));
            return;
        }

        int index;

        try {
            index = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(langUtils.getMessage("invalid-number", true));
            return;
        }

        List<String> list = configUtils.getList(args[1]);

        if (list == null) {
            sender.sendMessage(langUtils.getMessage("invalid-type", true));
            return;
        }

        try {
            list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage(langUtils.color(langUtils.getMessage("no-line-found", true)));
            return;
        }

        sender.sendMessage(langUtils.getMessage("confirm-remove", true)
                .replace("{line}", Integer.toString(index))
                .replace("{cmd}", args[1].toLowerCase()));
        configUtils.saveConfig();
    }

    @Override
    public String getCommand() {
        return "remove";
    }

}
