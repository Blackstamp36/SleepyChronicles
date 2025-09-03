package org.blackstamp.sleepyChronicles.listener.entity.zombie.paleSoul;

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

        if(entity.getScoreboardTags().contains("paleSoul")){
            if(e.getTarget() == null) return;

            Entity target = e.getTarget();

            if(target.getScoreboardTags().contains("paleSoul")){
                e.setCancelled(true);

            }

        }
    }
}
