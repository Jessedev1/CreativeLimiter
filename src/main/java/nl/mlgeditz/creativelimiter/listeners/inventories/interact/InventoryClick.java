package nl.mlgeditz.creativelimiter.listeners.inventories.interact;

import org.bukkit.event.Listener;

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
