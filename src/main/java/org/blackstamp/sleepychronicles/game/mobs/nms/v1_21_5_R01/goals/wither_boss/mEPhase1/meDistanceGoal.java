//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1;
//
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.GameType;
//import net.minecraft.world.phys.Vec3;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
//
//import java.util.EnumSet;
//
//public class meDistanceGoal extends Goal {
//    private final mechanicalEye entity;
//    private final double minDistance;
//    private final double maxDistance;
//
//    public meDistanceGoal(mechanicalEye entity, double minDistance, double maxDistance) {
//        this.entity = entity;
//        this.minDistance = minDistance;
//        this.maxDistance = maxDistance;
//        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
//    }
//
//    @Override
//    public boolean canUse() {
//        LivingEntity target = entity.getTarget();
//        return target instanceof Player &&
//                ((Player) target).gameMode() == GameType.SURVIVAL;
//    }
//
//    @Override
//    public boolean canContinueToUse() {
//        return canUse();
//    }
//
//    @Override
//    public boolean isInterruptable() {
//        return true;
//    }
//
//    @Override
//    public void tick() {
//        LivingEntity target = entity.getTarget();
//        if (target == null) return;
//
//        entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
//
//        double actualDistance = entity.distanceTo(target);
//
//        if (actualDistance < minDistance) {
//            Vec3 retreatDir = entity.position().subtract(target.position()).normalize();
//            Vec3 retreatPos = entity.position().add(retreatDir.scale(minDistance + 2));
//
//            entity.getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, 1.0);
//
//        } else if (actualDistance > maxDistance) {
//            entity.getNavigation().moveTo(target.getX(), target.getY() + 5, target.getZ(), 1.25);
//
//        } else {
//            Vec3 hoverPos = entity.position().add(
//                    (entity.getRandom().nextDouble() - 0.5) * 2,
//                    (entity.getRandom().nextDouble() - 0.5),
//                    (entity.getRandom().nextDouble() - 0.5) * 2
//            );
//
//            entity.getNavigation().moveTo(hoverPos.x, hoverPos.y, hoverPos.z, 0.5);
//        }
//
//    }
//}
