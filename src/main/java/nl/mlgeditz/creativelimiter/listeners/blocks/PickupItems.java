package nl.mlgeditz.creativelimiter.listeners.blocks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.Plugin;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

@SuppressWarnings("deprecation")
public class PickupItems implements Listener {

	public ArrayList<Player> pick = new ArrayList<Player>();

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (ChangeGameMode.getBuildingPlayers().contains(p)) {
			e.setCancelled(true);
			if (!pick.contains(p)) {
				p.sendMessage(Main.messageData.get("noPickups").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
				pick.add(p);
				Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin) Main.pl, new Runnable() {
					public void run() {
						pick.remove(p);
					}
				}, 100L);
			}
		}
	}
}
