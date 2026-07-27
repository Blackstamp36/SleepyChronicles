package org.blackstamp.sleepychronicles.api.mobs.boss.strategies;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public class FlightStrategy implements NavigationStrategy {

    @Override
    public Vec3 calculateRetreatPos(BossMob boss, LivingEntity target, double radius) {
        Vec3 retreatDir = boss.position().subtract(target.position()).normalize();
        Vec3 targetPos = boss.position().add(retreatDir.scale(radius));

        boolean isReachable = boss.level().noCollision(boss, boss.getBoundingBox().move(targetPos));

        return isReachable ? targetPos :  boss.position();
    }

    @Override
    public Vec3 calculateStrafePos(BossMob boss, LivingEntity target, boolean strafeLeft, double radius){
        Vec3 bossDir = target.position().subtract(boss.position()).normalize();
        Vec3 strafeDir = bossDir.cross(UP_VECTOR).normalize();

        if(strafeLeft) strafeDir = strafeDir.scale(-1);

        return boss.position().add(strafeDir).scale(radius);
    }

    @Override
    public void move(BossMob boss, double x, double y, double z, double speed){
        double dx = x - boss.getX();
        double dy = y - boss.getY();
        double dz = z - boss.getZ();

        Vec3 distanceVec = new Vec3(dx,dy,dz);

        if(distanceVec.lengthSqr() < 0.01){
            boss.setDeltaMovement(Vec3.ZERO);
            return;
        }

        Vec3 velocity = distanceVec.normalize().scale(speed * 0.1);

        boss.setDeltaMovement(velocity);

        LivingEntity target = boss.getTarget();
        boss.lookAt(target);
    }
}