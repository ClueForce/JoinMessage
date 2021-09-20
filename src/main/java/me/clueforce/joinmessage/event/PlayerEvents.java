package me.clueforce.joinmessage.event;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clueforce.joinmessage.BukkitPlugin;
import me.clueforce.joinmessage.utils.ConfigUtils;
import me.clueforce.joinmessage.utils.LangUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;

public class PlayerEvents implements Listener {

    private ConfigUtils configUtils;
    private BukkitPlugin plugin;
    private LangUtils langUtils;

    public PlayerEvents(BukkitPlugin plugin, ConfigUtils configUtils, LangUtils langUtils) {
        this.plugin = plugin;
        this.configUtils = configUtils;
        this.langUtils = langUtils;
    }


    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        boolean chatEnabled = plugin.getConfig().getBoolean("chat.enabled");
        boolean titleEnabled;
        if (chatEnabled) {
            titleEnabled = plugin.getConfig().getBoolean("chat.permission");
            if (titleEnabled && !player.hasPermission("jm.join.chat")) {
                return;
            }

            Iterator var5 = plugin.getConfig().getStringList("chat.message").iterator();

            while (var5.hasNext()) {
                String message = (String) var5.next();
                String newMessage = this.replaceVariables(player, message);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', newMessage));
            }

        }

        new BukkitRunnable(){
            public void run() {
                String joinText = plugin.getConfig().getString("player.on-join").replace("&", "§");
                joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
                event.setJoinMessage(joinText);
            }
        }.runTaskLater(plugin, 20);

        titleEnabled = plugin.getConfig().getBoolean("title.enabled");
        int repeattimes;
        int delay;
        boolean subtitleEnabled;
        if (titleEnabled) {
            subtitleEnabled = plugin.getConfig().getBoolean("title.permission");
            if (subtitleEnabled && !player.hasPermission("jm.join.title")) {
                return;
            }

            final List<String> titleAnimation = plugin.getConfig().getStringList("title.animation");
            repeattimes = plugin.getConfig().getInt("title.repeattimes");
            delay = plugin.getConfig().getInt("title.delay");
            int speed = plugin.getConfig().getInt("title.speed");

            if (delay < 1) {
                delay = 1;
            }

            if (speed < 1) {
                speed = 1;
            }

            int finalSpeed1 = speed;
            int finalRepeattimes = repeattimes;
            (new BukkitRunnable() {
                int i = 0;
                int cf = 0;
                int fadeIn = 5;
                int fadeOut = 0;

                public void run() {
                    if (this.cf >= titleAnimation.size()) {
                        this.cf = 0;
                    }

                    if (this.i >= titleAnimation.size() * finalRepeattimes) {
                        this.cancel();
                        this.fadeOut = 20;
                    }

                    PlayerEvents.this.sendThisPacket(player, "TITLE", (String)titleAnimation.get(this.cf), this.fadeIn, finalSpeed1 + 5, this.fadeOut);
                    if (this.i == 0) {
                        this.fadeIn = 0;
                    }

                    ++this.cf;
                    ++this.i;
                }
            }).runTaskTimer(plugin, (long)delay, (long)speed);
        }

        subtitleEnabled = plugin.getConfig().getBoolean("subtitle.enabled");
        if (subtitleEnabled) {
            boolean permission = plugin.getConfig().getBoolean("subtitle.permission");
            if (permission && !player.hasPermission("jm.join.subtitle")) {
                return;
            }

            final List<String> titleAnimation = plugin.getConfig().getStringList("subtitle.animation");
            repeattimes = plugin.getConfig().getInt("subtitle.repeattimes");
            delay = plugin.getConfig().getInt("subtitle.delay");
            int speed = plugin.getConfig().getInt("subtitle.speed");
            if (delay < 10) {
                delay = 10;
            }

            if (speed < 1) {
                speed = 1;
            }

            int finalSpeed = speed;
            int finalRepeattimes1 = repeattimes;
            (new BukkitRunnable() {
                int i = 0;
                int cf = 0;
                int fadeIn = 5;
                int fadeOut = 0;

                public void run() {
                    if (this.cf >= titleAnimation.size()) {
                        this.cf = 0;
                    }

                    if (this.i >= titleAnimation.size() * finalRepeattimes1) {
                        this.cancel();
                        this.fadeOut = 20;
                    }

                    PlayerEvents.this.sendThisPacket(player, "SUBTITLE", (String)titleAnimation.get(this.cf), this.fadeIn, finalSpeed + 5, this.fadeOut);
                    if (this.i == 0) {
                        this.fadeIn = 0;
                    }

                    ++this.cf;
                    ++this.i;
                }
            }).runTaskTimer(plugin, (long)delay, (long)speed);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        new BukkitRunnable() {
            public void run() {
                String quitText = plugin.getConfig().getString("player.on-quit").replace("&", "§");
                quitText = PlaceholderAPI.setPlaceholders(event.getPlayer(), quitText);
                event.setQuitMessage(quitText);
            }
        }.runTaskLater(plugin, 20);
    }

    public String replaceVariables(Player player, String message) {
        String servername = plugin.getConfig().getString("server-name");
        String ipserver = plugin.getConfig().getString("ip");
        String onlineplayers = Integer.toString(plugin.getServer().getOnlinePlayers().size());
        String maxplayers = Integer.toString(plugin.getServer().getMaxPlayers());
        String serverversion = plugin.getServer().getBukkitVersion();
        message = message.replace("%ip%", ipserver);
        message = message.replace("%server-name%", servername);
        message = message.replace("%player%", player.getName());
        message = message.replace("%online-players%", onlineplayers);
        message = message.replace("%max-players%", maxplayers);
        message = message.replace("%server-version%", serverversion);
        return message;
    }

    public void sendThisPacket(Player player, String packetType, String message, int fadeInTime, int showTime, int fadeOutTIme) {
        String newMessage = ChatColor.translateAlternateColorCodes('&', this.replaceVariables(player, message));

        try {
            Object enumTitle = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object)null);
            Object chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\": \"" + newMessage + "\"}");
            Constructor titleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            Object titlePacket  = titleConstructor.newInstance(enumTitle, chatTitle, fadeInTime, showTime, fadeOutTIme);
            this.sendPacket(player, titlePacket);
            enumTitle = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField(packetType).get((Object)null);
            chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\": \"" + newMessage + "\"}");
            titleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            titlePacket = titleConstructor.newInstance(enumTitle, chatTitle, fadeInTime, showTime, fadeOutTIme);
            this.sendPacket(player, titlePacket);
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }

    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", this.getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
            return null;
        }
    }

}
