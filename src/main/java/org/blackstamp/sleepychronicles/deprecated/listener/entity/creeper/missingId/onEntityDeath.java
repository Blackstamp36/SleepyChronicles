package org.blackstamp.sleepychronicles.deprecated.listener.entity.creeper.missingId;

import org.blackstamp.sleepychronicles.deprecated.items.nullItems.nullItems;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        nullItems nullItems = new nullItems();

        if (entity instanceof Creeper
                && entity.getScoreboardTags().contains("missingId")
                && entity.getKiller() != null) {
            e.getDrops().clear();
            e.getDrops().add(nullItems.createNullItem(Material.GUNPOWDER, "Nullpowder"));
        }

    }
}

