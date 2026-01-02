package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.emperorOfDarkness;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
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
        if(!(craftEntity.getHandle() instanceof emperorOfDarkness eOD)) return;

        if(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) e.setDamage(e.getDamage() * 0.15);
        else if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) e.setDamage(e.getDamage() * 0.35);

        if(eOD.getBossPhase() == 1 && eOD.getHealth() <= (eOD.getMaxHealth() * 0.5)) eOD.initSecondPhase(eOD);
        else if(eOD.getBossPhase() == 2 && eOD.getHealth() <= (eOD.getMaxHealth() * 0.15)) eOD.setTickCooldown(5);
    }
}
