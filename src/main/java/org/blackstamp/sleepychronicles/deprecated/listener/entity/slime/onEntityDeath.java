package org.blackstamp.sleepychronicles.deprecated.listener.entity.slime;

import org.blackstamp.sleepychronicles.deprecated.items.drop.slimeDrops;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        slimeDrops slimeDrops = new slimeDrops();
        LivingEntity entity = e.getEntity();

        if (entity instanceof Slime && entity.getScoreboardTags().contains("seedGhostSlime")) {
            e.getDrops().clear();
            e.getDrops().add(slimeDrops.createGhostSeed());
        }
    }
}

