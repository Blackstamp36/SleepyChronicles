package org.blackstamp.sleepyChronicles.listener.entity.ghast.eyelessGhast;

import org.blackstamp.sleepyChronicles.item.drop.ghastDrops;
import org.blackstamp.sleepyChronicles.util.Registrable;
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
        ghastDrops ghastDrops = new ghastDrops();

        if (entity instanceof Ghast && entity.getScoreboardTags().contains("eyelessGhast")) {
            e.getDrops().clear();
            e.getDrops().add(ghastDrops.createBloodTear());
        }
    }
}

