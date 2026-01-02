package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.theBeliever;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.evoker.theBeliever;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

@Registrable
public class onProjectileHit implements Listener {
    private final int particleCount = 16;

    @EventHandler(priority = EventPriority.LOW)
    private void onProjectileHit(ProjectileHitEvent e) {
        Entity entity = e.getEntity();
        LivingEntity caster = (LivingEntity) e.getEntity().getShooter();
        Entity hitEntity = e.getHitEntity();
        ParticleManager pM = new ParticleManager(entity.getWorld());

        if(!(caster instanceof Player p)) return;
        if(!(hitEntity instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof theBeliever)) return;

        Location originLoc = entity.getOrigin();
        Location hitLoc = hitEntity.getLocation();

        if(originLoc == null) return;

        if(!(originLoc.distance(hitLoc) >= 8.0)) return;

        e.setCancelled(true);
        entity.remove();
        p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.25F,0.5F);
        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.25F,0.5F);
        pM.spawnParticle(hitLoc, Particle.SCULK_CHARGE_POP,null,
                particleCount,0.75,1.5,0.75,0.0);
        pM.spawnParticle(hitLoc, Particle.SCULK_SOUL,null,
                particleCount,0.75,1.5,0.75,0.0);
    }
}
