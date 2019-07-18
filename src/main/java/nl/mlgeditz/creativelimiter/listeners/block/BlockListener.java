package nl.mlgeditz.creativelimiter.listeners.block;

import nl.mlgeditz.creativelimiter.utils.Logger;
import nl.mlgeditz.creativelimiter.utils.XMaterial;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import nl.mlgeditz.creativelimiter.Main;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;

/**
 * Created by MLGEditz and/or other contributors
 * No part of this publication may be reproduced,
 * distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 2018 by MLGEditz
*/

public class BlockListener implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		int x = b.getLocation().getBlockX();
		int y = b.getLocation().getBlockY();
		int z = b.getLocation().getBlockZ();
		
		String world = b.getLocation().getWorld().getName();
		String loc = x + ", " + y + ", " + z + ", " + world;
		
		if (p.getGameMode() == GameMode.CREATIVE) {
			if (Main.pl.getConfig().getStringList("Deny-Placing").contains(b.getType().toString().toUpperCase())) {
				if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.place")) {
					e.setCancelled(true);
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("cannotPlace")));
				}
			}
		}

		if (p.getGameMode() == GameMode.CREATIVE) {
			Main.getCache().add(loc, "LOCATION");
		}
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE) {
			if (Main.pl.getConfig().getStringList("Deny-Placing").contains(e.getBucket().toString().toUpperCase())) {
				if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.place")) {
					e.setCancelled(true);
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("cannotPlace")));
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		int x = b.getLocation().getBlockX();
		int y = b.getLocation().getBlockY();
		int z = b.getLocation().getBlockZ();
		String world = b.getLocation().getWorld().getName();
		String loc = x + ", " + y + ", " + z + ", " + world;
		try {
			if (Main.getCache().get(loc) != null) {
				if (p.getGameMode() != GameMode.CREATIVE) {
					if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.break")) {
						p.sendMessage(Logger.prefixFormat(Main.messageData.get("cannotBreak")));
						e.setCancelled(true);
					}
				}
			}
		} catch (Exception ex) {
			Logger.log(Logger.Severity.ERROR, "Something went wrong while loading block from database... Error: " + ex.getMessage());
		}
	}
}
