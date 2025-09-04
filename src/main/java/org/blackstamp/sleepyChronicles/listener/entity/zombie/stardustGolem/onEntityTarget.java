package org.blackstamp.sleepyChronicles.listener.entity.zombie.stardustGolem;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

@Registrable
public class onEntityTarget implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e){
        Entity entity = e.getEntity();

        if(entity.getScoreboardTags().contains("allyMob") || entity.isInvulnerable()){
            if(e.getTarget() == null) return;

            Entity target = e.getTarget();

            if(target.getScoreboardTags().contains("allyMob") || entity.isInvulnerable()){
                e.setCancelled(true);

            }

        }
    }
}
