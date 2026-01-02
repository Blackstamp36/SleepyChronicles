package org.blackstamp.sleepychronicles.deprecated.listener.entity.drowned;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        GlobalClass global = new GlobalClass();

        if(global.getServerDay() >= 6) {
            if (entity instanceof Drowned) e.getDrops().clear();

        }
    }
}

