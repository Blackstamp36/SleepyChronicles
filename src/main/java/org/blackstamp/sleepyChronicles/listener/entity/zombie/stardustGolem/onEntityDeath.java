package org.blackstamp.sleepyChronicles.listener.entity.zombie.stardustGolem;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity instanceof Zombie && entity.getScoreboardTags().contains("stardustGolem")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            for(Player nearby : entity.getLocation().getNearbyPlayers(10)){
                nearby.playSound(nearby.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.5F,0.35F);

            }
        }
    }
}
