package nl.mlgeditz.creativelimiter.listeners.blocks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

/**
 * Created by MLGEditz and/or other contributors
 * No part of this publication may be reproduced,
 * distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 by MLGEditz
*/

public class DropItems implements Listener {
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (ChangeGameMode.getBuildingPlayers().contains(p)) {
			if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.drop")) {
			e.setCancelled(true);
			p.sendMessage(Main.messageData.get("noDrops")
					.replaceAll("&", "§")
					.replaceAll("%prefix%", Main.messageData.get("Prefix")
									        .replaceAll("&", "§")));
		}
		}
	}
}
