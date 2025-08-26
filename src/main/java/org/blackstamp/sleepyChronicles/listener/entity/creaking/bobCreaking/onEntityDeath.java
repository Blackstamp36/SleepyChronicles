package org.blackstamp.sleepyChronicles.listener.entity.creaking.bobCreaking;

import org.blackstamp.sleepyChronicles.item.drops.creakingDrops;
import org.blackstamp.sleepyChronicles.item.drops.ghastDrops;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.entity.Creaking;
import org.bukkit.entity.Ghast;
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

        if (entity instanceof Creaking && entity.getScoreboardTags().contains("bobCreaking")) {
            e.getDrops().add(creakingDrops.createBobFlesh());
        }
    }
}

