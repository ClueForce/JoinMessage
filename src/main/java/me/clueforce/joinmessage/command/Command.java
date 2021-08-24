package me.clueforce.joinmessage.command;

import me.clueforce.joinmessage.BukkitPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {

    private BukkitPlugin plugin;

    public Command(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§7[§aJoin§fMessage§7] §7Thanks for Download the Plugin :3");
            sender.sendMessage("§7[§aJoin§fMessage§7] §7Welcome §6" + sender.getName() + " §7to the JoinMessage commands!");
            sender.sendMessage("§7[§aJoin§fMessage§7] §7To show all commands use §6/jm cmd");
            sender.sendMessage("§7[§aJoin§fMessage§7] §7Plugin by §6ClueForce§7, version: §6" + plugin.getDescription().getVersion());
            return true;
        }
        if (args[0].equalsIgnoreCase("cmd")) {
            if (!sender.hasPermission("jm.cmd")) {
                sender.sendMessage(plugin.getConfig().getString("message.command.no-permission"));
                return true;
            }
            sender.sendMessage("7[§aJoin§fMessage§7] Reload config file §6/jm reload");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("jm.reload")) {
                sender.sendMessage(plugin.getConfig().getString("messages.command.no-permission").replace("&", "§"));
                return true;
            }
            sender.sendMessage(plugin.getConfig().getString("message.command.reload").replace("&", "§"));
            plugin.reloadConfig();
            sender.sendMessage("§7[§aJoin§fMessage§7] §aplugin has been reloaded");
            return true;
        }
        return false;
    }
}
