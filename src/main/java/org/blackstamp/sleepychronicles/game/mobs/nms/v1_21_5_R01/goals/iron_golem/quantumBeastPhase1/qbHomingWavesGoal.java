package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1;

import lombok.Getter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.homingWave;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.bukkit.scheduler.BukkitRunnable;

public class qbHomingWavesGoal extends Goal {

    private final quantumBeast entity;
    private final int projectileDamage;
    private final int tickCooldown;
    @Getter
    private final int projectileCount;

    public qbHomingWavesGoal(quantumBeast entity, int projectileDamage,
                             int projectileCount, int tickCooldown) {
        this.entity = entity;
        this.projectileDamage = projectileDamage;
        this.projectileCount = projectileCount;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(quantumBeast.bossAttacks.HOMING_WAVES)
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
        if(entity.getBossPhase() == 1) entity.currentAttack = quantumBeast.bossAttacks.EARTHQUAKE;
        else entity.currentAttack = quantumBeast.bossAttacks.SPEED_BOOST;
    }

    @Override
    public void tick(){
        LivingEntity target = entity.getTarget();
        if(target == null) return;

        fireWaves(entity);
    }

    private void fireWaves(Mob entity){
        Level nmsLevel = entity.level();

        new BukkitRunnable() {
            int tickCount = 0;
            int projectilesShot = 0;

            @Override
            public void run() {
                tickCount++;

                if(projectilesShot >= getProjectileCount() || tickCount >= 120) this.cancel();

                if(tickCount % 10 == 0) {
                    projectilesShot++;

                    if(entity.getTarget() == null){
                        this.cancel();
                        return;
                    }

                    homingWave projectile = new homingWave(EntityType.ARMOR_STAND, nmsLevel,
                            projectileDamage, 15 + (int) (tickCount * 0.25), entity.getTarget(), entity);

                    projectile.setPos(entity.position());
                    nmsLevel.addFreshEntity(projectile);

                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);

    }


}
