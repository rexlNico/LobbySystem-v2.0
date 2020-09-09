package de.rexlnico.lobbysystem.building;

import de.rexlnico.lobbysystem.methodes.user.UserManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("LobbySystem.build")) {
                if (BuildListener.canBuild.contains(player)) {
                    BuildListener.canBuild.remove(player);
                    sendMessage(player, "§cDu kanst nun nicht mehr bauen!");
                    player.getInventory().clear();
                    UserManager.setInventory(UserManager.getUser(player));
                    player.setGameMode(GameMode.SURVIVAL);
                } else {
                    BuildListener.canBuild.add(player);
                    sendMessage(player, "§aDu kanst nun bauen!");
                    player.getInventory().clear();
                    player.setGameMode(GameMode.CREATIVE);
                }
            }
        }
        return true;
    }

    public static void sendMessage(Player p, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"), (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

}
