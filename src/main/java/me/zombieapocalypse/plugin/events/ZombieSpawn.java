package me.zombieapocalypse.plugin.events;

import me.zombieapocalypse.plugin.ZombieApocalypse;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class ZombieSpawn implements Listener {

    public int RandomNumber(int high, int low) {
        Random rand = new Random();
        int result = rand.nextInt(high-low) + low;

        return result;
    }

    public boolean night() {
        Server server = getServer();
        long time = server.getWorld("world").getTime();

        if(time > 0 && time < 12300) {
            return false;
        }

        return true;
    }

    @EventHandler
    public void MobSpawnEven(CreatureSpawnEvent event) {
        if(ZombieApocalypse.start) {
            if (event.getEntityType() == EntityType.ZOMBIE && night()) {

                Zombie zombie = (Zombie) event.getEntity();
                Location zombieLocation = zombie.getLocation();
                zombie.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(200);

                switch (RandomNumber(8, 0)) {
                    case 7:
                        zombie.setCustomName("Tank");
                        zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3));
                        break;
                    case 5:
                        zombie.setCustomName("Hulk");
                        zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 3));
                        zombie.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 3));
                        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3));
                        break;
                    case 4:
                        zombie.setCustomName("Bomber");
                        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.TNT));
                        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3));
                        break;
                    default:
                        zombie.setCustomName("Normal Zombie");
                        zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3));
                }
            }

            if (!night() && event.getEntityType() == EntityType.ZOMBIE) {
                Zombie zombie = (Zombie) event.getEntity();

                zombie.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(16);
            }
        }
    }
}