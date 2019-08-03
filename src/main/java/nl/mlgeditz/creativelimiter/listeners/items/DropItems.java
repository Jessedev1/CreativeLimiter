package nl.mlgeditz.creativelimiter.listeners.items;

import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
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
				p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("noDrops")));
			}
		}
	}
}
