package nl.mlgeditz.creativelimiter.utils;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Logger {

    public static ConsoleCommandSender out = CreativeLimiter.pl.getServer().getConsoleSender();

    public static String colorFormat(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String prefixFormat(String string) {
        String prefix = CreativeLimiter.messageData.get("Prefix");
        return colorFormat(string.replaceAll("%prefix%", prefix));
    }

    public static void log (Severity severity, String string) {
        out.sendMessage(Logger.colorFormat(severity.prefix + " &r" + string));
    }

    public static void noPlayer() {
        log(Severity.ERROR, "You must be a player to do this.");
    }

    public static String noPermission(String permission) {
        return prefixFormat("&7You need the permission &3" + permission + "&f to do this");
    }

    public enum Severity {

        INFO("&2[INFO]"), WARNING("&6[WARNING]"), ERROR("&4[ERROR]"), DEBUG("&b[DEBUG]");

        String prefix;

        Severity(String prefix) {

            this.prefix = prefix;

        }

    }

}
