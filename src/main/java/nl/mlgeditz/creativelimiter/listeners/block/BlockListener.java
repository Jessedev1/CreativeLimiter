package nl.mlgeditz.creativelimiter.listeners.block;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
			if (CreativeLimiter.getAPI().isPlaceBlocker(b.getType().toString().toUpperCase())) {
				if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.place")) {
					e.setCancelled(true);
					p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("cannotPlace")));
				}
			}
		}

		if (p.getGameMode() == GameMode.CREATIVE) {
			CreativeLimiter.getCache().add(loc, "LOCATION");
		}
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE) {
			if (CreativeLimiter.getAPI().isPlaceBlocker(e.getBucket().toString().toUpperCase())) {
				if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.place")) {
					e.setCancelled(true);
					p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("cannotPlace")));
				}
			}
		}
	}

	@EventHandler
	public void onItemPlace(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (p.getGameMode() == GameMode.CREATIVE) {
			if (CreativeLimiter.getAPI().isPlaceBlocker(p.getInventory().getItemInHand().getType().toString().toUpperCase())) {
				if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.place")) {
					e.setCancelled(true);
					p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("cannotPlace")));
				}
			}
		}

		Location location = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().add(0.5, 0, 0.5);
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		String world = location.getWorld().getName();
		String loc = x + ", " + y + ", " + z + ", " + world;

		if (p.getGameMode() == GameMode.CREATIVE) {
			CreativeLimiter.getCache().add(loc, "LOCATION");
		}
	}

	@EventHandler
	public void onItemBreak(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
		Player p = (Player) e.getDamager();
		Entity entity = e.getEntity();
		int x = entity.getLocation().getBlockX();
		int y = entity.getLocation().getBlockY();
		int z = entity.getLocation().getBlockZ();
		String world = entity.getLocation().getWorld().getName();
		String loc = x + ", " + y + ", " + z + ", " + world;

		try {
			if (CreativeLimiter.getCache().get(loc) != null) {
				if (p.getGameMode() != GameMode.CREATIVE) {
					if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.break")) {
						p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("cannotBreak")));
						e.setCancelled(true);
					}
				}
			}
		} catch (Exception ex) {
			Logger.log(Logger.Severity.ERROR, "Something went wrong while loading block from database... Error: " + ex.getMessage());
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
			if (CreativeLimiter.getCache().get(loc) != null) {
				if (p.getGameMode() != GameMode.CREATIVE) {
					if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.break")) {
						p.sendMessage(Logger.prefixFormat(CreativeLimiter.messageData.get("cannotBreak")));
						e.setCancelled(true);
					}
				}
			}
		} catch (Exception ex) {
			Logger.log(Logger.Severity.ERROR, "Something went wrong while loading block from database... Error: " + ex.getMessage());
		}
	}
}
