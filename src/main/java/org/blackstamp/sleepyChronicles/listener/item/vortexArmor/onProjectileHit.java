package org.blackstamp.sleepyChronicles.listener.item.vortexArmor;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinkets.trinketItems;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;

@Registrable
public class onProjectileHit implements Listener {

    @EventHandler
    private void onProjectileHit(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity damager = e.getDamageSource().getCausingEntity();
        Entity projectile = e.getDamageSource().getDirectEntity();

        if (projectile instanceof Projectile && damager instanceof Player p) {
                double originalDamage = e.getDamage();
                double modifiedDamage;

                if (global.hasCustomArmor(p, "vortex")){
                    modifiedDamage = originalDamage + (originalDamage * 0.3);
                    e.setDamage(modifiedDamage);

                }

            }

        }
    }

