package de.rexlnico.lobbysystem.methodes.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class ClickEvent {

    public InventoryClickEvent inventoryClickEvent;
    public Player player;

    public abstract void run();

}
