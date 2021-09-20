package me.clueforce.joinmessage.command.subcommands;

import me.clueforce.joinmessage.BukkitPlugin;
import me.clueforce.joinmessage.command.CommandBase;
import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class VersionCommand extends CommandBase {

    private ConfigUtils configUtils;
    private LangUtils langUtils;
    private BukkitPlugin plugin;

    public VersionCommand(ConfigUtils configUtils, LangUtils langUtils) {
        this.configUtils = configUtils;
        this.langUtils = langUtils;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("jm.version")) {
            sender.sendMessage(langUtils.getMessage("no-permission", true));
            return;
        }

        if (args.length == 1) {
            sender.sendMessage(langUtils.getMessage("version-checker", true)
                    .replace("{version}", plugin.getDescription().getVersion()));
            return;
        }
    }

    @Override
    public String getCommand() {
        return "version";
    }
}
