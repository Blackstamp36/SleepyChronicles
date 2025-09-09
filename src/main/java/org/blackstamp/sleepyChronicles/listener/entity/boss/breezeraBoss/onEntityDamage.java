package org.blackstamp.sleepyChronicles.listener.entity.boss.breezeraBoss;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bogged.breezeraBoss;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onEntityDamage implements Listener {

    @EventHandler
    private void onEntityDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof breezeraBoss boss)) return;

        double originalDamage = e.getDamage();
        double modifiedDamage = 1.0;
        LivingEntity causingEntity = (LivingEntity) e.getDamageSource().getCausingEntity();

        if(boss.getBossPhase() == 1 && boss.getHealth() <= (boss.getMaxHealth() * 0.5)) boss.startPhaseTwo();

        if(boss.getBossPhase() == 2){
            if(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) modifiedDamage = 0.15;

            else if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                    || e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) modifiedDamage = 0.30;

            e.setDamage(originalDamage * modifiedDamage);

            if(causingEntity instanceof CraftPlayer p){
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 0.5F, 1.25F);
            }
        }
    }
}
