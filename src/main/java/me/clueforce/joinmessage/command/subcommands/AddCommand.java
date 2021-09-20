package me.clueforce.joinmessage.command.subcommands;

import me.clueforce.joinmessage.command.CommandBase;
import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AddCommand extends CommandBase {

    private ConfigUtils configUtils;
    private LangUtils langUtils;

    public AddCommand(ConfigUtils configUtils, LangUtils langUtils) {
        this.configUtils = configUtils;
        this.langUtils = langUtils;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("jm.admin.add")) {
            sender.sendMessage(langUtils.getMessage("no-permission", true));
            return;
        }

        if (args.length == 1) {
            sender.sendMessage(langUtils.getMessage("add-example", true));
            return;
        }

        if (args.length == 2) {
            sender.sendMessage(langUtils.getMessage("specify-message", true));
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 2; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        List<String> list = configUtils.getList(args[1]);

        if (list == null) {
            sender.sendMessage(langUtils.getMessage("invalid-type", true));
            return;
        }

        list.add(sb.toString().trim());
        sender.sendMessage(langUtils.getMessage("confirm-add", true)
                .replace("{cmd}", args[1].toLowerCase()));
        configUtils.saveConfig();
    }

    @Override
    public String getCommand() {
        return "add";
    }


}
