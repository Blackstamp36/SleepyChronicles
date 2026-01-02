package org.blackstamp.sleepychronicles.deprecated.listener.entity.fox.kitsuneFox;

import org.blackstamp.sleepychronicles.deprecated.items.drop.foxDrops;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Fox;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        foxDrops items = new foxDrops();

        if (entity instanceof Fox && entity.getScoreboardTags().contains("kitsuneFox")) {
            e.getDrops().clear();

            if (entity.getKiller() != null) {
                e.getDrops().add(items.createKitsuneTail());

            }
        }
    }
}
