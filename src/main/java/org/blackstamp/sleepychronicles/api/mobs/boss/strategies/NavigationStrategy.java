package org.blackstamp.sleepychronicles.api.mobs.boss.strategies;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public interface NavigationStrategy {
    Vec3 upVec = new Vec3(0,1,0);

    Vec3 calculateRetreatPos(BossMob boss, LivingEntity target, double radius);
    Vec3 calculateStrafePos(BossMob boss, LivingEntity target, boolean rightStrafe);
    void move(BossMob boss, double x, double y, double z, double speed);
}
