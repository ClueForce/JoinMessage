package me.clueforce.joinmessage;

import me.clueforce.joinmessage.command.*;
import me.clueforce.joinmessage.event.PlayerEvents;
import me.clueforce.joinmessage.event.PluginUpdateEvents;
import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPlugin extends JavaPlugin {

    public BukkitPlugin() {

    }

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "Loading Configuration file..");
        ConfigUtils configUtils = new ConfigUtils(this);
        LangUtils langUtils = new LangUtils(this);

        configUtils.loadConfig();
        langUtils.loadLang();

        getLogger().info(ChatColor.GREEN + "Loading Commands");
        getCommand("joinmessage").setExecutor(new CommandManager(configUtils, langUtils));
        getCommand("discord").setExecutor(new DiscordCommand(configUtils, langUtils));
        getCommand("forum").setExecutor(new ForumCommand(configUtils, langUtils));
        getCommand("info").setExecutor(new InfoCommand(configUtils, langUtils));
        getCommand("store").setExecutor(new StoreCommand(configUtils, langUtils));
        getCommand("website").setExecutor(new WebsiteCommand(configUtils, langUtils));
        getCommand("socialmedia").setExecutor(new SocialMediaCommand(configUtils, langUtils));
        getLogger().info(ChatColor.GREEN + "All Commands has been loaded.");

        getServer().getPluginManager().registerEvents(new PlayerEvents(this, configUtils, langUtils), this);
        getServer().getPluginManager().registerEvents(new PluginUpdateEvents(this, configUtils), this);
        getLogger().info(ChatColor.GREEN + "JoinMessage has been loaded. your server using version" + ChatColor.YELLOW + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "Goodbye....");
    }
}
