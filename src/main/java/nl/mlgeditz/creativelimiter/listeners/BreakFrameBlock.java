package nl.mlgeditz.creativelimiter.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import nl.mlgeditz.creativelimiter.Main;

/**
 * Created by MLGEditz and/or other contributors
 * No part of this publication may be reproduced,
 * distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 2018 by MLGEditz
*/

public class BreakFrameBlock implements Listener {
	
	@EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        for (Entity entity : e.getBlock().getWorld().getNearbyEntities(e.getBlock().getLocation(), 2, 2, 2)) {
            if (entity instanceof ItemFrame && entity.getLocation().getBlock().getRelative(((ItemFrame) entity).getAttachedFace()).equals(e.getBlock())) {
                
                if (e.getPlayer().getGameMode() == GameMode.SURVIVAL || e.getPlayer().getGameMode() == GameMode.ADVENTURE) {
                	if (!e.getPlayer().hasPermission("limiter.bypass") || !e.getPlayer().hasPermission("limiter.break")) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Main.messageData.get("cannotBreak").replaceAll("&", "§").replaceAll("%prefix%",
    					Main.messageData.get("Prefix").replaceAll("&", "§")));
                    break;
                }
            }
        }
    }
	}

}
