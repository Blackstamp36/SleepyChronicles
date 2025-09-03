package org.blackstamp.sleepyChronicles.listener.item.solarArmor;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
        DamageType damageType = e.getDamageSource().getDamageType();

        if (entity instanceof Player p) {
            if(global.hasCustomArmor(p, "solar")){

                if(e.getDamageSource().getCausingEntity() != null){
                e.getDamageSource().getCausingEntity().setFireTicks(100);
            }

                if(damageType.equals(DamageType.IN_FIRE)
                        || damageType.equals(DamageType.ON_FIRE)){
                    e.setCancelled(true);
                    }
                }

            }
        }
    }

