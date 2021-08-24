package me.clueforce.joinmessage;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clueforce.joinmessage.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "Loading configuration file.");
        getConfig().options().copyDefaults(true);
        saveConfig();
        getLogger().info(ChatColor.GREEN + "Configuration file has loaded.");

        getLogger().info(ChatColor.GREEN + "Loading Dependencies Plugin");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
            getLogger().info(ChatColor.GREEN + "PlaceholderAPI has founded. this plugin can Enable");
        } else {
            getLogger().warning(ChatColor.RED + "PlaceholderAPI not detected. Please add this Plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getLogger().info(ChatColor.GREEN + "Loading commands");
        getCommand("joinmessage").setExecutor(new Command(this));
        getLogger().info(ChatColor.GREEN + "Command has been loaded. enjoy the plugins! :3");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "Goodbye....");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String joinText = getConfig().getString("message.join-message");
        joinText = PlaceholderAPI.setPlaceholders(e.getPlayer(), joinText);
        e.setJoinMessage(joinText);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        String quitText = getConfig().getString("message.quit-message");
        quitText = PlaceholderAPI.setPlaceholders(e.getPlayer(), quitText);
        e.setQuitMessage(quitText);
    }
}
