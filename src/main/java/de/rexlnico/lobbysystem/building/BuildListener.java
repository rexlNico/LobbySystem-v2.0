package de.rexlnico.lobbysystem.building;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.ArrayList;

public class BuildListener implements Listener {

    public static ArrayList<Player> canBuild = new ArrayList<>();

    @EventHandler
    public void on(BlockBreakEvent event) {
        event.setCancelled(!canBuild.contains(event.getPlayer()));
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        event.setCancelled(!canBuild.contains(event.getPlayer()));
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        event.setCancelled(!canBuild.contains(event.getPlayer()));
    }

    @EventHandler
    public void on(PlayerDropItemEvent event) {
        event.setCancelled(!canBuild.contains(event.getPlayer()));
    }

    @EventHandler
    public void on(PlayerPickupItemEvent event) {
        event.setCancelled(!canBuild.contains(event.getPlayer()));
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player)
            event.setCancelled(!canBuild.contains((Player) event.getDamager()));
    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player)
            event.setCancelled(true);
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        event.setCancelled(!canBuild.contains((Player) event.getWhoClicked()));
    }

    @EventHandler
    public void on(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

}
