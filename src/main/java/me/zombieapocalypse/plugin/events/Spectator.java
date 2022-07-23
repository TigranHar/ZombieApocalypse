package me.zombieapocalypse.plugin.events;

import me.zombieapocalypse.plugin.ZombieApocalypse;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Spectator implements Listener {

    public static int spectatorPlayers = 0;

    public static boolean LostNights() {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if(player.getGameMode() == GameMode.SPECTATOR) {
                if(spectatorPlayers < Bukkit.getOnlinePlayers().size()) {
                    spectatorPlayers++;
                }

                if(spectatorPlayers >= Bukkit.getOnlinePlayers().size()) {
                    spectatorPlayers = 0;
                    return true;
                }
            }
        }

        return false;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamemode spectator " + player.getDisplayName());

        if(LostNights()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title @a subtitle {\"text\":\"Score " + ZombieApocalypse.nightsSurvived + "\", \"color\":\"yellow\"} ");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title @a title {\"text\":\"Failed to survive\", \"color\":\"red\"} ");
            ZombieApocalypse.playSoundForAll(Sound.ENTITY_WITHER_DEATH);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "start-zombieApocalypse");
            ZombieApocalypse.nightsSurvived = 0;
            ZombieApocalypse.startOver = true;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=zombie]");
        }
    }
}
