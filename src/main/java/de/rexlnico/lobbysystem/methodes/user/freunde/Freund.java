package de.rexlnico.lobbysystem.methodes.user.freunde;

import de.rexlnico.lobbysystem.methodes.UUIDFetcher;
import de.rexlnico.lobbysystem.methodes.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Freund {

    public boolean online;
    public boolean accepted;
    public UUID uuid;
    public String name;
    public String status;
    public String server;
    public ItemStack skull;

    public Freund(boolean online, boolean accepted, UUID uuid, String status, String server) {
        this.online = online;
        this.accepted = accepted;
        this.uuid = uuid;
        this.name = UUIDFetcher.getName(uuid);
        this.status = status;
        this.server = server;
        skull = new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(name).setName(name).build();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isOnline() {
        return online;
    }

    public String getName() {
        return name;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getStatus() {
        return status;
    }

    public String getServer() {
        return server;
    }

    public ItemStack getSkullItem() {
        if (!isAccepted())
            return new ItemBuilder(skull).setName("§e" + name).setLore(status.equals("") ? "" : "//" + "§7Status §8➤ §e" + status).build();
        if (isOnline())
            return new ItemBuilder(skull).setName("§a" + name).setLore("§7Server §8➤ §a" + server + (status.equals("") ? "" : "//" + "§7Status §8➤ §e" + status)).build();
        return new ItemBuilder(skull).setName("§c" + name).setLore("§cOffline" + (status.equals("") ? "" : "//" + "§7Status §8➤ §e" + status)).build();
    }
}
