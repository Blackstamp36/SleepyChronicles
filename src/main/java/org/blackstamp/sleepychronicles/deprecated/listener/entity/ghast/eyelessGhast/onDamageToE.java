package org.blackstamp.sleepychronicles.deprecated.listener.entity.ghast.eyelessGhast;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
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
        GlobalClass global = new GlobalClass();
        Entity entity = e.getEntity();
        DamageType damageType = e.getDamageSource().getDamageType();

        if (entity instanceof Ghast && entity.getScoreboardTags().contains("eyelessGhast")) {
            if (damageType.equals(DamageType.ARROW)) {
                global.teleportRandom(entity, 8);
            }
        }
    }
}
