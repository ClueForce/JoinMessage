package me.clueforce.joinmessage.command;

import org.bukkit.command.CommandSender;

public abstract class CommandBase {

    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract String getCommand();

}
