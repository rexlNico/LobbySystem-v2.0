package de.rexlnico.lobbysystem.methodes.user;

import de.rexlnico.lobbysystem.lobbyitems.teleporter.Teleports;
import de.rexlnico.lobbysystem.methodes.ScoreboardManager;
import de.rexlnico.lobbysystem.methodes.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class UserManager implements Listener {

    public static HashMap<Player, User> users = new HashMap<Player, User>();

    public static User getUser(Player player) {
        return users.get(player);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        join(player);
        setInventory(getUser(player));
        ScoreboardManager.setScoreboard(player);
        ScoreboardManager.setSideScoreboard(player);
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        quit(event.getPlayer());
    }

    public static void setInventory(User user) {
        Player player = user.getPlayer();
        Teleports.SPAWN.teleport(player);
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§bTeleporter").build());
        if (user.getSettings().playerHider) {
            player.getInventory().setItem(1, new ItemBuilder(Material.STICK).setName("§7PlayerHider §8➤ §aAn").build());
        } else {
            player.getInventory().setItem(1, new ItemBuilder(Material.BLAZE_ROD).setName("§7PlayerHider §8➤ §cAus").build());
        }
        if (player.hasPermission("LobbySystem.autonick")) {
            player.getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG).setName("§7AutoNick §8➤ " + (user.getSettings().autoNick ? "§aAn" : "§cAus")).enchantIftrue(user.getSettings().autoNick).build());
        }
        player.getInventory().setItem(7, new ItemBuilder(Material.NETHER_STAR).setName("§eLobbyWechsler").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.SKULL_ITEM, 1, 3).setName("§bProfile").setSkullOwner(player.getName()).build());
    }

    public static void join(Player player) {
        users.put(player, new User(player));
    }

    public static void quit(Player player) {
        getUser(player).save();
        users.remove(player);
    }

}
