package de.rexlnico.lobbysystem.methodes.user;

import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.ScoreboardManager;
import de.rexlnico.lobbysystem.methodes.user.freunde.Freunde;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.UUID;

public class User {

    private Player player;
    private UUID uuid;
    private int coins = 0;
    private Settings settings;
    private Freunde freunde;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        settings = new Settings(this);
        freunde = new Freunde(this);
        if (existUser()) {
            ResultSet res = Main.getMySQL().query("SELECT * FROM Coins WHERE UUID = '" + uuid + "'");
            try {
                if (res.next()) {
                    coins = res.getInt("Coins");
                }
            } catch (Exception ignore) {
            }
        }
    }

    private boolean existUser() {
        ResultSet res = Main.getMySQL().query("SELECT * FROM Coins WHERE UUID = '" + uuid + "'");
        try {
            return res.next();
        } catch (Exception ignored) {
        }
        return false;
    }

    public void update() {
        ScoreboardManager.setSideScoreboard(player);
    }

    public UUID getUUID() {
        return uuid;
    }

    public Settings getSettings() {
        return settings;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCoins() {
        return coins;
    }

    public Freunde getFreunde() {
        return freunde;
    }

    public void save() {
        settings.save();
    }

}
