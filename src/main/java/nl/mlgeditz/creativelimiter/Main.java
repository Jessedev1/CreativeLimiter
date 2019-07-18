package nl.mlgeditz.creativelimiter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import nl.mlgeditz.creativelimiter.commands.CreativeLimiter;
import nl.mlgeditz.creativelimiter.config.FileManager;
import nl.mlgeditz.creativelimiter.utils.Logger;
import nl.mlgeditz.creativelimiter.utils.MemoryCache;
import nl.mlgeditz.creativelimiter.utils.Updater;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
	public static File messages;
	public static FileConfiguration messagesConfiguration;
	private static Database thdb;
	public static HashMap<String, String> messageData = new HashMap<String, String>();
	public static boolean MinetopiaSDB = false;
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
		getCommand("creativelimiter").setExecutor(new CreativeLimiter());

		FileManager messages = new FileManager("messages.yml");
		messages.createFile();
		messages.add("Prefix", "&8[&7CreativeLimiter&8]");
		messages.add("noPermissions", "%prefix% &cYou do not have &4permission &cfor this");
		messages.add("noDrops", "%prefix% &cYou cannot drop items in &4creative &cmode");
		messages.add("noPickups", "%prefix% &cYou cannot pickup items in &4creative &cmode");
		messages.add("dontTakeItem", "%prefix% &cYou are not allowed to pickup items created in &4creative &cmode");
		messages.add("openChest", "%prefix% &cYou cannot open an &4Chest &cIn &4Creative &cMode!");
		messages.add("openHopper", "%prefix% &cYou cannot open an &4Hopper &cin &4Creative &cMode!");
		messages.add("openDropper", "%prefix% &cYou cannot open an &4Dropper &cin &4Creative &cMode!");
		messages.add("openFurnace", "%prefix% &cYou cannot open an &4Furnace &cin &4Creative &cMode!");
		messages.add("openEnchantingTable", "%prefix% &cYou cannot open an &4Enchanting Table &cin &4Creative &cMode!");
		messages.add("openBeacon", "%prefix% &cYou cannot open an &4Beacon &cin &4Creative &cMode!");
		messages.add("openShulker", "%prefix% &cYou cannot open an &4Shulker &cin &4Creative &cMode!");
		messages.add("openDispenser", "%prefix% &cYou cannot open an &4Dispenser &cin &4Creative &cMode!");
		messages.add("openJukebox", "%prefix% &cYou cannot interact with a &4Jukebox &cin &4Creative &cMode!");
		messages.add("openArmorStand", "%prefix% &cYou cannot interact with an &4Armor Stand &cin &4Creative &cMode!");
		messages.add("openCraftingTable", "%prefix% &cYou cannot open an &4Crafting Table &cin &4Creative &cMode!");
		messages.add("openBrewingStand", "%prefix% &cYou cannot open an &4Brewing Stand &cin &4Creative &cMode!");
		messages.add("openPin", "%prefix% &cYou cannot open an &4Safety Deposit Box &cin &4Creative &cMode!");
		messages.add("openRugzak", "%prefix% &cYou cannot open an &4Backpack &cin &4Creative &cMode!");
		messages.add("cannotBreak", "%prefix% &cYou cannot §4Break §cThis §4Block§c!");
		messages.add("cannotPlace", "%prefix% &cYou cannot §4Place §cThis §4Block§c!");
		messages.save();

		for (String message : messages.getFile().getKeys(false)) {
			messageData.put(message, messages.getFile().getString(message));
		}

		FileManager config = new FileManager("config.yml");
		config.createFile();
		ArrayList<String> list = new ArrayList<>();
		list.add("DIAMOND_BLOCK");
		list.add("GOLD_BLOCK");
		list.add("IRON_BLOCK");
		list.add("EMERALD_BLOCK");
		list.add("DIAMOND_ORE");
		list.add("GOLD_ORE");
		list.add("EMERALD_ORE");
		list.add("IRON_ORE");
		config.write("Deny-Placing", list);
		config.save();

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
			MinetopiaSDB = true;
        }

		Logger.log(Logger.Severity.INFO, "Syncing cache with database");
		getCache().sync();
	}
	
	public static Database thdb() {
		return thdb;
	}

	public static void reloadConfigFiles() {
		try {
			Main.pl.reloadConfig();
			messagesConfiguration.save(messages);
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
			if (ChangeGameMode.getBuildingPlayers().contains(p)) ChangeGameMode.leaveBuildMode(p);
		}

		Logger.log(Logger.Severity.INFO, "Syncing cache with database");
		getCache().sync();

		Logger.log(Logger.Severity.INFO, "Disabling CreativeLimiter...");
	}
}
