package nl.mlgeditz.creativelimiter.listeners.inventories.open;

import nl.minetopiasdb.api.API;
import nl.minetopiasdb.api.enums.DataType;
import nl.mlgeditz.creativelimiter.CreativeLimiter;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;
import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.block.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class OpenInventory18 implements Listener {

    public ArrayList<Player> cd = new ArrayList<Player>();

    @EventHandler
    public void onOpenChest(InventoryOpenEvent e) {
        HumanEntity p = e.getPlayer();
        if (ChangeGameMode.getBuildingPlayers().contains(p)) {
            if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.openinv")) {
                if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest || e.getInventory().equals(p.getEnderChest())) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openChest")));
                } else if (e.getInventory().getHolder() instanceof Hopper) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openHopper")));
                } else if (e.getInventory().getHolder() instanceof Dropper) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openDropper")));
                } else if (e.getInventory().getHolder() instanceof Furnace) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openFurnace")));
                } else if (e.getInventory().getHolder() instanceof Beacon) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openBeacon")));
                } else if (e.getInventory().getHolder() instanceof ShulkerBox) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openShulker")));
                } else if (e.getInventory().getHolder() instanceof Dispenser) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openDispenser")));
                } else if (e.getInventory().getHolder() instanceof BrewingStand) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openBrewingStand")));
                } else if (e.getInventory().getHolder() instanceof HopperMinecart) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openHopper")));
                } else if (e.getInventory().getHolder() instanceof StorageMinecart) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openChest")));
                } else if (CreativeLimiter.MinetopiaSDB && e.getView().getTitle().equals(API.getFile(DataType.MESSAGE).getString("SDB.Type.Titel").replaceAll("&", "§"))) {
                    p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("openPin")));
                } else return;
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void on18Interact(PlayerInteractEvent event) {
        OpenInventory.handleInteract(event);
    }
}
