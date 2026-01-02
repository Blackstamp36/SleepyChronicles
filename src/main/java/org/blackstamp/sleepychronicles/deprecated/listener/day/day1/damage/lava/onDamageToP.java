package org.blackstamp.sleepychronicles.deprecated.listener.day.day1.damage.lava;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

@Registrable
public class onDamageToP implements Listener {
    private final int particleCount = 16;
    private final double lavaKnockback = 0.95;
    private final double lavaDamage = 20;

    private final Vector velocity = new Vector(
            0,
            lavaKnockback,
            0
    );

    @EventHandler
    private void onPDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        ParticleManager pM = new ParticleManager(entity.getWorld());
        EntityDamageEvent.DamageCause damageCause = e.getCause();
        Location l = entity.getLocation();

        if(!(entity instanceof Player p)) return;
        if(!(damageCause.equals(EntityDamageEvent.DamageCause.LAVA))) return;

        e.setDamage(lavaDamage);

        pM.spawnParticle(l, Particle.FLAME,null,
                particleCount,0.75,1.5,0.75,0.0);

        p.setVelocity(velocity);
        p.playSound(p.getLocation() , Sound.ENTITY_GENERIC_EXTINGUISH_FIRE,0.75F,0.25F);
        p.playSound(p.getLocation() , Sound.BLOCK_NOTE_BLOCK_BASS,0.75F,1.25F);

        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () ->
                p.setFireTicks(0), 3);
        }
    }

