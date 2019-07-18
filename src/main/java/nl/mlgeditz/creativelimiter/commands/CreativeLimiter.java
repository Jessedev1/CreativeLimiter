package nl.mlgeditz.creativelimiter.commands;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CreativeLimiter implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("creativelimiter")) {

            if (args.length < 1) {
                sender.sendMessage(Logger.colorFormat("-=&8[&7CreativeLimiter&8]=-"));
                sender.sendMessage(Logger.colorFormat("&8Created by: &7MLGEditz &8and &7MrWouter"));
                sender.sendMessage(Logger.colorFormat("&8-=+=-"));
                sender.sendMessage(Logger.colorFormat("&8/&7creativelimiter &8reload &7- &7Reload config files!"));
                sender.sendMessage(Logger.colorFormat("&8/&7creativelimiter &8sync &7- &7Clear cache (Use if memory is full!)"));
                sender.sendMessage(Logger.colorFormat("-=&8[&7CreativeLimiter&8]=-"));
                return false;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (p.hasPermission("limiter.reload")) {
                        Main.reloadConfigFiles();
                        p.sendMessage(Logger.prefixFormat("%prefix% &7Reloaded config files successfully!"));
                        return false;
                    } else {
                        p.sendMessage(Logger.prefixFormat(Main.messageData.get("noPermissions")));
                        return false;
                    }
                }
                Main.reloadConfigFiles();
                sender.sendMessage(Logger.prefixFormat("%prefix% &7Reloaded config files successfully!"));
                return false;
            }

            if (args[0].equalsIgnoreCase("sync")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (p.hasPermission("limiter.sync")) {
                        Main.getCache().sync();
                        p.sendMessage(Logger.prefixFormat("%prefix% &7Synced data with database successfully!"));
                        return false;
                    } else {
                        p.sendMessage(Logger.prefixFormat(Main.messageData.get("noPermissions")));
                        return false;
                    }
                }
                Main.getCache().sync();
                sender.sendMessage(Logger.prefixFormat("%prefix% &7Synced data with database successfully!"));
                return false;
            }
        }
        return false;
    }
}
