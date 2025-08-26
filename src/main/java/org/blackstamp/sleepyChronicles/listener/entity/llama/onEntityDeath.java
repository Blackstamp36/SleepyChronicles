package org.blackstamp.sleepyChronicles.listener.entity.llama;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity instanceof Llama && entity.getScoreboardTags().contains("aggresiveLlama")) {
            e.getDrops().clear();
        }
    }
}

