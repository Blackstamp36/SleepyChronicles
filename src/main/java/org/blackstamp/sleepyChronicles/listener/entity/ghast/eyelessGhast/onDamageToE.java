package org.blackstamp.sleepyChronicles.listener.entity.ghast.eyelessGhast;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        DamageType damageType = e.getDamageSource().getDamageType();

        if (entity instanceof Ghast && entity.getScoreboardTags().contains("eyelessGhast")) {
            if (damageType.equals(DamageType.ARROW)) {
                global.teleportRandom(entity, 8);
            }
        }
    }
}
