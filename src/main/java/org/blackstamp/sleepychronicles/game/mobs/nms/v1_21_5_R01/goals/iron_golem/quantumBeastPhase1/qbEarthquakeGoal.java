package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

public class qbEarthquakeGoal extends Goal {

    private final quantumBeast entity;
    private final int earthquakeDamage;
    private final int tickCooldown;
    private final int particleCount = 50;
    private final int earthquakeDelayTicks;

    public qbEarthquakeGoal(quantumBeast entity, int earthquakeDamage,
                            int earthquakeDelayTicks, int tickCooldown) {
        this.entity = entity;
        this.earthquakeDamage = earthquakeDamage;
        this.tickCooldown = tickCooldown;
        this.earthquakeDelayTicks = earthquakeDelayTicks;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(quantumBeast.bossAttacks.EARTHQUAKE)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
    }

    @Override
    public void stop(){
        entity.currentAttack = quantumBeast.bossAttacks.HOMING_WAVES;
    }

    @Override
    public void tick(){
        LivingEntity target = entity.getTarget();
        if(target == null) return;

        createEarthquake(target.getBukkitLivingEntity().getLocation());
    }

    private void createEarthquake(Location l){
        Vec3 eqVec3 = new Vec3(l.x(), l.y(), l.z());
        Location eqLoc = new Location(l.getWorld(), eqVec3.x(), getFirstSolidBlock(eqVec3).getY() + 1, eqVec3.z());
        ParticleManager pM = new ParticleManager(l.getWorld());

        new BukkitRunnable() {
            int tickCount = 0;

            @Override
            public void run() {
                tickCount++;

                if(tickCount >= earthquakeDelayTicks){
                    for (org.bukkit.entity.Player nearby : eqLoc.getNearbyPlayers(3)){
                        nearby.damage(earthquakeDamage, entity.getBukkitLivingEntity());
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.85F, 0.75F);
                    }
                    pM.spawnParticle(eqLoc, Particle.EXPLOSION_EMITTER, null,
                            1, 0.0, 0.0, 0.0, 1.0);
                    this.cancel();
                    return;
                }

                if(tickCount % 10 == 0) {
                    pM.spawnParticle(eqLoc, Particle.BLOCK, Material.DIRT.createBlockData(),
                            particleCount * 2, 0.75, 0.25, 0.75, 1.0);
                    pM.spawnCircle(eqLoc, Particle.BLOCK, 3,
                            particleCount, 0.0, Material.DIRT.createBlockData());
                    pM.spawnCircle(eqLoc, Particle.SMALL_GUST, 3,
                            20, 0.0, null);
                    for (org.bukkit.entity.Player nearby : eqLoc.getNearbyPlayers(6)){
                        nearby.playSound(nearby.getLocation(), Sound.BLOCK_ROOTED_DIRT_BREAK, 0.85F, 0.75F);
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.15F, 0.75F);
                        nearby.playSound(nearby.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.15F, 0.75F);
                    }
                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
    }

    private BlockPos getFirstSolidBlock(Vec3 startPos){
        Level level = entity.level();
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
