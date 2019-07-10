package nl.mlgeditz.creativelimiter.manager;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class InventoryManager {
	
	private static HashMap<UUID, ItemStack[]> inventories = new HashMap<>();
	
	public static ItemStack[] getSavedInventory(UUID u) {
		return inventories.get(u);
	}

	public static void setSavedInventory(UUID u, ItemStack[] items) {
		removeSavedInventory(u);
		inventories.put(u, items);
	}

	public static void removeSavedInventory(UUID u) {
		inventories.remove(u);
	}
}
