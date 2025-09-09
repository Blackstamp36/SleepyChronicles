package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1;

import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.manager.CollisionManager;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class quantumBeastShockWaveGoal extends Goal {
    CollisionManager cM = new CollisionManager();

    double shockWaveDamage = 32;
    int shockWaveCooldown = 0;

    private final quantumBeast entity;

    public quantumBeastShockWaveGoal(quantumBeast entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        if(target == null) return false;

        org.bukkit.entity.LivingEntity bukkitT = target.getBukkitLivingEntity();
        org.bukkit.entity.LivingEntity bukkitE = entity.getBukkitLivingEntity();

        double distanceToTarget = bukkitE.getLocation().distance(bukkitT.getLocation());

        return distanceToTarget >= 16.0
                && entity.hasLineOfSight(target);
    }

    @Override
    public void tick() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
        org.bukkit.entity.LivingEntity bukkitE = entity.getBukkitLivingEntity();

        if(target == null) return;

        if (shockWaveCooldown <= 0) {
            createShockWave(bukkitE.getLocation(), target);
            shockWaveCooldown = 20;

        } else shockWaveCooldown--;
    }

    private void createShockWave(Location startLoc, net.minecraft.world.entity.LivingEntity target) {
        ParticleManager pM = new ParticleManager(startLoc.getWorld());
        LivingEntity targetEntity = target.getBukkitLivingEntity();
        ArmorStand shockWave = (ArmorStand) startLoc.getWorld().spawnEntity(startLoc, EntityType.ARMOR_STAND);
        shockWave.setInvulnerable(true);
        shockWave.setInvisible(true);
        shockWave.setAI(false);
        Location previousLoc = targetEntity.getLocation();

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {

                Vector toTarget = previousLoc.toVector().subtract(shockWave.getLocation().toVector()).normalize();
                Vector currentVel = shockWave.getVelocity();
                Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.6);
                shockWave.setVelocity(newVel);

                pM.spawnParticle(shockWave.getLocation(), Particle.BLOCK, Material.DIRT.createBlockData()
                        ,25,0.25,0.05,0.25,1.0);

                for(org.bukkit.entity.Player n : shockWave.getLocation().getNearbyPlayers(5))
                    n.playSound(shockWave.getLocation(), Sound.BLOCK_ROOTED_DIRT_BREAK,0.5F,0.25F);

                if(cM.checkBoundingBoxCollision(shockWave, targetEntity)
                        || shockWave.isDead()
                        || (ticks+= 2) > 60){
                    shockWave.remove();
                    this.cancel();
                    pM.spawnParticle(shockWave.getLocation(), Particle.EXPLOSION, null,
                            30, 0.75, 0.25, 0.75, 1.0);
                    for(LivingEntity n : shockWave.getLocation().getNearbyLivingEntities(4,3,4))
                        doWaveImpact(n);

                    for(org.bukkit.entity.Player n : shockWave.getLocation().getNearbyPlayers(5))
                        n.playSound(n.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5F, 0.75F);
                }

            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 2);
    }

    private void doWaveImpact(LivingEntity damagedEntity){
        damagedEntity.damage(shockWaveDamage, this.entity.getBukkitEntity());

        if(damagedEntity instanceof org.bukkit.entity.Player p) {
            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.75F, 1.25F);
        }
    }
}
