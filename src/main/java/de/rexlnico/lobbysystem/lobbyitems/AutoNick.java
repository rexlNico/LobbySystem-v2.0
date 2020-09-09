package de.rexlnico.lobbysystem.lobbyitems;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.inventory.ItemBuilder;
import de.rexlnico.lobbysystem.methodes.user.User;
import de.rexlnico.lobbysystem.methodes.user.UserManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoNick implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            User user = UserManager.getUser(player);
            if (e.getItem().getType().equals(Material.NAME_TAG)) {
                user.getSettings().autoNick = !user.getSettings().autoNick;
                player.setItemInHand(new ItemBuilder(Material.NAME_TAG).setName("§7AutoNick §8➤ " + (user.getSettings().autoNick ? "§aAn" : "§cAus")).enchantIftrue(user.getSettings().autoNick).build());
                player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
            }
        }
    }

    @EventHandler
    public void on(final PlayerInteractAtEntityEvent e) {
        final Player player = e.getPlayer();
        if (player.getItemInHand().getType() == Material.NAME_TAG) {
            final String oldName = e.getRightClicked().getCustomName();
            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getRightClicked().setCustomName(oldName);
                    User user = UserManager.getUser(player);
                    player.setItemInHand(new ItemBuilder(Material.NAME_TAG).setName("§7AutoNick §8➤ " + (user.getSettings().autoNick ? "§aAn" : "§cAus")).enchantIftrue(user.getSettings().autoNick).build());
                }
            }.runTaskLater(Main.getPlugin(), 1);
        }
    }

}
