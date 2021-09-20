package me.clueforce.joinmessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.lang.reflect.Constructor;

public class Packet implements Listener {

    private BukkitPlugin plugin;

    public Packet(BukkitPlugin pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().equals("title")) {
            try {
                Object enumTitle = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object)null);
                Object chat = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"Welcome\"}");
                Constructor<?> titleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object packet = titleConstructor.newInstance(enumTitle, chat, 20, 40, 20);
                this.sendPacket(e.getPlayer(), packet);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
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
