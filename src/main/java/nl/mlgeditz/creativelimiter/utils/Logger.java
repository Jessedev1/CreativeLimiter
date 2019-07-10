package nl.mlgeditz.creativelimiter.utils;

import nl.mlgeditz.creativelimiter.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Logger {

    public static ConsoleCommandSender out = Main.pl.getServer().getConsoleSender();

    public static String colorFormat(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String prefixFormat(String string) {
        String prefix = Main.messageData.get("Prefix");
        return colorFormat(string.replaceAll("%prefix%", prefix));
    }

    public static void log (Severity severity, String string) {
        out.sendMessage(Logger.colorFormat(severity.prefix + " &r" + string));
    }

    public static void noPlayer() {
        log(Severity.ERROR, "Dit kan enkel worden uitgevoerd door een speler.");
    }

    public static String noPermission(String permission) {
        return prefixFormat("&7Je moet de permissie §3" + permission + "§f hebben om dit uit te voeren");
    }

    public enum Severity {

        INFO("&2[INFO]"), WARNING("&6[WARNING]"), ERROR("&4[ERROR]"), DEBUG("&b[DEBUG]");

        String prefix;

        Severity(String prefix) {

            this.prefix = prefix;

        }

    }

}
