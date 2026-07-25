package org.blackstamp.sleepychronicles.api.mobs.boss.strategies;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public class GroundStrategy implements NavigationStrategy {

    @Override
    public Vec3 calculateRetreatPos(BossMob boss, LivingEntity target, int radius){
        double xDiff = boss.getX() - target.getX();
        double zDiff = boss.getZ() - target.getZ();

        Vec3 retreatPos = new Vec3(xDiff,0,zDiff)
                .normalize()
                .scale(radius)
                .add(boss.position());

        int maxDepth = 5;

        for(int i = 1; i <= maxDepth; i++){
            BlockPos blockPos = new BlockPos((int) retreatPos.x, (int) retreatPos.y - i, (int) retreatPos.z);

            if(!boss.level().getBlockState(blockPos).isAir())
                return new Vec3(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
        }

        return null;
    }

    @Override
    public Vec3 calculateStrafePos(BossMob boss, LivingEntity target, boolean strafeLeft){
        Vec3 retreatDir = target.position().subtract(boss.position()).normalize();

        Vec3 retreatVec = retreatDir.cross(upVec).normalize();

        if(strafeLeft) retreatVec.scale(-1);

        return retreatVec;
    }
}
