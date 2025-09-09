package org.blackstamp.sleepyChronicles.listener.entity.skeleton.banditSkeleton;

import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity instanceof Skeleton && entity.getScoreboardTags().contains("banditSkeleton")) e.getDrops().clear();
    }
}

