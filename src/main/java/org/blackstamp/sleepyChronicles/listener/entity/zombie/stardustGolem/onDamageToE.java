package org.blackstamp.sleepyChronicles.listener.entity.zombie.stardustGolem;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.stardustGolem;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();

        if(!(entity instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();

        if(nmsEntity == null) return;
        if(!(nmsEntity instanceof stardustGolem)) return;

        if(e.getCause().equals(EntityDamageEvent.DamageCause.KILL)) return;

        e.setCancelled(true);

    }
}
