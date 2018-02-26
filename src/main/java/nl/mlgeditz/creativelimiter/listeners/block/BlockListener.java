package nl.mlgeditz.creativelimiter.listeners.block;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import nl.mlgeditz.creativelimiter.Main;

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
			p.sendMessage(Main.messageData.get("cannotPlace").replaceAll("&", "§").replaceAll("%prefix%",
					Main.messageData.get("Prefix").replaceAll("&", "§")));

		}
		}
		}
		try {
			if (p.getGameMode() == GameMode.CREATIVE) {
			Main.thdb().getNewStatement().executeUpdate("INSERT INTO block VALUES ('" + loc + "')");
			}
		} catch (Exception ex) {
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
		if (Main.thdb().getNewStatement().executeQuery("SELECT * FROM block WHERE loc='" + loc + "'").next()) {
			if (p.getGameMode() != GameMode.CREATIVE) {
				if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.break")) {
			e.setCancelled(true);
			p.sendMessage(Main.messageData.get("cannotBreak").replaceAll("&", "§").replaceAll("%prefix%",
					Main.messageData.get("Prefix").replaceAll("&", "§")));
			}
			}
		}
		} catch (Exception ex) {
		}
	}

}
