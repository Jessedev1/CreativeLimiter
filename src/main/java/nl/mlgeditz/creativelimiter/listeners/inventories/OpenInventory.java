package nl.mlgeditz.creativelimiter.listeners.inventories;

import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;
import wouter.is.cool.DataType;

/**
 * Created by MLGEditz and/or other contributors No part of this publication may
 * be reproduced, distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 by MLGEditz
 */

public class OpenInventory implements Listener {

	@EventHandler
	public void onOpenChest(InventoryOpenEvent e) {
		HumanEntity p = e.getPlayer();
		if (ChangeGameMode.getBuildingPlayers().contains(p) && !p.hasPermission("limiter.bypass")) {
			if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest
					|| e.getInventory().equals(p.getEnderChest())) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openChest").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			} else if (e.getInventory().getHolder() instanceof Hopper) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openHopper").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			} else if (e.getInventory().getHolder() instanceof Dropper) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openDropper").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			} else if (e.getInventory().getHolder() instanceof Furnace) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openFurnance").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			} else if (e.getInventory().getHolder() instanceof Beacon) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openBeacon").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			} else if (e.getInventory().getHolder() instanceof ShulkerBox) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openShulker").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			} else if (e.getInventory().getHolder() instanceof Dispenser) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openDispenser").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			} else if (e.getInventory().getTitle().equals(wouter.is.cool.API.getFile(DataType.MESSAGE).getString("SDB.Type.Titel").replaceAll("&", "§"))) {
				e.setCancelled(true);
				p.sendMessage(Main.messageData.get("openPin").replaceAll("&", "§").replaceAll("%prefix%",
						Main.messageData.get("Prefix").replaceAll("&", "§")));
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		if (ChangeGameMode.getBuildingPlayers().contains(p)) {
		if (b.getType() == Material.ENCHANTMENT_TABLE) {
			e.setCancelled(true);
			p.sendMessage(Main.messageData.get("openEnchantingTable").replaceAll("&", "§").replaceAll("%prefix%",
					Main.messageData.get("Prefix").replaceAll("&", "§")));
		} else if (b.getType() == Material.BEACON) {
			e.setCancelled(true);
			p.sendMessage(Main.messageData.get("openBeacon").replaceAll("&", "§").replaceAll("%prefix%",
					Main.messageData.get("Prefix").replaceAll("&", "§")));
		}
		}
	}
}