package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.ghast.eodPhase1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.armor_stand.homingProjectile;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class eodHomingProjectilesGoal extends Goal {

    private final double minDistance;
    private final int projectileCount;
    private final int sphereDamage = 40;
    private final int projectileDamage;

    private final emperorOfDarkness entity;

    public eodHomingProjectilesGoal(emperorOfDarkness entity, double minDistance, int projectileCount, int projectileDamage) {
        this.minDistance = minDistance;
        this.entity = entity;
        this.projectileCount = projectileCount;
        this.projectileDamage = projectileDamage;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.distanceTo(target) >= minDistance
                && entity.getGoalCooldown() <= 0;
    }

    @Override
    public void start() {
        entity.setGoalCooldown(100);
    }

    @Override
    public void tick() {
        super.tick();

        fireProjectiles();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    private void fireProjectiles() {
        if (!(entity.getTarget() instanceof Player target)) return;
        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
        Level nmsLevel = entity.level();
        Vec3 lookVec = new Vec3(target.getX(), target.getY(), target.getZ());

        entity.getLookControl().setLookAt(lookVec);

        new BukkitRunnable() {
            int tickCount = 0;
            final Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());

            @Override
            public void run() {
                if(tickCount++ >= (10 * projectileCount)){
                    initSphereAttack();
                    this.cancel();
                }

                if(tickCount % 10 == 0) {
                    homingProjectile projectile = new homingProjectile(EntityType.ARMOR_STAND, nmsLevel,
                            projectileDamage,40 + tickCount, 15 + tickCount,
                            target, entity);

                    projectile.setPos(startPos);
                    nmsLevel.addFreshEntity(projectile);
                    bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.85F,1.75F);
                }

            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 1);
    }

    private void initSphereAttack(){
        if(!(ThreadLocalRandom.current().nextInt(0, 1000) <= 99)) return;

        entity.increaseGoalCooldown(80);

        Entity bukkitE = entity.getBukkitLivingEntity();
        bukkitE.setGlowing(true);
        entity.setNoAi(true);
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());

        new BukkitRunnable() {
            int tickCount = 0;

            @Override
            public void run() {
                Location currentLoc = bukkitE.getLocation();

                if(tickCount++ >= 60) {
                    entity.setNoAi(false);
                    bukkitE.setGlowing(false);
                    pM.spawnParticle(currentLoc, Particle.EXPLOSION_EMITTER, null,
                            10,2.0,2.0,2.0,1.0);
                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(12)){
                        nearby.damage(sphereDamage, entity.getBukkitLivingEntity());
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.85F,0.75F);
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 1.75F);
                    }

                    this.cancel();
                }

                if(tickCount % 20 == 0) {
                    pM.spawnSphere(currentLoc, Particle.END_ROD,
                            12, 500, 0.0,null,0,360);

                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(24))
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 1.75F);
                }

            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 1);

    }
}
