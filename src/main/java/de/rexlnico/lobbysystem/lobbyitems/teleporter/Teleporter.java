package de.rexlnico.lobbysystem.lobbyitems.teleporter;

import de.rexlnico.lobbysystem.methodes.inventory.AnimatedInventory;
import de.rexlnico.lobbysystem.methodes.inventory.ClickEvent;
import de.rexlnico.lobbysystem.methodes.inventory.InventoryAnimation;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Teleporter implements Listener {

    private static final AnimatedInventory teleporterGui = new AnimatedInventory(InventoryAnimation.TOP_TO_BOTTOM, 9 * 5, "§8➥ §bTeleporter", 100).setSound(Sound.LAVA_POP);

    public Teleporter() {
        for (final Teleports teleport : Teleports.values()) {
            teleporterGui.setTemplateSlot(teleport.getSlot(), teleport.getItemStack());
            teleporterGui.addClickEvent(teleport.getSlot(), new ClickEvent() {
                @Override
                public void run() {
                    teleport.teleport(player);
                }
            });
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            if (e.getItem().getType().equals(Material.COMPASS)) {
                teleporterGui.openInventory(player);
            }
        }
    }

}
