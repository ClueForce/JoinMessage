package me.clueforce.joinmessage.command.subcommands;

import me.clueforce.joinmessage.command.CommandBase;
import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends CommandBase {

    private ConfigUtils configUtils;
    private LangUtils langUtils;

    public ReloadCommand(ConfigUtils configUtils, LangUtils langUtils) {
        this.configUtils = configUtils;
        this.langUtils = langUtils;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("jm.admin.reload")) {
            sender.sendMessage(langUtils.getMessage("no-permission", true));
            return;
        }

        if (args.length == 1) {
            configUtils.loadConfig();
            langUtils.loadLang();
            sender.sendMessage(langUtils.getMessage("files-reloaded", true));
            return;
        }

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("config")) {
                configUtils.loadConfig();
                sender.sendMessage(langUtils.getMessage("config-reloaded", true));
                return;
            }

            if (args[1].equalsIgnoreCase("language")) {
                langUtils.loadLang();
                sender.sendMessage(langUtils.getMessage("lang-reloaded", true));
                return;
            }

            sender.sendMessage(langUtils.getMessage("invalid-file-name", true));
        }
    }

    @Override
    public String getCommand() {
        return "reload";
    }

}
