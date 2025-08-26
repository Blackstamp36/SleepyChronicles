package org.blackstamp.sleepyChronicles.listener.entity;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onEntityDamage(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        DamageType damageType = e.getDamageSource().getDamageType();

        if(global.getServerDay() >= 6){
            if (!(entity instanceof Player)) {
                if (damageType.equals(DamageType.EXPLOSION)
                        || damageType.equals(DamageType.PLAYER_EXPLOSION)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}


