package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.quantumBeast;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
    Entity entity = e.getEntity();
    Entity damager = e.getDamageSource().getDirectEntity();

    if(!(entity instanceof Player p)) return;
    if(!(damager instanceof CraftEntity craftEntity)) return;
    if(!(craftEntity.getHandle() instanceof quantumBeast)) return;

    if(damager.getLocation().distance(p.getLocation()) <= 4.0)
        liftPlayer(damager, p);
    }

    private void liftPlayer(Entity damager, Player p) {
        ParticleManager particleManager = new ParticleManager(damager.getWorld());
        Location pLoc = p.getLocation();
        Location eLoc = damager.getLocation();
        Vector direction = pLoc.toVector().subtract(eLoc.toVector()).normalize();

        Vector velocity = new Vector(
                direction.getX() * 2.25,
                1.55,
                direction.getZ() * 2.25
        ).add(new Vector(
                (Math.random() - 0.5) * 0.3,
                0,
                (Math.random() - 0.5) * 0.3
        ));

        if(pLoc.distanceSquared(eLoc) < 0.0001) return;

        p.setVelocity(velocity);
        p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 0,false,false));
        p.playSound(pLoc, Sound.BLOCK_ANVIL_PLACE, 0.85F, 0.75F);
        p.playSound(pLoc, Sound.ENTITY_WITHER_BREAK_BLOCK, 0.85F, 0.5F);
        particleManager.spawnParticle(pLoc, Particle.EXPLOSION, null,
                15, 0.5,0.25,0.5,1.0);
    }
}
