package nl.mlgeditz.creativelimiter.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

/**
 * Created by MLGEditz and/or other contributors No part of this publication may
 * be reproduced, distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 by MLGEditz
 */

public class GameModeChecker extends BukkitRunnable {

	@Override
	public void run() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getGameMode() == GameMode.CREATIVE) {
				if (!all.hasPermission("limiter.bypass") || !all.hasPermission("limiter.holdinv")) {
				if (!ChangeGameMode.getBuildingPlayers().contains(all)) {
					ChangeGameMode.enterBuildMode(all);
				}
				}
			} else if (all.getGameMode() == GameMode.SURVIVAL || all.getGameMode() == GameMode.ADVENTURE) {
				if (!all.hasPermission("limiter.bypass") || !all.hasPermission("limiter.holdinv")) {
				if (!all.hasPermission("limiter.bypass")) {
				if (ChangeGameMode.getBuildingPlayers().contains(all)) {
					ChangeGameMode.leaveBuildMode(all);
				}
				}
				}
			}
		}
	}

}
