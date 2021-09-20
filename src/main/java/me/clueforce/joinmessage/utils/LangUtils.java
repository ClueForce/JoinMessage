package me.clueforce.joinmessage.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class LangUtils {

    private FileConfiguration config;

    private Plugin plugin;

    public LangUtils(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadLang() {
        File file = new File(plugin.getDataFolder(), "language.yml");

        if (!file.exists()) {
            plugin.getLogger().info("No Language File Found, Creating...");
            plugin.saveResource("language.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getMessage(String key, boolean prefixed) {
        if (prefixed) {
            return color(config.getString("prefix") + " " + config.getString(key));
        }

        return color(config.getString(key));
    }

    public List<String> getList(String key) {
        return config.getStringList(key);
    }


}
