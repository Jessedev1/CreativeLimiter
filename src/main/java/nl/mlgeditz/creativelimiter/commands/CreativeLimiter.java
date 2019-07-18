package nl.mlgeditz.creativelimiter.commands;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.utils.Logger;
import nl.mlgeditz.creativelimiter.utils.Updater;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Set;

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
                sender.sendMessage(Logger.colorFormat("&8/&7creativelimiter &8blockinfo &7- &7Get name of the block you're looking at"));
                sender.sendMessage(Logger.colorFormat("&8/&7creativelimiter &8update &7- &7Install newest version of CreativeLimiter."));
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

            if (args[0].equalsIgnoreCase("blockinfo")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Logger.prefixFormat(Main.messageData.get("noPlayer")));
                    return false;
                }
                Player p = (Player) sender;

                if(!p.hasPermission("limiter.blockinfo")) {
                    p.sendMessage(Logger.prefixFormat(Main.messageData.get("noPermissions")));
                    return false;
                }

                Block lookingblock = p.getTargetBlock((Set<Material>) null, 200);
                p.sendMessage(Logger.prefixFormat("%prefix% You are looking at the block: &c" + lookingblock.getType().toString().toUpperCase()));
                return false;
            }

            if (args[0].equalsIgnoreCase("update")) {
                if(!sender.hasPermission("limiter.blockinfo")) {
                    sender.sendMessage(Logger.prefixFormat(Main.messageData.get("noPermissions")));
                    return false;
                }

                if (sender instanceof Player) {
                    sender.sendMessage(Logger.prefixFormat("%prefix% Checking for new updates..."));
                    if (Updater.getUpdater().checkForUpdates()) {
                        sender.sendMessage(Logger.prefixFormat("%prefix% Found §c1 §7new update. Installing..."));
                        Updater.getUpdater().installUpdate();
                    } else {
                        sender.sendMessage(Logger.prefixFormat("%prefix% Could not find an update. Try again later."));
                    }
                } else {
                    if (Updater.getUpdater().checkForUpdates()) {
                        Updater.getUpdater().installUpdate();
                    }
                }
            }
        }
        return false;
    }
}
