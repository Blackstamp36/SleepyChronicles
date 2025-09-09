package org.blackstamp.sleepyChronicles.listener.entity.zombie.stardustGolem;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.stardustGolem;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if(!(entity instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();

        if(nmsEntity == null) return;
        if(!(nmsEntity instanceof stardustGolem)) return;

        e.getDrops().clear();
        e.setDroppedExp(0);

        for(Player nearby : entity.getLocation().getNearbyPlayers(10)){
            nearby.playSound(nearby.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.5F,0.35F);

        }
    }
}
