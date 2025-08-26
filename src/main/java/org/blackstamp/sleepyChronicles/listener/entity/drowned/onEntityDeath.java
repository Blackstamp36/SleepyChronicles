package org.blackstamp.sleepyChronicles.listener.entity.drowned;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
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
        globalClass global = new globalClass();

        if(global.getServerDay() >= 6) {
            if (entity instanceof Drowned) {
                e.getDrops().clear();
            }

        }
    }
}

