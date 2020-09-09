package de.rexlnico.lobbysystem.methodes;

import de.rexlnico.lobbysystem.methodes.user.ScoreboardApi;
import de.rexlnico.lobbysystem.methodes.user.User;
import de.rexlnico.lobbysystem.methodes.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    private static Scoreboard scoreboard;

    static {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewTeam("001Admin");
        scoreboard.getTeam("001Admin").setPrefix("§4Admin §8● §4");
        scoreboard.registerNewTeam("002Developer");
        scoreboard.getTeam("002Developer").setPrefix("§bDev §8● §b");
        scoreboard.registerNewTeam("003Moderator");
        scoreboard.getTeam("003Moderator").setPrefix("§9Mod §8● §1");
        scoreboard.registerNewTeam("004Builder");
        scoreboard.getTeam("004Builder").setPrefix("§eBuilder §8● §e");
        scoreboard.registerNewTeam("005Supporter");
        scoreboard.getTeam("005Supporter").setPrefix("§aSupp §8● §a");
        scoreboard.registerNewTeam("006Youtuber");
        scoreboard.getTeam("006Youtuber").setPrefix("§5");
        scoreboard.registerNewTeam("007Premium");
        scoreboard.getTeam("007Premium").setPrefix("§6");
        scoreboard.registerNewTeam("008Spieler");
        scoreboard.getTeam("008Spieler").setPrefix("§7");
    }

    public static void setScoreboard(Player player) {
        String team = "";
        if (player.hasPermission("Rang.admin")) {
            team = "001Admin";
        } else if (player.hasPermission("Rang.developer")) {
            team = "002Developer";
        } else if (player.hasPermission("Rang.moderator")) {
            team = "003Moderator";
        } else if (player.hasPermission("Rang.builder")) {
            team = "004Builder";
        } else if (player.hasPermission("Rang.supporter")) {
            team = "005Supporter";
        } else if (player.hasPermission("Rang.youtuber")) {
            team = "006Youtuber";
        } else if (player.hasPermission("Rang.premium")) {
            team = "007Premium";
        } else {
            team = "008Spieler";
        }

        scoreboard.getTeam(team).addPlayer(player);
        player.setDisplayName(scoreboard.getTeam(team).getPrefix() + player.getName());

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreboard);
        }
    }

    public static void setSideScoreboard(Player player) {

        User user = UserManager.getUser(player);

        ScoreboardApi scoreboardApi = new ScoreboardApi(player, "§2§lr§aexlNico.de §8● §7Lobby");

        scoreboardApi.addLine("§a");
        scoreboardApi.addLine("§b  §a■ §fDein Rang");
        scoreboardApi.addLine("§c     §8§l» " + getRang(player));
        scoreboardApi.addLine("§d");
        scoreboardApi.addLine("§b  §a■ §fDeine Coins");
        scoreboardApi.addLine("§c     §8§l§l» §7" + user.getCoins());
        scoreboardApi.addLine("§e");
        scoreboardApi.addLine("§b  §a■ §fOnline Freunde");
        scoreboardApi.addLine("§c     §8§l» §7" + user.getFreunde().getOnlineCount());
        scoreboardApi.addLine("§f");
        scoreboardApi.addLine("§b  §a■ §fDiscord");
        scoreboardApi.addLine("§c     §8§l» §7dc.rexlNico.de");
        scoreboardApi.addLine("§1");
        scoreboardApi.sendScoreboard();
    }

    public static String getRang(Player player) {
        if (player.hasPermission("Rang.admin")) {
            return "§4Admin";
        } else if (player.hasPermission("Rang.developer")) {
            return "§bDeveloper";
        } else if (player.hasPermission("Rang.moderator")) {
            return "§9Moderator";
        } else if (player.hasPermission("Rang.builder")) {
            return "§eBuilder";
        } else if (player.hasPermission("Rang.supporter")) {
            return "§aSupporter";
        } else if (player.hasPermission("Rang.youtuber")) {
            return "§5Youtuber";
        } else if (player.hasPermission("Rang.premium")) {
            return "§6Premium";
        }
        return "§7Spieler";
    }

}