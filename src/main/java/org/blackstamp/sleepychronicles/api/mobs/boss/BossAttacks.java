package org.blackstamp.sleepychronicles.api.mobs.boss;

import net.minecraft.world.entity.LivingEntity;

public interface BossAttacks {
    void cast(BossMob boss, LivingEntity target);
}