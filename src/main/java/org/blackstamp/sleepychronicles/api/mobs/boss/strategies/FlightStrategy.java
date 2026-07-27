package org.blackstamp.sleepychronicles.api.mobs.boss.strategies;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public class FlightStrategy implements NavigationStrategy {

    @Override
    public Vec3 calculateRetreatPos(BossMob boss, LivingEntity target, int radius) {
        Vec3 retreatDir = target.position().subtract(boss.position()).normalize();
        Vec3 targetPos = boss.position().add(retreatDir.scale(radius));

        boolean isReachable = boss.level().noCollision(boss, boss.getBoundingBox().move(targetPos));

        if(isReachable) return targetPos;
        else return null;
    }

    @Override
    public Vec3 calculateStrafePos(BossMob boss, LivingEntity target, boolean strafeLeft){
        Vec3 retreatDir = target.position().subtract(boss.position()).normalize();

        Vec3 retreatVec = retreatDir.cross(upVec).normalize();

        if(strafeLeft) retreatVec.scale(-1);

        return retreatVec;
    }

    @Override
    public void move(BossMob boss, double x, double y, double z, double speed){
        boss.getMoveControl().setWantedPosition(x,y,z,speed);
    }
}