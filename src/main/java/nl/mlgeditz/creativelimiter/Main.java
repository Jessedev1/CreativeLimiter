package nl.mlgeditz.creativelimiter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import nl.mlgeditz.creativelimiter.listeners.BreakFrameBlock;
import nl.mlgeditz.creativelimiter.listeners.GameModeChecker;
import nl.mlgeditz.creativelimiter.listeners.blocks.DropItems;
import nl.mlgeditz.creativelimiter.listeners.blocks.PickupItems;
import nl.mlgeditz.creativelimiter.listeners.frames.ItemFrameLimiter;
import nl.mlgeditz.creativelimiter.listeners.inventories.OpenInventory;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

/**
 * Created by MLGEditz and/or other contributors No part of this publication may
 * be reproduced, distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 by MLGEditz
 */

public class Main extends JavaPlugin implements Listener {

	
	public static Plugin pl;
	public static HashMap<String, String> messageData = new HashMap<String, String>();

	public void onEnable() {
		pl = this;
		Bukkit.broadcastMessage("heyy");
		Bukkit.getPluginManager().registerEvents(new OpenInventory(), this);
		Bukkit.getPluginManager().registerEvents(new ItemFrameLimiter(), this);
		Bukkit.getPluginManager().registerEvents(new DropItems(), this);
		Bukkit.getPluginManager().registerEvents(new PickupItems(), this);
		Bukkit.getPluginManager().registerEvents(new BreakFrameBlock(), this);
		createConfig();

		@SuppressWarnings("unused")
		BukkitTask gmcheck = new GameModeChecker().runTaskTimer(this, 0, 1);
	}

	private void createConfig() {
		File f = new File(getDataFolder() + File.separator + "messages.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
				Bukkit.getConsoleSender().sendMessage("De messages.yml is succesvol aangemaakt!");
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.getConsoleSender()
						.sendMessage("§4Er is iets fout gegaan tijdens het genereren van de messages.yml");
			}
		}

		setMessage("Prefix", "&8[&7CreativeLimiter&8]");
		setMessage("noPermissions", "%prefix% &cYou do not have &4permission &cfor this");
		setMessage("noDrops", "%prefix% &cYou cannot drop items in &4creative &cmode");
		setMessage("noPickups", "%prefix% &cYou cannot pickup items in &4creative &cmode");
		setMessage("dontTakeItem", "%prefix% &cYou are not allowed to pickup items created in &4creative &cmode");
		setMessage("openChest", "%prefix% &cYou cannot open an &4Chest &cIn &4Creative &cMode!");
		setMessage("openHopper", "%prefix% &cYou cannot open an &4Hopper &cin &4Creative &cMode!");
		setMessage("openDropper", "%prefix% &cYou cannot open an &4Dropper &cin &4Creative &cMode!");
		setMessage("openFurnance", "%prefix% &cYou cannot open an &4Furnance &cin &4Creative &cMode!");
		setMessage("openEnchantingTable", "%prefix% &cYou cannot open an &4Enchanting Table &cin &4Creative &cMode!");
		setMessage("openBeacon", "%prefix% &cYou cannot open an &4Beacon &cin &4Creative &cMode!");
		setMessage("openShulker", "%prefix% &cYou cannot open an &4Shulker &cin &4Creative &cMode!");
		setMessage("openDispenser", "%prefix% &cYou cannot open an &4Dispenser &cin &4Creative &cMode!");
		setMessage("openPin", "%prefix% &cYou cannot open an &4Safesty Deposit Box &cin &4Creative &cMode!");
		setMessage("cannotBreak", "%prefix% &cYou cannot §4Break §cThis §4Block§c!");

		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		for (String message : config.getKeys(false)) {
			messageData.put(message, config.getString(message));
		}
	}

	private void setMessage(String name, String message) {
		File f = new File(getDataFolder() + File.separator + "messages.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!config.isSet(name)) {
			config.set(name, message);
			try {
				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDisable() {
		while (ChangeGameMode.getBuildingPlayers().iterator().hasNext()) {
			Player p = ChangeGameMode.getBuildingPlayers().iterator().next();
			ChangeGameMode.leaveBuildMode(p);
		}
	}
}