package org.blackstamp.sleepyChronicles.listener.item.rangerEmblem;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinkets.trinketItems;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Inventory;

@Registrable
public class onProjectileHit implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onProjectileHit(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity damager = e.getDamageSource().getCausingEntity();
        Entity projectile = e.getDamageSource().getDirectEntity();

        if (projectile instanceof Projectile && damager instanceof Player p) {
                playerData data = global.getPlayerData(p.getUniqueId());
                Inventory perksInv = data.getTrinketsAsInventory(p);
                double originalDamage = e.getDamage();
                double modifiedDamage;

                if (perksInv.contains(trinkets.createRangerEmblem())) {
                    modifiedDamage = originalDamage + (originalDamage * 0.15);
                    e.setDamage(modifiedDamage);

                }
            }

        }
    }

