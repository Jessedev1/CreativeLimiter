package nl.mlgeditz.creativelimiter.listeners.inventories.interact;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class InventoryClick implements Listener {

//    @EventHandler
//    public void InventoryClickEvent(InventoryClickEvent e) {
//        Player p = (Player) e.getWhoClicked();
//        if (p.getGameMode() == GameMode.CREATIVE) {
//            if (CreativeLimiter.getAPI().isInteractBlocker(e.getCurrentItem().getType().toString().toUpperCase())) {
//                if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.interact")) {
//                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("cannotInteract")));
//                    p.getInventory().remove(e.getCurrentItem());
//                    p.closeInventory();
//                    e.setCancelled(true);
//                }
//            }
//        }
//    }

}
