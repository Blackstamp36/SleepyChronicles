package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase1;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.HomingProjectile;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.shockWave;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class eodHomingProjectilesGoal extends Goal {

    private final double minDistance;
    private final int projectileCount;
    private final int sphereDamage = 50;
    private final int waveDamage = 36;
    private final int projectileDamage;
    private final int tickCooldown;

    private final emperorOfDarkness entity;

    public eodHomingProjectilesGoal(emperorOfDarkness entity, double minDistance,
                                    int projectileCount, int projectileDamage, int tickCooldown) {
        this.minDistance = minDistance;
        this.entity = entity;
        this.projectileCount = projectileCount;
        this.projectileDamage = projectileDamage;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.distanceTo(target) >= minDistance
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
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
                    executeRandomSkill();
                    this.cancel();
                }

                if(tickCount % 10 == 0) {
                    HomingProjectile projectile = new HomingProjectile(EntityType.ARMOR_STAND, nmsLevel,
                            projectileDamage,70 + tickCount, 40 + tickCount,
                            target, entity);

                    projectile.setPos(startPos);
                    nmsLevel.addFreshEntity(projectile);
                    bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.85F,1.75F);
                }

            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
    }

    private void initSphereAttack(){
        Entity bukkitE = entity.getBukkitLivingEntity();
        bukkitE.setGlowing(true);
        entity.setNoAi(true);
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());

        entity.increaseTickCooldown(80);

        new BukkitRunnable() {
            int tickCount = 0;

            @Override
            public void run() {
                Location currentLoc = bukkitE.getLocation();

                if(tickCount++ >= 60) {
                    entity.setNoAi(false);
                    bukkitE.setGlowing(false);
                    pM.particle(currentLoc, Particle.EXPLOSION_EMITTER, null,
                            10,2.0,2.0,2.0,1.0);
                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(12)){
                        nearby.damage(sphereDamage, entity.getBukkitLivingEntity());
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.85F,0.75F);
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 1.75F);
                    }

                    this.cancel();
                }

                if(tickCount % 20 == 0) {
                    pM.sphere(currentLoc, Particle.END_ROD,
                            12, 500, 0.0,null);

                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(24))
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 1.75F);
                }

            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);

    }

    private void executeRandomSkill(){
        if(!ThreadLocalRandom.current().nextBoolean()) return;

        if(ThreadLocalRandom.current().nextBoolean()) initSphereAttack();
        else {
            sendShockWaves();
            Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), this::sendShockWaves, 20);
            Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), this::sendShockWaves, 40);
        }
    }

    private void sendShockWaves() {
        entity.increaseTickCooldown(20);
        double height = getFirstSolidBlock().getY() + 1;

        entity.teleportTo(entity.getX(), height, entity.getZ());

        for(org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(16)){
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL,0.85F,1.75F);
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_WITHER_SPAWN,0.85F,1.75F);
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,0.85F,0.75F);
        }
        for(double i = 0; i <= 360; i+= 11.25) sendSingleShockWave(i);
    }

    private void sendSingleShockWave(double degrees){
        Level nmsLevel = entity.level();
        double height = getFirstSolidBlock().getY() + 1;

        shockWave shockwave = new shockWave(EntityType.ARMOR_STAND, nmsLevel,
                waveDamage,80, entity, degrees);

        shockwave.setPos(entity.getX(), height, entity.getZ());
        nmsLevel.addFreshEntity(shockwave);
    }

    private BlockPos getFirstSolidBlock() {
        Level level = entity.level();
        Vec3 startPos = entity.position();
        Vec3 endPos = new Vec3(startPos.x, level.getMinY(), startPos.z);

        BlockHitResult result = level.clip(new ClipContext(
                startPos,
                endPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                entity
        ));

        if (result.getType() == HitResult.Type.BLOCK) return result.getBlockPos();

        return entity.blockPosition();
    }
}
