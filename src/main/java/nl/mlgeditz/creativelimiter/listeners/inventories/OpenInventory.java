package nl.mlgeditz.creativelimiter.listeners.inventories;

import java.util.ArrayList;

import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.block.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;
import nl.mlgeditz.creativelimiter.utils.XMaterial;

import nl.minetopiasdb.api.API;
import nl.minetopiasdb.api.enums.DataType;

/**
 * Created by MLGEditz and/or other contributors No part of this publication may
 * be reproduced, distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 by MLGEditz
 */

public class OpenInventory implements Listener {
	
	public ArrayList<Player> cd = new ArrayList<Player>();
	
	

	@EventHandler
	public void onOpenChest(InventoryOpenEvent e) {
		HumanEntity p = e.getPlayer();
		if (ChangeGameMode.getBuildingPlayers().contains(p)) {
			if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.openinv")) {
			if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest || e.getInventory().equals(p.getEnderChest())) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openChest")));
			} else if (e.getInventory().getHolder() instanceof Hopper) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openHopper")));
			} else if (e.getInventory().getHolder() instanceof Dropper) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openDropper")));
			} else if (e.getInventory().getHolder() instanceof Furnace) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openFurnace")));
			} else if (e.getInventory().getHolder() instanceof Beacon) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openBeacon")));
			} else if (e.getInventory().getHolder() instanceof ShulkerBox) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openShulker")));
			} else if (e.getInventory().getHolder() instanceof Dispenser) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openDispenser")));
			} else if (e.getInventory().getHolder() instanceof BrewingStand) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openBrewingStand")));
			} else if (e.getInventory().getHolder() instanceof HopperMinecart) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openHopper")));
			} else if (e.getInventory().getHolder() instanceof StorageMinecart) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openChest")));
			} else if (Main.MinetopiaSDB && e.getView().getTitle().equals(API.getFile(DataType.MESSAGE).getString("SDB.Type.Titel").replaceAll("&", "§"))) {
				p.sendMessage(Logger.prefixFormat(Main.messageData.get("openPin")));
			} else return;
			e.setCancelled(true);
		}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		EquipmentSlot hand = e.getHand();

		if (ChangeGameMode.getBuildingPlayers().contains(p)) {
			if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.openinv")) {
				if (b == null) return;
				if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

				if (hand.equals(EquipmentSlot.HAND) && b.getType() == XMaterial.ENCHANTING_TABLE.parseMaterial()) {
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("openEnchantingTable")));
				} else if (hand.equals(EquipmentSlot.HAND) && b.getType() == XMaterial.BEACON.parseMaterial()) {
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("openBeacon")));
				} else if (hand.equals(EquipmentSlot.HAND) && p.getItemInHand().getType() == XMaterial.CARROT_ON_A_STICK.parseMaterial()) {
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("openRugzak")));
				} else if (hand.equals(EquipmentSlot.HAND) && b.getType() == XMaterial.RED_SANDSTONE_STAIRS.parseMaterial()) {
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("openPin")));
				} else if (hand.equals(EquipmentSlot.HAND) && b.getType() == XMaterial.JUKEBOX.parseMaterial()) {
					p.sendMessage(e.getAction().toString() + " : " + hand);
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("openJukebox")));
				} else if (hand.equals(EquipmentSlot.HAND) && b.getType() == XMaterial.CRAFTING_TABLE.parseMaterial()) {
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("openCraftingTable")));
				} else if (hand.equals(EquipmentSlot.HAND) && b.getType() == XMaterial.ARMOR_STAND.parseMaterial()) {
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("openArmorStand")));
				} else return;
				e.setCancelled(true);
			}
		}
	}
}
