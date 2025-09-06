package org.blackstamp.sleepyChronicles.listener.entity.ally;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.allyMob;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onTargetEntity implements Listener {

    @EventHandler
    private void onTrack(EntityTargetEvent e){
        Entity entity = e.getEntity();
        Entity target = e.getTarget();

        if(target == null) return;

        if(target.isInvulnerable()) {
            e.setCancelled(true);
            return;
        }

        if (!(entity instanceof CraftEntity craftEntity)) return;
        if (!(target instanceof CraftEntity craftTarget)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();
        net.minecraft.world.entity.Entity nmsTarget = craftTarget.getHandle();
        if(nmsEntity instanceof allyMob && nmsTarget instanceof allyMob) e.setCancelled(true);

    }
}