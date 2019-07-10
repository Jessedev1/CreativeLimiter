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
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("limiter.reload")) {

                }
            }
        }
        return false;
    }
}
