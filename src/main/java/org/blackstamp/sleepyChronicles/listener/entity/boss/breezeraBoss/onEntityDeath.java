package org.blackstamp.sleepyChronicles.listener.entity.boss.breezeraBoss;

import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity.getScoreboardTags().contains("breezeraBoss")) {
            e.getDrops().clear();
        }
    }
}

