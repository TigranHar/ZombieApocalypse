package me.zombieapocalypse.plugin;

import me.zombieapocalypse.plugin.commands.StopCommand;
import me.zombieapocalypse.plugin.events.Spectator;
import me.zombieapocalypse.plugin.events.ZombieJump;
import me.zombieapocalypse.plugin.events.ZombieSpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZombieApocalypse extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ZombieSpawn(), this);
        getServer().getPluginManager().registerEvents(new ZombieJump(), this);
        getServer().getPluginManager().registerEvents(new Spectator(), this);
        getCommand("start-zombieApocalypse").setExecutor(this);
        getCommand("stop-zombieApocalypse").setExecutor(new StopCommand());

        System.out.print( "[Zombie Apocalypse] Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.print("[Zombie Apocalypse] Disabled");
    }

    public static boolean start = false;
    public static boolean startOver = false;
    public static int count = 3600;
    public static int nightsSurvived = 0;

    ZombieSpawn zombieSpawn = new ZombieSpawn();
    public static boolean night = false;

    public static void playSoundForAll(Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(player.getLocation(), sound, 100, 1);
        }
    }

    public static void ChangeGameMods() {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if(player.getGameMode() == GameMode.SPECTATOR) {
                player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("reload-zombieApocalypse")) {
            getServer().getPluginManager().disablePlugin(this);
            getServer().getPluginManager().enablePlugin(this);

            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.YELLOW +"Reload Complete");
        }

        else if(cmd.getName().equalsIgnoreCase("start-zombieApocalypse")) {

            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {

                    if(start && count <= 0 && startOver) {
                        start = false;
                        startOver = false;
                        count = 3600;
                        ChangeGameMods();
                    }

                    if((zombieSpawn.night()) && !night) {
                        night = true;
                    }

                    if(!(zombieSpawn.night()) && night) {
                        night = false;
                        ChangeGameMods();
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"stop-zombieApocalypse");
                    }

                    switch (count) {

                        case 3600:
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"1 Hour Until Zombies Spawn\", \"color\":\"dark_green\"} ");
                            playSoundForAll(Sound.BLOCK_NOTE_BLOCK_PLING);
                            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse] 1 Hour Until Zombies Spawn");
                            break;

                        case 3000:
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"50 minutes Until Zombies Spawn\", \"color\":\"dark_green\"} ");
                            playSoundForAll(Sound.BLOCK_NOTE_BLOCK_PLING);
                            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse] 50 minutes Until Zombies Spawn");
                            break;

                        case 2400:
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"40 minutes Until Zombies Spawn\", \"color\":\"green\"} ");
                            playSoundForAll(Sound.BLOCK_NOTE_BLOCK_PLING);
                            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.GREEN +" 40 minutes Until Zombies Spawn");
                            break;

                        case 1800:
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"30 minutes Until Zombies Spawn\", \"color\":\"green\"} ");
                            playSoundForAll(Sound.BLOCK_NOTE_BLOCK_PLING);
                            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.GREEN +" 30 minutes Until Zombies Spawn");
                            break;

                        case 1200:
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"20 minutes Until Zombies Spawn\", \"color\":\"yellow\"} ");
                            playSoundForAll(Sound.BLOCK_NOTE_BLOCK_PLING);
                            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.YELLOW +" 20 minutes Until Zombies Spawn");
                            break;

                        case 600:
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"10 minutes Until Zombies Spawn\", \"color\":\"gold\"} ");
                            playSoundForAll(Sound.BLOCK_NOTE_BLOCK_PLING);
                            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.GOLD +" 10 minutes Until Zombies Spawn");
                            break;
                        case 0:
                            start = true;

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"Zombies Are Coming\", \"color\":\"red\"} ");
                            playSoundForAll(Sound.ENTITY_WITHER_DEATH);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set night");
                            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Zombie Apocalypse]" + ChatColor.RED +" Zombies Are Coming");
                            break;
                    }

                    if(count >= 0) {
                        count--;
                    }
                }
            }, 0L, 20L);
        }

        return false;
    }
}
