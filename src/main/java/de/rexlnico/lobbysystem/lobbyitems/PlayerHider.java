package de.rexlnico.lobbysystem.lobbyitems;

import de.rexlnico.lobbysystem.methodes.inventory.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.user.User;
import de.rexlnico.lobbysystem.methodes.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerHider implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            User user = UserManager.getUser(player);
            if (e.getItem().getType().equals(Material.STICK)) {
                user.getSettings().playerHider = false;
                player.setItemInHand(new ItemBuilder(Material.BLAZE_ROD).setName("§7PlayerHider §8➤ §cAus").build());
                player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(all);
                }
            } else if (e.getItem().getType().equals(Material.BLAZE_ROD)) {
                user.getSettings().playerHider = true;
                player.setItemInHand(new ItemBuilder(Material.STICK).setName("§7PlayerHider §8➤ §aAn").build());
                player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    player.hidePlayer(all);
                }
            }
        }
    }

}
