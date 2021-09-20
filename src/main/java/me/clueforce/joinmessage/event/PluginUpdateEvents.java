package me.clueforce.joinmessage.event;

import me.clueforce.joinmessage.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class PluginUpdateEvents implements Listener {

    private Plugin plugin;
    private ConfigUtils configUtils;

    public PluginUpdateEvents(Plugin plugin, ConfigUtils configUtils) {
        this.plugin = plugin;
        this.configUtils = configUtils;
    }

    private final String apiUrl = "https://api.spigotmc.org/legacy/update.php?resource=95640/";
    private final String resourceURL = "https://www.spigotmc.org/resources/%E2%AD%90-joinmessage-%E2%AD%90-customize-your-join-and-quit-message.95640/";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!configUtils.getConfig().getBoolean("update-checker")) {
            return;
        }

        if (event.getPlayer().hasPermission("jm.admin.updater")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(apiUrl).openConnection();
                String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

                if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                    event.getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Join" + ChatColor.WHITE + "Message" + ChatColor.GRAY + "] " + ChatColor.RED + "Your plugin Is out of Date!");
                    event.getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Join" + ChatColor.WHITE + "Message" + ChatColor.GRAY + "] " + ChatColor.RED + "Click Here To Update: " + ChatColor.GREEN + resourceURL);
                } else {
                    event.getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Join" + ChatColor.WHITE + "Message" + ChatColor.GRAY + "] " + ChatColor.GREEN + "You are running " + ChatColor.YELLOW + "JoinMessage" + ChatColor.GREEN + "version" + ChatColor.AQUA + plugin.getDescription().getVersion());
                    event.getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Join" + ChatColor.WHITE + "Message" + ChatColor.GRAY + "] " + ChatColor.GREEN + "The Plugin is up to date!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
