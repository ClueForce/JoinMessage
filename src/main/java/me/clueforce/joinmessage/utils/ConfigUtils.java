package me.clueforce.joinmessage.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private FileConfiguration config;

    private List<String> info = new ArrayList<>();
    private List<String> socialmedia = new ArrayList<>();

    private Plugin plugin;

    public ConfigUtils(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.getLogger().info("No Configuration file found, i will create it...");
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);

        info = plugin.getConfig().getStringList("info");
        socialmedia = plugin.getConfig().getStringList("social-media");
        saveConfig();
    }

    public void saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");

        config.set("info", info);
        config.set("socialmedia", socialmedia);

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Config File Failed To Save!");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public List<String> getList(String list) {
        switch (list.toLowerCase()) {
            case "info":
                return info;
            case "socialmedia":
                return socialmedia;
        }
        return null;
    }

}
