package org.blackstamp.sleepychronicles.api.mobs.boss.strategies;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public class GroundStrategy implements NavigationStrategy {

    @Override
    public Vec3 calculateRetreatPos(BossMob boss, LivingEntity target, int radius){
        Mob entity = boss.getEntity();
        Vec3 playerPos = target.position();

        if(entity instanceof PathfinderMob pathfinderMob)
            return DefaultRandomPos.getPosAway(pathfinderMob,radius,radius,playerPos);

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
