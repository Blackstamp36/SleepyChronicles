package org.blackstamp.sleepyChronicles.listener.entity.creeper.missingId;

import org.blackstamp.sleepyChronicles.item.null_items.nullItems;
import org.blackstamp.sleepyChronicles.util.Registrable;
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

        if (entity instanceof Creeper && entity.getScoreboardTags().contains("missingId")) {
            e.getDrops().clear();
            e.getDrops().add(nullItems.createNullItem(Material.GUNPOWDER, "Nullpowder"));
        }

    }
}

