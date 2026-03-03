package org.blackstamp.sleepychronicles.game.effect;

import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class CursedEffect implements Listener {

    private static final double DAMAGE_MULTIPLIER = 1.25D;

    @EventHandler
    public void effect(EntityRegainHealthEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!p.hasPotionEffect(PotionEffectType.INFESTED)) return;

        ParticleManager particleManager = new ParticleManager(p.getWorld());
        particleManager.particle(p.getLocation(), Particle.SNEEZE,null,50,0.75,1.0);

        p.damage(e.getAmount() * DAMAGE_MULTIPLIER);
        e.setCancelled(true);
    }
}
