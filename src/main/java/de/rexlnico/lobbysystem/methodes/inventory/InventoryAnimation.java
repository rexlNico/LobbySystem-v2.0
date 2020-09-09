package de.rexlnico.lobbysystem.methodes.inventory;

import de.rexlnico.lobbysystem.methodes.user.User;
import de.rexlnico.lobbysystem.methodes.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public enum InventoryAnimation {

    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP,
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT;

    InventoryAnimation() {
    }

    public void animateInventory(final Player player, final Inventory templateInventory, final long sleep, final Sound sound) {
        final Inventory inventory = Bukkit.createInventory(null, templateInventory.getSize(), templateInventory.getTitle());
        User user = UserManager.getUser(player);
        ItemBuilder.fillInv(inventory);
        if (!user.getSettings().guiAnimations) {
            for (int i = 0; i < templateInventory.getSize(); i++) {
                if (templateInventory.getItem(i) != null) {
                    inventory.setItem(i, templateInventory.getItem(i));
                }
            }
            player.openInventory(inventory);
            return;
        }
        player.openInventory(inventory);
        switch (this) {

            case TOP_TO_BOTTOM:
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(sleep);
                        } catch (Exception ignored) {
                        }
                        for (int i = 0; i < templateInventory.getSize(); i++) {
                            if (templateInventory.getItem(i) != null) {
                                inventory.setItem(i, templateInventory.getItem(i));
                                if (sound != null) {
                                    if (!inventory.getViewers().isEmpty())
                                        player.playSound(player.getLocation(), sound, 1, 1);
                                }
                                if (sleep > 0) {
                                    try {
                                        Thread.sleep(sleep);
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                            if (inventory.getViewers().isEmpty()) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
                break;
            case BOTTOM_TO_TOP:
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(sleep);
                        } catch (Exception ignored) {
                        }
                        for (int i = templateInventory.getSize() - 1; i > 0; i--) {
                            if (templateInventory.getItem(i) != null) {
                                inventory.setItem(i, templateInventory.getItem(i));
                                if (sound != null) {
                                    if (!inventory.getViewers().isEmpty())
                                        player.playSound(player.getLocation(), sound, 1, 1);
                                }
                                if (sleep > 0) {
                                    try {
                                        Thread.sleep(sleep);
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                            if (inventory.getViewers().isEmpty()) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
                break;

            case LEFT_TO_RIGHT:
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(sleep);
                        } catch (Exception ignored) {
                        }
                        for (int j = 0; j < 9; j++) {
                            for (int i = 0; i < (templateInventory.getSize() / 9); i++) {
                                if (templateInventory.getItem(j + (i * 9)) != null) {
                                    inventory.setItem(j + (i * 9), templateInventory.getItem(j + (i * 9)));
                                    if (sound != null) {
                                        if (!inventory.getViewers().isEmpty())
                                            player.playSound(player.getLocation(), sound, 1, 1);
                                    }
                                    if (sleep > 0) {
                                        try {
                                            Thread.sleep(sleep);
                                        } catch (Exception ignored) {
                                        }
                                    }
                                }
                                if (inventory.getViewers().isEmpty()) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                    }
                }).start();
                break;
            case RIGHT_TO_LEFT:
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(sleep);
                        } catch (Exception ignored) {
                        }
                        for (int j = 8; j > 0; j--) {
                            for (int i = 0; i < ((templateInventory.getSize()) / 9); i++) {
                                if (templateInventory.getItem(j + (i * 9)) != null) {
                                    inventory.setItem(j + (i * 9), templateInventory.getItem(j + (i * 9)));
                                    if (sound != null) {
                                        if (!inventory.getViewers().isEmpty())
                                            player.playSound(player.getLocation(), sound, 1, 1);
                                    }
                                    if (sleep > 0) {
                                        try {
                                            Thread.sleep(sleep);
                                        } catch (Exception ignored) {
                                        }
                                    }
                                }
                                if (inventory.getViewers().isEmpty()) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                    }
                }).start();
                break;

        }
    }

}
