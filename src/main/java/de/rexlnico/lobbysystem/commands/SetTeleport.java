package de.rexlnico.lobbysystem.commands;

import de.rexlnico.lobbysystem.lobbyitems.teleporter.Teleports;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetTeleport implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("LobbySystem.teleports")) {
                if (args.length == 1) {
                    Teleports teleports = Teleports.getTeleportFromString(args[0]);
                    if (teleports == null) {
                        player.sendMessage("§4Dieser Teleport existiert nicht!");
                        return false;
                    }
                    teleports.setTeleport(player.getLocation());
                    player.sendMessage("§aDu hast den Teleport für " + teleports.getName() + " §agesetzt!");
                } else {
                    player.sendMessage("§4Bitte nutze /teleport <Name>");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (Teleports teleports : Teleports.values()) {
                list.add(teleports.name());
            }
        }
        return list;
    }
}
