package me.zombieapocalypse.plugin.commands;


import me.zombieapocalypse.plugin.ZombieApocalypse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartCommand implements CommandExecutor {

    public static boolean start = false;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("start-zombieApocalypse")) {
            StartCommand.start = true;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"Zombies Are Coming\", \"color\":\"red\"} ");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:entity.wither.death player @a");
        }

        return true;
    }
}
