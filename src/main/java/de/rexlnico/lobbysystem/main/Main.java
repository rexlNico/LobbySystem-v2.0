package de.rexlnico.lobbysystem.main;

import de.rexlnico.lobbysystem.building.BuildCommand;
import de.rexlnico.lobbysystem.building.BuildListener;
import de.rexlnico.lobbysystem.commands.SetTeleport;
import de.rexlnico.lobbysystem.listeners.Chat;
import de.rexlnico.lobbysystem.lobbyitems.AutoNick;
import de.rexlnico.lobbysystem.lobbyitems.LobbyWechsler;
import de.rexlnico.lobbysystem.lobbyitems.PlayerHider;
import de.rexlnico.lobbysystem.lobbyitems.teleporter.Teleporter;
import de.rexlnico.lobbysystem.methodes.MySQL;
import de.rexlnico.lobbysystem.methodes.ScoreboardManager;
import de.rexlnico.lobbysystem.methodes.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    private final PluginManager pm = Bukkit.getPluginManager();
    private static MySQL sql;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§6Enabling LobbySystem");

        plugin = this;

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        try {
            sql = new MySQL("193.111.199.29", 3306, "Netzwerk", "Oo5MkaKlq6GgpJM3", "Netzwerk");
            sql.queryUpdate("CREATE TABLE IF NOT EXISTS Settings(UUID varchar(36), PlayerHider varchar(5), AutoNick varchar(5), GuiAnimations varchar(5), FriendRequests varchar(5), PartyRequests varchar(5))");
            sql.queryUpdate("CREATE TABLE IF NOT EXISTS Freunde(UUID varchar(36), Freunde TEXT(100000), Requests TEXT(100000), Status varchar(16))");
            sql.queryUpdate("CREATE TABLE IF NOT EXISTS Coins(UUID varchar(36), Coins INT, Boosts varchar(255))");
        } catch (Exception ignored) {
        }

        pm.registerEvents(new UserManager(), this);
        pm.registerEvents(new Teleporter(), this);
        pm.registerEvents(new AutoNick(), this);
        pm.registerEvents(new PlayerHider(), this);
        pm.registerEvents(new BuildListener(), this);
        pm.registerEvents(new LobbyWechsler(), this);
        pm.registerEvents(new Chat(), this);

        getCommand("teleport").setExecutor(new SetTeleport());
        getCommand("build").setExecutor(new BuildCommand());

        for (Player all : Bukkit.getOnlinePlayers()) {
            UserManager.join(all);
        }

        for (Player a : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.setScoreboard(a);
            ScoreboardManager.setSideScoreboard(a);
        }

        Bukkit.getConsoleSender().sendMessage("§aEnabled LobbySystem");
    }

    @Override
    public void onDisable() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            UserManager.quit(all);
        }
        Bukkit.getConsoleSender().sendMessage("§cDisabled LobbySystem");
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static MySQL getMySQL() {
        return sql;
    }

    public void addListener(Listener listener) {
        pm.registerEvents(listener, this);
    }

}
