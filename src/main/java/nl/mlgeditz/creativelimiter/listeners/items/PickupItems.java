package nl.mlgeditz.creativelimiter.listeners.items;

import java.util.ArrayList;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

public class PickupItems implements Listener {

	private ArrayList<Player> pick = new ArrayList<>();

	//Should be replaced with EntityPickupItemEvent, but is first seen in 1.12
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (ChangeGameMode.getBuildingPlayers().contains(p)) {
			if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.pickup")) {
				if (!pick.contains(p)) {
					p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("noPickups")));
					pick.add(p);
					Bukkit.getScheduler().scheduleSyncDelayedTask(CreativeLimiter.pl, new Runnable() {
						public void run() {
							pick.remove(p);
						}
					}, 100L);
				}
				e.setCancelled(true);
			}
		}
	}
}
