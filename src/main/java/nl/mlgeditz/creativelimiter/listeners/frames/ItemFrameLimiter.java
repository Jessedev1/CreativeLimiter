package nl.mlgeditz.creativelimiter.listeners.frames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

public class ItemFrameLimiter implements Listener {

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			e.setCancelled(true);
			return;
		}
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		if (ent instanceof ItemFrame) {
			if (ChangeGameMode.getBuildingPlayers().contains(p) && p.getInventory().getItemInMainHand() != null
					&& p.getInventory().getItemInMainHand().getType() != Material.AIR) {
				ItemFrame ifr = (ItemFrame) ent;
				e.setCancelled(true);
				ItemStack cur = p.getInventory().getItemInMainHand();
				ItemMeta curm = cur.getItemMeta();
				List<String> lore = curm != null && curm.hasLore() ? curm.getLore() : new ArrayList<>();
				if ((lore == null || lore.isEmpty()) || !lore.contains("CreativeLimiter item.")) {
					lore.add("CreativeLimiter item.");
				}
				curm.setLore(lore);
				cur.setItemMeta(curm);
				ifr.setItem(cur);
			}
		}
	}

	@EventHandler
	public void itemFrameItemRemoval(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof ItemFrame && e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			ItemFrame ifr = (ItemFrame) e.getEntity();
			if (!ChangeGameMode.getBuildingPlayers().contains(p)) {
				ItemStack cur = ifr.getItem();
				ItemMeta curm = cur.getItemMeta();
				List<String> lore = curm != null && curm.hasLore() ? curm.getLore() : new ArrayList<>();
				if (lore.contains("CreativeLimiter item.")) {
					if (!p.hasPermission("limiter.bypass")) {
					e.setCancelled(true);
					p.sendMessage(Main.messageData.get("dontTakeItem").replaceAll("&", "§").replaceAll("%prefix%",
							Main.messageData.get("Prefix").replaceAll("&", "§")));
					}
				}
			}
		}
	}

	@EventHandler
	public void itemFrameDestroyed(HangingBreakEvent e) {
		if (e.getEntity() instanceof ItemFrame) {
			ItemFrame ifr = (ItemFrame) e.getEntity();
			ItemStack cur = ifr.getItem();
			ItemMeta curm = cur.getItemMeta();
			List<String> lore = curm != null && curm.hasLore() ? curm.getLore() : new ArrayList<>();
			if (lore.contains("CreativeLimiter item.")) {
				e.setCancelled(true);
			}
		}
	}
}