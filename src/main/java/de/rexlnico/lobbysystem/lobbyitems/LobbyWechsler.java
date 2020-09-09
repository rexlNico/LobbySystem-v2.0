package de.rexlnico.lobbysystem.lobbyitems;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.provider.service.SpecificCloudServiceProvider;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.inventory.AnimatedInventory;
import de.rexlnico.lobbysystem.methodes.inventory.ClickEvent;
import de.rexlnico.lobbysystem.methodes.inventory.InventoryAnimation;
import de.rexlnico.lobbysystem.methodes.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyWechsler implements Listener {

    AnimatedInventory lobbyWechsler = new AnimatedInventory(InventoryAnimation.LEFT_TO_RIGHT, 9 * 3, "§8➥ §eLobbyWechsler", 40).setSound(Sound.LAVA_POP);

    public LobbyWechsler() {
        for (int i = 0; i < 7; i++) {
            lobbyWechsler.addClickEvent(10 + i, new ClickEvent() {
                @Override
                public void run() {
                    if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().startsWith("§aLobby ")) {
                        if (!BridgePlayerManager.getInstance().getOnlinePlayer(player.getUniqueId()).getConnectedService().getServerName().replace("Lobby-", "").equals(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().replace("§aLobby ", ""))) {
                            player.playSound(player.getLocation(), Sound.DIG_WOOL, 1, 1);
                            ByteArrayDataOutput out = ByteStreams.newDataOutput();
                            out.writeUTF("Connect");
                            out.writeUTF("Lobby-" + inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().replace("§aLobby ", ""));
                            player.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
                        } else {
                            player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                        }
                    }
                }
            });
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem().getType().equals(Material.NETHER_STAR)) {
                for (int i = 1; i < 8; i++) {
                    SpecificCloudServiceProvider specificCloudServiceProvider = CloudNetDriver.getInstance().getCloudServiceProvider("Lobby-" + i);
                    if (specificCloudServiceProvider == null || specificCloudServiceProvider.getServiceInfoSnapshot() == null) {
                        lobbyWechsler.setTemplateSlot(i + 9, new ItemBuilder(Material.STAINED_CLAY, 1, 14).setName("§4Lobby " + i).build());
                    } else {
                        int online = ServiceInfoSnapshotUtil.getOnlineCount(specificCloudServiceProvider.getServiceInfoSnapshot());
                        int max = ServiceInfoSnapshotUtil.getMaxPlayers(specificCloudServiceProvider.getServiceInfoSnapshot());
                        boolean b2 = BridgePlayerManager.getInstance().getOnlinePlayer(event.getPlayer().getUniqueId()).getConnectedService().getServerName().equals("Lobby-" + i);
                        if (online >= max) {
                            lobbyWechsler.setTemplateSlot(i + 9, new ItemBuilder(Material.STAINED_CLAY, 1, 4).enchantIftrue(b2).setLore("§8➥ §6" + online + "§8/§6" + max).setName("§6Lobby " + i).build());
                        } else {
                            lobbyWechsler.setTemplateSlot(i + 9, new ItemBuilder(Material.STAINED_CLAY, 1, 5).enchantIftrue(b2).setLore("§8➥ §a" + online + "§8/§a" + max).setName("§aLobby " + i).build());
                        }

                    }
                }
                lobbyWechsler.openInventory(event.getPlayer());
            }
        }
    }

}