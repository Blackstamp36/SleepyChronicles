package org.blackstamp.sleepyChronicles.listener.entity.boss.quantumBeast;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.damage.DamageType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    public void onDamageToE(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof quantumBeast)) return;

        if (!e.getDamageSource().getDamageType().equals(DamageType.GENERIC_KILL)) e.setCancelled(true);

    }
}
