package nl.mlgeditz.creativelimiter.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class LimitedInventoryEvent extends Event implements Cancellable {

    private InventoryOpenEvent event;
    private boolean isCancelled;

    public LimitedInventoryEvent(InventoryOpenEvent event) {
        this.event = event;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return (Player) this.event.getPlayer();
    }

    public Inventory getInventory() {
        return this.event.getInventory();
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
