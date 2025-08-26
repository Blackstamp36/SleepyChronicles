package org.blackstamp.sleepyChronicles.listener.entity.bosses.mechanicalEye;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@Registrable
public class onProjectileLaunch implements Listener {
    globalClass global = new globalClass();

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof WitherSkull skull) {
            if (skull.getShooter() instanceof Wither wither) {
                if (wither.getScoreboardTags().contains("mechanicalEye")) {
                    e.setCancelled(true);
                    for(Player p : wither.getWorld().getNearbyPlayers(wither.getLocation(), 10)){
                        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.75F, 1.5F);
                        p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0F, 1.5F);
                    }
                    createParticleProjectile(wither, skull.getLocation(), skull.getVelocity());

                }
            }
        }
    }

    private void createParticleProjectile(Wither wither, Location startLoc, Vector initialVelocity) {
        Vector direction = initialVelocity.normalize();
        double speed = 1.5;

        new BukkitRunnable() {
            final Location currentLoc = startLoc.clone();
            final Vector velocity = direction.clone().multiply(speed);
            int ticks = 0;
            final int maxTicks = 40;

            @Override
            public void run() {
                if (ticks++ > maxTicks || wither.isDead() || !wither.isValid()) {
                    this.cancel();
                    createImpactEffect(currentLoc);
                    return;
                }

                currentLoc.add(velocity);

                spawnProjectileParticles(currentLoc);

                if (checkCollision(currentLoc, wither)) {
                    this.cancel();
                }
            }
        }.runTaskTimer(sleepyChronicles.getter(), 0L, 5L);

        startLoc.getWorld().playSound(startLoc, Sound.ENTITY_ENDER_EYE_LAUNCH, 1.0f, 0.8f);
    }

    private void spawnProjectileParticles(Location location) {
        location.getWorld().spawnParticle(Particle.FLAME,
                location, 6, 0.1, 0.1, 0.1, 0.01);

        location.getWorld().spawnParticle(Particle.DRAGON_BREATH,
                location, 6, 0.2, 0.2, 0.2, 0.02);
        global.spawnParticles(location, Particle.BLOCK, Material.REDSTONE_BLOCK, 3);
    }

    private boolean checkCollision(Location projectileLoc, Wither shooter) {
        if (projectileLoc.getBlock().getType().isSolid()) {
            createImpactEffect(projectileLoc);
            return true;
        }

        for (org.bukkit.entity.LivingEntity entity : projectileLoc.getWorld().getLivingEntities()) {
            if (entity != shooter && entity.getLocation().distance(projectileLoc) < 1.5) {
                onEntityHit(entity, shooter, projectileLoc);
                return true;
            }
        }

        return false;
    }

    private void onEntityHit(LivingEntity entity, Wither shooter, Location hitLoc) {
        entity.damage(20, shooter);

        Vector knockback = hitLoc.toVector().subtract(shooter.getLocation().toVector())
                .normalize().multiply(2.5).setY(0.5);
        entity.setVelocity(knockback);

        createImpactEffect(hitLoc);

        if (entity instanceof Player p) {
            p.playSound(hitLoc, Sound.ENTITY_BLAZE_DEATH, 0.5F, 0.5F);
            p.playSound(hitLoc, Sound.ENTITY_GENERIC_EXPLODE, 0.5F, 0.75F);
        }
    }

    private void createImpactEffect(Location l) {
        global.spawnParticles(l, Particle.EXPLOSION, null, 5);
        global.spawnParticles(l, Particle.END_ROD, null, 10);
    }
}


