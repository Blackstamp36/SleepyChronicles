package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.skeleton.ptPhase1;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.planterrorBoss;

import java.util.EnumSet;

public class ptDistanceGoal extends Goal {
    private final planterrorBoss entity;
    private final double minDistance;
    private final double maxDistance;
    private final int minDistanceDamage;

    public ptDistanceGoal(planterrorBoss entity, double minDistance,
                          double maxDistance, int minDistanceDamage) {
        this.entity = entity;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.minDistanceDamage = minDistanceDamage;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();

        return target instanceof Player &&
                ((Player) target).gameMode() == GameType.SURVIVAL;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public boolean isInterruptable() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();
        if (target == null) return;

        entity.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double actualDistance = entity.distanceTo(target);

        if (actualDistance < minDistance) {
            Vec3 retreatDir = entity.position().subtract(target.position()).normalize();
            Vec3 retreatPos = entity.position().add(retreatDir.scale(minDistance + 3));

            entity.getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, 1.75);

            entity.getBukkitLivingEntity().swingMainHand();
            target.getBukkitLivingEntity().damage(minDistanceDamage,entity.getBukkitLivingEntity());

        } else if (actualDistance > maxDistance) {
            entity.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.75);

        } else {
            Vec3 hoverPos = entity.position().add(
                    (entity.getRandom().nextDouble() - 0.5) * 2,
                    (entity.getRandom().nextDouble() - 0.5),
                    (entity.getRandom().nextDouble() - 0.5) * 2
            );

            entity.getNavigation().moveTo(hoverPos.x, hoverPos.y, hoverPos.z, 0.5);
        }

    }
}
