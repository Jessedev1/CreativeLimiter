package nl.mlgeditz.creativelimiter.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MLGEditz and/or other contributors No part of this publication may
 * be reproduced, distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 by MLGEditz
 */

public class ChangeGameMode {

	private static List<Player> manager = new ArrayList<>();

	public static void enterBuildMode(Player p) {
		manager.add(p);
		InventoryManager.setSavedInventory(p.getUniqueId(), p.getInventory().getContents());
		p.getInventory().clear();
	}

	public static void leaveBuildMode(Player p) {
		manager.remove(p);
		p.getInventory().clear();
		for (ItemStack is : InventoryManager.getSavedInventory(p.getUniqueId())) {
			if (is != null) {
				p.getInventory().addItem(is);
			}
		}

	}

	public static List<Player> getBuildingPlayers() {
		return manager;
	}
}
