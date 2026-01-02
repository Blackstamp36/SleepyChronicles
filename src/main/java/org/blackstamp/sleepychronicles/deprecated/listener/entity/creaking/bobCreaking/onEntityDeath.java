package org.blackstamp.sleepychronicles.deprecated.listener.entity.creaking.bobCreaking;

import org.blackstamp.sleepychronicles.deprecated.items.drop.creakingDrops;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Creaking;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        creakingDrops creakingDrops = new creakingDrops();

        if (entity instanceof Creaking
                && entity.getScoreboardTags().contains("bobCreaking")
                && entity.getKiller() != null) {
            e.getDrops().add(creakingDrops.createBobFlesh());
        }
    }
}

