package de.rexlnico.lobbysystem.methodes.user;

import de.rexlnico.lobbysystem.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Settings {

    private User user;
    public boolean playerHider = false;
    public boolean autoNick = false;
    public boolean guiAnimations = true;
    public boolean friendRequests = true;
    public boolean partyRequests = true;

    public Settings(User user) {
        this.user = user;
        if (existUser()) {
            ResultSet res = Main.getMySQL().query("SELECT * FROM Settings WHERE UUID = '" + user.getUUID() + "'");
            try {
                if (res.next()) {
                    playerHider = Boolean.parseBoolean(res.getString("PlayerHider"));
                    autoNick = Boolean.parseBoolean(res.getString("AutoNick"));
                    guiAnimations = Boolean.parseBoolean(res.getString("GuiAnimations"));
                    friendRequests = Boolean.parseBoolean(res.getString("FriendRequests"));
                    partyRequests = Boolean.parseBoolean(res.getString("PartyRequests"));
                }
            } catch (Exception ignore) {
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (UserManager.getUser(all) != null && UserManager.getUser(all).getSettings().playerHider) {
                all.hidePlayer(user.getPlayer());
            }
        }
        if (playerHider) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                user.getPlayer().hidePlayer(all);
            }
        }
    }

    private boolean existUser() {
        ResultSet res = Main.getMySQL().query("SELECT * FROM Settings WHERE UUID = '" + user.getUUID() + "'");
        try {
            return res.next();
        } catch (Exception ignored) {
        }
        return false;
    }

    public void save() {
        try {
            if (existUser()) {
                PreparedStatement ps = Main.getMySQL().getConnection().prepareStatement("UPDATE Settings SET PlayerHider = ?, AutoNick = ?, GuiAnimations = ?, FriendRequests = ?, PartyRequests = ? WHERE UUID = ?");
                ps.setString(1, String.valueOf(playerHider));
                ps.setString(2, String.valueOf(autoNick));
                ps.setString(3, String.valueOf(guiAnimations));
                ps.setString(4, String.valueOf(friendRequests));
                ps.setString(5, String.valueOf(partyRequests));
                ps.setString(6, String.valueOf(user.getUUID()));
                ps.execute();
                ps.close();
            } else {
                PreparedStatement ps = Main.getMySQL().getConnection().prepareStatement("INSERT INTO Settings VALUES (?, ?, ?, ?, ?, ?)");
                ps.setString(1, String.valueOf(user.getUUID()));
                ps.setString(2, String.valueOf(playerHider));
                ps.setString(3, String.valueOf(autoNick));
                ps.setString(4, String.valueOf(guiAnimations));
                ps.setString(5, String.valueOf(friendRequests));
                ps.setString(6, String.valueOf(partyRequests));
                ps.executeUpdate();
                ps.close();
            }
        } catch (Exception ignored) {
        }
    }

}
