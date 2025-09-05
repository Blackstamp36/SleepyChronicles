package org.blackstamp.sleepyChronicles.listener.item.solar.solarArmor;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerDamage(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        EntityDamageEvent.DamageCause damageCause = e.getCause();
        double originalDamage = e.getDamage();

        if (entity instanceof Player p) {
            if (global.hasCustomArmor(p, "solar")) {
                if (e.getDamageSource().getCausingEntity() != null)
                    e.getDamageSource().getCausingEntity().setFireTicks(100);

                switch (damageCause) {
                    case FIRE, FIRE_TICK:
                        e.setCancelled(true);
                        break;

                    case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK:
                        e.setDamage(originalDamage * 0.95);
                        break;
                }
            }
        } else if(e.getDamageSource().getCausingEntity() instanceof Player){
            if(damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) ||
                    damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)){
                e.setDamage(originalDamage + (originalDamage * 0.15));
            }

        }
    }
}

