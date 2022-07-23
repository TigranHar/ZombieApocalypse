package me.zombieapocalypse.plugin.commands;

import me.zombieapocalypse.plugin.ZombieApocalypse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("stop-zombieApocalypse")) {
            ZombieApocalypse.nightsSurvived++;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title @a subtitle {\"text\":\"Nights survived " + ZombieApocalypse.nightsSurvived + "\", \"color\":\"yellow\"} ");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title @a title {\"text\":\"Zombies are stopped\", \"color\":\"green\"} ");
            ZombieApocalypse.playSoundForAll(Sound.UI_TOAST_CHALLENGE_COMPLETE);
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.GREEN +" Zombies Are Stopped");
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.YELLOW +" Nights survived " + ZombieApocalypse.nightsSurvived);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "start-zombieApocalypse");
            ZombieApocalypse.startOver = true;
            ZombieApocalypse.start = true;
        }

        return true;
    }
}
