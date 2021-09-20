package me.clueforce.joinmessage.command;

import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SocialMediaCommand implements CommandExecutor {

    private ConfigUtils configUtils;
    private LangUtils langUtils;

    public SocialMediaCommand(ConfigUtils configUtils, LangUtils langUtils) {
        this.configUtils = configUtils;
        this.langUtils = langUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!configUtils.getConfig().getBoolean("options.social-media-enabled")) {
            sender.sendMessage(langUtils.getMessage("command-disabled", true));
            return true;
        }

        for (String s : configUtils.getConfig().getStringList("social-media")) {
            if (s.equalsIgnoreCase("\"\"")) {
                sender.sendMessage("");
                continue;
            }
            sender.sendMessage(langUtils.color(s));
        }
        return true;
    }
}
