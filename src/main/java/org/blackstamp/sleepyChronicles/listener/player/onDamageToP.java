package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.damage.DamageType.FALL;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerDamage(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        World w = entity.getWorld();
        DamageType damageType = e.getDamageSource().getDamageType();

        if (entity instanceof Player p) {
            if(p.getOpenInventory().getOriginalTitle().equals("TRINKETS")){
                p.getOpenInventory().close();
            }
        }

        if(entity instanceof Player p
                && damageType.equals(DamageType.OUT_OF_WORLD)
                && w.getName().equals("world_aftermath")){
            double x = p.getLocation().getX();
            double y = p.getLocation().getY() + 225;
            double z = p.getLocation().getZ();

            Location overworldL = new Location(Bukkit.getWorld("world"), x, y, z);
            p.teleport(overworldL);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,400,0, true,false));
        }

        if (entity instanceof Player p && p.hasPotionEffect(PotionEffectType.LUCK)){
            e.setCancelled(true);

        } else if(entity instanceof Player p && p.hasPotionEffect(PotionEffectType.UNLUCK)){
            e.setDamage((e.getDamage() + 1.5) * p.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier());
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 1, 0.5F);
        }

        if (global.getServerDay() >= 3) {
            if (entity instanceof Player) {
                if (damageType.equals(FALL)
                        || damageType.equals(DamageType.STARVE)
                        || damageType.equals(DamageType.DROWN)
                        || damageType.equals(DamageType.IN_WALL)
                        || damageType.equals(DamageType.IN_FIRE)
                        || damageType.equals(DamageType.LAVA)) {
                    e.setDamage(e.getDamage() * 2);

                    }
                }
            }
        }
    }

