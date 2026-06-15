package org.blackstamp.sleepychronicles.api.mobs.boss;

import net.minecraft.world.entity.LivingEntity;

public interface BossAttack {
    void cast(BossMob boss, LivingEntity target);
    double getMinDistance();
    double getMaxDistance();
    int getWindupTicks();
    int getRecoveryTicks();
    int getCooldownTicks();
}