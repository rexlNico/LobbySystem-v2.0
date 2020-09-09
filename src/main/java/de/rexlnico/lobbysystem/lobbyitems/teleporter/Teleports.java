package de.rexlnico.lobbysystem.lobbyitems.teleporter;

import de.rexlnico.lobbysystem.methodes.inventory.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public enum Teleports {

    SPAWN("§bSpawn", "Spawn", 31, new ItemStack(Material.MAGMA_CREAM)),
    UHC("§eUHC", "UHC", 24, new ItemStack(Material.GOLD_AXE)),
    SPECIAL_UHC("§eSpecial UHC", "SpecialUHC", 17, new ItemBuilder(Material.GOLD_AXE).enchant().build()),
    BEDWARS("§4BedWars", "Bedwars", 9, new ItemStack(Material.BED)),
    KNOCKY("§8Knocky", "Knocky", 38, new ItemStack(Material.STICK)),
    MMORPG("§5MMORPG", "MMORPG", 13, new ItemStack(Material.DIAMOND_HELMET)),
    SKYWARS("§2SkyWars", "Skywars", 20, new ItemStack(Material.GRASS)),
    TTT("§4TTT", "TTT", 2, new ItemStack(Material.IRON_SWORD)),
    MOBARENA("§cMob Arena", "MobArena", 6, new ItemBuilder(Material.MONSTER_EGG, 1, 50).build()),
    MINEFIGHTER("§7Minefighter", "Minefighter", 42, new ItemStack(Material.DIAMOND_PICKAXE));

    private String title;
    private String name;
    private int slot;
    private ItemStack itemStack;

    Teleports(String title, String name, int slot, ItemStack itemStack) {
        this.title = title;
        this.name = name;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return new ItemBuilder(itemStack).setName(getTitle()).build();
    }

    public String getTitle() {
        return title;
    }

    public void teleport(Player player) {
        File file = new File("plugins/LobbySystem/Teleports/" + name + ".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!file.exists() || !cfg.contains("teleport")) {
            if (player.hasPermission("LobbySystem.teleports")) {
                player.sendMessage("§4Der Teleport " + title + " §4ist nicht gesetzt!");
            }
            return;
        }
        Location loc = (Location) cfg.get("teleport");
        player.teleport(loc);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
    }

    public void setTeleport(Location location) {
        File file = new File("plugins/LobbySystem/Teleports/" + name + ".yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("teleport", location);
        try {
            cfg.save(file);
        } catch (IOException ignored) {
        }
    }

    public static Teleports getTeleportFromString(String input) {
        for (Teleports teleports : values()) {
            if (teleports.name().equalsIgnoreCase(input)) {
                return teleports;
            }
        }
        return null;
    }

}
