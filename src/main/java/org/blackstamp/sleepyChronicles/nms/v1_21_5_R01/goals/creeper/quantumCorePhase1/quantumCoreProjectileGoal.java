package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.creeper.quantumCorePhase1;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.manager.CollisionManager;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class quantumCoreProjectileGoal extends Goal {
    CollisionManager cM = new CollisionManager();

    int attackCooldown = 0;
    int burstCount = 0;

    private final quantumCore entity;

    public quantumCoreProjectileGoal(quantumCore entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if(!(entity.getTarget() instanceof Player targetPlayer)) return false;

        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();
        LivingEntity bukkitEntity = entity.getBukkitLivingEntity();
        double distanceToTarget = bukkitEntity.getLocation().distance(bukkitPlayer.getLocation());

        return distanceToTarget >= 7.0
                && distanceToTarget < 18.0
                && bukkitEntity.hasLineOfSight(bukkitPlayer)
                && bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL);
    }

    @Override
    public void tick() {
        Player targetPlayer = (Player) entity.getTarget();
        if (targetPlayer == null) return;

        if(attackCooldown >= 1) {
            attackCooldown--;
            return;
        }

        if(burstCount < 5){
            entity.shootQuantumProjectile(targetPlayer);
            burstCount++;
            attackCooldown = 6;

        } else {
            burstCount = 0;
            attackCooldown = 20;
        }
    }

    private void firePrismBarrage() {
        if(!(entity.getTarget() instanceof Player targetPlayer)) return;

        Location mobLoc = entity.getBukkitEntity().getLocation();

        // Creates 2 prisms that home in on targeted player.
        for (int i = 0; i < 2; i++) {
            Vector offset = new Vector(
                    (Math.random() - 0.5) * 3,
                    (Math.random() - 0.5) * 2 + 2,
                    (Math.random() - 0.5) * 3
            );

            Location spawnLoc = mobLoc.clone().add(offset);
            createHomingPrism(spawnLoc, targetPlayer, 10 + (i * 10)); // Staggered homing delay
        }

    }

    private void createHomingPrism(Location startLoc, net.minecraft.world.entity.LivingEntity target, int delay) {
        ParticleManager particleManager = new ParticleManager(startLoc.getWorld());
        LivingEntity targetEntity = target.getBukkitLivingEntity();
        ArmorStand prism = (ArmorStand) startLoc.getWorld().spawnEntity(startLoc, EntityType.ARMOR_STAND);
        prism.setInvulnerable(true);
        prism.setInvisible(true);
        prism.setAI(false);
        Location previousLoc = targetEntity.getLocation();

        new BukkitRunnable() {
            int ticks = 0;
            boolean isHoming = false;

            @Override
            public void run() {
                Location currentLoc = targetEntity.getLocation();

                if (prism.isDead() || (ticks+= 5) > 100) {
                    prism.remove();
                    this.cancel();
                    return;
                }

                if (ticks == delay) isHoming = true;

                if (isHoming && currentLoc != null) {
                    Vector toTarget = currentLoc.toVector().subtract(prism.getLocation().toVector()).normalize();
                    Vector currentVel = prism.getVelocity();
                    Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.6);
                    prism.setVelocity(newVel);

                } else {
                    Vector toTarget = previousLoc.toVector().subtract(prism.getLocation().toVector()).normalize();
                    Vector currentVel = prism.getVelocity();
                    Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.6);
                    prism.setVelocity(newVel);
                }

                particleManager.spawnParticle(prism.getLocation(), Particle.SWEEP_ATTACK, null
                        ,6,0.15,0.5,0.15,1.0);

                if(cM.checkBoundingBoxCollision(prism, targetEntity)){
                    prism.remove();
                    this.cancel();
                    doPrismImpact(targetEntity);
                }
            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 5);
    }

    private void doPrismImpact(LivingEntity damagedEntity){
        damagedEntity.damage(5, this.entity.getBukkitEntity());

        if(damagedEntity instanceof org.bukkit.entity.Player p)
            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_HURT,0.75F,1.25F);


    }
}

