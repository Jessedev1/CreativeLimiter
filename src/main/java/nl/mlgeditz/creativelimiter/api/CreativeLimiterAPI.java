package nl.mlgeditz.creativelimiter.api;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
import nl.mlgeditz.creativelimiter.utils.Logger;

public class CreativeLimiterAPI {

    private Plugin plugin;

    public CreativeLimiterAPI(String plugin) {
        if (isHookExternal(Bukkit.getPluginManager().getPlugin(plugin))) {
            Logger.log(Logger.Severity.INFO, plugin + " tries to hook into CreativeLimiter... Starting the hook.");
            if (Bukkit.getServer().getPluginManager().getPlugin(plugin) != null) {
                Logger.log(Logger.Severity.INFO, "Found " + plugin + ". Hooking...");
                this.plugin = Bukkit.getPluginManager().getPlugin(plugin);
                Logger.log(Logger.Severity.INFO, plugin + " hooked successfully into CreativeLimiter...");
            } else {
                Logger.log(Logger.Severity.ERROR, "Something went wrong while hooking into " + plugin + " The resource couldnt be loaded. Please contact the developer of " + plugin);
            }
        } else {
            this.plugin = Bukkit.getPluginManager().getPlugin(plugin);
        }
    }

    public CreativeLimiterAPI(Plugin plugin) {
        if (isHookExternal(plugin)) {
            Logger.log(Logger.Severity.INFO, plugin.getDescription().getName() + " tries to hook into CreativeLimiter... Starting the hook.");
            if (Bukkit.getServer().getPluginManager().getPlugin(plugin.getDescription().getName()) != null) {
                Logger.log(Logger.Severity.INFO, "Found " + plugin.getDescription().getName() + ". Hooking...");
                this.plugin = plugin;
                Logger.log(Logger.Severity.INFO, plugin.getDescription().getName() + " hooked successfully into CreativeLimiter...");
            } else {
                Logger.log(Logger.Severity.ERROR, "Something went wrong while hooking into " + plugin.getDescription().getName() + " The resource couldnt be loaded. Please contact the developer of " + plugin);
            }
        } else {
            this.plugin = plugin;
        }
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = Bukkit.getPluginManager().getPlugin(plugin);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public void addPlaceBlocker(String block) {
        CreativeLimiter.denyPlacing.add(block.toUpperCase());
    }

    public void removePlaceBlocker(String block) {
        CreativeLimiter.denyPlacing.remove(block.toUpperCase());
    }

    public boolean isPlaceBlocker(String block) {
        return CreativeLimiter.denyPlacing.contains(block.toUpperCase());
    }


    public void addInteractBlocker(String block) {
        CreativeLimiter.denyInteract.add(block.toUpperCase());
    }

    public void removeInteractBlocker(String block) {
        CreativeLimiter.denyInteract.remove(block.toUpperCase());
    }

    public boolean isInteractBlocker(String block) {
        return CreativeLimiter.denyInteract.contains(block.toUpperCase());
    }

    public void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    private boolean isHookExternal(Plugin plugin) {
        return (CreativeLimiter.pl != plugin);
    }
}
