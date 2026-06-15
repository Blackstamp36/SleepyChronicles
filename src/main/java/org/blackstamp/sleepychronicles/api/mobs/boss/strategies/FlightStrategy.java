package org.blackstamp.sleepychronicles.api.mobs.boss.strategies;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public class FlightStrategy implements NavigationStrategy {

    @Override
    public Vec3 calculateRetreatPos(BossMob boss, LivingEntity target, int radius) {
        Mob entity = boss.getEntity();
        Vec3 retreatDir = target.position().subtract(entity.position()).normalize();
        Vec3 targetPos = entity.position().add(retreatDir.scale(radius));

        boolean isReachable = entity.level().noCollision(entity, entity.getBoundingBox().move(targetPos));

        if(isReachable) return targetPos;
        else return null;
    }

    @Override
    public Vec3 calculateStrafePos(BossMob boss, LivingEntity target, boolean strafeLeft){
        Mob entity = boss.getEntity();
        Vec3 retreatDir = target.position().subtract(entity.position()).normalize();

        Vec3 retreatVec = retreatDir.cross(upVec).normalize();

        if(strafeLeft) retreatVec.scale(-1);

        return retreatVec;
    }
}