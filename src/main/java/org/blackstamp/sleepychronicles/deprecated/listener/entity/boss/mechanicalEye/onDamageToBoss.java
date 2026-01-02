package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.mechanicalEye;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToBoss implements Listener {

    @EventHandler
    public void onDamageToE(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof mechanicalEye)) return;

        if(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) e.setDamage(e.getDamage() * 0.4);
        else if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) e.setDamage(e.getDamage() * 0.75);

    }
}
