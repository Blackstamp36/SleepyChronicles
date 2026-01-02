package org.blackstamp.sleepychronicles.deprecated.listener.entity.phantom.seekerPhantom;

import org.blackstamp.sleepychronicles.deprecated.items.drop.phantomDrops;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        phantomDrops drops = new phantomDrops();
        Random r = new Random();
        int chance = 10;

        if (entity instanceof Phantom && entity.getScoreboardTags().contains("seekerPhantom")) {
            e.getDrops().clear();

            if(r.nextInt(1,100) <= chance) {
                e.getDrops().add(drops.createLens());
            }
        }

    }
}

