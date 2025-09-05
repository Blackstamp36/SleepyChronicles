package org.blackstamp.sleepyChronicles.listener.entity.creeper;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        DamageType damageType = e.getDamageSource().getDamageType();

        if(entity.getScoreboardTags().contains("blackHole")) e.setCancelled(true);

        if (entity instanceof Creeper
                && (entity.getScoreboardTags().contains("missingId"))){
            if (damageType.equals(DamageType.ARROW) || damageType.equals(DamageType.MOB_PROJECTILE)) {
                e.setCancelled(true);
            }

        }
    }
}
