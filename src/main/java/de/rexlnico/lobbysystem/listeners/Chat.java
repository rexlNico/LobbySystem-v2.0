package de.rexlnico.lobbysystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    @EventHandler
    public void on(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        if (player.hasPermission("lobbySystem.chatcolor")) {
            Bukkit.broadcastMessage(player.getDisplayName() + " §8» §7" + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        } else {
            Bukkit.broadcastMessage(player.getDisplayName() + " §8» §7" + e.getMessage());
        }
    }

}
