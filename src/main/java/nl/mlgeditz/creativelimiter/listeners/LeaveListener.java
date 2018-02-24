package nl.mlgeditz.creativelimiter.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

/**
 * Created by MLGEditz and/or other contributors
 * No part of this publication may be reproduced,
 * distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 2018 by MLGEditz
*/

public class LeaveListener implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		ChangeGameMode.leaveBuildMode(p);
		p.setGameMode(GameMode.SURVIVAL);
	}

}
