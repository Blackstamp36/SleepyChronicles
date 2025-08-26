package org.blackstamp.sleepyChronicles.listener.entity.zombie.paleSoul;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.paleSoul;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

@Registrable
public class onEntityTarget implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e){
        Entity entity = e.getEntity();
        Entity target = e.getTarget();

        if(((CraftEntity) entity).getHandle() instanceof paleSoul){

            if(((CraftEntity) target).getHandle() instanceof paleSoul){
                e.setCancelled(true);

            }

        }
    }
}
