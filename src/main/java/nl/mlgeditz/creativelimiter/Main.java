package nl.mlgeditz.creativelimiter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.mlgeditz.creativelimiter.utils.Logger;
import nl.mlgeditz.creativelimiter.utils.MemoryCache;
import nl.mlgeditz.creativelimiter.utils.Updater;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mlgeditz.creativelimiter.db.Database;
import nl.mlgeditz.creativelimiter.listeners.BreakFrameBlock;
import nl.mlgeditz.creativelimiter.listeners.GameModeChecker;
import nl.mlgeditz.creativelimiter.listeners.LeaveListener;
import nl.mlgeditz.creativelimiter.listeners.block.BlockListener;
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
	public File messages = new File(getDataFolder(), "messages.yml");
	public FileConfiguration messagesConfiguration = YamlConfiguration.loadConfiguration(messages);
	private static Database thdb;
	public static HashMap<String, String> messageData = new HashMap<String, String>();
	public List<String> list = getConfig().getStringList("Deny-Placing");
	private static MemoryCache cache = new MemoryCache();

	public void onEnable() {
		pl = this;
		Bukkit.getPluginManager().registerEvents(new OpenInventory(), this);
		Bukkit.getPluginManager().registerEvents(new ItemFrameLimiter(), this);
		Bukkit.getPluginManager().registerEvents(new DropItems(), this);
		Bukkit.getPluginManager().registerEvents(new PickupItems(), this);
		Bukkit.getPluginManager().registerEvents(new BreakFrameBlock(), this);
		Bukkit.getPluginManager().registerEvents(new LeaveListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		createConfig();
		
		list.add("DIAMOND_BLOCK");
		list.add("GOLD_BLOCK");
		list.add("IRON_BLOCK");
		list.add("EMERALD_BLOCK");
		list.add("DIAMOND_ORE");
		list.add("GOLD_ORE");
		list.add("EMERALD_ORE");
		list.add("IRON_ORE");
		getConfig().set("Deny-Placing", list);
		saveConfig();

		new GameModeChecker().runTaskTimer(this, 0, 1);
		
		try {
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdir();
		thdb = new Database(new File(this.getDataFolder(), "blockdata.db"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Updater.getUpdater().checkForUpdates();

		if (Bukkit.getServer().getPluginManager().getPlugin("MinetopiaSDB") != null) {
		    Logger.log(Logger.Severity.INFO, "Found MinetopiaSDB dependency. Hooking into the API...");
        }

		Logger.log(Logger.Severity.INFO, "Syncing cache with database");
		getCache().sync();
	}
	
	public static Database thdb() {
		return thdb;
	}

	private void createConfig() {
		if (!messages.exists()) {
			try {
				messages.createNewFile();
				Logger.log(Logger.Severity.INFO, "Created messages.yml succesfully!");
			} catch (IOException e) {
				Logger.log(Logger.Severity.ERROR, "Something went wrong while creating messages.yml Error: " + e.getMessage());
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
		setMessage("openRugzak", "%prefix% &cYou cannot open an &4Backpack &cin &4Creative &cMode!");
		setMessage("cannotBreak", "%prefix% &cYou cannot §4Break §cThis §4Block§c!");
		setMessage("cannotPlace", "%prefix% &cYou cannot §4Place §cThis §4Block§c!");

		for (String message : messagesConfiguration.getKeys(false)) {
			messageData.put(message, messagesConfiguration.getString(message));
		}
	}

	private void setMessage(String name, String message) {
		if (!messagesConfiguration.isSet(name)) {
			messagesConfiguration.set(name, message);
			try {
				messagesConfiguration.save(messages);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void reloadConfigFiles() {
		try {
			this.reloadConfig();
			this.messagesConfiguration.save(messages);
		} catch (IOException e) {
			Logger.log(Logger.Severity.ERROR, "Something went wrong while reloading files... Error: " + e.getMessage());
		}
	}

	public static MemoryCache getCache() {
		return Main.cache;
	}

	@Override
	public void onDisable() {
		//New arraylist because concurrent exceptions
		Logger.log(Logger.Severity.INFO, "Removing all players from buildmode...");
		for (Player p: new ArrayList<>(ChangeGameMode.getBuildingPlayers())) {
			ChangeGameMode.leaveBuildMode(p);
		}

		Logger.log(Logger.Severity.INFO, "Syncing cache with database");
		getCache().sync();

		Logger.log(Logger.Severity.INFO, "Disabling CreativeLimiter...");
	}
}
