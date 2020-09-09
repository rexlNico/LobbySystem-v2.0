package de.rexlnico.lobbysystem.methodes.user.freunde;

import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.rexlnico.lobbysystem.main.Main;
import de.rexlnico.lobbysystem.methodes.user.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Freunde {

    private ArrayList<Freund> friends = new ArrayList<Freund>();
    private String status = "";
    private User user;

    public Freunde(User user) {
        this.user = user;
        if (existUser()) {
            ResultSet res = Main.getMySQL().query("SELECT * FROM Freunde WHERE UUID = '" + user.getUUID() + "'");
            try {
                if (res.next()) {
                    String[] freunde = res.getString("Freunde") != null ? res.getString("Freunde").split(";") : null;
                    String[] request = res.getString("Requests") != null ? res.getString("Requests").split(";") : null;
                    status = res.getString("Status");
                    if (freunde != null) {
                        for (String st : freunde) {
                            UUID uuid = UUID.fromString(st);
                            ResultSet fRes = Main.getMySQL().query("SELECT Status FROM Freunde WHERE UUID = '" + uuid.toString() + "'");
                            String userStatus = "";
                            if (fRes.next()) userStatus = fRes.getString("Status");
                            boolean online = BridgePlayerManager.getInstance().getOnlinePlayer(uuid) != null;
                            Freund freund = new Freund(online, true, uuid, userStatus, online ? BridgePlayerManager.getInstance().getOnlinePlayer(uuid).getConnectedService().getServerName() : "");
                            friends.add(freund);
                        }
                    }
                    if (request != null) {
                        for (String st : request) {
                            UUID uuid = UUID.fromString(st);
                            ResultSet fRes = Main.getMySQL().query("SELECT * FROM Freunde WHERE UUID = '" + uuid + "'");
                            String userStatus = "";
                            if (fRes.next()) userStatus = fRes.getString("Status");
                            boolean online = BridgePlayerManager.getInstance().getOnlinePlayer(uuid) != null;
                            Freund freund = new Freund(online, false, uuid, userStatus, online ? BridgePlayerManager.getInstance().getOnlinePlayer(uuid).getConnectedService().getServerName() : "");
                            friends.add(freund);
                        }
                    }
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
    }

    private boolean existUser() {
        ResultSet res = Main.getMySQL().query("SELECT * FROM Freunde WHERE UUID = '" + user.getUUID() + "'");
        try {
            return res.next();
        } catch (Exception ignored) {
        }
        return false;
    }

    public String getStatus() {
        return status;
    }

    private boolean existUser(UUID uuid) {
        ResultSet res = Main.getMySQL().query("SELECT * FROM Freunde WHERE UUID = '" + uuid + "'");
        try {
            return res.next();
        } catch (Exception ignored) {
        }
        return false;
    }

    public Freund getFreund(UUID uuid) {
        for (Freund freund : friends) {
            if (freund.getUUID().equals(uuid)) {
                return freund;
            }
        }
        return null;
    }

    public int getOnlineCount() {
        int count = 0;
        for (Freund freund : friends) {
            if (freund.online && freund.accepted) {
                count++;
            }
        }
        return count;
    }

}
