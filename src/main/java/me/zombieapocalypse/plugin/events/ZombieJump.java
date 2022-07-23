package me.zombieapocalypse.plugin.events;

import me.zombieapocalypse.plugin.ZombieApocalypse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class ZombieJump implements Listener {
    ZombieSpawn zombieSpawn = new ZombieSpawn();

    @EventHandler
    public void OnZombieMove(PlayerMoveEvent event) {
        if(ZombieApocalypse.start) {
            Player p = event.getPlayer();
            int playerPos = (int) p.getLocation().getY();
            int highestBlock = p.getLocation().getWorld().getHighestBlockAt(p.getLocation()).getY();

            if(zombieSpawn.night() && p.getNearbyEntities(60, 60, 60).size() <= 100) {

                int locX = p.getLocation().getBlockX() + zombieSpawn.RandomNumber(50, -50);
                int locZ = p.getLocation().getBlockZ() + zombieSpawn.RandomNumber(50, -50);
                int locXU= p.getLocation().getBlockX() + zombieSpawn.RandomNumber(5, 1);
                int locZU = p.getLocation().getBlockZ() + zombieSpawn.RandomNumber(5, 1);
                int locY = p.getLocation().getBlockY() + 5;

                if(playerPos > highestBlock) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon minecraft:zombie " + locX + " " + locY + " " + locZ);
                }

                if(playerPos < highestBlock) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon minecraft:zombie " + locXU + " " + locY + " " + locZU);
                }
            }

            else if(zombieSpawn.night() && p.getNearbyEntities(60, 60, 60).size() >= 200) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=zombie]");
            }

            for (Entity en : p.getNearbyEntities(1, 20, 1))
            {
                if(en.getType() == EntityType.ZOMBIE) {
                    Zombie zombie = (Zombie) en;

                    if(zombie.getEquipment().getItemInMainHand().equals(new ItemStack(Material.TNT))) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon minecraft:tnt "+ p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " {Fuse:30}");
                        zombie.damage(10000);
                    }

                    if(playerPos - en.getLocation().getY() > 5) {
                        zombie.setVelocity(zombie.getLocation().getDirection().multiply(0.3).setY(2));
                    }
                }
            }
        }
    }
}
