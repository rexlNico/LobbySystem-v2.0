package de.rexlnico.lobbysystem.entitys;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EntityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("LobbySystem.entity")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("SKYWARS") || args[0].equalsIgnoreCase("BEDWARS")) {

                    }
                } else {
                    player.sendMessage("§4Bitte nutze /entity <Name>");
                }
            }
        }
        return true;
    }
}
