package org.blackstamp.sleepyChronicles.listener.entity.boss.mechanicalEye;

import org.blackstamp.sleepyChronicles.item.drop.witherDrops;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity instanceof Wither && entity.getScoreboardTags().contains("mechanicalEye")) {
            witherDrops witherDrops = new witherDrops();

            e.getDrops().clear();
            e.getDrops().add(witherDrops.createSoulOfVision());
        }
    }
}

